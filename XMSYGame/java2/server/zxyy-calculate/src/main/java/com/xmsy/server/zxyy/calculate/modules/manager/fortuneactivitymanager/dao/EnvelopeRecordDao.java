package com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.dao;



import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.entity.EnvelopeRecordEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 天降财神红包记录
 *
 * @author ahui
 * @email sunlightcs@gmail.com
 * @date 2019-12-12 11:38:04
 */
@Mapper
public interface EnvelopeRecordDao extends BaseMapper<EnvelopeRecordEntity> {
	/**
	 * 批量插入红包记录
	 * @param envelopeRecordList
	 * @return
	 */
	Integer addBatch(@Param("envelopeRecordList")List<EnvelopeRecordEntity> envelopeRecordList);
}
