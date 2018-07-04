package com.tcredit.creditHunan.service.impl;

import com.alibaba.fastjson.JSON;
import com.tcredit.creditHunan.service.SpiderService;
import com.tcredit.creditHunan.spider.AdministrativePunishListProcessor;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;

import java.io.IOException;
import java.util.Date;

/**
 * Created by yp-tc-m-7179 on 2018/7/4.
 *
 */
@Service
public class SpiderServiceImpl implements SpiderService {


    public void crawlAdministrativePunishment(Date date) {
        if (date == null)
            crawlAll();
        else crawlPart(date);
    }

    private void crawlPart(Date publishDate) {
        String publishDateStr = DateFormatUtils.format(publishDate, "yyyy-MM-dd");
        for (int i = 0; i < AdministrativePunishListProcessor.OVER_PAGE_NUM; i++) {
            String[] dataArr = null;
            try {
                dataArr = JSON.parseObject(Jsoup.connect(AdministrativePunishListProcessor.PUNISH_LIST_URL + "&startrecord=" + i * AdministrativePunishListProcessor.NUM_PER_PAGE + "&endrecord=" + AdministrativePunishListProcessor.NUM_PER_PAGE + i * AdministrativePunishListProcessor.NUM_PER_PAGE).timeout(60000).get().data()).getJSONArray("dataStore").toArray(new String[]{});
            } catch (IOException e) {
                e.printStackTrace();
                //如果数据数组为空，那么说明没有数据了，不再继续
                return;
            }
            //分析每条数据的发布日期
            for (int j = 0; j < dataArr.length; j++) {
                String[] fields = dataArr[j].split("\\$");
                if (fields[5].trim().equalsIgnoreCase(publishDateStr)) {
                    crawlPart(i * AdministrativePunishListProcessor.NUM_PER_PAGE + j);
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
        index=800;//网页有bug，从页面读取的数据来看根本没有20000+的数据
        Spider spider = Spider.create(new AdministrativePunishListProcessor()).thread(5);
        if (index < AdministrativePunishListProcessor.NUM_PER_PAGE)
            spider.addUrl(AdministrativePunishListProcessor.PUNISH_LIST_URL + "&startrecord=0" + "&endrecord=" + index).run();
        else {
            for (int i = 0; i < index / AdministrativePunishListProcessor.NUM_PER_PAGE; i++) {
                Request request = new Request(AdministrativePunishListProcessor.PUNISH_LIST_URL + "&startrecord=" + (index - i * AdministrativePunishListProcessor.NUM_PER_PAGE - AdministrativePunishListProcessor.NUM_PER_PAGE + 1) + "&endrecord=" + (index - i * AdministrativePunishListProcessor.NUM_PER_PAGE));
                spider.addRequest(request);
            }
            spider.run();
        }
    }
}