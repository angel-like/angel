package com.xmsy.server.zxyy.manager.modules.manager.batchrechargeprop.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.OrderNoUtil;
import com.xmsy.server.zxyy.manager.modules.app.event.PushService;
import com.xmsy.server.zxyy.manager.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.manager.modules.manager.batchrechargeprop.dao.BatchRechargePropDao;
import com.xmsy.server.zxyy.manager.modules.manager.batchrechargeprop.entity.BatchRechargePropEntity;
import com.xmsy.server.zxyy.manager.modules.manager.batchrechargeprop.service.BatchRechargePropService;
import com.xmsy.server.zxyy.manager.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.shopproprecord.entity.ShopPropRecordEntity;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@Service("batchRechargePropService")
public class BatchRechargePropServiceImpl extends ServiceImpl<BatchRechargePropDao, BatchRechargePropEntity> implements BatchRechargePropService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PushService pushService;
	
	@Autowired
	private UserService userService;	
	
	@Override
	public void createBatchrechargeprop(BatchRechargePropEntity batchRechargeProp,Long sysUserId,
			String sysUserName) {
		log.info("[人工充值创建订单] createbatchRechargeProp {}", batchRechargeProp);
		// 管理员充值订单
		batchRechargeProp.setSysUserId(sysUserId);
		batchRechargeProp.setSysUserAccount(sysUserName);
		batchRechargeProp.setOrderNo(OrderNoUtil.getOrderNo());
		batchRechargeProp.setPropId(batchRechargeProp.getPropId());
		batchRechargeProp.setPropNum(batchRechargeProp.getPropNum());
		batchRechargeProp.setAccount(batchRechargeProp.getAccount());
		this.baseMapper.insert(batchRechargeProp);
		UserEntity entity = new UserEntity();
		entity.setAccount(batchRechargeProp.getAccount());
		UserEntity user = userService.selectOne(new EntityWrapper<UserEntity>(entity));
		UserEntity userProp = new UserEntity();
		userProp.setId(user.getId());
		userProp.setRoomCard(batchRechargeProp.getPropNum());
		Integer i = userDao.updateUserWalletByUserId(userProp);
		if(i>0) {
			// 添加金币兑换房卡表（道具交易记录表）
			ShopPropRecordEntity shopPropRecord = new ShopPropRecordEntity();
			shopPropRecord.setSysPropId(batchRechargeProp.getPropId());// 道具名称id(0表示在道具列表里不存在)
			shopPropRecord.setPropNumber(Integer.parseInt(String.valueOf(batchRechargeProp.getPropNum())));// 兑换的道具数量
			shopPropRecord.setType(Constant.PROP_USE_TYPE_2);// 类型0：购买 1：使用 2赠送
			shopPropRecord.setProduceId(0L); // 产品id(0表示在商城产品表里不存在)
			shopPropRecord.setUserId(user.getId());// 会员id
			shopPropRecord.setUserAccount(batchRechargeProp.getAccount());// 会员账号
			shopPropRecord.insert();
		}
		UserInfoMessage message = new UserInfoMessage(userProp, null);
		log.info("[赠送房卡-推送消息] message {}", message);
		pushService.pushUserInfo(message);
	}


}
