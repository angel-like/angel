package com.xmsy.server.zxyy.webhome.modules.manager.noticemanagement.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.param.MessageRequestVo;
import com.xmsy.server.zxyy.webhome.modules.manager.noticemanagement.entity.NoticeManagementEntity;

/**
 * 消息管理
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-03 17:26:35
 */
@Mapper
public interface NoticeManagementDao extends BaseMapper<NoticeManagementEntity> {
	/**
	 * 查找用户未失效的公告列表
	 * @param vo
	 * @return
	 */
	List<Map<String, Object>> findNoticeListByEffect(MessageRequestVo vo);
	
}
