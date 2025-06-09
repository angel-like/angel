package com.atguigu.springcloud.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lixiaolong
 */
@Configuration
public class GateWayConfig {

    /**
     *       routes:
     *         - id: payment_routh #payment_route    #路由的ID，没有固定规则但要求唯一，建议配合服务名
     *           uri: http://localhost:8001          #匹配后提供服务的路由地址
     *           #uri: lb://springcloud-payment-service #匹配后提供服务的路由地址
     *           predicates:
     *             - Path=/payment/get/**         # 断言，路径相匹配的进行路由
     *  类似以上配置，访问http://localhost:9527/guonei时，跳转到http://news.baidu.com/guone，跟请求转发或重定向差不多
     *  类似以上配置，访问http://localhost:9527/guonei时，跳转到http://metaso.cn/，跟请求转发或重定向差不多，只能http的
     * @param routeLocatorBuilder
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("path_route_atguigu", r -> r.path("/metaso").uri("http://metaso.cn/"))
                .build();
        return routes.build();
    }
}

