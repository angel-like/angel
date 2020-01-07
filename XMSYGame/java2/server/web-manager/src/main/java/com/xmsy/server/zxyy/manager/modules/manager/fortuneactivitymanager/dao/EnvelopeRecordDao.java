package com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.entity.EnvelopeRecordEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 天降财神红包记录
 *
 * @author ahui
 * @email sunlightcs@gmail.com
 * @date 2019-12-12 11:38:04
 */
@Mapper
public interface EnvelopeRecordDao extends BaseMapper<EnvelopeRecordEntity> {
    List<EnvelopeRecordEntity> getCount(Pagination page, EnvelopeRecordEntity envelopeRecordEntity);
}
