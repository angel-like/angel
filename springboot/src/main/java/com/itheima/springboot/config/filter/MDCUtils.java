package com.itheima.springboot.config.filter;

import com.itheima.springboot.utils.HutoolUtils;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

public class MDCUtils {

    /**
     * 放到常量类里
     */
    public static final String TRACE_ID_KEY="traceId";

    /**
     * 生成MDC的traceId
     * @return
     */
    public static String generaterMdcTraceId(){
        HutoolUtils hutoolUtils=new HutoolUtils();
        String traceId= hutoolUtils.fetchSnowFlakeId().toString();
        MDC.put(TRACE_ID_KEY,traceId);
        return traceId;
    }

    /**
     * 生成MDC的traceId ,先判断是否有追踪id
     * @return
     */
    public static String generaterMdcTraceId(String traceId){
        if(StringUtils.isEmpty(traceId)){
            return generaterMdcTraceId();
        }
        MDC.put(TRACE_ID_KEY,traceId);
        return traceId;
    }

    /**
     * 获取当前MDC里已有的 traceId
     * @return
     */
    public static String getTraceId(){
        return MDC.get(TRACE_ID_KEY);
    }

    /**
     * 移除当前MDC里已有的 traceId
     * @return
     */
    public static void removeTraceId(){
        MDC.remove(TRACE_ID_KEY);
    }

}
