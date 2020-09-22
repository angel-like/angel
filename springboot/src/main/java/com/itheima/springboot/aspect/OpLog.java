package com.itheima.springboot.aspect;

import java.lang.annotation.*;

/**
 * 请描述类 <br>
 *
 * @author ljx
 * <p>
 * Created by ljx wu on 2020-09-22 13:53
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpLog {
    public long timout() default -1L;  //有默认值，注解那可填可不填
    public String value() ;  //无默认值，注解那必须要填写
}
