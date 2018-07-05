package com.tcredit.creditHunan.spider;

import com.tcredit.creditHunan.service.AdministrativePunishmentService;
import com.tcredit.creditHunan.service.SpiderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by yp-tc-m-7179 on 2018/7/4.
 *
 */
@Component
public class SpiderScheduler {
    @Resource
    private SpiderService spiderService;
    @Resource
    private AdministrativePunishmentService administrativePunishmentService;
    @Scheduled(fixedRate = 1000*300)//3秒执行一次
//    @Scheduled(cron= "0 0 3 * * *")//每天凌晨3点，执行一次
    public void run() throws Exception{
        final Date latestPublishDate = administrativePunishmentService.selectLatestPublishDate();
        spiderService.crawlAdministrativePunishment(latestPublishDate);
    }
}
