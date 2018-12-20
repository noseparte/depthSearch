package com.noseparte.mongo.restful;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.noseparte.base.bean.Response;
import com.noseparte.base.controller.BaseController;
import com.noseparte.mongo.MongoDBConfig;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2018 noseparte © BeiJing BoLuo Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2018-12-20 -- 19:01
 * @Version 1.0
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("/api/mongo")
public class MongoController extends BaseController {

    @Autowired
    private MongoDBConfig mongoDBConfig;

    @PostMapping("/fetch-data")
    public Response gettingData(){
        List<Document> results = new ArrayList<>();
        try {
            MongoDatabase database = mongoDBConfig.getDatabase("h5wx_login_server");
            MongoCollection<Document> collection = database.getCollection("player_tree_node_info");
            FindIterable<Document> documents = collection.find();
            MongoCursor<Document> mongoCursor = documents.iterator();
            while(mongoCursor.hasNext()){
                Document doc = mongoCursor.next();
                results.add(doc);
            }
            return getResponse().success(results);
        } catch (Exception e) {
            log.error("Failed to fetch data from the mongo driver, errorMsg,{}",e.getMessage());
            return getResponse().failure(e.getMessage());
        }
    }


}
