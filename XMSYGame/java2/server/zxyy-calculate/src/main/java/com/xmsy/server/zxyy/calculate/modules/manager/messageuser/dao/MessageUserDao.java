package com.xmsy.server.zxyy.calculate.modules.manager.messageuser.dao;

import com.xmsy.server.zxyy.calculate.modules.manager.messageuser.entity.MessageUserEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 消息管理
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-02 16:39:41
 */
@Mapper
public interface MessageUserDao extends BaseMapper<MessageUserEntity> {
	int deleteUserByMessageId(Long messageId);
	int insertBatch(List<MessageUserEntity> entityList) ;
	List<MessageUserEntity> findMessageUser(@Param("messageIds") Iterable<Long> messageIds,@Param("userId") Long userId) ;
}
