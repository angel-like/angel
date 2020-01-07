package com.xmsy.server.zxyy.manager.modules.manager.userlogin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.Query;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.manager.userlogin.dao.UserLoginDao;
import com.xmsy.server.zxyy.manager.modules.manager.userlogin.entity.UserLoginEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userlogin.service.UserLoginService;
import com.xmsy.server.zxyy.manager.utils.IPQueryUtil;
import com.xmsy.server.zxyy.manager.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("userLoginService")
public class UserLoginServiceImpl extends ServiceImpl<UserLoginDao, UserLoginEntity> implements UserLoginService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<UserLoginEntity> page = this.selectPage(new Query<UserLoginEntity>(params).getPage(),
				new EntityWrapper<UserLoginEntity>());

		return new PageUtils(page);
	}

	// 新增用户登陆记录
	@Override
	public void insert(Long userId, String ip, String registerDeviceCode, String deviceType, String token,
			String type) {
		UserLoginEntity entity = new UserLoginEntity();
		entity.setUserId(userId);
		entity.setIp(ip);
		String ipData[] = IPQueryUtil.queryIp(ip);
		try {
			if(ipData!=null) {
				entity.setNation(ipData[0]);
				entity.setIpAddress(ipData[0]+ipData[1]+ipData[2]);
			}
		} catch (Exception e1) {
			log.error("[ip解析返回错误]", e1);
		}
		entity.setDeviceCode(registerDeviceCode);
		entity.setLoginStatus(type);
		entity.setDeviceType(deviceType);
		entity.setToken(token);
		baseMapper.insert(entity);
	}

	// 根据用户token获取用户登陆记录
	@Override
	public List<UserLoginEntity> getLoginRecordForToken(String token) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserLoginEntity entity = new UserLoginEntity();
		entity.setUserId(userId);
		List<UserLoginEntity> list = baseMapper.selectList(new EntityWrapper<UserLoginEntity>(entity));
		log.info("[getLoginRecordForToken] list {}", list);
		if (CollectionUtils.isEmpty(list)) {
			// 用户有token就一定登陆过，所以一定会有登陆记录，如果没有就是有问题
			throw new RRException(ErrorCode.UserErrorCode.USER_LOGIN_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_LOGIN_ERRO.getCode());
		}
		return list;
	}

	@Override
	public Page<UserLoginEntity> pageList(UserLoginEntity userLogin, PageParam pageParam) {
		Page<UserLoginEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造
		// page 对象
		return page.setRecords(this.baseMapper.pageList(page,userLogin));
	}

	@Override
	public Map<String, Object> getLoginCountByDeviceType() {
		List<Map<String, Object>> loginList = baseMapper.countOnlineNumByDeviceType();
		Map<String, Object> map = new HashMap<>();
		int pcNum =0;
		int androidNum =0;
		int iphoneNum =0;
		int otherNum =0;
		if(loginList!=null && !loginList.isEmpty()) {
			for(Map<String, Object> data : loginList) {
//				pc android iphone 其他
				String deviceType=data.get("deviceType")!=null?data.get("deviceType").toString().trim():"";
				if(SysConstant.DEVICE_TYPE_PC.equalsIgnoreCase(deviceType)) {
					pcNum+=MathUtil.getBigDecimal(data.get("num")).intValue();
				}else if(SysConstant.DEVICE_TYPE_ANDROID.equalsIgnoreCase(deviceType)) {
					androidNum+=MathUtil.getBigDecimal(data.get("num")).intValue();
				}else if(SysConstant.DEVICE_TYPE_IPHONE.equalsIgnoreCase(deviceType)) {
					iphoneNum+=MathUtil.getBigDecimal(data.get("num")).intValue();
				}else if(SysConstant.DEVICE_TYPE_OTHER.equalsIgnoreCase(deviceType)) {
					otherNum+=MathUtil.getBigDecimal(data.get("num")).intValue();
				}else {
					pcNum+=MathUtil.getBigDecimal(data.get("num")).intValue();
				}
			}
		}
		map.put("pcNum", pcNum);
		map.put("androidNum", androidNum);
		map.put("iphoneNum", iphoneNum);
		map.put("otherNum", otherNum);
		return map;
	}

	@Override
	public Map<String, Object> getUserLoginByUserId(Long userId, String token) {
		Map<String, Object> data =this.baseMapper.getUserLoginByUserId(userId, token);
		return data==null ? new HashMap<>() :data;
	}

}
