package com.xmsy.server.zxyy.manager.modules.manager.uservip.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.manager.uservip.dao.UserVipDao;
import com.xmsy.server.zxyy.manager.modules.manager.uservip.entity.UserVipEntity;
import com.xmsy.server.zxyy.manager.modules.manager.uservip.service.UserVipService;


@Service("userVipService")
public class UserVipServiceImpl extends ServiceImpl<UserVipDao, UserVipEntity> implements UserVipService {

	@Override
	public List<Map<String, Object>> findUserVipForSelect() {
		return this.baseMapper.findUserVipForSelect();
	}

	@Override
	public Map<Long, String> getVipMap() {
		List<UserVipEntity> dataList = this.selectList(new EntityWrapper<UserVipEntity>(new UserVipEntity()));
		Map<Long, String> vipMap=new HashMap<>();
		vipMap.put(0l, "VIP0");
		if(null != dataList && !dataList.isEmpty()) {
			for(UserVipEntity data : dataList) {
				vipMap.put(data.getId(), data.getName());
			}
		}
		return vipMap;
	}


}
