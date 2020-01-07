package com.xmsy.server.zxyy.webhome.modules.manager.imshuffling.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.imshuffling.entity.ImShufflingEntity;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImShufflingResult;


/**
 * 33轮播图管理
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-06-21 11:09:52
 */
public interface ImShufflingService extends IService<ImShufflingEntity> {
	
	List<ImShufflingResult> selectShufflingList();

}

