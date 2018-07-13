package com.tcredit.creditHunan.service;

/**
 * Created by yp-tc-m-7179 on 2018/7/12.
 *
 */
public interface SupremeCourtDataService {
    //默认的标示位，法院网站查询数据必须带这个（网站中使用的md5的一个字符串，用这个默认的不晓得会不会出问题）
    String DEFAULT_CAPTCHA_ID = "supremeCourt";
    //获取验证码的链接
    String CAPTCHA_GET_URL = "http://zxgk.court.gov.cn/shixin/captcha.do?captchaId=%s";
    //失信信息法院查询链接，返回的是html格式
    String SHIXIN_SEARCH_URL = "http://zxgk.court.gov.cn/shixin/findDis?pName=%s&pCode=%s&captchaId=%s&pProvince=0";
    //失信信息获取，返回json格式
    String SHIXIN_DETAIL_URL = "http://zxgk.court.gov.cn/shixin/disDetail?id=%s&pCode=%s&captchaId=%s";

    String getpCode() throws Exception;

    boolean pCodeIsRight(String pCode,String token);

    //获取该对象在法院库中的id
    String[] getId (String name, String pCode) throws Exception;

    boolean saveData(String[] idArr,String pCode);
}
