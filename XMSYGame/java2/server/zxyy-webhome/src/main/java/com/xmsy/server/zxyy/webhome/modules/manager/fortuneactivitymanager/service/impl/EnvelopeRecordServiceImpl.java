package com.xmsy.server.zxyy.webhome.modules.manager.fortuneactivitymanager.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.modules.manager.fortuneactivitymanager.dao.EnvelopeRecordDao;
import com.xmsy.server.zxyy.webhome.modules.manager.fortuneactivitymanager.entity.EnvelopeRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.fortuneactivitymanager.entity.EnvelopeTaskConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.fortuneactivitymanager.entity.FortuneActiviConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.fortuneactivitymanager.service.EnvelopeRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.fortuneactivitymanager.service.EnvelopeTaskConfigService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.utils.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("envelopeRecordService")
public class EnvelopeRecordServiceImpl extends ServiceImpl<EnvelopeRecordDao, EnvelopeRecordEntity> implements EnvelopeRecordService {
	@Autowired
	private EnvelopeTaskConfigService envelopeTaskConfigService;
	@Autowired
	private UserService userService;
	/**
	 * 开启一个红包
	 * @param envelopeRecord
	 */
	@Override
	public Long openEnvelopeOne(EnvelopeRecordEntity envelopeRecord,FortuneActiviConfigEntity fortuneActiviConfig,UserEntity user) {
		//1.查找红包对应事件
		EnvelopeTaskConfigEntity envelopeTaskConfig = envelopeTaskConfigService.selectById(envelopeRecord.getEventId());
		if(envelopeTaskConfig==null) {  //一般不会发生这种情况
			envelopeRecord.setOpenTime(DateUtils.getToday());//开启时间
			envelopeRecord.setStatus(1);//1表示已开启
			envelopeRecord.updateById();
			throw new RRException(ErrorCode.UserEnvelope.USER_ENVELOPE_TASK_ERROR.getErrMsg(),
					ErrorCode.UserEnvelope.USER_ENVELOPE_TASK_ERROR.getCode());
		}
		//2.获取随机金币
		Random random = new Random();
		int max=fortuneActiviConfig.getMaxnumReward().intValue();
		int min=fortuneActiviConfig.getMinnumReward().intValue();
		int coinInt = random.nextInt(max)%(max-min+1) + min;
		long coin=(long)coinInt;
		//3.修改会员金币
		userService.updateUserCoin(user, coin, Constant.TransactionType.ENVELOPETASK.getValue(),
				Constant.TransactionDetailType.ONE_ENVELOPE_RECEIVE.getValue());
		//4.修改红包记录
		envelopeRecord.setOpenTime(DateUtils.getToday());//开启时间
		envelopeRecord.setOpenNum(1);//单个的开启数量为1
		envelopeRecord.setReceiveCoin(coin);//获得的金币
		envelopeRecord.setBeforeOpenCoin(user.getCoin());//开启前金币
		envelopeRecord.setAfterOpenCoin(user.getCoin()+coin);//开启后金币
		envelopeRecord.setStatus(1);//1表示已开启
		envelopeRecord.updateById();
		return coin;
	}
	/**
	 * 开启全部红包
	 * @param envelopeRecord
	 */
	@Override
	public Long openEnvelopeAll(List<EnvelopeRecordEntity> envelopeRecordlist, FortuneActiviConfigEntity fortuneActiviConfig,
			UserEntity user) {
		Long coin=0l;
		Date now = DateUtils.getToday();//统一开启时间
		//1.遍历所有红包记录
		for(EnvelopeRecordEntity envelopeRecord:envelopeRecordlist) {
			//2.获取  随机金币
			Random random = new Random();
			int max = fortuneActiviConfig.getMaxnumReward().intValue();
			int min = fortuneActiviConfig.getMinnumReward().intValue();
			int coinOne = random.nextInt(max)%(max-min+1) + min;
			coin+=coinOne;
			//修改每个红包的开启时间 获得的金币  状等等
			envelopeRecord.setOpenTime(now);//开启时间
			envelopeRecord.setOpenNum(envelopeRecordlist.size());//全部的开启数量为多少个
			envelopeRecord.setReceiveCoin((long)coinOne);//获得的金币
			envelopeRecord.setBeforeOpenCoin(user.getCoin()+coin-(long)coinOne);//开启前金币
			envelopeRecord.setAfterOpenCoin(user.getCoin()+coin);//开启后金币
			envelopeRecord.setStatus(1);//1表示已开启
		}
		// 3.修改会员金币
		userService.updateUserCoin(user, coin, Constant.TransactionType.ENVELOPETASK.getValue(),
				Constant.TransactionDetailType.MANY_ENVELOPE_RECEIVE.getValue());
		this.updateBatchById(envelopeRecordlist);
		return coin;
		
	}



}
