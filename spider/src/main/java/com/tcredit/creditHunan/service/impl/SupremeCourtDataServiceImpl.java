package com.tcredit.creditHunan.service.impl;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.tcredit.creditHunan.service.SupremeCourtDataService;
import net.sourceforge.tess4j.Tesseract;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yp-tc-m-7179 on 2018/7/12.
 *
 */
@Service
public class SupremeCourtDataServiceImpl implements SupremeCourtDataService {

    //尝试获取验证码的次数
    private static final int TRY_TIMES = 100;

    //样本
    private static final String[] SAMPLE_IDS = {"100000000", "700000000", "702465087", "702465123"};

    public String getpCode() throws Exception {
        String CAPTCHA_ID = "supremeCourt";
        String pCode = null;
        for (int i = 0; i < TRY_TIMES; i++) {
            BufferedImage image = ImageIO.read(HttpRequest.get(String.format(SupremeCourtDataService.CAPTCHA_GET_URL, CAPTCHA_ID)).execute().bodyStream());
            if (image == null) return null;
            pCode = new Tesseract().doOCR(image).trim().toLowerCase();
            //todo 校验验证码准确性
            if (pCodeIsRight(pCode, CAPTCHA_ID))
                break;
        }
        return pCode;
    }

    public boolean pCodeIsRight(String pCode, String token) {
        //遍历样本，能获取到数据，说明验证码正确，接下来一段时间内就可以用这个验证码查询数据
        for (String sampleId : SAMPLE_IDS) {
            //目前看到的验证码都是字符+数字，长度4位
            if (pCode.length() == 4 &&
                    !responseIsBlank(HttpUtil.get(String.format(SHIXIN_DETAIL_URL, sampleId, pCode, token)).trim()))
                return true;
        }
        return false;
    }

    public String[] getId(String name, String pCode) throws Exception{
        List<String> idList = new ArrayList<String>();
        String url = String.format(SHIXIN_SEARCH_URL,URLEncoder.encode(name,"utf-8"),pCode,DEFAULT_CAPTCHA_ID);
        String dataHtml = HttpUtil.get(url);
        Elements elements = Jsoup.parse(dataHtml).select("tbody tr");
        for (int i = 1; i < elements.size(); i++) {
            idList.add(elements.get(i).select("td:eq(4) a").attr("id"));
        }
        return idList.toArray(new String[]{});
    }

    public boolean saveData(String[] idArr,String pCode) {
        for (String id : idArr) {
            String resStr = HttpUtil.get(String.format(SHIXIN_DETAIL_URL, id, pCode,DEFAULT_CAPTCHA_ID)).trim();
            if (responseIsBlank(resStr)){
                //todo 更新验证码
//                getpCode()
            }
        }

        return false;
    }

    private boolean responseIsBlank(String resStr){
        return "{}".equalsIgnoreCase(resStr.trim());
    }

}
