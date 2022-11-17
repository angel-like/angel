package com.itheima.springboot;

import java.nio.charset.Charset;

import com.itheima.springboot.aspect.OpLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itheima.springboot.service.ScheduleService;

@EnableScheduling
@EnableAsync//开启异步任务
@Controller  //扫描当前包以及子包下的service ，所以扫描不到外面的包
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class})//排除redis的自动配置//RedisAutoConfiguration.class ,
@Configuration
public class HelloApplication {

    @RequestMapping("hello")//路径直接http://localhost:8080/hello   不要写项目名
    @ResponseBody
    public String hello() {
        return "hello world！ 测试springboot！";
    }
    
	@Autowired
	private ScheduleService scheduleService;
	@RequestMapping("hello4")
    @ResponseBody
    @OpLog(value ="454555")
	public String hello4() {
		scheduleService.hello4();//定时任务
		return "success4";
	}
	

    @Bean//消息转化器
    public StringHttpMessageConverter stringHttpMessageConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("ISO-8859-1"));
        return converter;
    }

    public static void main(String[] args) {
        // SpringApplication.run(HelloApplication.class, args);
        SpringApplication application = new SpringApplication(HelloApplication.class);
        application.setBannerMode(Mode.CONSOLE);
        application.run(args);
    }

}
