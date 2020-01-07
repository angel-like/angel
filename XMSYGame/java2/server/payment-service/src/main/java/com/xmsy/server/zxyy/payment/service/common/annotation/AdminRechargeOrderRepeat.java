package com.xmsy.server.zxyy.payment.service.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 人工充值订单提交，防止重复提交
 * 
 * @author adu
 * @email xxxxxx
 * @date 2017年3月22日 16:19:56
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdminRechargeOrderRepeat {

	String value() default "";
}
