package com.xmsy.server.zxyy.webhome.modules.manager.userlog.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.modules.manager.userlog.entity.UserLogEntity;

import java.util.Map;

/**
 * 系统日志
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-04 16:04:27
 */
public interface UserLogService extends IService<UserLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

