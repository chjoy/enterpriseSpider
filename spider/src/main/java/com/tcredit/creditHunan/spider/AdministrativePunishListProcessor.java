package com.tcredit.creditHunan.spider;

import com.alibaba.fastjson.JSON;
import com.tcredit.creditHunan.service.SpiderService;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yp-tc-m-7179 on 2018/7/3.
 * 行政处罚分页列表爬取
 */
public class AdministrativePunishListProcessor implements PageProcessor {

    //行政处罚第一页地址，包含部分数据，比如总条数等
    public static final String PUNISH_FIRST_PAGE_URL = "http://www.credithunan.gov.cn:30816/publicity_hn/webInfo/punishmentList.do";
    //行政处罚分页json数据请求地址
    public static final String PUNISH_LIST_URL = "http://www.credithunan.gov.cn:30816/publicity_hn/webInfo/punishmentProxy.do?id2=8B86B4A723181B63D0F867419678A2063B5889BA1C0CBE7001DFD3675795774D3C59DCA50C558BE4&perpage=1&totalRecord=1";
    //最多尝试翻多少页,理论上如果每天都爬的话，应该翻不了多少页
    public static final int OVER_PAGE_NUM = 100 * 1000;
    //单页条数,依赖网站所能提供的每页数据量，不能随便改
    public static final int NUM_PER_PAGE = 10;

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    public void process(Page page) {
        String dataListStr = page.getRawText();
        dataListStr = dataListStr.substring(dataListStr.indexOf("["), dataListStr.indexOf("]") + 1);
        if (StringUtils.isNotBlank(dataListStr)) {
            Spider contentSpider = Spider.create(new AdministrativePunishContentProcessor()).thread(5);
            String[] dataArr = JSON.parseArray(dataListStr).toArray(new String[]{});
            for (String dataStr : dataArr) {
                String[] fields = dataStr.split("\\$");
                //将发布时间和id保存到request
                Map<String, Object> extras = new HashMap<String, Object>();
                extras.put("publishTimeStr", fields[5].trim());
                extras.put("unionId", fields[1].trim());
                Request request = new Request(AdministrativePunishContentProcessor.PUNISH_CONTENT_URL + fields[1]).setExtras(extras);
                contentSpider.addRequest(request);
            }
            contentSpider.run();
        }
    }

    public Site getSite() {
        return site;
    }

}
