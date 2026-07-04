package com.atguigu.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @auther zzyybs@126.com
 * @create 2025-07-25 21:25
 * @Description 知识出处，https://java2ai.com/docs/1.0.0.2/tutorials/basics/prompt/?spm=5176.29160081.0.0.2856aa5cdeol7a
 */
@RestController
public class PromptController
{
    @Resource(name = "deepseek")
    private ChatModel deepseekChatModel;
    @Resource(name = "qwen")
    private ChatModel qwenChatModel;

    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekChatClient;
    @Resource(name = "qwenChatClient")
    private ChatClient qwenChatClient;



    // http://localhost:8005/prompt/chat?question=火锅介绍下
    @GetMapping("/prompt/chat")
    public Flux<String> chat(String question)
    {

        return deepseekChatClient.prompt()
                // AI 能力边界
                .system("你是一个法律助手，只回答法律问题，其它问题回复，我只能回答法律相关问题，其它无可奉告")
                .user(question)
                .stream()
                .content();
    }






    /**
     * http://localhost:8005/prompt/chat2?question=葫芦娃
     * @param question
     * @return
     */
    @GetMapping("/prompt/chat2")
    public Flux<ChatResponse> chat2(String question)
    {
        // 系统消息
        SystemMessage systemMessage = new SystemMessage("你是一个讲故事的助手,每个故事控制在300字以内");

        // 用户消息
        UserMessage userMessage = new UserMessage(question);

        Prompt prompt = new Prompt(userMessage, systemMessage);
        //这里返回的一整个json
        return deepseekChatModel.stream(prompt);

    }

    /**
     * http://localhost:8005/prompt/chat3?question=葫芦娃
     * @param question
     * @return
     */
    @GetMapping("/prompt/chat3")
    public Flux<String> chat3(String question)
    {
        // 系统消息
        SystemMessage systemMessage = new SystemMessage("你是一个讲故事的助手," +
                "每个故事控制在600字以内且以HTML格式返回");

        // 用户消息
        UserMessage userMessage = new UserMessage(question);

        Prompt prompt = new Prompt(userMessage, systemMessage);

        return deepseekChatModel.stream(prompt)
                .map(response -> response.getResults().get(0).getOutput().getText());

    }

    /**
     * http://localhost:8005/prompt/chat4?question=葫芦娃
     * @param question
     * @return
     */
    @GetMapping("/prompt/chat4")
    public String chat4(String question)
    {
        AssistantMessage assistantMessage = deepseekChatClient.prompt()
                    .user(question)
                    .call()
                    .chatResponse()
                    .getResult()
                    .getOutput();

        return assistantMessage.getText();

    }

    /**
     * http://localhost:8005/prompt/chat5?city=北京
     * 近似理解Tool后面章节讲解......
     * @param city
     * @return
     */
    @GetMapping("/prompt/chat5")
    public String chat5(String city)
    {

        String answer = deepseekChatClient.prompt()
                .user(city + "未来3天天气情况如何?")
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getText();
        //这里只是演示、完全无效果
        ToolResponseMessage toolResponseMessage =
                new ToolResponseMessage(
                        List.of(new ToolResponseMessage.ToolResponse("1","获得天气",city)
                        )
                );

        String toolResponse = toolResponseMessage.getText();

        String result = answer + toolResponse;

        return result;
    }
}
