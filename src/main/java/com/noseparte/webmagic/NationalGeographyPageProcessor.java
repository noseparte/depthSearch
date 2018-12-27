package com.noseparte.webmagic;

import com.alibaba.fastjson.JSON;
import com.noseparte.base.utils.DateFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright © 2018 noseparte © BeiJing BoLuo Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2018-12-25 -- 17:33
 * @Version 1.0
 * @Description     Webmagic 网络爬虫
 */
@Slf4j
@Component
public class NationalGeographyPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("(http://www.dili360.com/travel/sight/\\w+)").all());
        page.putField("link", page.getUrl());
        page.putField("scenery", page.getHtml().xpath("//h1/text()").toString());
        page.putField("geography", page.getHtml().xpath("//div[@class='article-left']/a/h3/text()").toString());
        page.putField("title", page.getHtml().xpath("//div[@class='detail']/h3/a/text()").toString());
        page.putField("author", page.getHtml().xpath("//a[@class='link']/text()").toString());
        page.putField("authorUrl", page.getHtml().xpath("//a[@class='link']/@abs:href").toString());
        String tips = page.getHtml().xpath("//p[@class='tips']/text()").toString();
        if (tips == null){
            //skip this page
            page.setSkip(true);
        }else{
            log.info("tips=================== ,{},length,{}",tips,tips.length());
            String cnDate = tips.trim().substring(tips.length() - 13, tips.length()-2);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date publishedTime = null;
            try {
                publishedTime = DateFormat.ParseDate(cnDate);
            } catch (Exception e) {
                log.error("发布时间格式化失败, publish error,{}",e.getMessage());
            }
            page.putField("publishedTime", publishedTime);
        }
        ResultItems resultItems = page.getResultItems();
        log.info(JSON.toJSONString(resultItems));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new NationalGeographyPageProcessor())
                .addUrl("http://www.dili360.com/travel/sight/")
                .addPipeline(new NationalGeographyPipeline())
//                .addPipeline(new ConsolePipeline())
//                .addPipeline(new JsonFilePipeline("D:\\data\\webmagic\\"))
                .thread(5)
                .run();
    }
}
