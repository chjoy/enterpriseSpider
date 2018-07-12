package com.tcredit.creditHunan.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.tcredit.creditHunan.service.SpiderService;
import com.tcredit.creditHunan.service.SupremeCourtDataService;
import com.tcredit.creditHunan.spider.AdministrativePunishListProcessor;
import net.sourceforge.tess4j.Tesseract;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.client.HttpClient;
import org.jsoup.Jsoup;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.utils.HttpConstant;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by yp-tc-m-7179 on 2018/7/4.
 *
 */
@Service
public class SpiderServiceImpl implements SpiderService {
    @Resource
    private ThreadPoolTaskExecutor taskExecutor;
    @Resource
    private SupremeCourtDataService supremeCourtDataService;


    public void crawlAdministrativePunishment(Date date) {
        if (date == null)
            crawlAll();
        else crawlPart(date);
    }

    public void crawlSupremeCourtData(String... names) throws Exception{
        final String pCode = supremeCourtDataService.getpCode();
        if (pCode==null) return;
        for (String name : names) {
            taskExecutor.execute(new Runnable() {
                public void run() {
                    //todo 多线程实现数据入库
                    System.out.println(pCode);
                }
            });
        }
    }

    private void crawlPart(Date publishDate) {
//        String publishDateStr = DateFormatUtils.format(publishDate, "yyyy-MM-dd");
        for (int i = 0; i < AdministrativePunishListProcessor.OVER_PAGE_NUM; i++) {
            String[] dataArr = null;
            try {
                String result = Jsoup.connect(AdministrativePunishListProcessor.PUNISH_LIST_URL + "&startrecord=" + i * AdministrativePunishListProcessor.NUM_PER_PAGE + "&endrecord=" + (AdministrativePunishListProcessor.NUM_PER_PAGE + i * AdministrativePunishListProcessor.NUM_PER_PAGE)).timeout(60000).post().toString();
                dataArr = JSON.parseArray(result.substring(result.indexOf("["), result.indexOf("]") + 1)).toArray(new String[]{});
            } catch (IOException e) {
                e.printStackTrace();
                //如果数据数组为空，那么说明没有数据了，不再继续
                return;
            }
            //分析每条数据的发布日期
            for (int j = 0; j < dataArr.length; j++) {
                String[] fields = dataArr[j].split("\\$");
                Date date = null;
                try {
                    date = DateUtils.parseDate(fields[5].trim(), "yyyy-MM-dd", "yyyy/MM/dd");
                } catch (ParseException e) {
                    e.printStackTrace();
                    return;
                }
                if (publishDate.compareTo(date) == 0) {
                    crawlPart(i * AdministrativePunishListProcessor.NUM_PER_PAGE + j-1);//不包含本条目，所以-1
                    return;
                }
            }
        }
    }

    private void crawlAll() {
        try {
            String data = Jsoup.connect(AdministrativePunishListProcessor.PUNISH_FIRST_PAGE_URL).timeout(60000).get()
                    .getElementsByTag("script").eq(6).first().data();
            data = data.substring(data.indexOf("({") + 1, data.indexOf("})") + 1);
//            int total = JSON.parseObject(data).getIntValue("totalRecord");//居然无法转换，神奇了
            int total = Integer.valueOf(data.substring(data.indexOf("totalRecord:") + "totalRecord:".length(), data.indexOf(",dataStore:")));
            crawlPart(total);
        } catch (Exception e) {
            //todo 处理异常
            e.printStackTrace();
        }
    }

    private void crawlPart(int index) {
        if (index==0) return;
//        index = 8700;//网页有bug，从页面读取的数据来看根本没有20000+的数据
        Spider spider = Spider.create(new AdministrativePunishListProcessor()).thread(5);
        for (int i = 0; i < index / AdministrativePunishListProcessor.NUM_PER_PAGE; i++) {
            Request request = new Request(AdministrativePunishListProcessor.PUNISH_LIST_URL + "&startrecord=" + (index - i * AdministrativePunishListProcessor.NUM_PER_PAGE - AdministrativePunishListProcessor.NUM_PER_PAGE + 1) + "&endrecord=" + (index - i * AdministrativePunishListProcessor.NUM_PER_PAGE));
            request.setMethod(HttpConstant.Method.POST);
            spider.addRequest(request);
        }
        Request request = new Request(AdministrativePunishListProcessor.PUNISH_LIST_URL + "&startrecord=1" + "&endrecord=" + index % AdministrativePunishListProcessor.NUM_PER_PAGE).setMethod(HttpConstant.Method.POST);
        spider.addRequest(request);
        spider.run();
    }
}
