package com.tcredit.creditHunan.service;

/**
 * Created by yp-tc-m-7179 on 2018/7/12.
 *
 */
public interface SupremeCourtDataService {
    //获取验证码的链接
    String CAPTCHA_GET_URL = "http://zxgk.court.gov.cn/shixin/captcha.do?captchaId=%s";
    //失信信息法院查询链接，返回的是html格式
    String SHIXIN_SEARCH_URL = "http://zxgk.court.gov.cn/shixin/findDis?pName=%s&pCode=%s&captchaId=%s";
    //失信信息获取，返回json格式
    String SHIXIN_DETAIL_URL = "http://zxgk.court.gov.cn/shixin/disDetail?id=%s&pCode=%s&captchaId=%s";

    String getpCode() throws Exception;

    boolean pCodeIsRight(String pCode,String token);
}
