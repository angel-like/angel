package com.xmsy.server.zxyy.manager.modules.manager.asynchronous;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.manager.cache.EhCacheName;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.DateUtils;
import com.xmsy.server.zxyy.manager.modules.app.event.PushService;
import com.xmsy.server.zxyy.manager.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.giftcoinconfig.entity.GiftCoinConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.giftcoinconfig.service.GiftCoinConfigService;
import com.xmsy.server.zxyy.manager.modules.manager.messagemanagement.service.MessageManagementService;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.usergiftrecord.entity.UserGiftRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.usergiftrecord.service.UserGiftRecordService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Component
public class EncouragementService {

	@Autowired
	private GiftCoinConfigService giftCoinConfigService;
	@Autowired
	private UserService userService;
	@Autowired
	private PushService pushService;
	@Autowired
	private MessageManagementService messageManagementService;
	@Autowired
	private UserGiftRecordService userGiftRecordService;
	@Autowired
	private ValueOperations<String, Object> valueOperations;

	public static final String KEY_CACHE_PRE = "userLoseNum_";

	@Async
	public void grantEncouragementAsync(GameRecordEntity gameRecord) {
		String key = KEY_CACHE_PRE + gameRecord.getUserId();
		UserEntity user = userService.selectById(gameRecord.getUserId());
		if (user == null || user.getId() == null) {
			log.info("查无此用户 userId:{}", gameRecord.getUserId());
			return;
		}
		log.info("gameRecord:{}", gameRecord);
		Object loseNumObj = valueOperations.get(EhCacheName.USER_VICTORY_RECORD_CACHE + key);
		Integer loseNum = Integer.valueOf(loseNumObj.toString());
		if (loseNum == null) {
			loseNum = 0;
		}
		if (gameRecord.getPrizeCoins().compareTo(0l) < 0) {
			loseNum++;

		} else {
			loseNum = 0;
		}
		log.info("loseNum:{}", loseNum);
		valueOperations.set(EhCacheName.USER_VICTORY_RECORD_CACHE + key, loseNum);
		List<GiftCoinConfigEntity> configList = giftCoinConfigService.getEncouragementConfig();
		if (configList != null && !configList.isEmpty()) {
			log.info("configList:{}", configList);
			Date nowDate = new Date();
			for (GiftCoinConfigEntity config : configList) {
				// 连输的次数小于鼓励金配置的连输次数，终止
				if (loseNum < config.getNum()) {
					break;
				}
				Date startDate = DateUtils.addDayZeroPoint(nowDate, (config.getCycle() - 1) * -1);
				UserGiftRecordEntity userGiftRecord = new UserGiftRecordEntity();
				userGiftRecord.setType(Constant.ACTIVITYTYPE_1);
				userGiftRecord.setDetailType(Constant.UserActivityAwardType.ENCOURAGE.getValue());
				userGiftRecord.setUserId(gameRecord.getUserId());

				int num = userGiftRecordService.selectCount(
						new EntityWrapper<UserGiftRecordEntity>(userGiftRecord).ge("create_time", startDate));
				if (num >= config.getMaxNum()) {
					// 发放次数达到最大值
					return;
				}
				Long userCoin = 0l;
				if (config.getSendType() == Constant.SENDTYPE_0) {
					userCoin = config.getCoin();
					userService.updateUserCoin(user, config.getCoin(), Constant.TransactionType.REBATE.getValue(),
							Constant.TransactionDetailType.USERENCOURAGE.getValue());
				}
				userGiftRecord.setUserAccount(gameRecord.getUserAccount());
				userGiftRecord.setSendType(config.getSendType());
				userGiftRecord.setReceive(config.getSendType() == Constant.SENDTYPE_0);
				userGiftRecord.setNum(config.getCoin().intValue());
				userGiftRecord.setPropId(Constant.COIN_PROP_ID);
				userGiftRecordService.insertGetId(userGiftRecord);
				messageManagementService.saveUserMessage(userGiftRecord.getId(), userGiftRecord.getReceive(),
						Constant.ACTIVITYTYPE_1, config.getTemplateId(), gameRecord.getUserId(),
						gameRecord.getUserAccount());
				UserEntity pushMessage = new UserEntity();
				pushMessage.setCoin(userCoin);
				pushMessage.setId(user.getId());
				pushMessage.setUnreadNum(1);
				UserInfoMessage message = new UserInfoMessage(pushMessage, null);
				log.info("[用户连输鼓励金-推送消息] message {}", message);
				pushService.pushExchange(message);

			}
		}
	}
}
