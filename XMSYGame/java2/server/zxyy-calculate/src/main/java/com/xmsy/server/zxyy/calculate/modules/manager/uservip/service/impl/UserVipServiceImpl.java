package com.xmsy.server.zxyy.calculate.modules.manager.uservip.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.calculate.modules.manager.headframe.dao.HeadframeDao;
import com.xmsy.server.zxyy.calculate.modules.manager.orderrecharge.dao.OrderRechargeDao;
import com.xmsy.server.zxyy.calculate.modules.manager.push.PushService;
import com.xmsy.server.zxyy.calculate.modules.manager.push.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.calculate.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.calculate.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.userheadframe.dao.UserHeadframeDao;
import com.xmsy.server.zxyy.calculate.modules.manager.userheadframe.entity.UserHeadframeEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.uservip.dao.UserVipDao;
import com.xmsy.server.zxyy.calculate.modules.manager.uservip.entity.UserVipEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.uservip.service.UserVipService;
import com.xmsy.server.zxyy.calculate.modules.manager.userviprecord.entity.UserVipRecordEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("userVipService")
public class UserVipServiceImpl extends ServiceImpl<UserVipDao, UserVipEntity> implements UserVipService {
	@Autowired
	private OrderRechargeDao orderRechargeDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private HeadframeDao headframeDao;
	@Autowired
	private UserHeadframeDao userHeadframeDao;
	@Autowired
	private PushService pushService;
	@Autowired
	private UserVipDao userVipDao;

	@Override
	public void userUpgradeVip(UserEntity user) {
		Long rechargeAmount=orderRechargeDao.sumRechargeAmountByUserId(user.getId());
		if(rechargeAmount == null ) {
			log.error("userAccount:{} rechargeAmount 已经完成的充值金额为null",user.getAccount());
			return;
		}
		UserInfoMessage userInfoMessage = new UserInfoMessage();
		userInfoMessage.setCurrentRechargeAmount(rechargeAmount);
		userInfoMessage.setUid(user.getId());
		log.info("userAccount:{} rechargeAmount 已经完成的充值金额为{}",user.getAccount(),rechargeAmount);
		UserVipEntity userVip = this.baseMapper.getUserVip(rechargeAmount);
		if(userVip == null || userVip.getId() == null) {
			if(user.getVipId()>0){
				userInfoMessage.setNextVipId(-1l);
				log.error("\"userAccount:{} userVip 查无资料 rechargeAmount:{}",user.getAccount(),rechargeAmount);
				pushService.pushUserInfo(userInfoMessage);
				return;

			}
			UserVipEntity userVipEntity = userVipDao.selectNextVip(0);
			userInfoMessage.setNextVipId(userVipEntity.getId());
			userInfoMessage.setNextVipName(userVipEntity.getName());
			userInfoMessage.setNextRechargeAmount(userVipEntity.getRechargeReached());
			pushService.pushUserInfo(userInfoMessage);
			return;

		}else if(user.getVipId().equals(userVip.getId())) {
			log.debug("\"userAccount:{} userVip 和当前用户vip等级一样 rechargeAmount:{}",user.getAccount(),rechargeAmount);
			pushService.pushUserInfo(userInfoMessage);
			return;
		}
		userDao.updateUserVip(user.getId(), userVip.getId());
		UserVipRecordEntity userVipRecord = new UserVipRecordEntity();
		userVipRecord.setUserId(user.getId());
		userVipRecord.setUserAccount(user.getAccount());
		userVipRecord.setVipId(userVip.getId());
		userVipRecord.setRechargeAmount(rechargeAmount);
		userVipRecord.insert();
		List<Long> headframeIdList = headframeDao.getHeadframeList(userVip.getSort());
		if(headframeIdList!=null && !headframeIdList.isEmpty()) {
			List<UserHeadframeEntity> userHeadframeList = userHeadframeDao.selectList(new EntityWrapper<UserHeadframeEntity>
				(new UserHeadframeEntity().setUserId(user.getId())));
			if(userHeadframeList!=null && !userHeadframeList.isEmpty()) {
				for(UserHeadframeEntity userHeadframe: userHeadframeList) {
					headframeIdList.remove(userHeadframe.getHeadframeId());
				}
			}
			if(headframeIdList.isEmpty()) {
				return;
			}
			for(Long headframeId: headframeIdList) {
				UserHeadframeEntity userHeadframe = new UserHeadframeEntity();
				userHeadframe.setHeadframeId(headframeId);
				userHeadframe.setUserId(user.getId());
				userHeadframe.insert();
			}
//
		}

		UserVipEntity userVipEntity = userVipDao.selectNextVip(userVip.getSort());
		if(userVipEntity==null) {
			userInfoMessage.setNextVipId(-1l);
		}else {
			userInfoMessage.setNextVipId(userVipEntity.getId());
			userInfoMessage.setNextVipName(userVipEntity.getName());
			userInfoMessage.setNextRechargeAmount(userVipEntity.getRechargeReached());
		}
		userInfoMessage.setVipId(userVip.getId());
		userInfoMessage.setVipName(userVip.getName());
		pushService.pushUserInfo(userInfoMessage);
	}

	@Override
	public void userUpgradeVip(Long userId) {
		UserEntity user = userDao.selectById(userId);
		if(user==null) {
			return;
		}
		this.userUpgradeVip(user);
	}


}
