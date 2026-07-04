package com.atguigu.study.controller;

import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingOptions;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @auther zzyybs@126.com
 * @create 2025-07-29 19:54
 * @Description TODO
 */
@RestController
@Slf4j
public class Embed2VectorController
{
    @Resource
    private EmbeddingModel embeddingModel;

    @Resource
    private VectorStore vectorStore;

    /**
     * 文本向量化
     * http://localhost:8011/text2embed?msg=射雕英雄传
     *
     * @param msg
     * @return
     */
    @GetMapping("/text2embed")
    public EmbeddingResponse text2Embed(String msg)
    {
        //EmbeddingResponse embeddingResponse = embeddingModel.call(new EmbeddingRequest(List.of(msg), null));

        EmbeddingResponse embeddingResponse = embeddingModel.call(new EmbeddingRequest(List.of(msg),
                DashScopeEmbeddingOptions.builder().withModel("text-embedding-v3").build()));

        System.out.println(Arrays.toString(embeddingResponse.getResult().getOutput()));

        return embeddingResponse;
    }


    /**
     * 文本向量化 后存入向量数据库RedisStack
     * docker run -d --name redis-stack-server -p 6379:6379 redis/redis-stack-server （docker启动）
     * redis里：key *  查询所有key
     *  json.get 获取key对应的value信息
     *  json.del 删除
     */
    @GetMapping("/embed2vector/add")
    public void add()
    {
        //生成 spring.ai.vectorstore.redis.prefix=custom-prefix 前置名称的key信息
        Document illm = new Document("i study LLM");
        Document ijava = new Document("i love java");
        List<Document> documents = List.of(illm, ijava);
        vectorStore.add(documents);//这方法里封装有 text2Embed方法里embeddingModel.call()的 调用链路
    }


    /**
     * 从向量数据库RedisStack查找，进行相似度查找
     * http://localhost:8011/embed2vector/get?msg=LLM
     * @param msg
     * @return
     */
    @GetMapping("/embed2vector/get")
    public List getAll(@RequestParam(name = "msg") String msg)
    {
        SearchRequest searchRequest = SearchRequest.builder()
                .query(msg)
                .topK(2)
                .build();

        List<Document> list = vectorStore.similaritySearch(searchRequest);

        System.out.println(list);

        return list;
    }
}
