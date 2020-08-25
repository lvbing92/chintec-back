package com.chintec.auth;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@SpringBootTest
public class ElasticsearchTest {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void addMsg() throws IOException {
        // 准备数据
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("username", "zhangsan");
        jsonMap.put("age", 18);
        jsonMap.put("address", "sh");
        // 指定索引库和 id 及数据
        IndexRequest indexRequest = new IndexRequest("ego").id("1").source(jsonMap);
        // 执行请求
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        log.info(indexResponse.toString());
    }
}
