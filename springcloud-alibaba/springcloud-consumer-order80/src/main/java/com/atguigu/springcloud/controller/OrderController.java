package com.atguigu.springcloud.controller;


import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("consumer")
public class OrderController {

    //public static final String PAYMENT_URL = "http://localhost:8001";

    //eureka集群情况下(ApplicationContextConfig类开启@LoadBalanced)，使用该地址可以去访问（大小写都行）  "http://localhost:8001" 或 "http://localhost:8002"
    public static final String PAYMENT_URL = "http://springcloud-payment-service";

    @Resource
    private RestTemplate restTemplate;

    // 注入自定义的负载均衡规则
    //@Resource
    //private MyLoadBalancer myLoadBalancer;


    //@Resource
    //private DiscoveryClient discoveryClient;

    @PostMapping("/payment/create")
    public CommonResult<Payment> create(@RequestBody Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }


//    @GetMapping("/payment/queryAllByLimit")
//    public CommonResult queryAllByLimit(@RequestParam(defaultValue = "0") int offset,
//                                        @RequestParam(defaultValue = "10") int limit) {
//        return restTemplate.getForObject(
//                PAYMENT_URL + "/payment/queryAllByLimit?offset=" + offset + "&limit=" + limit, CommonResult.class);
//    }
//
    @GetMapping("/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id") Long id) {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);

        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        } else {
            return new CommonResult<>(444, "操作失败");
        }
    }
//
//    /**
//     * @author lixiaolong
//     * @date 2020/12/23 10:27
//     * @description 测试自定义的负载均衡规则
//     */
//    @GetMapping(value = "/payment/lb")
//    public String getPaymentLB() {
//        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
//
//        if (instances == null || instances.isEmpty()) {
//            return null;
//        }
//
//        // 调用自定义的负载均衡策略
//        ServiceInstance serviceInstance = myLoadBalancer.instances(instances);
//        URI uri = serviceInstance.getUri();
//        return restTemplate.getForObject(uri + "/payment/lb", String.class);
//
//    }
//
    // ====================> zipkin+sleuth
    @GetMapping("/payment/zipkin")
    public String paymentZipkin() {
        String result = restTemplate.getForObject(PAYMENT_URL + "/payment/zipkin/", String.class);
        return result;
    }

}
