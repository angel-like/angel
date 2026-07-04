package com.atguigu.study.controller;

import com.alibaba.cloud.ai.advisor.DocumentRetrievalAdvisor;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetriever;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetrieverOptions;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @auther zzyybs@126.com
 * @create 2025-08-01 16:51
 * @Description TODO
 */
@RestController
public class BailianRagController
{
    @Resource
    private ChatClient chatClient;
    @Resource
    private DashScopeApi dashScopeApi;

    /**
     * http://localhost:8017/bailian/rag/chat
     * http://localhost:8017/bailian/rag/chat?msg=A0001
     * @param msg
     * @return
     */
    @GetMapping("/bailian/rag/chat")
    public Flux<String> chat(@RequestParam(name = "msg",defaultValue = "00000错误信息") String msg)
    {
        // 百炼 RAG 构建器
        DocumentRetriever retriever = new DashScopeDocumentRetriever(dashScopeApi,
                DashScopeDocumentRetrieverOptions.builder()
                        .withIndexName("spring ai alibaba") // 知识库名称
                        .build()
        );

        return chatClient.prompt()
                .user(msg)
                .advisors(new DocumentRetrievalAdvisor(retriever))
                .stream()
                .content();
    }

}
