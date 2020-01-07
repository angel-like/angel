package com.xmsy.server.zxyy.manager.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 推送任务，防止重复确认
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2017年3月8日 上午10:19:56
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PushOrderRepeat {

	String value() default "";
}
