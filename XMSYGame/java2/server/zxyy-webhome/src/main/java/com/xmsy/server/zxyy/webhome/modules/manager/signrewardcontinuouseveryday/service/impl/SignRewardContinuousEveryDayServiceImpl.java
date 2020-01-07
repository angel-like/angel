package com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuouseveryday.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuouseveryday.dao.SignRewardContinuousEveryDayDao;
import com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuouseveryday.entity.SignRewardContinuousEveryDayEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuouseveryday.service.SignRewardContinuousEveryDayService;
import com.xmsy.server.zxyy.webhome.modules.manager.uservip.entity.UserVipEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.uservip.service.UserVipService;


@Service("signRewardContinuousEveryDayService")
public class SignRewardContinuousEveryDayServiceImpl extends ServiceImpl<SignRewardContinuousEveryDayDao, SignRewardContinuousEveryDayEntity> implements SignRewardContinuousEveryDayService {
	@Autowired
	private UserVipService userVipService;
	/**
	 * 根据vipID查询签到信息
	 * @param vipId
	 * @return
	 */
	@Override
	public List<SignRewardContinuousEveryDayEntity> selectSignIn(Long vipId) {
		return baseMapper.selectSignIn(vipId);
	}

	@Override
	public List<Map<String, Object>> getSignRewardData(List<SignRewardContinuousEveryDayEntity> listSignReward) {
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		Map<String, Object> map=new HashMap<String, Object>();
		int i=0;
		String keyData[] = new String[] {"","dayOne","dayTwo","dayThree","dayFour"
				,"dayFive","daySix","daySeven"};
		for(SignRewardContinuousEveryDayEntity signReward:listSignReward) {					
			if(i!=0 && listSignReward.get(i-1).getVipId()!=signReward.getVipId()) {	
				Long vipId=listSignReward.get(i-1).getVipId();//获取上一条 每天签到奖励			
				if(vipId==0) {
					map.put("vipName", "VIP0");	
				}else {//通过vipId去查找vip等级
					map.put("vipName",  userVipService.selectById(vipId).getName());
				}
				map.put("vipId",vipId);
				list.add(map);
				map=new HashMap<String, Object>();
			}
			map.put(keyData[signReward.getDayNum()], signReward.getReward());	
			i++;
		}
		SignRewardContinuousEveryDayEntity lastEntity = listSignReward.get(listSignReward.size()-1);
		UserVipEntity vip = userVipService.selectById(lastEntity.getVipId());
		map.put("vipName", vip.getName());	
		map.put("vipId",lastEntity.getVipId());
		list.add(map);
		return list;
	}


}
