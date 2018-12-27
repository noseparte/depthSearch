package com.noseparte.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

    /**
     * Copyright © 2018 noseparte © BeiJing BoLuo Network Technology Co. Ltd.
     *
     * @Author Noseparte
     * @Compile 2018-12-20 -- 17:06
     * @Version 1.0
     * @Description
     */
    @Slf4j
    @Configuration
    public class MongoDBConfig {

        @Autowired
        private MongoConstant mongoConstant;

    public static MongoClient client = null;
//    public static MongoDatabase database = null;
    public static ServerAddress serverAddress = null;
//    public static MongoCredential credential = null;
    public static List<ServerAddress> serverAddresses = new ArrayList<>();


    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Mongodb driver connection
     * {@linkplain //Since MongoDB 3.0 begin support mongoClient client,Connected mongoDB DataBase(CRUD) }
     */
    public void getMongoConn() {
        try {
            // Mongo服务的地址 类似JDBC (Java DataBase Connectivity)
            serverAddress = new ServerAddress(mongoConstant.getHost(), mongoConstant.getPort());
            serverAddresses.add(serverAddress);
            // MongoCredential 连接mongo的凭证,需注意password为{ final char[] password }
//			client = new MongoClient(serverAddresses);
            client = MongoClients.create(
                    MongoClientSettings.builder()
                            .applyToClusterSettings(builder ->
                                    builder.hosts(serverAddresses))
//                            .credential(credential)
                            .build());
        } catch (Exception e) {
            log.info("mongodb driver connected failure : errorMsg,{}",e.getMessage());
        }
//        if(null != client) {
//            database = client.getDatabase("login");
//        }
    }

    public MongoClient getMongoClient() {
        if(null == client) {
            getMongoConn();
        }
        return client;
    }

    /**
     * 获取database
     *
     * @return
             */
    public MongoDatabase getDatabase(String database) {
        if (null == client) {
            getMongoConn();
        }
        return client.getDatabase(database);
    }






}
