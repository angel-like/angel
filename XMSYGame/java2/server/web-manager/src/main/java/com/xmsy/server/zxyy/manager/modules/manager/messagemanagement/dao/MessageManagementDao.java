package com.xmsy.server.zxyy.manager.modules.manager.messagemanagement.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.manager.modules.manager.messagemanagement.entity.MessageManagementEntity;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.param.MessageRequestVo;

/**
 * 消息管理
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-02 16:39:41
 */
@Mapper
public interface MessageManagementDao extends BaseMapper<MessageManagementEntity> {
	int insertEntityReturnId(MessageManagementEntity entity);
	List<MessageManagementEntity> findMemberMessageByEffect(@Param("contentType") Integer contentType,@Param("userId")Long userId);
	
	/**
	 * 查找用户的所有的消息id
	 * @param vo
	 * @return
	 */
	List<Long> findMessageIDByUser(MessageRequestVo vo);
	/**
	 * 查找用户未删除的站内信
	 * 分页
	 * @param page
	 * @param vo
	 * @return
	 */
	List<Map<String, Object>> findMemberMessageByEffectPage(Pagination page,@Param("vo")MessageRequestVo vo);
	/**
	 * 计算未读的个数
	 * @param vo
	 * @return
	 */
	Integer countUnreadNumber(MessageRequestVo vo);
}
