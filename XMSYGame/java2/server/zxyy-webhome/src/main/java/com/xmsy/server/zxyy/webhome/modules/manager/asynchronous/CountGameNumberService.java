package com.xmsy.server.zxyy.webhome.modules.manager.asynchronous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.usergamenumber.service.UserGameNumberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CountGameNumberService {
	
	@Autowired
	private UserGameNumberService userGameNumberService;

	@Async
	public void countUserGameNumAsync(GameRecordEntity gameRecord) {
		log.debug("用户游戏记录 gameRecord:{}", gameRecord);
		try {
			userGameNumberService.saveUserGameNumber(gameRecord);
		} catch (Exception e) {
			log.info("CountGameNumberService 计算会员游戏次数报错");
			log.error(e.getMessage());
		}
	}
}
