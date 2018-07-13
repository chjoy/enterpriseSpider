package com.tcredit.creditHunan.service.impl;

/**
 * Created by yp-tc-m-7179 on 2018/7/13.
 *
 */
public class SupremeCourtDataServiceImplTest {
    @org.junit.Test
    public void getId() throws Exception {
        SupremeCourtDataServiceImpl supremeCourtDataService = new SupremeCourtDataServiceImpl();
        String str = supremeCourtDataService.getId("湖南湘波房地产开发有限公司","shvw");
        System.out.println(str);
    }

}