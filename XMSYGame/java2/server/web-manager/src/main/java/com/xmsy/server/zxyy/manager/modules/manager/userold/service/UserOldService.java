package com.xmsy.server.zxyy.manager.modules.manager.userold.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.userold.entity.UserOldEntity;


/**
 * 用户信息表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-22 15:56:39
 */
public interface UserOldService extends IService<UserOldEntity> {
	/**
	 * 查询分页参数
	 * @param userold
	 * @param pageParam
	 * @return
	 */
	PageUtils findUserPage(UserOldEntity userold, PageParam pageParam);
	/**
	 * 修改备注
	 * @param userOldEntity
	 * @return
	 */
	Integer updateUserRemark(UserOldEntity userOldEntity);
}

