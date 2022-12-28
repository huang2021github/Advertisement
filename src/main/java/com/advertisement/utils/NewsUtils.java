package com.advertisement.utils;

import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
import cn.edu.hfut.dmic.contentextractor.News;
import com.advertisement.domain.NewsResponse;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻采集工具
 */
public class NewsUtils {


    public static List<NewsResponse> getNewsList() throws Exception{
        //获取网易新闻首页
        News news =  ContentExtractor.getNewsByUrl("https://news.163.com/");
        //获取首页 element
        Element element = news.getContentElement();
        //获取新闻列表
        Elements newsdata_items = element.getElementsByClass("newsdata_item");

        List<NewsResponse> newsResponses = new ArrayList<>();
        for (Element newsdata_item : newsdata_items) {
            //获取所有div
            Elements div = newsdata_item.getElementsByTag("div");
            for (Element element1 : div) {
                if (newsResponses.size()>=20) break;
                NewsResponse newsResponse = new NewsResponse();
                //获取新闻链接
                String href = element1.getElementsByTag("a").eq(0).attr("href");
                newsResponse.setLinked(href);
                newsResponses.add(newsResponse);
            }
        }
        for (NewsResponse item : newsResponses) {
            News newsByUrl = ContentExtractor.getNewsByUrl(item.getLinked());
            //获取整个新闻的 html代码
            Element contentElement = newsByUrl.getContentElement();
            //获取标题
            String title = newsByUrl.getTitle();
            item.setTitle(title);
            //获取文章中的第一个图片
            Elements imgs = contentElement.getElementsByTag("img");
            if (imgs != null && imgs.size() > 0) {
                Element loopElement = null;
                for (int i = 0; i < imgs.size(); i++) {
                    loopElement = imgs.get(i);
                    item.setTitleImage(loopElement.attr("src"));
                    break;
                }
            }
        }
        return newsResponses;
    }
    public static String getContentHtmlByUrl(String url) throws Exception{
        News newsByUrl = ContentExtractor.getNewsByUrl(url);
        //获取整个新闻的 html代码
        Element contentElement = newsByUrl.getContentElement();
        //获取内容的html
        Elements post_body = contentElement.getElementsByClass("post_body");
        if (post_body!=null){
            return post_body.html();
        }
        return "";
    }
}
