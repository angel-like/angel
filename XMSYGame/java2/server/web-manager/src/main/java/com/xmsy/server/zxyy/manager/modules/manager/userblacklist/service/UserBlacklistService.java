package com.xmsy.server.zxyy.manager.modules.manager.userblacklist.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.userblacklist.entity.UserBlacklistEntity;

/**
 * 用户白名单表
 *
 * @author lzy
 * @email xxxxx
 * @date 2019-01-15 10:50:25
 */
public interface UserBlacklistService extends IService<UserBlacklistEntity> {

    PageUtils queryPage(Map<String, Object> params,Boolean isWhile);


	List<UserBlacklistEntity> selectList();   
    
}

