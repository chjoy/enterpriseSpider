package com.tcredit.creditHunan;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by yp-tc-m-7179 on 2018/7/3.
 *  行政许可爬取
 */
public class AdministrativeLicensingProcessor implements PageProcessor{
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    public void process(Page page) {

    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        Spider spider = Spider.create(new AdministrativeLicensingProcessor()).thread(5);

    }
}
