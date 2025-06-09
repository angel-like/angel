package com.atguigu.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.alibaba.service.PaymentService;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author lixiaolong
 * @date 2020-02-25 16:05
 * @SentinelResource(value = "(要垄断或者限流的)方法", blockHandler = "兜底方法")
 *     a. 无blockHandler参数
 *          配置流控规则时需要用全路径 /sentinel/rateLimit/byUrl ：   就系统默认提示
 *          配置流控规则用vlaue =""里的方法，相当于没有限流，报页面错误(Whitelabel Error Page) ，value里用private方法一样效果
 *     b. 有blockHandler参数
 *         配置流控规则时需要用全路径 /sentinel/rateLimit/byUrl ：   就系统默认提示
 *         配置流控规则用vlaue =""里的方法，才会走兜底方法
 *   综上： 要用SentinelResource，最好就是加上blockHandler，用value里匹配的值去流控
 */
@RestController
@Slf4j
public class CircleBreakerController {
    public static final String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/consumer/fallback/{id}")
    //@SentinelResource(value = "fallback") //没有配置 ，直接抛异常页面。需要配合sentinel后台监控使用，查看sentinel-service8401了解或笔记里 或类上面注释
    //@SentinelResource(value = "fallback", fallback = "handlerFallback") //fallback只负责业务异常
    //@SentinelResource(value = "fallback",blockHandler = "blockHandler") //blockHandler只负责sentinel控制台配置违规
    @SentinelResource(value = "fallback", fallback = "handlerFallback", blockHandler = "blockHandler"
            , exceptionsToIgnore = {IllegalArgumentException.class})//假如报IllegalArgumentException异常，就没有fallback跟blockHandler里的兜底了
    public CommonResult<Payment> fallback(@PathVariable Long id) {
        System.out.println("进入fallback方法");
        CommonResult<Payment> result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, CommonResult.class, id);

        if (id == 4) {
            throw new IllegalArgumentException("IllegalArgumentException,非法参数异常....");
        } else if (result.getData() == null) {
            throw new NullPointerException("NullPointerException,该ID没有对应记录,空指针异常");
        }

        return result;
    }

    //本例是fallback
    public CommonResult handlerFallback(@PathVariable Long id, Throwable e) {
        Payment payment = new Payment(id, "null");
        return new CommonResult<>(444, "兜底异常handlerFallback,exception内容  " + e.getMessage(), payment);
    }

    //本例是blockHandler
    public CommonResult blockHandler(@PathVariable Long id, BlockException blockException) {
        Payment payment = new Payment(id, "null");
        return new CommonResult<>(445, "blockHandler-sentinel限流,无此流水: blockException  " + blockException.getMessage(), payment);
    }

    //==================测试OpenFeign==================
    @Resource
    private PaymentService paymentService;

    @GetMapping(value = "/consumer/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id) {
        return paymentService.paymentSQL(id);
    }
}
