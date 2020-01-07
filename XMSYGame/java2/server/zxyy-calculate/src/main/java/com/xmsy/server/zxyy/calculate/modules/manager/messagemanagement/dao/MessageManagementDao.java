package com.xmsy.server.zxyy.calculate.modules.manager.messagemanagement.dao;

import com.xmsy.server.zxyy.calculate.modules.manager.messageuser.param.MessageRequestVo;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.calculate.modules.manager.messagemanagement.entity.MessageManagementEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 消息管理
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-02 16:39:41
 */
@Mapper
public interface MessageManagementDao extends BaseMapper<MessageManagementEntity> {

	void insertEntityReturnId(MessageManagementEntity messageManagement);
	 List<MessageManagementEntity> findMemberMessageByEffect(@Param("contentType") Integer contentType, @Param("userId") Long userId);
	List<MessageManagementEntity> findMemberMessageByUser(@Param("vo") MessageRequestVo vo);
}
