package com.tcredit.creditHunan.spider;

import com.tcredit.creditHunan.Entry;
import com.tcredit.creditHunan.enums.DataStateEnum;
import com.tcredit.creditHunan.model.AdministrativePunishment;
import com.tcredit.creditHunan.service.AdministrativePunishmentService;
import org.apache.commons.lang3.time.DateUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by yp-tc-m-7179 on 2018/7/3.
 * 行政处罚详细数据爬取
 */
public class AdministrativePunishContentProcessor implements PageProcessor {
    public static final String PUNISH_CONTENT_URL = "http://www.credithunan.gov.cn:30816/publicity_hn/webInfo/punishmentDetail.do?id=";

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    public void process(Page page) {
        AdministrativePunishment administrativePunishment = new AdministrativePunishment();
        //创建时间
        administrativePunishment.setCreateTime(new Date());
        //数据有效性
        administrativePunishment.setDataState(DataStateEnum.ACTIVE.getValue());

        administrativePunishment.setUnionId((String) page.getRequest().getExtra("unionId"));
        //发布时间，字符串类型
        String publishTimeStr = (String) page.getRequest().getExtra("publishTimeStr");
        administrativePunishment.setPublishTimeStr(publishTimeStr);
        //发布时间
        try {
            administrativePunishment.setPublishTime(DateUtils.parseDate(publishTimeStr, "yyyy-MM-dd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //案件名称
        administrativePunishment.setTitle(page.getHtml().xpath("//td[@class='listf2']/text()").get().trim());
        administrativePunishment.setPunishNo(page.getHtml().xpath("//table[@class='xzcf_bg']/tbody/tr[1]/td[@class='xzcf_xx']/text()").get().trim());
        //行政相对人
        administrativePunishment.setSubject(page.getHtml().xpath("//table[@class='xzcf_bg']/tbody/tr[2]/td[@class='xzcf_xx']/text(1)").get().trim());
        administrativePunishment.setLegalRepresentative(page.getHtml().xpath("//table[@class='xzcf_bg']/tbody/tr[2]/td[@class='xzcf_xx']/text(2)").get().trim());
        //执法部门
        administrativePunishment.setLegalOperationDepartment(page.getHtml().xpath("//table[@class='xzcf_bg']/tbody/tr[3]/td[@class='xzcf_xx']/text()").get().trim());
        //做出处罚的时间
        String punishTimeStr = page.getHtml().xpath("//table[@class='xzcf_bg']/tbody/tr[4]/td[@class='xzcf_xx']/text()").get().trim();
        administrativePunishment.setPunishTimeStr(punishTimeStr);
        try {
            administrativePunishment.setPunishTime(DateUtils.parseDate(punishTimeStr, "yyyy-MM-dd", "yyyy/MM/dd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //行政处罚决定书（全文或摘要）
        administrativePunishment.setContent(page.getHtml().xpath("//td[@class='xzcf_jds']/text()").get().trim());
        //保存
        Entry.context.getBean(AdministrativePunishmentService.class).insert(administrativePunishment);
    }

    public Site getSite() {
        return site;
    }

}
