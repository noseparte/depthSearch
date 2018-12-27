package com.noseparte.lucene;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.noseparte.mongo.MongoDBConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.FileSystems;

/**
 * Copyright © 2018 noseparte © BeiJing BoLuo Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2018-12-24 -- 16:08
 * @Version 1.0
 * @Description     更新Lucene的索引库
 */
@Slf4j
@Component
public class LuceneIndexesFactory {

    public static String INDEX_PATH = "";

    /** 分配lucene索引库的磁盘地址*/
    static {
        String os_name = System.getProperty("os.name");
        if (os_name.toLowerCase().equals("win")) {
            INDEX_PATH = "D:\\data\\lucene\\lucene-db";
        }else
            INDEX_PATH = "/data/lucene/lucene-db";
    }

    @Autowired
    private MongoDBConfig mongoDBConfig;

    /**
    // 原始文档 ==> 创建索引
    // 1.获得文档; 2.构架文档对象; 3.分析文档(分词); 4.创建索引;

     * 见数据库文件生成本地索引文件，创建索引
     * {@link // https://www.cnblogs.com/dacc123/p/8228298.html}
     * {@linkplain  // 索引的创建 IndexWriter 和索引速度的优化 }
     */
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void updateIndex(){
    	IndexWriter indexWriter = null;
    	try {
    		Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(INDEX_PATH));
    		// 根据空格和符号来完成分词，还可以完成数字、字母、E-mail地址、IP地址以及中文字符的分析处理，还可以支持过滤词表，用来代替StopAnalyzer能够实现的过滤功能。
            //Analyzer analyzer = new StandardAnalyzer();
    		// 实现了以词典为基础的正反向全切分，以及正反向最大匹配切分两种方法。IKAnalyzer是第三方实现的分词器，继承自Lucene的Analyzer类，针对中文文本进行处理。
			Analyzer analyzer = new IKAnalyzer(true);
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			indexWriterConfig.setRAMBufferSizeMB(16.0);
			indexWriter = new IndexWriter(directory, indexWriterConfig);
			long deleteCount = indexWriter.deleteAll();	// 清除以前的索引
			log.info("索引库清除完毕，清除数量为,deleteCount,{}",deleteCount);

            MongoDatabase database = mongoDBConfig.getDatabase("h5wx_login_server");
            MongoCollection<Document> collection = database.getCollection("player_tree_node_info");
			FindIterable<Document> Documents = collection.find();
			for(Document cursor : Documents) {
                org.apache.lucene.document.Document document = new org.apache.lucene.document.Document();
				document.add(new Field("id", cursor.getObjectId("_id").toString(), TextField.TYPE_STORED));
				document.add(new Field("playerId", cursor.getString("playerId"), TextField.TYPE_STORED));
				indexWriter.addDocument(document);
			}
		} catch (Exception e) {
			log.error("创建索引失败。 errorMsg,{}",e.getMessage());
		} finally {
			try {
				if (null != indexWriter) {
					indexWriter.close();
				}
			} catch (Exception ex) {
				log.error("索引流关闭失败,error,{}",ex.getMessage());
			}
		}
    }

	public static IndexSearcher init() throws IOException {
		IndexSearcher indexSearcher =null;
		AnalyzingInfixSuggester suggester =null;
		if(indexSearcher == null) {
			// 1、创建Directory
			Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(INDEX_PATH));
			// 2、创建IndexReader
			DirectoryReader directoryReader = DirectoryReader.open(directory);
			// 3、根据IndexReader创建IndexSearch
			indexSearcher = new IndexSearcher(directoryReader);
		}
		return indexSearcher;
	}


}
