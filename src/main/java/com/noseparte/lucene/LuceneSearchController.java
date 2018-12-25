package com.noseparte.lucene;

import com.noseparte.base.bean.Response;
import com.noseparte.base.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2018 noseparte © BeiJing BoLuo Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2018-12-25 -- 11:15
 * @Version 1.0
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("api/lucene")
public class LuceneSearchController extends BaseController {

    @PostMapping("/search")
    public Response retrieval(@RequestParam("keyword") String keyword){
        log.info("本次检索的关键词为：keyword,{} ========= ",keyword);
        List<String> result = new ArrayList<>();
        try{
            Analyzer analyzer = new IKAnalyzer(true);
            // 简单的查询，创建Query表示搜索域为content包含keyWord的文档
            //Query query = new QueryParser("content", analyzer).parse(keyWord);
            String[] fields = {"playerId"};
            // MUST 表示and，MUST_NOT 表示not ，SHOULD表示or
            BooleanClause.Occur[] clauses = {BooleanClause.Occur.SHOULD};
            // MultiFieldQueryParser表示多个域解析， 同时可以解析含空格的字符串，如果我们搜索"上海 中国"
            Query multiFieldQuery = MultiFieldQueryParser.parse(keyword, fields, clauses, analyzer);
            // 5、根据searcher搜索并且返回TopDocs
            IndexSearcher indexSearcher = LuceneIndexesFactory.init();
            // 5、根据searcher搜索并且返回TopDocs
            TopDocs topDocs = indexSearcher.search(multiFieldQuery, 100);
            log.info("共找到的匹配处: hitsCount,{}",topDocs.totalHits);
            // 6、根据TopDocs获取ScoreDoc对象
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            log.info("共找到文档的匹配数：docLength,{}",scoreDocs.length);
            QueryScorer scorer = new QueryScorer(multiFieldQuery,"content");
            SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter("<span style=\"color:red\">","</span>");
            Highlighter highlighter = new Highlighter(htmlFormatter, scorer);
            highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));
            for(ScoreDoc scoreDoc : scoreDocs){
                // 7、根据searcher和ScoreDoc对象获取具体的Document对象
                Document document = indexSearcher.doc(scoreDoc.doc);
                String id = document.get("id");
                String playerId = document.get("playerId");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",id);
                jsonObject.put("playerId",playerId);
                result.add(jsonObject.toString());
            }
            return getResponse().success(result);
        }catch (Exception e){
            log.error("检索失败, 异常原因：errorMsg,{}",e.getMessage());
            return getResponse().failure(e.getMessage());
        }
    }



}
