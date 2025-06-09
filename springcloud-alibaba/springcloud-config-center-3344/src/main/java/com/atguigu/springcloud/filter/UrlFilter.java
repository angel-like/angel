//package com.atguigu.springcloud.filter;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequestWrapper;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//
//// Created by Zjt on 2021/1/15 13:53
//@WebFilter("/*")
//public class UrlFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//        String url = httpServletRequest.getRequestURI();
//        System.out.println(url);
//        if (!url.endsWith("/actuator/bus-refresh")) {
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        }
//        String body = (httpServletRequest).toString();
//        System.out.println("original body: " + body);
//        RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
//        filterChain.doFilter(requestWrapper, servletResponse);
//    }
//
//    private class RequestWrapper extends HttpServletRequestWrapper {
//        public RequestWrapper(HttpServletRequest request) {
//            super(request);
//        }
//
//        @Override
//        public ServletInputStream getInputStream() throws IOException {
//            byte[] bytes = new byte[0];
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//            ServletInputStream servletInputStream = new ServletInputStream() {
//                @Override
//                public int read() throws IOException {
//                    return byteArrayInputStream.read();
//                }
//
//                @Override
//                public boolean isFinished() {
//                    return byteArrayInputStream.read() == -1 ? true : false;
//                }
//
//                @Override
//                public boolean isReady() {
//                    return false;
//                }
//
//                @Override
//                public void setReadListener(ReadListener listener) {
//
//                }
//            };
//            return servletInputStream;
//        }
//    }
//}