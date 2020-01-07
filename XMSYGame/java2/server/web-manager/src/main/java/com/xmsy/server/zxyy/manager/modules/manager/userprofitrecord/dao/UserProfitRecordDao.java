package com.xmsy.server.zxyy.manager.modules.manager.userprofitrecord.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.manager.modules.manager.userprofitrecord.entity.UserProfitRecordEntity;

/**
 * 用户账户金额收益记录表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-19 17:48:37
 */
@Mapper
public interface UserProfitRecordDao extends BaseMapper<UserProfitRecordEntity> {
    List<UserProfitRecordEntity> getList(Pagination page, @Param("record") UserProfitRecordEntity config);

}
