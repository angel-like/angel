package com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.entity.FortuneActiviConfigEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 天降财神活动配置
 *
 * @author ahui
 * @email sunlightcs@gmail.com
 * @date 2019-12-12 11:38:04
 */
@Mapper
public interface FortuneActiviConfigDao extends BaseMapper<FortuneActiviConfigEntity> {
    List<Map<String,Object>> getMap();
}
