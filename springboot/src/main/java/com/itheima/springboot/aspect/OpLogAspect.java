package com.itheima.springboot.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import com.itheima.springboot.param.RequestParam;

/**
 * 请描述类 <br>
 *
 * @author ljx
 * <p>
 * Created by ljx wu on 2020-09-22 13:58
 */
@Aspect	//3.2 切面类
@Configuration  //配置文件
public class OpLogAspect {
    @Pointcut("@annotation(com.itheima.springboot.aspect.OpLog)")//3.1  切入点
    public void logPointCut() {
    }

    //@Before("@annotation(opLog)") 这两个 注解  功能都一样 ，上面这个比较简洁
    @Before("logPointCut() && @annotation(opLog)  && args(param,..)") //3.3 前置通知
    public void before(OpLog opLog,RequestParam param) { //可以获取传入的参数
        String value = opLog.value();
        long timout = opLog.timout();
        System.out.println("字符串:"+value+"\n超时:"+timout+"\n参数:"+param.getAppkey());
    }

}
