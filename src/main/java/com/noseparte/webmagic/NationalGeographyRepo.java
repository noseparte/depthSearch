package com.noseparte.webmagic;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Copyright © 2018 noseparte © BeiJing BoLuo Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2018-12-26 -- 21:04
 * @Version 1.0
 * @Description     国家地理数据
 */
@Data
@Document(collection = "mg_national_geography_repo")
public class NationalGeographyRepo {

    @Id
    private String id;

    private String scenery;    // 景观   h1/text()
    private String geography;  // 地形   div[@class='article-left']/a/h3/text()
    private String link;       // 链接   (http://www.dili360\.com/travel/sight/\w+)
    private String title;      // 标题   div[@class='detail']/h3/a/text()
    private String author;     // 作者   a[@class='link']/text())
    private String authorUrl;  // 作者介绍   a[@class='link']/@abs:href

    private Date publishedTime; // 发布时间 p[@class='tips']/text()
    private Date createTime = new Date();
    private Boolean isDelete = Boolean.FALSE;

    public NationalGeographyRepo() {
    }

    public NationalGeographyRepo(String scenery, String geography, String link, String title, String author, String authorUrl, Date publishedTime) {
        this.scenery = scenery;
        this.geography = geography;
        this.link = link;
        this.title = title;
        this.author = author;
        this.authorUrl = authorUrl;
        this.publishedTime = publishedTime;
    }
}
