/*
package com.cetc.log.service.impl;

import com.alibaba.fastjson.JSON;
import com.cetc.common.core.alm.PageResult;
import com.cetc.common.core.alm.Result;
import com.cetc.common.core.utils.IdWorker;
import com.cetc.log.service.LogService;
import com.cetc.model.log.Log;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import lombok.extern.slf4j.Slf4j;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class ESLogServiceImpl implements LogService {
    private static final Logger logger = LoggerFactory.getLogger(ESLogServiceImpl.class);

    private static final String index_name = "index_log8";
    
    private static final String index_type ="type_log";

    @Autowired
    private RestHighLevelClient rhlClient;

    @Autowired
    private IdWorker idWorker;

    @Override
    @Async
    public void save(Log log) {
        log.setId(idWorker.nextId());
        String logJson = JSON.toJSONString(log);
        IndexRequest indexRequest = new IndexRequest(index_name, index_type,log.getId().toString());
        indexRequest.source(logJson, XContentType.JSON);
        try {
            rhlClient.index(indexRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Result findPageLogs(PageResult pageResult) {
        SearchRequest searchRequest = new SearchRequest(index_name);
        searchRequest.types(index_type);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(3);
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse search = rhlClient.search(searchRequest);
            SearchHits hits = search.getHits();
            List<Log> logs = new ArrayList<Log>();
            for (SearchHit hit:hits) {
                System.out.println(111112222);
                System.out.println(hit.getSourceAsString());
                Log log = JSON.parseObject(hit.getSourceAsString(), Log.class);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostConstruct
    public void initIndex() {
        if (!checkIndexExists(index_name)) {
            if(indexCreate()){
                logger.info("***************日志索引创建成功*************************");
            }else{
                logger.info("***************日志索引创建失败*************************");
            }

        }

    }

    public boolean indexCreate(){
        CreateIndexRequest indexRequest = new CreateIndexRequest(index_name);
        indexRequest.settings(Settings.builder()
                .put("index.number_of_shards",3)
                .put("index.number_of_replicas",2));

        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.startObject(index_type);
                {
//                    builder.startObject("properties") ;
//                    {
//                        builder.startObject("createTime");
//                        {
//                            builder.field("type","date");
//                            builder.field("format","yyyy-MM-dd HH:mm:ss");
//                        }
//                        builder.endObject();
//                    }
//                    builder.endObject();
                }
                builder.endObject();
            }
            builder.endObject();
           indexRequest.mapper(index_type,builder);

            CreateIndexResponse createIndexResponse = rhlClient.indices().create(indexRequest);

            return true;
        } catch (exception e) {
            e.printStackTrace();
           return  false;
        }

    }




    public boolean checkIndexExists(String indexName) {
        try {
            GetIndexRequest request = new GetIndexRequest();
            request.indices(indexName);
            request.local(false);
            request.humanReadable(true);
            boolean exists = rhlClient.indices().exists(request);
            return exists;
        } catch (IOException e) {
            e.printStackTrace();

        }
        return false;
    }






}
*/
