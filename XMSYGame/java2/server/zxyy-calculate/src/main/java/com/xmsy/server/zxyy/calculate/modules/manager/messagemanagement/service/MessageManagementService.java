package com.xmsy.server.zxyy.calculate.modules.manager.messagemanagement.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.calculate.modules.manager.messagemanagement.entity.MessageManagementEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.messageuser.param.MessageRequestVo;

import java.util.List;

/**
 * 消息管理
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-02 16:39:41
 */
public interface MessageManagementService extends IService<MessageManagementEntity> {

	/**
	 * 保存会员站内信
	 * 
	 * @param messageManagement
	 * @return
	 */
	void save(MessageManagementEntity messageManagement, Long userId);
   /**
 * 查询未收到的会员站内信
 *
 * @param messageManagement
 * @return
 */
   List<MessageManagementEntity> findMemberMessageByEffect(Integer contentType,Long userId );
	List<MessageManagementEntity> findMemberMessageByUser(MessageRequestVo vo );
}
