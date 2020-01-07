package com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.dao.UserRebateCommissionRecordDao;
import com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.entity.UserRebateCommissionDateDetailsEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.entity.UserRebateCommissionDetailsEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.entity.UserRebateCommissionRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.entity.UserRebateCommissionSumEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.service.UserRebateCommissionRecordService;

@Service("userRebateCommissionRecordService")
public class UserRebateCommissionRecordServiceImpl
		extends ServiceImpl<UserRebateCommissionRecordDao, UserRebateCommissionRecordEntity>
		implements UserRebateCommissionRecordService {
	@Override
	public List<Map<String, Object>> getList(){
		return  this.baseMapper.getList();
	};
	@Override
	public Page<UserRebateCommissionDetailsEntity> getDetailsList(
			UserRebateCommissionDetailsEntity userrebatecommissionrecord, PageParam pageParam) {
		Page<UserRebateCommissionDetailsEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());
//		return page.setRecords(this.baseMapper.getDetailsList(page, userrebatecommissionrecord));
		List<UserRebateCommissionDetailsEntity> dataList = this.baseMapper.getDetailsListForSimplify(page, userrebatecommissionrecord);
		Set<Long> uidList = new HashSet<>();
		for(UserRebateCommissionDetailsEntity user : dataList) {
			uidList.add(user.getUserId());
		}
		List<Map<String, Object>> userBetCoinList = this.baseMapper.findUserTotalBetCoin(uidList);
		if(userBetCoinList != null && !userBetCoinList.isEmpty()) {
			Map<Long, Map<String, Object>> userBetCoin = new HashMap<Long, Map<String, Object>>();
			for(Map<String, Object> data: userBetCoinList) {
				userBetCoin.put(Long.parseLong(data.get("userId").toString()), data);
			}
			for(UserRebateCommissionDetailsEntity user : dataList) {
				Map<String, Object> data = userBetCoin.get(user.getUserId());
				if(data!=null) {
					user.setBetCoins(MathUtil.getBigDecimal(data.get("betCoins")).longValue());
					user.setNum(MathUtil.getBigDecimal(data.get("num")).intValue());
				}else {
					user.setBetCoins(0l);
					user.setNum(0);
				}

			}

		}
		page.setRecords(dataList);
		return page;
	}

	@Override
	public Page<UserRebateCommissionDateDetailsEntity> dateList(
			UserRebateCommissionDateDetailsEntity userRebateCommissionDateDetailsEntity, PageParam pageParam) {
		Page<UserRebateCommissionDateDetailsEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());
		return page.setRecords(this.baseMapper.dateList(page, userRebateCommissionDateDetailsEntity));
	}

	/**
	 * 获取用户指定时间的佣金总额
	 */
	@Override
	public UserRebateCommissionSumEntity selectSumCommission(Long userId, String queryDate, String startDate,
			String endDate) {
		// TODO Auto-generated method stub
		return baseMapper.selectSumCommission(userId, queryDate, startDate, endDate);
	}

}
