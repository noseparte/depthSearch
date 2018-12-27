package com.noseparte.webmagic;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.noseparte.mongo.MongoDBConfig;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Date;

/**
 * Copyright © 2018 noseparte © BeiJing BoLuo Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2018-12-27 -- 11:27
 * @Version 1.0
 * @Description
 */
@Slf4j
@Component
public class NationalGeographyPipeline implements Pipeline {

    @Autowired
    private MongoDBConfig mongoDBConfig;

    @Override
    public void process(ResultItems resultItems, Task task) {
        log.info("爬虫结果入库开始");
        MongoDatabase database = mongoDBConfig.getDatabase("depth-search");
        MongoCollection<Document> collection = database.getCollection("mg_national_geography_repo");
        Document doc = new Document();
        doc.append("scenery",resultItems.get("scenery").toString());
        doc.append("geography",resultItems.get("geography").toString());
        doc.append("link",resultItems.get("link").toString());
        doc.append("title",resultItems.get("title").toString());
        doc.append("author",resultItems.get("author").toString());
        doc.append("authorUrl",resultItems.get("authorUrl").toString());
        doc.append("publishedTime",resultItems.get("publishedTime"));
        doc.append("createTime",new Date());
        doc.append("isDelete", Boolean.FALSE);
        collection.insertOne(doc);
        log.info("入库结束");
    }
}
