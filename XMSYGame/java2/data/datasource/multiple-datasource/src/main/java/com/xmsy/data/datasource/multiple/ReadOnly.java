package com.xmsy.data.datasource.multiple;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * .只读注解
 *
 * @author chenjisi
 * @date 2018年1月12日 上午2:02:19 
 * @Description: TODO
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadOnly {

}
