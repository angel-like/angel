package com.xmsy.server.zxyy.webhome.modules.manager.userpointkill.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
//import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userpointkill.dao.UserPointKillDao;
import com.xmsy.server.zxyy.webhome.modules.manager.userpointkill.entity.UserPointKillEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userpointkill.service.UserPointKillService;


@Service("userPointKillService")
public class UserPointKillServiceImpl extends ServiceImpl<UserPointKillDao, UserPointKillEntity> implements UserPointKillService {
	@Autowired
	private PushService pushService;
	/*@Autowired
	private UserService userService;*/
	/**
	 * 通过每一局游戏记录   计算剩余多少解除点杀名单  跟  退出点杀名单 
	 * @param gameRecord
	 */
	@Override
	@Transactional
	public void countUserPointKill(GameRecordEntity gameRecord) {
		//1.查询该用户是否     在点杀名单  + 点杀状态
		UserPointKillEntity userPointKill = this.baseMapper.selectOne(
				new UserPointKillEntity().setUserId(gameRecord.getUserId()).setPointKillEnable(true));
		if(userPointKill==null) {
			return;
		}
		//2.获取该用户   赢了了多少   =中奖金币(已经判断赢了多少了)					下注总金币-中奖金币
		Long winCoin=gameRecord.getPrizeCoins();//gameRecord.getBetCoins()-gameRecord.getPrizeCoins();
		//3.剩余解除退出金额 
		Long remainAmount=userPointKill.getRemainAmount()+winCoin;
		if(remainAmount<=0) {//3.1如果达到  退出 点杀名单要求
			//3.1.1修改点杀名单
			userPointKill.setRemainAmount(remainAmount);//设置  剩余解除退出金额 为0
			userPointKill.setPointKillEnable(false);//设置  已解除点杀名单
			userPointKill.setRemake("已回收足够金额");
			userPointKill.setOperationTime(new Date());
			this.baseMapper.updateById(userPointKill);
			//3.1.2修改用户表
			UserEntity user=new UserEntity();
			user.setId(gameRecord.getUserId());
			user.setPointKillEnable(false);//退出点杀名单
			user.setPointKillRate(new BigDecimal(0));//点杀概率变为0
			user.updateById();//AR更新 User表
			//3.1.3通知游服退出点杀名单
			//UserEntity userEntity=userService.selectById(gameRecord.getUserId());
			UserInfoMessage userInfoMessage=new UserInfoMessage();
			userInfoMessage.setUid(gameRecord.getUserId());
			userInfoMessage.setPointKillEnable(false);
			pushService.pushServerUserInfo(userInfoMessage);
			return;
		}
		//3.2如果未达到  退出 点杀名单要求
		userPointKill.setRemainAmount(remainAmount);//修改剩余解除退出金额
		this.baseMapper.updateById(userPointKill);
	}


}
