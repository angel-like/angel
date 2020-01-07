package com.xmsy.server.zxyy.manager.modules.manager.userlogin.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.userlogin.entity.UserLoginEntity;

/**
 * 用户登陆记录表
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
public interface UserLoginService extends IService<UserLoginEntity> {

	PageUtils queryPage(Map<String, Object> params);

	void insert(Long userId, String ip, String registerDeviceCode, String deviceType, String token, String type);

	List<UserLoginEntity> getLoginRecordForToken(String token);

	/**
	 * 计算设备的登录人数
	 * 
	 * @return
	 */
	Map<String, Object> getLoginCountByDeviceType();
	
	Map<String, Object> getUserLoginByUserId(Long userId,String token);

	Page<UserLoginEntity> pageList(UserLoginEntity userLogin, PageParam pageParam);

}
