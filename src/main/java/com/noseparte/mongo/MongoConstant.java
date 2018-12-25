package com.noseparte.mongo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Copyright © 2018 noseparte © BeiJing BoLuo Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile --
 * @Version 1.0
 * @Description
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MongoConstant {

    public String host;
    public Integer port;
    public String username;
    public String password;

}
