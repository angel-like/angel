package com.xmsy.server.zxyy.game.common.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Redis切面处理类
 *
 * @author aleng
 * @email xxxxxx
 * @date 2017-07-17 23:30
 */
@Aspect
@Configuration
public class RedisAspect {
//    private Logger logger = LoggerFactory.getLogger(getClass());
    //是否开启redis缓存  true开启   false关闭
    @Value("${spring.redis.open: false}")
    private boolean open;

//    @Around("execution(* com.xmsy.server.zxyy.game.common.utils.RedisUtils.*(..))")
//    public Object around(ProceedingJoinPoint point) throws Throwable {
//        Object result = null;
//        if(open){
//            try{
//                result = point.proceed();
//            }catch (Exception e){
//                logger.error("redis error", e);
//                throw new RRException("Redis服务异常");
//            }
//        }
//        return result;
//    }
}
