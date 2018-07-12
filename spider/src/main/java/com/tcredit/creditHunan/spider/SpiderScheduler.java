package com.tcredit.creditHunan.spider;

import com.tcredit.creditHunan.service.AdministrativePunishmentService;
import com.tcredit.creditHunan.service.SpiderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by yp-tc-m-7179 on 2018/7/4.
 */
@Component
public class SpiderScheduler {

    @Resource
    private ThreadPoolTaskExecutor taskExecutor;
    @Resource
    private SpiderService spiderService;
    @Resource
    private AdministrativePunishmentService administrativePunishmentService;

    @Scheduled(fixedRate = 1000 * 60 * 120)//120分钟执行一次
//    @Scheduled(cron= "0 0 3 * * *")//每天凌晨3点，执行一次
    public void run() throws Exception {

        //信用湖南-行政处罚信息爬取 http://www.credithunan.gov.cn:30816/publicity_hn/webInfo/punishmentList.do
//        taskExecutor.execute(new Runnable() {
//            public void run() {
//                final Date latestPublishDate = administrativePunishmentService.selectLatestPublishDate();
//                spiderService.crawlAdministrativePunishment(latestPublishDate);
//            }
//        });

        //最高人民法院数据爬取
        taskExecutor.execute(new Runnable() {
            public void run() {
                try {
                    spiderService.crawlSupremeCourtData("湖南湘波房地产开发有限公司");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
