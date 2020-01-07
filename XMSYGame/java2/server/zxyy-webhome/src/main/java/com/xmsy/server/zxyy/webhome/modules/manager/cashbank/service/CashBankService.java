package com.xmsy.server.zxyy.webhome.modules.manager.cashbank.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.modules.manager.cashbank.entity.CashBankEntity;

/**
 * 系统银行表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-07 18:46:36
 */
public interface CashBankService extends IService<CashBankEntity> {
	
	//获取收款银行
	public Page<CashBankEntity> findCashBankPage(PageParam pageParam, CashBankEntity cashBankEntity);
	
	//获取收款银行
	public List<CashBankEntity> findCashBankByHierarchyId(String hierarchyId);
}
