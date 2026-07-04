package com.atguigu.study.controller;

import com.alibaba.cloud.ai.dashscope.api.DashScopeSpeechSynthesisApi;
import com.alibaba.cloud.ai.dashscope.audio.DashScopeSpeechSynthesisOptions;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisModel;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisPrompt;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisResponse;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * @auther zzyybs@126.com
 * @create 2025-07-29 18:35
 * @Description TODO
 */
@RestController
public class Text2VoiceController
{
    @Resource
    private SpeechSynthesisModel speechSynthesisModel;

    // voice model
    public static final String BAILIAN_VOICE_MODEL = "cosyvoice-v2";
    // voice timber 音色列表：https://help.aliyun.com/zh/model-studio/cosyvoice-java-sdk#722dd7ca66a6x
    public static final String BAILIAN_VOICE_TIMBER = "longyingcui";//龙应催


    /**
     * http://localhost:8010/t2v/voice
     * @param msg
     * @return
     */
    @GetMapping("/t2v/voice")
    public String voice(@RequestParam(name = "msg",defaultValue = "温馨提醒，支付宝到账100元请注意查收") String msg)
    {
        String filePath = "d:\\" + UUID.randomUUID() + ".mp3";

        //1 语音参数设置 （参考以下两个合一的调用链接）
        //https://help.aliyun.com/zh/model-studio/cosyvoice-java-sdk?
        //spm=a2c4g.11186623.help-menu-2400256.d_2_6_0_0.2bb422e7Wq1PSv&scm=20140722.H_2868658._.OR_help-T_cn~zh-V_1#36bbb575c7czo
        DashScopeSpeechSynthesisOptions options = DashScopeSpeechSynthesisOptions.builder()
                .model(BAILIAN_VOICE_MODEL)//模型
                .voice("longhuhu")//音色
                .responseFormat(DashScopeSpeechSynthesisApi.ResponseFormat.MP3)//音频格式
                .volume(50)//音量，取值范围
                .speed(1.0f)//语速
                .pitch(1.0d)//语调
                .build();

        //2 调用大模型语音生成对象
        SpeechSynthesisResponse response = speechSynthesisModel.call(new SpeechSynthesisPrompt(msg, options));

        //3 字节流语音转换
        ByteBuffer byteBuffer = response.getResult().getOutput().getAudio();

        //4 文件生成
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath))
        {
            fileOutputStream.write(byteBuffer.array());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //5 生成路径OK
        return filePath;
    }
}
