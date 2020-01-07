package com.xmsy.server.zxyy.manager.modules.manager.userpointkill.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.service.GameRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.service.OrderTakeMoneyService;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.userbalance.entity.UserBalanceEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userbalance.service.UserBalanceService;
import com.xmsy.server.zxyy.manager.modules.manager.userpointkill.dao.UserPointKillDao;
import com.xmsy.server.zxyy.manager.modules.manager.userpointkill.entity.UserPointKillEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userpointkill.param.UserPointKillManagerParam;
import com.xmsy.server.zxyy.manager.modules.manager.userpointkill.service.UserPointKillService;

@Service("userPointKillService")
public class UserPointKillServiceImpl extends ServiceImpl<UserPointKillDao, UserPointKillEntity>
		implements UserPointKillService {
	@Autowired
	private UserBalanceService userBalanceService;
	@Autowired
	private OrderTakeMoneyService orderTakeMoneyService;
	@Autowired
	private GameRecordService gameRecordService;
	@Autowired
	private UserService userService;
	/**
	 * 查找会员点杀管理的 其他相关数据
	 * 
	 * @param userMapList
	 */
	@Override
	public void findOtherData(List<Map<String, Object>> userMapList,UserEntity user) {
		for (Map<String, Object> map : userMapList) {
			// 获取会员id 或者 会员账号
			Long userId = MathUtil.getBigDecimal(map.get("id")).longValue();
			// 1.获取总资产  金币+余额宝+取款的(取款要*100)
			BigDecimal totalCoin = MathUtil.getBigDecimal(map.get("coin"));
			UserBalanceEntity userBalanceEntity = userBalanceService
					.selectOne(new EntityWrapper<UserBalanceEntity>(new UserBalanceEntity().setUserId(userId)));
			if(userBalanceEntity!=null) {//余额宝=正在计算收益的金额+未计算收益的金额
				totalCoin = totalCoin.add(
						MathUtil.getBigDecimal(userBalanceEntity.getMoney() + userBalanceEntity.getUnprofitMoney()));
			}
			//根据会员id统计当前会员 总的取款金额+行政费(包含未审核 跟已完成)
			BigDecimal totalOrderTakeAmountTake = orderTakeMoneyService.totalOrderTakeAmountTake(userId,
					user.getStartTime(), user.getEndTime());
			if(totalOrderTakeAmountTake!=null) {
				totalCoin=totalCoin.add(totalOrderTakeAmountTake).multiply(MathUtil.getBigDecimal(100));
			}
			map.put("totalCoin", totalCoin);
			//2.获取总投入
			BigDecimal totalPutInto = gameRecordService.totalPutInto(userId,
					user.getStartTime(), user.getEndTime());
			if(totalPutInto==null) {
				totalPutInto=MathUtil.getBigDecimal(0);
			}
			map.put("putInto", totalPutInto);
			//3.获取总产出
			BigDecimal totalPutOut = gameRecordService.totalPutOut(userId,
					user.getStartTime(), user.getEndTime());
			if(totalPutOut==null) {
				totalPutOut=MathUtil.getBigDecimal(0);
			}
			map.put("putOut", totalPutOut);
			//4.收益
			map.put("profit", totalPutOut.subtract(totalPutInto));
		}

	}

	@Override
	public BigDecimal gainTotalCoin(Long userId) {
		UserEntity user = userService.selectById(userId);
		if(user==null) {
			return MathUtil.getBigDecimal(0);
		}
		//1.用户当前金币
		BigDecimal totalCoin = MathUtil.getBigDecimal(user.getCoin());
		//2.余额宝=正在计算收益的金额+未计算收益的金额
		UserBalanceEntity userBalanceEntity = userBalanceService
				.selectOne(new EntityWrapper<UserBalanceEntity>(new UserBalanceEntity().setUserId(userId)));
		if(userBalanceEntity!=null) {
			totalCoin = totalCoin.add(
					MathUtil.getBigDecimal(userBalanceEntity.getMoney() + userBalanceEntity.getUnprofitMoney()));
		}
		//3.总的取款金额+行政费(包含未审核 跟已完成)
		BigDecimal totalOrderTakeAmountTake = orderTakeMoneyService.totalOrderTakeAmountTake(userId,null,null);
		if(totalOrderTakeAmountTake!=null) {
			totalCoin=totalCoin.add(totalOrderTakeAmountTake).multiply(MathUtil.getBigDecimal(100));
		}
		return totalCoin;
	}

	@Override
	public List<Map<String, Object>> findUserPointKillManagerPage(UserPointKillManagerParam userPointKillManagerParam,
			Page<Map<String, Object>> page) {
		return this.baseMapper.findUserPointKillManagerPage(userPointKillManagerParam, page);
	}

}
