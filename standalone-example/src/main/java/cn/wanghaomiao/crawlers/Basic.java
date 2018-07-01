package cn.wanghaomiao.crawlers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.seimicrawler.xpath.JXDocument;
import org.springframework.beans.factory.annotation.Autowired;

import cn.wanghaomiao.dao.mybatis.MybatisStoreDAO;
import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;

/**
 * @author github.com/zhegexiaohuozi seimimaster@gmail.com
 * @since 2015/10/21.
 */
//@Crawler(name = "basic",queue = CustomSeimiQueue.class,useUnrepeated=false)
@Crawler(name = "basic",useUnrepeated=false)
public class Basic extends BaseSeimiCrawler {
	
	@Autowired
    private MybatisStoreDAO storeToDbDAO;
	
    @Override
    public String[] startUrls() {
        //两个是测试去重的
        try {
			return new String[]{"https://list.tmall.com/search_shopitem.htm?user_id=407910984&cat=2&spm=875.7931836/B.a2227oh.d100&oq=" + URLEncoder.encode("得力官方旗舰店","GBK") + "&from=mallfp..pc_1_searchbutton&ds=1&stype=search"};
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return null ;
    }

    @Override
    public void start(Response response) {
        JXDocument doc = response.document();
        try {
            List<Object> urls = doc.sel("//a[@class='sHe-shop']/@href");
            logger.info("{}", urls.size());
            for (Object s:urls){
            	String url = "http:" + s.toString() ;
            	System.out.println(url);
            	Request request = new Request();
                String[] urlPies = url.split("##");
                if (urlPies.length >= 2 && StringUtils.lowerCase(urlPies[1]).equals("post")) {
                    request.setHttpMethod(HttpMethod.POST);
                }
                request.setCrawlerName(crawlerName);
                request.setUrl(url);
                request.setCallBack("getTitle");
                push(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void getTitle(Response response){
        JXDocument doc = response.document();
        try {
        	logger.info("url:{}",response.getRealUrl()) ;
            //logger.info("url:{} {}", response.getUrl(), doc.sel("//h1[@class='postTitle']/a/text()|//a[@id='cb_post_title_url']/text()"));
            //do something
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(URLDecoder.decode("%B5%C3%C1%A6%B9%D9%B7%BD%C6%EC%BD%A2%B5%EA","GBK")) ;
		System.out.println(URLEncoder.encode("得力官方旗舰店","GBK")) ;
    }
}
