package com.xmsy.network.mq.rabbitmq.producer.test;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.network.mq.rabbitmq.producer.send.Producer;

/**
 * 测试
 *
 * @author test
 * @email xxxxx
 * @date 2019-02-20 16:00:25
 */
@RestController
@RequestMapping("V1.0/test")
public class AppController {

	@Resource
	private Producer producer;

	
	/**
	 * 通过房间ID获取用户玩过的游戏
	 */
	@GetMapping("/test")
	public String userGameRecordForGameId() {
		producer.push("haha");
		return "ok";
	}

}
