package com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchtaskdetail.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchtaskdetail.entity.PoolDispatchTaskDetailEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 派奖奖项明细表
 * 
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
@Mapper
public interface PoolDispatchTaskDetailDao extends BaseMapper<PoolDispatchTaskDetailEntity> {
	List<Map<String, Object>> findTaskDetailPage(Map<String, Object> param);
	Integer queryTotal(Map<String, Object> param);
	Integer updateEntityByIdForTrim(PoolDispatchTaskDetailEntity entity);
}
