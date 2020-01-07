package com.xmsy.server.zxyy.webhome.modules.manager.messageuser.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.entity.MessageUserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.param.MessageRequestVo;
import com.xmsy.server.zxyy.webhome.modules.manager.sysmessageprop.entity.SysMessagePropEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

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
	int insertEntityReturnId(MessageUserEntity entity);
	int insertBatch(List<MessageUserEntity> entityList) ;
	List<MessageUserEntity> findMessageUser(@Param("messageIds") Iterable<Long> messageIds,@Param("userId") Long userId) ;
	
	/**
	 * 根据消息Id和userid 查询
	 * @param messageId
	 * @param userId
	 * @return
	 */
	MessageUserEntity queryByMessageId(@Param("messageId") Long messageId , @Param("userId") Long userId);
	
	/**
	 * 查找用户的所有的  站内信删除状态:未删除---未领取      的消息     道具实体
	 * @param MessageRequestVo
	 * @return
	 */
	List<SysMessagePropEntity> findMessageProp(@Param("vo")MessageRequestVo vo);
	
	/**
	 * 查找用户的所有的  站内信删除状态:未删除---未领取      的站内信用户关系表实体  
	 * @param MessageRequestVo
	 * @return
	 */
	List<MessageUserEntity> findMessageUserByUserId(@Param("vo")MessageRequestVo vo);
	List<Map<String, Object>> findMessageByUserId(Pagination page, @Param("vo")MessageRequestVo vo);

}
