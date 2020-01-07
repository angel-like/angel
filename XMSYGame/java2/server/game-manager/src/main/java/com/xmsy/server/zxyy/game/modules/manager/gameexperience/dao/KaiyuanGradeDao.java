package com.xmsy.server.zxyy.game.modules.manager.gameexperience.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.game.modules.manager.gameexperience.entity.KaiyuanGradeEntity;

/**
 * 开元游戏场次等级
 *
 * @author adai
 * @email sunlightcs@gmail.com
 * @date 2019-11-28 09:12:57
 */
@Mapper
public interface KaiyuanGradeDao extends BaseMapper<KaiyuanGradeEntity> {
    List<KaiyuanGradeEntity> findList(Pagination page, @Param("kaiYuanGradeParam") KaiyuanGradeEntity kaiYuanGradeParam);
}
