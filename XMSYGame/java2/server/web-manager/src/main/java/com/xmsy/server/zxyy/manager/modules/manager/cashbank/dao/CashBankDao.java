package com.xmsy.server.zxyy.manager.modules.manager.cashbank.dao;

import java.util.List;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.cashbank.entity.CashBankEntity;

/**
 * 系统银行表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-07 18:46:36
 */
@Mapper
public interface CashBankDao extends BaseMapper<CashBankEntity> {

	// 获取层级收款银行
	public List<CashBankEntity> findCashBanks(Pagination page,CashBankEntity cashBankEntity);

}
