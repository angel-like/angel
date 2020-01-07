package com.xmsy.server.zxyy.schedule.schedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.server.GiftCoinConfigService;
import com.xmsy.server.zxyy.schedule.server.UserService;
import com.xmsy.server.zxyy.schedule.utils.Constant;
import com.xmsy.server.zxyy.schedule.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户救济金 定时任务
 * 
 * @author adu
 *
 */
@Slf4j
@Component
public class UserReliftRewardScheduleTask {
	
	@Autowired
	private UserService userService;
	@Autowired
	private GiftCoinConfigService giftCoinConfigService;
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	/**
	 * 查询用户余额，检查是否达到送救济金
	 */
//	@Scheduled(cron = "0 0 0/1 * * ? ")
	@Scheduled(fixedDelay = 1000 * 60)
	public void userReliftReward() {
		try {
			
			int type =Constant.UserActivityAwardType.RELIEF.getValue();
			JSONArray configList = giftCoinConfigService.findGiftCoinConfigList(type);
			if (configList == null || configList.size() == 0) {
				log.debug("救济金userReliftReward config 查无记录");
				return;
			}
			Date nowDate = new Date();
			for(int i = 0 ;i<configList.size();i++) {
				JSONObject config = configList.getJSONObject(i);
				int maxNum = config.getIntValue("maxNum");
				int num = config.getIntValue("num");
				int cycle = config.getIntValue("cycle");
				Long vipId = config.getLong("vipId");
				vipId=vipId==null?0:vipId;
				Date startDate = DateUtils.addDayZeroPoint(nowDate, (cycle-1)*-1);
				JSONArray dataArray = userService.findUserListByBankruptcy(DateUtils.formatTime(startDate), type,num, maxNum,vipId);
				if (dataArray == null || dataArray.size() == 0) {
					log.debug("救济金userReliftReward config:{} 用户达标查无记录",config);
					continue;
				}
				for(int j = 0 ;j<dataArray.size();j++) {
					saveUserGiftCoin(dataArray.getJSONObject(j), config);
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveUserGiftCoin(JSONObject userGame,JSONObject giftCoinConfig) {
		try {
			threadPoolTaskExecutor.execute(new Thread(() -> {
				giftCoinConfigService.saveUserReliftCoin(userGame, giftCoinConfig);
			}));
		} catch (Exception e) {
			log.error("userReliftReward error {}", e);
		}
	}

}
