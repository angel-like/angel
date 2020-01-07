package com.xmsy.server.zxyy.manager.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ping域名工具类
 * 
 * @author Administrator
 *
 */
public class DomainUtil {
	
	public static long ping(String domian) {  
        long start = System.currentTimeMillis();  
        Domain result = new Domain();  
        try {  
            InetAddress address = InetAddress.getByName(domian);  
            result.ip = address.getHostAddress();  
            result.host = address.getHostName();  
            long end = System.currentTimeMillis(); 
            result.time = (end - start);  
        } catch (UnknownHostException e) {  
            result.ip = "0.0.0.0";  
            result.host = "UNKONW";  
        }  
        return result.time;  
    }  
  
    public static class Domain {  
        String ip;  
        String host;  
        long time;  
  
        @Override  
        public String toString() {  
            return String.format("host=%s, ip=%s, time=%s", host, ip, time);  
        }  
    }  

}
