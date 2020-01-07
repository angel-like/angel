package com.xmsy.server.zxyy.webhome.modules.manager.userprofitrecord.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.userprofitrecord.entity.UserProfitRecordEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户账户金额收益记录表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-06-19 17:48:37
 */
@Mapper
public interface UserProfitRecordDao extends BaseMapper<UserProfitRecordEntity> {
	List<Map<String, Object>> getUserYuEBaoProfitDetailDataDao(@Param("userProfitRecordEntity") UserProfitRecordEntity userProfitRecordEntity);
}
