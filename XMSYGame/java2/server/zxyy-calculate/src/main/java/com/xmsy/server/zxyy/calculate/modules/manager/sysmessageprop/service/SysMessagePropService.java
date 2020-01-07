package com.xmsy.server.zxyy.calculate.modules.manager.sysmessageprop.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.calculate.modules.manager.sysmessageprop.entity.SysMessagePropEntity;


/**
 * 站内信-道具列表
 *
 * @author adu
 * @email xxxxx
 * @date 2019-05-23 19:08:25
 */
public interface SysMessagePropService extends IService<SysMessagePropEntity> {
	/**
	 * 根据邮件id取得道具列表
	 * @param messageId
	 * @return
	 */
	List<Map<String, Object>> findMessagePropListByMessageId(Long messageId);
}

