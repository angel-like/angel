package com.xmsy.server.zxyy.webhome.modules.manager.sysmessageprop.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.sysmessageprop.entity.SysMessagePropEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 站内信-道具列表
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-05-23 19:08:25
 */
@Mapper
public interface SysMessagePropDao extends BaseMapper<SysMessagePropEntity> {
	int insertBatch(List<SysMessagePropEntity> list);
	/**
	 * 根据邮件id取得道具列表
	 * @param messageId
	 * @return
	 */
	List<Map<String, Object>> findMessagePropListByMessageId(Long messageId);
}
