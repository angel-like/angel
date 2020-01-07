package com.xmsy.server.zxyy.webhome.modules.manager.asynchronous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Component
public class UpdateUserAccountService {

	@Autowired
	private UserService userService;

	@Async
	public void UpdateUserAccountAsync(Long userId,String account) {
		try {
			userService.batchUpgradeUserAccount(userId, account);
		} catch (Exception e) {
			log.error("批量更新用户账号 error {}",e.getMessage());
		}
	}
}
