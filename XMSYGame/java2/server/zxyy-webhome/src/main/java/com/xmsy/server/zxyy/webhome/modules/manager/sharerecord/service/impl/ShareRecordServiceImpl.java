package com.xmsy.server.zxyy.webhome.modules.manager.sharerecord.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.DateUtils;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.app.share.param.ShareParam;
import com.xmsy.server.zxyy.webhome.modules.manager.giftcoinconfig.entity.GiftCoinConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.giftcoinconfig.service.GiftCoinConfigService;
import com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.service.MessageManagementService;
import com.xmsy.server.zxyy.webhome.modules.manager.playertasks.entity.PlayerTasksEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sharerecord.dao.ShareRecordDao;
import com.xmsy.server.zxyy.webhome.modules.manager.sharerecord.entity.ShareRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sharerecord.service.ShareRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.signuserrecord.entity.SignUserRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.signuserrecord.service.SignUserRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalancerecord.entity.UserBalanceRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalancerecord.service.UserBalanceRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.usergiftrecord.entity.UserGiftRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.usergiftrecord.service.UserGiftRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationrecord.entity.UserRecommendationRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationrecord.service.UserRecommendationRecordService;

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
	private UserRecommendationRecordService userRecommendationRecordService;
	
	@Autowired
	private SignUserRecordService signUserRecordService;
	@Autowired
	private UserBalanceRecordService userBalanceRecordService;
	
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
		configParam.setEnable(true);
		List<GiftCoinConfigEntity> giftCoinConfigList = giftCoinConfigService.
				selectList(new EntityWrapper<GiftCoinConfigEntity>(configParam).orderBy("id", true));
		Date nowDate =new Date();
		for(GiftCoinConfigEntity config:giftCoinConfigList) {
			 //假如用户vip等级小于配置的等级，就跳出循环
			 if(user.getVipId() < config.getVipId()) {
				 continue;
			 }
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
	/**
	 * 福缘任务
	 * 通过会员分享的次数 +已领取次数   =》 获取是否完成（通过已领取次数*条件判断），完成次数
	 */
	@Override
	public PlayerTasksEntity otherTask(UserEntity user, PlayerTasksEntity playerTask, int cycle) {
		// 1 查询 周期（当天）时间内 会员分享的次数
		ShareRecordEntity shareRecordEntity=new ShareRecordEntity();
		shareRecordEntity.setUserId(user.getId());//会员id
		EntityWrapper<ShareRecordEntity> wrapper = new EntityWrapper<ShareRecordEntity>(shareRecordEntity);
		wrapper.ge("create_time", DateUtils.addDayZeroPoint(new Date(),cycle));//周期条件 1天  cycle为0
		wrapper.le("create_time", new Date());
		Integer doTaskNum=0;//做的任务次数
		// 2 通过不同分享类型  或者  功能类型 判断当天所做的任务次数
		/*if(playerTask.getFunctionalType()==0) {//随便分享记录都行
			doTaskNum = this.selectCount(wrapper);//获取任务次数
			playerTask.setCompleteNum(doTaskNum);
		}else if(playerTask.getFunctionalType()==1) {//分享QQ
			shareRecordEntity.setShareTo("QQ");
			doTaskNum = this.selectCount(wrapper);//获取任务次数
			playerTask.setCompleteNum(doTaskNum);
		}else */
		if("shareWeChat".equals(playerTask.getFunctionalType())) {/**分享微信*/
			shareRecordEntity.setShareTo("WeChat");
			doTaskNum = this.selectCount(wrapper);//获取任务次数
			playerTask.setCompleteNum(doTaskNum);
		}else if("peopleAgency".equals(playerTask.getFunctionalType())) {/**发展下级代理*/
			EntityWrapper<UserRecommendationRecordEntity> wrapper2 = new EntityWrapper<UserRecommendationRecordEntity>(
					new UserRecommendationRecordEntity().setPromoterId(user.getId()));
			wrapper2.ge("create_time", DateUtils.addDayZeroPoint(new Date(),cycle));//周期条件 1天  cycle为0
			wrapper2.le("create_time", new Date());
			doTaskNum = userRecommendationRecordService.selectCount(wrapper2);//获取任务次数
			playerTask.setCompleteNum(doTaskNum);
		}
		else if("signEveryDay".equals(playerTask.getFunctionalType())) {/**每日签到*/
			EntityWrapper<SignUserRecordEntity> wrapperSign = new EntityWrapper<SignUserRecordEntity>(
					new SignUserRecordEntity().setUserId(user.getId()));
			wrapperSign.ge("create_time", DateUtils.addDayZeroPoint(new Date(),cycle));//周期条件 1天  cycle为0
			wrapperSign.le("create_time", new Date());
			doTaskNum = signUserRecordService.selectCount(wrapperSign);
			playerTask.setCompleteNum(doTaskNum);
		}else if("yuEBao".equals(playerTask.getFunctionalType())) {/**余额宝*/
			EntityWrapper<UserBalanceRecordEntity> wrapperSign = new EntityWrapper<UserBalanceRecordEntity>(
					new UserBalanceRecordEntity().setUserId(user.getId()).setType(0));//0:存入   1:取出
			wrapperSign.ge("create_time", DateUtils.addDayZeroPoint(new Date(),cycle));//周期条件 1天  cycle为0
			wrapperSign.le("create_time", new Date());
			doTaskNum = userBalanceRecordService.selectCount(wrapperSign);
			playerTask.setCompleteNum(doTaskNum);
		}
		// 3 做的任务次数-已领取次数*条件 >=满足的条件，将是否完成改成true，否的话将是否完成改成false
		if ((doTaskNum - playerTask.getReceiveTaskNum() * playerTask.getCondition()) >= playerTask.getCondition()) {
			playerTask.setWhetherComplete(true);
			if(playerTask.getReceiveTaskNum()<playerTask.getTaskNum()) {//领取次数<任务设置次数 
				Integer unTaskNumTotal=(doTaskNum - playerTask.getReceiveTaskNum() * playerTask.getCondition()) /playerTask.getCondition();
				Integer unTaskNum=unTaskNumTotal + playerTask.getReceiveTaskNum() > playerTask.getTaskNum()
						? playerTask.getTaskNum() - playerTask.getReceiveTaskNum()
						: unTaskNumTotal;
				user.setUnTaskNum(user.getUnTaskNum()+unTaskNum);//已完成任务数+unTaskNum  红点
			}
		} else {
			playerTask.setWhetherComplete(false);
		}
		return playerTask;
	}
	


}
