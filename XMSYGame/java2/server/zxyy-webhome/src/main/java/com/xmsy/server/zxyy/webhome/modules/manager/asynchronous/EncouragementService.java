package com.xmsy.server.zxyy.webhome.modules.manager.asynchronous;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.webhome.cache.EhCacheName;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.DateUtils;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.giftcoinconfig.entity.GiftCoinConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.giftcoinconfig.service.GiftCoinConfigService;
import com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.service.MessageManagementService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.usergiftrecord.entity.UserGiftRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.usergiftrecord.service.UserGiftRecordService;

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

	public static final String KEY_CACHE_PRE = "userLoseNum:";

	@Async
	public void grantEncouragementAsync(GameRecordEntity gameRecord) {
		String key = KEY_CACHE_PRE + gameRecord.getUserId();
		UserEntity user = userService.selectById(gameRecord.getUserId());
		if (user == null || user.getId() == null) {
			log.info("查无此用户 userId:{}", gameRecord.getUserId());
			return;
		}
		log.debug("gameRecord:{}", gameRecord);
		Object loseNumObj = valueOperations.get(EhCacheName.USER_VICTORY_RECORD_CACHE + key);
		Integer loseNum = 0;
		if (loseNumObj != null) {
			loseNum = Integer.valueOf(loseNumObj.toString());
		}
		if (gameRecord.getPrizeCoins().compareTo(0l) < 0) {
			loseNum++;
		} else {
			loseNum = 0;
		}
		log.debug("loseNum:{}", loseNum);
		valueOperations.set(EhCacheName.USER_VICTORY_RECORD_CACHE + key, loseNum);
		List<GiftCoinConfigEntity> configList = giftCoinConfigService.getEncouragementConfig();
		if (configList != null && !configList.isEmpty()) {
			log.debug("configList:{}", configList);
			Date nowDate = new Date();
			for (GiftCoinConfigEntity config : configList) {
				// 连输的次数小于鼓励金配置的连输次数，终止
				if (loseNum < config.getNum()) {
					break;
				}
				if(config.getVipId() !=null && config.getVipId().compareTo(user.getVipId())>0) {
					//会员的vip等级不符合
					continue;
				}
				int shouldNum = config.getNum()==null || config.getNum()<=0 ?0:loseNum/config.getNum();
				Date startDate = DateUtils.addDayZeroPoint(nowDate, (config.getCycle() - 1) * -1);
				UserGiftRecordEntity userGiftRecord = new UserGiftRecordEntity();
				userGiftRecord.setType(Constant.ACTIVITYTYPE_1);
				userGiftRecord.setDetailType(Constant.UserActivityAwardType.ENCOURAGE.getValue());
				userGiftRecord.setUserId(gameRecord.getUserId());

				int num = userGiftRecordService.selectCount(
						new EntityWrapper<UserGiftRecordEntity>(userGiftRecord).ge("create_time", startDate));
				if (num >= config.getMaxNum() || shouldNum<=num) {
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
				log.debug("[用户连输鼓励金-推送消息] message {}", message);
				pushService.pushExchange(message);

			}
		}
	}
}
