package com.xmsy.server.zxyy.manager.modules.manager.userviprecord.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.userviprecord.entity.UserVipRecordEntity;


/**
 * 用户vip等级记录表
 *
 * @author adu
 * @email xxxxx
 * @date 2019-06-11 16:04:14
 */
public interface UserVipRecordService extends IService<UserVipRecordEntity> {
	
	PageUtils findUserVipRecordPage(UserVipRecordEntity userVipRecord, PageParam pageParam);

}

