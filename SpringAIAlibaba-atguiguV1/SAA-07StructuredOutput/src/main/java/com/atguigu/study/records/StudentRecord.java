package com.atguigu.study.records;

/**
 * @auther zzyybs@126.com
 * @create 2025-09-08 16:53
 * @Description jdk14以后的新特性，记录类record = entity + lombok
 */
public record StudentRecord(String id, String sname,String major,String email) { }
