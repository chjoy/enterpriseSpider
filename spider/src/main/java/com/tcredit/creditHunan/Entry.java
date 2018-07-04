package com.tcredit.creditHunan;

import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by yp-tc-m-7179 on 2018/7/4.
 *
 */
public class Entry {
    public static void main(String[] args) throws Exception{
        new ClassPathXmlApplicationContext("app.xml");
    }
}
