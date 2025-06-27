package com.ljx.redis.controller;

import com.ljx.redis.service.EnvelopeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: RedPackageController
 * Package: com.ljx.redis.controller
 * Description:
 *    模拟微信抢红包的功能
 * @Author Xu, Luqin
 * @Create 2024/11/5 22:24
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping("/envelope")
@Api(tags = "模拟微信抢红包")
public class EnvelopeController {
    @Autowired
    private EnvelopeService envelopeService;
    @PostMapping("/send")
    public String send(Integer totalMoney, Integer count){
        return envelopeService.send(totalMoney, count);
    }

    @PostMapping("/hunt")
    public String hunt(String envelopeUUID,String userId){
        return envelopeService.hunt(envelopeUUID,userId);
    }
}
