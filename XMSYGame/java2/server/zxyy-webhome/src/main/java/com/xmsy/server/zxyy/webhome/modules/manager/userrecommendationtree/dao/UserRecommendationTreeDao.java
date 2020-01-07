package com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationtree.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationtree.entity.UserRecommendationTreeEntity;

/**
 * 用户推荐关系表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-06-05 15:35:08
 */
@Mapper
public interface UserRecommendationTreeDao extends BaseMapper<UserRecommendationTreeEntity> {

	List<Map<String, Object>> getSql();

	List<Map<String, Object>> getNoSuperiorSql();

}
