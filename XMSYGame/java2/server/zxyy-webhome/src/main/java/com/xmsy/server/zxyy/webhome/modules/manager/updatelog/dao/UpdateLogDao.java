package com.xmsy.server.zxyy.webhome.modules.manager.updatelog.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.updatelog.entity.UpdateLogEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 更新日志
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-25 16:47:37
 */
@Mapper
public interface UpdateLogDao extends BaseMapper<UpdateLogEntity> {
	
}
