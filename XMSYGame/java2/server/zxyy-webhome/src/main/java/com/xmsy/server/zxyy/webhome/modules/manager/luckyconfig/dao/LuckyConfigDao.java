package com.xmsy.server.zxyy.webhome.modules.manager.luckyconfig.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.webhome.modules.manager.luckyconfig.entity.LuckyConfigEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 幸运转盘
 * 
 * @author ayang
 * @email xxxxx
 * @date 2019-11-21 12:58:04
 */
@Mapper
public interface LuckyConfigDao extends BaseMapper<LuckyConfigEntity> {
  List<LuckyConfigEntity> getAllconfig(Long luckyId);
  List<LuckyConfigEntity>getSettings(Long luckyId);
  List<String>getNames(Long luckyId);
	
}
