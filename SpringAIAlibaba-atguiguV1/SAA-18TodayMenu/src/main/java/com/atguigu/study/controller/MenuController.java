package com.atguigu.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @auther zzyybs@126.com
 * @create 2025-09-11 19:00
 * @Description TODO
 */

@RestController
public class MenuController
{
    @Resource
    private ChatModel chatModel;

    /**
     * http://localhost:8018/eat
     *
     * zzyybs@126.com
     * @param question
     * @return
     */
    @GetMapping(value = "/eat")
    public Flux<String> eat(@RequestParam(name = "msg",defaultValue = "今天吃什么") String question)
    {
        String info = """
                你是一个AI厨师助手,每次随机生成三个家常菜，并且提供这些家常菜的详细做法步骤，以HTML格式返回
                字数控制在1500字以内。
                """;
        // 系统消息
        SystemMessage systemMessage = new SystemMessage(info);
        // 用户消息
        UserMessage userMessage = new UserMessage(question);

        Prompt prompt = new Prompt(userMessage, systemMessage);

        return chatModel.stream(prompt).map(response -> response.getResults().get(0).getOutput().getText());
    }
}
