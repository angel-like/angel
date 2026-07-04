package com.atguigu.study.config;

import cn.hutool.crypto.SecureUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.AbstractVectorStoreBuilder;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 启动redis-stack：
 *  docker run -d --name redis-stack-server -p 6379:6379 redis/redis-stack-server
 *  初次启动后： docker start 977d99c3edf3  启动容器
 * @auther zzyybs@126.com
 * @create 2025-07-30 12:16
 * @Description TODO
 */
@Configuration
public class InitVectorDatabaseConfig
{
    @Autowired
    private VectorStore vectorStore;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Value("classpath:ops.txt")
    private Resource opsFile;

    @PostConstruct
    public void init()
    {
        //1 读取文件
        TextReader textReader = new TextReader(opsFile);
        textReader.setCharset(Charset.defaultCharset());

        //2 文件转换为向量(开启分词)
        List<Document> list = new TokenTextSplitter().transform(textReader.read());

        //3 写入向量数据库RedisStack
        //vectorStore.add(list);

        // 解决上面第3步，向量数据重复问题，使用redis setnx命令处理

        //4 去重复版本 (有优化的空间，把文件的md5存入redis，每次启动时检查md5值是否一致，不一致再替换，一致则跳过)

        String sourceMetadata = (String)textReader.getCustomMetadata().get("source");

        String textHash = SecureUtil.md5(sourceMetadata);
        String redisKey = "vector-xxx:" + textHash;

        // 判断是否存入过,redisKey如果可以成功插入表示以前没有过，可以假如向量数据
        Boolean retFlag = redisTemplate.opsForValue().setIfAbsent(redisKey, "1");

        System.out.println("****retFlag : "+retFlag);

        if(Boolean.TRUE.equals(retFlag))
        {
            //键不存在，首次插入,可以保存进向量数据库
            vectorStore.add(list);
        }else {
            //键已存在，跳过或者报错
            //throw new RuntimeException("---重复操作");
            System.out.println("------向量初始化数据已经加载过，请不要重复操作");
        }
    }

}
