package com.tcredit.creditHunan.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.tcredit.creditHunan.service.SupremeCourtDataService;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

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
        String token = "supremeCourt";
//            FileOutputStream fileOutputStream = new FileOutputStream("/Users/yp-tc-m-7179/Downloads/test.png");
//            byte[] bytes = new byte[1024];
//            int n = -1;
//            while ((n = inputStream.read(bytes,0,bytes.length)) != -1) {
//                //写入相关文件
//                fileOutputStream.write(bytes, 0, n);
//            }
        String pCode = null;
        for (int i = 0; i < TRY_TIMES; i++) {
            BufferedImage image = ImageIO.read(HttpRequest.get(String.format(SupremeCourtDataService.CAPTCHA_GET_URL, token)).execute().bodyStream());
            if (image == null) return null;
            pCode = new Tesseract().doOCR(image).trim().toLowerCase();
            //todo 校验验证码准确性
            if (pCodeIsRight(pCode, token))
                break;
        }
        return pCode;
    }

    public boolean pCodeIsRight(String pCode, String token) {
        //遍历样本，能获取到数据，说明验证码正确，接下来一段时间内就可以用这个验证码查询数据
        for (String sampleId : SAMPLE_IDS) {
            //目前看到的验证码都是字符+数字，长度4位
            if (pCode.length() == 4 &&
                    !"{}".equalsIgnoreCase(HttpUtil.get(String.format(SHIXIN_DETAIL_URL, sampleId, pCode, token)).trim()))
                return true;
        }
        return false;
    }
}
