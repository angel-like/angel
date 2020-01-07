package com.xmsy.server.webhome;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;

public class RedisTest {
	@Autowired
	RedisTemplate<String, UserInfoMessage>  redisTemplate;
	@Autowired
	StringRedisTemplate  stringRedisTemplate;//(k,v)都是字符串的

	
	/**
	 * 五大数据类型
	 * String(字符串),List(列表集合),Set(集合),Hash(散列),Zset(有序集合)
	 * 		stringRedisTemplate.opsForValue()
	 * 		stringRedisTemplate.opsForList()
	 * 		stringRedisTemplate.opsForSet()
	 * 		stringRedisTemplate.opsForHash()
	 * 		stringRedisTemplate.opsForZSet()
	 * 		
	 */
	@Test
	public void stringRedisTemplateTest() {
		//保存   为什么这边注入的都是空的
		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
		valueOperations.set("valueOperations", "valueOperations");//valueOperations就是最基本的操作对象
		ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
		listOperations.leftPush("listOperations", "listOperations");//类似的
		
		
		stringRedisTemplate.opsForValue().set("msg", "stringRedisTemplate的测试");
		stringRedisTemplate.opsForValue().append("msgAppend", "msgAppend");
		
		String msg = stringRedisTemplate.opsForValue().get("msg");
		String msgAppend = stringRedisTemplate.opsForValue().get("msgAppend");
		System.out.println(msg+"---"+msgAppend);
		
		stringRedisTemplate.opsForList().leftPush("list", "1");
		stringRedisTemplate.opsForList().leftPush("list", "2");
		
	}
	/**
	 * 保存对象
	 */
	@Test
	public void RedisTemplateTest() {
		UserInfoMessage userInfoMessage=new UserInfoMessage();//类要可序列化的
		userInfoMessage.setVipId(1L);
		redisTemplate.opsForValue().set("userInfoMessage", userInfoMessage);
	}
}
