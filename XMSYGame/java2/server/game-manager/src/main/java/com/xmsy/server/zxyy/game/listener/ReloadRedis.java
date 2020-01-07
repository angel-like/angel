package com.xmsy.server.zxyy.game.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
@Component
public class ReloadRedis {
    @Autowired
    private ReloadInit reloadInit;
	
	@Async//异步调用
	public  void saveToRedis() {
		try {
			Thread.sleep(3000);//睡3秒是为了数据已经插入后 再去查询数据库 数据
			reloadInit.reloadInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
