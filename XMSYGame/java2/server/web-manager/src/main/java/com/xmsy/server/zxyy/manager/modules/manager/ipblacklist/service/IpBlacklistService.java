package com.xmsy.server.zxyy.manager.modules.manager.ipblacklist.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.ipblacklist.entity.IpBlacklistEntity;


/**
 * ip黑名单
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-02-17 15:58:11
 */
public interface IpBlacklistService extends IService<IpBlacklistEntity> {

	List<IpBlacklistEntity> selectList();

}

