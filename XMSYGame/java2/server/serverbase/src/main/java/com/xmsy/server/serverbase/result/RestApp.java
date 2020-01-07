package com.xmsy.server.serverbase.result;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Controller;

/**
 * .app返回结果注解定义
 * 
 * @author chenjisi
 * @since 2017年8月14日
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
public @interface RestApp {
	String type() default "json";
}
