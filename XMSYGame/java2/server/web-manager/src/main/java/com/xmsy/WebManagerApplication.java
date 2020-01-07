package com.xmsy;

import com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.redis.Receiver;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.util.Constant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.TimeZone
        ;

@SpringBootApplication
@ServletComponentScan
@EnableAsync
@EnableCaching
@EnableScheduling
@Slf4j
public class WebManagerApplication extends SpringBootServletInitializer {
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapterSingle, MessageListenerAdapter listenerAdapterList) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // 注入多个消息监听器(receiveSingle/receiveList)
        container.addMessageListener(listenerAdapterSingle, new PatternTopic(Constant.receiveSingle));
        container.addMessageListener(listenerAdapterList, new PatternTopic(Constant.receiveList));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapterSingle(Receiver receiver) {
        return new MessageListenerAdapter(receiver, Constant.singleMethodName);
    }

    @Bean
    MessageListenerAdapter listenerAdapterList(Receiver receiver) {
        return new MessageListenerAdapter(receiver, Constant.listMethodName);
    }

//    @Bean
//    Receiver receiver() {
//        return new Receiver();
//    }
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(WebManagerApplication.class);
        try {
            springApplication.run(args);
        } catch (Exception e) {
            System.out.println("==============================================");
            System.out.println("启动异常 ");
            System.out.println("==============================================");
            e.printStackTrace();
            System.exit(0);
        }
//		long maxM = Runtime.getRuntime().maxMemory() / 1000 / 1000;
//		long totalM = Runtime.getRuntime().totalMemory() / 1000 / 1000;
//		long usedM = Runtime.getRuntime().freeMemory() / 1000 / 1000;
//		System.out.println("==============================================");
//		System.out.println("maxMemory   = " + maxM);
//		System.out.println("totalMemory = " + totalM);
//		System.out.println("freeMemory  = " + usedM);
        log.info("startup-success");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebManagerApplication.class);
    }

    /**
     * 服务器时区设置
     * 解决服务端时差问题
     */
    @PostConstruct
    void setDefaultTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }

}
