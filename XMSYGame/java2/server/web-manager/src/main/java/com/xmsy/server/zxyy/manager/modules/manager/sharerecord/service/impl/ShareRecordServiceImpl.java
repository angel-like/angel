package com.xmsy.server.zxyy.manager.modules.manager.sharerecord.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.DateUtils;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.modules.app.event.PushService;
import com.xmsy.server.zxyy.manager.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.manager.modules.app.share.param.ShareParam;
import com.xmsy.server.zxyy.manager.modules.manager.giftcoinconfig.entity.GiftCoinConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.giftcoinconfig.service.GiftCoinConfigService;
import com.xmsy.server.zxyy.manager.modules.manager.messagemanagement.service.MessageManagementService;
import com.xmsy.server.zxyy.manager.modules.manager.sharerecord.dao.ShareRecordDao;
import com.xmsy.server.zxyy.manager.modules.manager.sharerecord.entity.ShareRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sharerecord.service.ShareRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.usergiftrecord.entity.UserGiftRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.usergiftrecord.service.UserGiftRecordService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("shareRecordService")
public class ShareRecordServiceImpl extends ServiceImpl<ShareRecordDao, ShareRecordEntity> implements ShareRecordService {

	@Autowired
	private UserGiftRecordService userGiftRecordService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PushService pushService;
	
	@Autowired
	private MessageManagementService messageManagementService;
	
	@Autowired
	private GiftCoinConfigService giftCoinConfigService;
	public static final int CONTENT_TYPE = 1; // 用户邮件类型
	public static final int TARGET_OBJECT = 1;// 指定用户
	
	/**
	 * 分享App赠送奖励金
	 */
	@Override
	public void shareAppGetCoin(ShareParam param,UserEntity user) {
		if(param.getClientType() == null) {
			throw new RRException(ErrorCode.ShareErrorCode.CLIENT_TYPE_ERRO.getErrMsg(),
					ErrorCode.ShareErrorCode.CLIENT_TYPE_ERRO.getCode());
		}
		if(param.getShareTo() == null) {
			throw new RRException(ErrorCode.ShareErrorCode.SHARE_TO_ERRO.getErrMsg(),
					ErrorCode.ShareErrorCode.SHARE_TO_ERRO.getCode());
		}
		/*================新增分享记录表==================*/
		ShareRecordEntity entity = new ShareRecordEntity();
		entity.setClientType(param.getClientType());
		entity.setShareTo(param.getShareTo());
		entity.setUserAccount(user.getAccount());
		entity.setUserId(user.getId());
		this.baseMapper.insert(entity);
		GiftCoinConfigEntity configParam=new GiftCoinConfigEntity();
		configParam.setType(Constant.UserActivityAwardType.SHARE.getValue());
		List<GiftCoinConfigEntity> giftCoinConfigList = giftCoinConfigService.
				selectList(new EntityWrapper<GiftCoinConfigEntity>(configParam).orderBy("id", true));
		Date nowDate =new Date();
		for(GiftCoinConfigEntity config:giftCoinConfigList) {
			 Date startDate = DateUtils.addDayZeroPoint(nowDate, (config.getCycle()-1)*-1);
			 ShareRecordEntity query = new ShareRecordEntity();
			 query.setUserId(user.getId());
			 int shareNum = this.baseMapper.selectCount(new EntityWrapper<ShareRecordEntity>(query).ge("create_time", startDate));
			 //分享的次数小于鼓励金配置的分享次数，终止
			 if(config.getNum() == null ||config.getNum()==0) {
				 continue;
			 }else if(shareNum%config.getNum()>0) {
				 continue;
			 }
			 //分享应该获得奖励的次数
			 int num=shareNum/config.getNum();
			 UserGiftRecordEntity userGiftRecord=new UserGiftRecordEntity();
			 userGiftRecord.setType(Constant.ACTIVITYTYPE_1);
			 userGiftRecord.setDetailType(Constant.UserActivityAwardType.SHARE.getValue());
			 userGiftRecord.setUserId(user.getId());
			 int giftNum=userGiftRecordService.selectCount(new EntityWrapper<UserGiftRecordEntity>(userGiftRecord)
					 .ge("create_time", startDate));
			 if(giftNum >=num || giftNum>=config.getMaxNum()) {
				 //发放次数达到最大值
				 continue;
			 }
			 Long userCoin=0l;
			 if(config.getSendType()==Constant.SENDTYPE_0) {
				 userCoin=config.getCoin();
				 userService.updateUserCoin(user, config.getCoin(),
						 Constant.TransactionType.REBATE.getValue(), 
						 Constant.TransactionDetailType.USERSHARE.getValue());
			 }
			 userGiftRecord.setUserAccount(user.getAccount());
			 userGiftRecord.setSendType(config.getSendType());
			 userGiftRecord.setReceive(config.getSendType()==Constant.SENDTYPE_0);
			 userGiftRecord.setNum(config.getCoin().intValue());
			 userGiftRecord.setPropId(Constant.COIN_PROP_ID);
			 userGiftRecordService.insertGetId(userGiftRecord);
			 messageManagementService.saveUserMessage(userGiftRecord.getId(),userGiftRecord.getReceive(), Constant.ACTIVITYTYPE_1, config.getTemplateId(),
					 user.getId(), user.getAccount());
			 UserEntity pushMessage = new UserEntity();
			pushMessage.setCoin(userCoin);
			pushMessage.setId(user.getId());
			pushMessage.setUnreadNum(1);
			UserInfoMessage message = new UserInfoMessage(pushMessage, null);
			log.info("[用户分享app送奖励金-推送消息] message {}", message);
			pushService.pushExchange(message);
		}
	}
	


}
