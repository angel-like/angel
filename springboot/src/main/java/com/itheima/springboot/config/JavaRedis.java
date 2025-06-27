package com.itheima.springboot.config;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class JavaRedis {
	public static void main(String[] args) {
		@SuppressWarnings("resource")//不要警告
		Jedis jedis=new Jedis("localhost");
		System.out.println("正在尝试连接");
		System.out.println("连接成功，服务正在运行："+jedis.ping());
		//1.设置 redis 字符串数据   并输出
		jedis.set("runoobkey", "www.runoob.com");
		System.out.println("存储的字符串是："+jedis.get("runoobkey"));
		
		System.out.println("1------>>>>>>>>>>");
		
		//2.设置list数据  存储数据到列表中
        jedis.lpush("site-list", "Runoob");
        jedis.lpush("site-list", "Google");
        jedis.lpush("site-list", "Taobao");
        // 获取存储的数据并输出
        List<String> list = jedis.lrange("site-list", 0 ,2);
        for(int i=0; i<list.size(); i++) {
            System.out.println("列表项为: "+list.get(i));
        }
        
        System.out.println("2------>>>>>>>>>>");
        
        //3.设置set数据
        Set<String> keys=jedis.keys("*");
        Iterator<String> it=keys.iterator();
        while(it.hasNext()) {//这个不能用if，if只能执行一次 
        	String key=it.next();
        	System.out.println(key);
        }
        System.out.println("另外一种遍历方法-------");
        for(String key:keys) {
        	System.out.println(key);
        }
        
        System.out.println("3------>>>>>>>>>>");
	}
}
