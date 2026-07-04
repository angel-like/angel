package com.atguigu.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;

/**
 * @auther zzyybs@126.com
 * @create 2025-07-26 16:25
 * @Description TODO
 */
@RestController
public class PromptTemplateController
{
    @Resource(name = "deepseek")
    private ChatModel deepseekChatModel;
    @Resource(name = "qwen")
    private ChatModel qwenChatModel;

    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekChatClient;
    @Resource(name = "qwenChatClient")
    private ChatClient qwenChatClient;

    /**
     * 表示去读取 类路径下的 模板文件内容
     */
    @Value("classpath:/prompttemplate/atguigu-template.txt")
    private org.springframework.core.io.Resource userTemplate;

    /**
     * @Description: PromptTemplate基本使用，使用占位符设置模版 PromptTemplate
     * @Auther: zzyybs@126.com
     * 测试地址
     * http://localhost:8006/prompttemplate/chat?topic=java&output_format=html&wordCount=200
     */
    @GetMapping("/prompttemplate/chat")
    public Flux<String> chat(String topic, String output_format, String wordCount)
    {
        PromptTemplate promptTemplate = new PromptTemplate("" +
                "讲一个关于{topic}的故事" +
                "并以{output_format}格式输出，" +
                "字数在{wordCount}左右");

        // PromptTempate -> Prompt
        Prompt prompt = promptTemplate.create(Map.of(
                "topic", topic,
                "output_format",output_format,
                "wordCount",wordCount));

        return deepseekChatClient.prompt(prompt).stream().content();
    }





    /**
     * @Description: PromptTemplate读取模版文件实现模版功能
     * @Auther: zzyybs@126.com
     * 测试地址
     * http://localhost:8006/prompttemplate/chat2?topic=java&output_format=html
     */
    @GetMapping("/prompttemplate/chat2")
    public String chat2(String topic,String output_format)
    {
        PromptTemplate promptTemplate = new PromptTemplate(userTemplate);

        Prompt prompt = promptTemplate.create(Map.of("topic", topic, "output_format", output_format));

        return deepseekChatClient.prompt(prompt).call().content();
    }


    /**
     *  @Auther: zzyybs@126.com
     * @Description:
     * 系统消息(SystemMessage)：设定AI的行为规则和功能边界(xxx助手/什么格式返回/字数控制多少)。
     * 用户消息(UserMessage)：用户的提问/主题
     * http://localhost:8006/prompttemplate/chat3?sysTopic=法律&userTopic=知识产权法
     *
     * http://localhost:8006/prompttemplate/chat3?sysTopic=法律&userTopic=夫妻肺片
     */
    @GetMapping("/prompttemplate/chat3")
    public String chat3(String sysTopic, String userTopic)
    {
        // 1.SystemPromptTemplate
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate("你是{systemTopic}助手，只回答{systemTopic}其它无可奉告，以HTML格式的结果。");
        Message sysMessage = systemPromptTemplate.createMessage(Map.of("systemTopic", sysTopic));
        // 2.PromptTemplate
        PromptTemplate userPromptTemplate = new PromptTemplate("解释一下{userTopic}");
        Message userMessage = userPromptTemplate.createMessage(Map.of("userTopic", userTopic));
        // 3.组合【关键】 多个 Message -> Prompt
        Prompt prompt = new Prompt(List.of(sysMessage, userMessage));
        // 4.调用 LLM
        return deepseekChatClient.prompt(prompt).call().content();
    }

    /**
     * 上面的切换为流式输出
     */
    @GetMapping("/prompttemplate/chat33")
    public Flux<String> chat33(String sysTopic, String userTopic)
    {
        // 1.SystemPromptTemplate
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate("你是{systemTopic}助手，只回答{systemTopic}其它无可奉告，以HTML格式的结果。");
        Message sysMessage = systemPromptTemplate.createMessage(Map.of("systemTopic", sysTopic));
        // 2.PromptTemplate
        PromptTemplate userPromptTemplate = new PromptTemplate("解释一下{userTopic}");
        Message userMessage = userPromptTemplate.createMessage(Map.of("userTopic", userTopic));
        // 3.组合【关键】 多个 Message -> Prompt
        Prompt prompt = new Prompt(List.of(sysMessage, userMessage));
        // 4.调用 LLM
        return deepseekChatClient.prompt(prompt).stream().content();
    }


    /**
     * @Description: 人物角色设定，通过SystemMessage来实现人物设定，本案例用ChatModel实现
     * 设定AI为”医疗专家”时，仅回答医学相关问题
     * 设定AI为编程助手”时，专注于技术问题解答
     * @Auther: zzyybs@126.com
     * http://localhost:8006/prompttemplate/chat4?question=牡丹花
     */
    @GetMapping("/prompttemplate/chat4")
    public String chat4(String question)
    {
        //1 系统消息
        SystemMessage systemMessage = new SystemMessage("你是一个Java编程助手，拒绝回答非技术问题。");
        //2 用户消息
        UserMessage userMessage = new UserMessage(question);
        //3 系统消息+用户消息=完整提示词
        //Prompt prompt = new Prompt(systemMessage, userMessage);
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        //4 调用LLM
        String result = deepseekChatModel.call(prompt).getResult().getOutput().getText();
        System.out.println(result);
        return result;
    }

    /**
     * @Description: 人物角色设定，通过SystemMessage来实现人物设定，本案例用ChatClient实现
     * 设定AI为”医疗专家”时，仅回答医学相关问题
     * 设定AI为编程助手”时，专注于技术问题解答
     * @Auther: zzyybs@126.com
     * http://localhost:8006/prompttemplate/chat5?question=火锅
     */
    @GetMapping("/prompttemplate/chat5")
    public Flux<String> chat5(String question)
    {
        return deepseekChatClient.prompt()
                .system("你是一个Java编程助手，拒绝回答非技术问题。")
                .user(question)
                .stream()
                .content();
    }
}
