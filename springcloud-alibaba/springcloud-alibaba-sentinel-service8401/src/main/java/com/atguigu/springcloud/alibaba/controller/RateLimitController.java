package com.atguigu.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.alibaba.myhandler.CustomerBlockHandler;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author lixiaolong
 * @date 2020-02-25 15:04
 */
@RestController
@RequestMapping("/sentinel")
public class RateLimitController {

    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    //配置流控规则用byResource ， 报handleException里错误  笔记里面的a与b中的a
    //配置流控规则用/sentinel/byResource，报默认报错
    public CommonResult byResource() {
        return new CommonResult(200, "按资源名称限流测试OK", new Payment(2020L, "serial001"));
    }

    public CommonResult handleException(BlockException exception) {
        return new CommonResult(444, exception.getClass().getCanonicalName() + "\t 服务不可用");
    }

    /**
     * Nacos里的限流配置 ( QPS 单机阈值1)
     * [{
     * 	"resource": "/sentinel/rateLimit/byUrl",
     * 	"limitApp": "default",
     * 	"grade": 1,
     * 	"count": 1,
     * 	"strategy": 0,
     * 	"controlBehavior": 0,
     * 	"clusterMode": false
     * }]
     *  resource：
     *  limitApp： 来源应用
     *  grade：阈值类型 0-线程数、1-QPS
     *  count：单据阈值
     *  strategy：流控模式 0-直接、1-关联、2-链路
     *  controlBehavior：流控效果  0-快速失败、 1-Warm Up、 2排队等待
     *  clusterMode：是否集群
     * @return
     */
    @GetMapping("/rateLimit/byUrl")
    @SentinelResource(value = "byUrl")
    //配置流控规则用/sentinel/rateLimit/byUrl,默认报错   笔记里面的a与b中的b
    //如果流控规则用byUrl，报页面错误(Whitelabel Error Page)，因为这里没写blockHandler，所以不行，都不是默认报错
    public CommonResult byUrl() {
        return new CommonResult(200, "按url限流测试OK", new Payment(2020L, "serial002"));
    }


    @GetMapping("/rateLimit/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",blockHandler = "handlerException2",blockHandlerClass = CustomerBlockHandler.class)
    //这里流控规则用customerBlockHandler ，跳转到CustomerBlockHandler类的handlerException2下
    //如果流控规则用/sentinel/rateLimit/customerBlockHandler路径，则跳转默认报错 Blocked by Sentinel (flow limiting)
    public CommonResult customerBlockHandler() {
        return new CommonResult(200, "按客戶自定义", new Payment(2020L, "serial003"));
    }
}