package com.tcredit.creditHunan.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by yp-tc-m-7179 on 2018/7/3.
 *  行政处罚详细数据爬取
 */
public class AdministrativePunishContentProcessor implements PageProcessor{
    public static final String PUNISH_CONTENT_URL = "http://www.credithunan.gov.cn:30816/publicity_hn/webInfo/punishmentDetail.do?id=";

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    public void process(Page page) {
        System.out.println(1);

    }

    public Site getSite() {
        return site;
    }

}
