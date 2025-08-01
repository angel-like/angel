package com.itheima.springboot.config.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Slf4j
//@Component
//配置过滤路径,怎么配都过滤全部。解决方式通过MyFilterConfig类里做配置
//@WebFilter(filterName = "XssFilter",urlPatterns = "/aaa")
public class XssFilter implements Filter {



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        long startTime = System.currentTimeMillis();
        String requestParams="";
        //1. 根据不同的请求获取 请求参数
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
            // 打印POST请求的body参数
            requestParams=stringBuilder.toString();
        }else if ("GET".equalsIgnoreCase(request.getMethod())) {
            // 处理GET请求参数
            Enumeration<String> parameterNames = request.getParameterNames();
            //Map<String, String> parameterMap = new HashMap<>();
            List<String> list=new ArrayList<>();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String paramValues = request.getParameter(paramName);
                //parameterMap.put(paramName, paramValues);
                list.add(paramName+"="+paramValues);
            }
            requestParams = JSON.toJSONString(list);
        }
        //2. 添加全链路日志追踪id (其他服务器有追踪id进来，就用该id)
        //2.1 判断其他服务器是否有传traceId，有的话使用，无则自己生产
        String gateWayTraceId = request.getHeader(MDCUtils.TRACE_ID_KEY);
        String traceId = MDCUtils.generaterMdcTraceId(gateWayTraceId);
        //2.2 把traceId放到response响应对象里，实现透传到其他服务器
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        response.setHeader(MDCUtils.TRACE_ID_KEY, traceId);
        //3. 打印请求日志信息
        log.info("接口调用前日志：{}，请求参数：{}" , request.getRequestURL(),requestParams);

        filterChain.doFilter(request,response);
        long time=System.currentTimeMillis()-startTime;
        log.info(request.getRequestURI()+"接口调用总耗时：" + time);
    }

    @Override
    public void destroy() {

    }
}
