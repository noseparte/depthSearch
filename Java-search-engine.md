## 1、Java 全文搜索引擎框架 Lucene

毫无疑问，Lucene是目前最受欢迎的Java全文搜索框架，准确地说，它是一个全文检索引擎的架构，提供了完整的查询引擎和索引引擎，部分文本分析引擎。Lucene为开发人员提供了相当完整的工具包，可以非常方便地实现强大的全文检索功能。下面有几款搜索引擎框架也是基于Lucene实现的。

官方网站：http://lucene.apache.org/
## 2、开源Java搜索引擎Nutch

Nutch 是一个开源Java实现的搜索引擎。它提供了我们运行自己的搜索引擎所需的全部工具。包括全文搜索和Web爬虫。

利用Nutch，你可以做到以下这些功能：

    每个月取几十亿网页
    为这些网页维护一个索引
    对索引文件进行每秒上千次的搜索
    提供高质量的搜索结果
    以最小的成本运作

官方网站：http://nutch.apache.org/
## 3、分布式搜索引擎 ElasticSearch

ElasticSearch就是一款基于Lucene框架的分布式搜索引擎，并且也是一款为数不多的基于JSON进行索引的搜索引擎。ElasticSearch特别适合在云计算平台上使用。

官方网站：http://www.elasticsearch.org/
## 4、实时分布式搜索引擎 Solandra

Solandra 是一个实时的分布式搜索引擎，基于 Apache Solr 和 Apache Cassandra 构建。

其特性如下：

    支持Solr的大多数默认特性 (search, faceting, highlights)
    数据复制，分片，缓存及压缩这些都由Cassandra来进行
    Multi-master (任意结点都可供读写)
    实时性高，写操作完成即可读到
    Easily add new SolrCores w/o restart across the cluster 轻松添加及重启结点

官方网站：https://github.com/tjake/Solandra
## 5、IndexTank

IndexTank是一套基于Java的索引-实时全文搜索引擎实现，IndexTank有以下几个特点：

    索引更新实时生效
    地理位置搜索
    支持多种客户端语言
    Ruby, Rails, Python, Java, PHP, .NET & more!
    支持灵活的排序与评分控制
    支持自动完成
    支持面搜索（facet search）
    支持匹配高亮
    支持海量数据扩展（Scalable from a personal blog to hundreds of millions of documents! ）
    支持动态数据

官方网站：https://github.com/linkedin/indextank-engine
## 6、搜索引擎 Compass

Compass是一个强大的,事务的,高性能的对象/搜索引擎映射(OSEM:object/search engine mapping)与一个Java持久层框架.Compass包括:

    搜索引擎抽象层(使用Lucene搜索引荐)
    OSEM (Object/Search Engine Mapping) 支持
    事务管理
    类似于Google的简单关键字查询语言
    可扩展与模块化的框架
    简单的API

官方网站：http://www.compass-project.org/
## 7、Java全文搜索服务器 Solr

Solr也是基于Java实现的，并且是基于Lucene实现的，Solr的主要特性包括：高效、灵活的缓存功能，垂直搜索功能，高亮显示搜索结果。值得注意的是，Solr还提供一款很棒的Web界面来管理索引的数据。

官方网站：http://lucene.apache.org/solr/
## 8、Lucene图片搜索 LIRE

LIRE是一款基于Java的图片搜索框架，其核心也是基于Lucene的，利用该索引就能够构建一个基于内容的图像检索(content- based image retrieval，CBIR)系统，来搜索相似的图像。

官方网站：http://www.Semanticmetadata.net/lire/
## 9、全文本搜索引擎 Egothor

Egothor是一个用Java编写的开源而高效的全文本搜索引擎。借助Java的跨平台特性，Egothor能应用于任何环境的应用，既可配置为单独的搜索引擎，又能用于你的应用作为全文检索之用。

官方网站：http://www.egothor.org/cms/