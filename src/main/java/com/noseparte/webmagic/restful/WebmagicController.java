package com.noseparte.webmagic.restful;

import com.noseparte.base.bean.Response;
import com.noseparte.base.controller.BaseController;
import com.noseparte.webmagic.NationalGeographyPageProcessor;
import com.noseparte.webmagic.NationalGeographyPipeline;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

/**
 * Copyright © 2018 noseparte © BeiJing BoLuo Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2018-12-17 -- 17:45
 * @Version 1.0
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("/api/webmagic")
public class WebmagicController extends BaseController {

    @Autowired
    private NationalGeographyPipeline nationalGeographyPipeline;

    @PostMapping("/spider")
    public Response spider(@RequestParam("spiderUrl") String spiderUrl){
        log.info("使用webmagic网络爬虫抓取数据开始");
        try{
            Spider.create(new NationalGeographyPageProcessor())
                    .addUrl(spiderUrl)
                    .addPipeline(nationalGeographyPipeline)
//                .addPipeline(new ConsolePipeline())
//                .addPipeline(new JsonFilePipeline("D:\\data\\webmagic\\"))
                    .thread(5)
                    .run();
            log.info("爬取结束");
            return getResponse().success();
        }catch (Exception e){
            log.error("爬取数据失败,errorMsg,{}",e.getMessage());
            return getResponse().failure(e.getMessage());
        }
    }









}
