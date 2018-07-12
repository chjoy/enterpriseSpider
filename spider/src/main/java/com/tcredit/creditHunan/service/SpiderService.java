package com.tcredit.creditHunan.service;

import java.util.Date;

/**
 * Created by yp-tc-m-7179 on 2018/7/4.
 *
 */
public interface SpiderService {

    /**
     * 爬取信用湖南行政处罚数据
     * @param date
     */
    void crawlAdministrativePunishment(Date date);

    void crawlSupremeCourtData(String... names) throws Exception;
}
