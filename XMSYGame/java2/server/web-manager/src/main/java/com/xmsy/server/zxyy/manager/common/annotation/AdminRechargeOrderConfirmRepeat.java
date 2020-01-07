package com.xmsy.server.zxyy.manager.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 人工充值订单确认，防止重复确认
 * 
 * @author adu
 * @email xxxxxx
 * @date 2017年3月22日 16:19:56
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdminRechargeOrderConfirmRepeat {

	String value() default "";
}
