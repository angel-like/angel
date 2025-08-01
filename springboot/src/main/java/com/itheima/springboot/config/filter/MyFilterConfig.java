package com.itheima.springboot.config.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyFilterConfig {

    @Bean
    public FilterRegistrationBean xssFilter(){
        FilterRegistrationBean registrationBean=new FilterRegistrationBean();
        XssFilter xssFilter=new XssFilter();
        registrationBean.setFilter(xssFilter);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
