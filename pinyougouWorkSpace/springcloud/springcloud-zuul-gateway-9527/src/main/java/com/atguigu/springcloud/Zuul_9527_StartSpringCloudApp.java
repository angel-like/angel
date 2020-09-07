package com.atguigu.springcloud;
 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
 
@SpringBootApplication
@EnableZuulProxy
public class Zuul_9527_StartSpringCloudApp
{
	/**
	 * 1. zuul网关其底层使用ribbon来实现请求的路由，并内置Hystrix，可选择性提供网关fallback逻辑。
	 * 2. 使用zuul的时候，并不推荐使用Feign作为application client端的开发实现。毕竟Feign技术是对ribbon的再封装，使用Feign本身会提高通讯消耗，降低通讯效率，只在服务相互调用的时候使用Feign来简化代码开发就够了。
	 *    商业开发中，使用Ribbon+RestTemplate来开发的比例更高。
	 * 3.https://www.cnblogs.com/jing99/p/11696192.html   配置详情
	 * @param args
	 */
  public static void main(String[] args)
  {
   SpringApplication.run(Zuul_9527_StartSpringCloudApp.class, args);
  }
}
 

