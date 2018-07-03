import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by yp-tc-m-7179 on 2018/7/3.
 *
 */
public class TestProcess implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);
    public void process(Page page) {
        Elements elements =page.getHtml().getDocument().getElementsByTag("script").eq(6);
        String data = elements.first().data();
        data = data.substring(data.indexOf("["),data.indexOf("]")+1);
        String[] arr = JSON.parseArray(data).toArray(new String[]{});
        for (String s : arr) {
            String[] fields = s.split("\\$");
            for (int i = 2; i < fields.length; i++) {
                System.out.println(fields[i]);
            }
        }

    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new TestProcess()).addUrl("http://www.credithunan.gov.cn:30816/publicity_hn/webInfo/punishmentProxy.do?startrecord=21&endrecord=30&perpage=10&totalRecord=25836").thread(5).run();
//        String str="[\"CF$C23C7E49E9BC366519D0CD3FA324790796D6D73D1BC8B754$未按规定核对、登记上网消费者的有效身份证件或者记录有关上网信息案$永兴县文化体育广电新闻出版局$2018-06-27$2018-06-27\", \"CF$C23C7E49E9BC366519D0CD3FA32479078F4735EE8C790CAA$接纳未成年人进入营业场所案$双峰县文化市场综合执法大队$2018-06-27$2018-06-27\", \"CF$C23C7E49E9BC366519D0CD3FA3247907CCB89148D187DD48$未按规定核对、登记上网消费者的有效身份证件或者记录有关上网信息案$永兴县文化体育广电新闻出版局$2018-06-27$2018-06-27\", \"CF$C23C7E49E9BC366519D0CD3FA324790766673CBCFF381F0A$接纳未成年人进入营业场所案$双峰县文化市场综合执法大队$2018-06-27$2018-06-27\", \"CF$C23C7E49E9BC366519D0CD3FA324790766B5A4EA3DCD038B$未按规定核对、登记上网消费者的有效身份证件或者记录有关上网信息案$永兴县文化体育广电新闻出版局$2018-06-27$2018-06-27\", \"CF$C23C7E49E9BC366519D0CD3FA3247907A7DFC451A63E1334$除国家法定节假日外，未成年人进入游戏区案$武冈市文化市场综合执法大队$2018-06-27$2018-06-27\", \"CF$C23C7E49E9BC366587B87A55F4CDA0294A255CF77577D7BC$未依照规定留存备查的材料案$娄底市文体广电新闻出版局$2018-06-26$2018-06-26\", \"CF$C23C7E49E9BC366587B87A55F4CDA0290DE51D748431E345$接纳未成年人进入营业场所案$道县文化市场综合执法局$2018-06-26$2018-06-26\", \"CF$C23C7E49E9BC366587B87A55F4CDA029AEABA1DD5832D6AA$接纳未成年人进入营业场所案$道县文化市场综合执法局$2018-06-26$2018-06-26\", \"CF$C23C7E49E9BC366587B87A55F4CDA02905D9175715AE90D1$接纳未成年人进入营业场所案$道县文化市场综合执法局$2018-06-26$2018-06-26\"]";
//        String[] arr = JSON.parseArray(str).toArray(new String[]{});
//        for (String s : arr) {
//            System.out.println(s);
//        }
    }
}
