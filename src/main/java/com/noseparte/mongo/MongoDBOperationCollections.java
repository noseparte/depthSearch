package com.noseparte.mongo;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2018 noseparte © BeiJing BoLuo Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile --
 * @Version 1.0
 * @Description
 */
@Slf4j
@Component
public class MongoDBOperationCollections {

    @Autowired
    private MongoDBConfig mongoDBConfig;

    public void getDate(){
        List<Document> results = new ArrayList<>();
        MongoDatabase database = mongoDBConfig.getDatabase("h5wx_login_server");
        MongoCollection<Document> collection = database.getCollection("player_tree_node_info");
        FindIterable<Document> documents = collection.find();
        MongoCursor<Document> mongoCursor = documents.iterator();
        while(mongoCursor.hasNext()){
            Document doc = mongoCursor.next();
            results.add(doc);
            log.info("doc: {}",doc.toString());
        }
    }


    public static void main(String[] args) {
         new MongoDBOperationCollections().getDate();
    }


}
