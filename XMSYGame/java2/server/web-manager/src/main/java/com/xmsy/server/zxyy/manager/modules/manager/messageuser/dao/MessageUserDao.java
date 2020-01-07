package com.xmsy.server.zxyy.manager.modules.manager.messageuser.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.entity.MessageUserEntity;

import java.util.List;
import java.util.Map;

import com.xmsy.server.zxyy.manager.modules.manager.messageuser.param.MessageRequestVo;
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
	
	/**
	 * 根据消息Id和userid 查询
	 * @param messageId
	 * @param userId
	 * @return
	 */
	MessageUserEntity queryByMessageId(@Param("messageId") Long messageId , @Param("userId") Long userId);
	List<Map<String, Object>> findMessageByUserId(Pagination page, @Param("vo") MessageRequestVo vo);
}
