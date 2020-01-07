package com.xmsy.server.zxyy.webhome.modules.manager.luckyuserrecord.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.webhome.modules.manager.luckyuserrecord.entity.LuckyUserRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户抽奖记录表
 *
 * @author ayang
 * @email xxxxx
 * @date 2019-11-22 17:55:00
 */
@Mapper
public interface LuckyUserRecordDao extends BaseMapper<LuckyUserRecordEntity> {
    List<Map<String, Object>> selectByUserId(@Param("userId")Long userId);
    List<Map<String, Object>>selectGrand();

    List<Map<String, Object>> selectAll();
}
