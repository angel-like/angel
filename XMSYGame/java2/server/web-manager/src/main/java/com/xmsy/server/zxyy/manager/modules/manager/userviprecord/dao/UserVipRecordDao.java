package com.xmsy.server.zxyy.manager.modules.manager.userviprecord.dao;

import com.xmsy.server.zxyy.manager.modules.manager.userviprecord.entity.UserVipRecordEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户vip等级记录表
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-06-11 16:04:14
 */
@Mapper
public interface UserVipRecordDao extends BaseMapper<UserVipRecordEntity> {
	
	/**
	 * 会员管理页面分页查询
	 * 
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> findUserVipRecordPage(@Param("userVipRecord") UserVipRecordEntity userVipRecord, Pagination page);
	
	
	
}
