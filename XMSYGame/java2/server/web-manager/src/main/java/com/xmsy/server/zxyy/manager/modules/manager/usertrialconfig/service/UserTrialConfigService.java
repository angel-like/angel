package com.xmsy.server.zxyy.manager.modules.manager.usertrialconfig.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.usertrialconfig.entity.UserTrialConfigEntity;

import java.util.Map;

/**
 * 试玩账号配置
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-25 15:52:20
 */
public interface UserTrialConfigService extends IService<UserTrialConfigEntity> {

    PageUtils queryPage(Map<String, Object> params);
    void save(UserTrialConfigEntity entity);
}

