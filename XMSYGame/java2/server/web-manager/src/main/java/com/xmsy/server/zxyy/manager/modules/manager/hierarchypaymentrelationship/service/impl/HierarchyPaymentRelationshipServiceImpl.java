package com.xmsy.server.zxyy.manager.modules.manager.hierarchypaymentrelationship.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.modules.manager.apppaycofig.dao.AppPayCofigDao;
import com.xmsy.server.zxyy.manager.modules.manager.cashbank.entity.CashBankEntity;
import com.xmsy.server.zxyy.manager.modules.manager.cashbank.service.CashBankService;
import com.xmsy.server.zxyy.manager.modules.manager.hierarchypaymentrelationship.dao.HierarchyPaymentRelationshipDao;
import com.xmsy.server.zxyy.manager.modules.manager.hierarchypaymentrelationship.entity.HierarchyPaymentRelationshipEntity;
import com.xmsy.server.zxyy.manager.modules.manager.hierarchypaymentrelationship.service.HierarchyPaymentRelationshipService;
import com.xmsy.server.zxyy.manager.modules.manager.payconfig.dao.PayConfigDao;
import com.xmsy.server.zxyy.manager.modules.manager.paymenttypeconfiguration.entity.PaymentTypeConfigurationEntity;
import com.xmsy.server.zxyy.manager.modules.manager.rechargeamount.entity.RechargeAmountEntity;
import com.xmsy.server.zxyy.manager.modules.manager.rechargeamount.service.RechargeAmountService;

import cn.hutool.json.JSONObject;


@Service("hierarchyPaymentRelationshipService")
public class HierarchyPaymentRelationshipServiceImpl extends ServiceImpl<HierarchyPaymentRelationshipDao, HierarchyPaymentRelationshipEntity> implements HierarchyPaymentRelationshipService {
	@Autowired
	private RechargeAmountService rechargeAmountService;
	@Autowired
	private CashBankService cashBankService;
	@Autowired
	private AppPayCofigDao appPayCofigDao;
	@Autowired
	private PayConfigDao payConfigDao;
	/**
	 * 删除（根据层级id）
	 * @param hierarchyId
	 */
	@Override
	public void deleteByHierarchyId(Long hierarchyId) {
		baseMapper.deleteByHierarchyId(hierarchyId);
	}

	/**
	 * 查询（根据层级id）
	 * @param hierarchyId
	 */
	@Override
	public List<Long> selectByHierarchyId(Long hierarchyId) {
		return baseMapper.selectByHierarchyId(hierarchyId);
	}

	@Override
	public List<PaymentTypeConfigurationEntity> getRechargeListByHierarchyId(Long hierarchyId) {
		List<PaymentTypeConfigurationEntity> paymentTypeList = this.baseMapper.
				getPaymentTypeByHierarchyId(hierarchyId);
		if(paymentTypeList == null || paymentTypeList.isEmpty()) {
			return new ArrayList<>();
		}
		for(PaymentTypeConfigurationEntity payType:paymentTypeList) {
			if(payType.getType() == Constant.PAYTYPE1) {//众享支付
				payType.setChild(rechargeAmountService.selectList(
						new EntityWrapper<RechargeAmountEntity>(new RechargeAmountEntity().setPayId(Constant.DEFAULT_PAY))
						.orderAsc(Lists.newArrayList("amount"))));
			}else if(payType.getType() == Constant.PAYTYPE2) {//快捷支付
				payType.setChild(payConfigDao.selectListForApp());
				payType.setPaymentType(null);
			}else if(payType.getType() == Constant.PAYTYPE3) {//在线充值
				payType.setPaymentType(null);
				CashBankEntity sysBank = new CashBankEntity();
				sysBank.setHierarchyId(hierarchyId.toString());
				Page<CashBankEntity> cashBank = cashBankService.findCashBankPage(new PageParam(), sysBank);
				List<Object> arry = new ArrayList<Object>();
				for (CashBankEntity cashBankEntity : cashBank.getRecords()) {
					JSONObject obj = new JSONObject();
					obj.put("children", cashBankEntity);
					obj.put("bank", cashBankEntity.getName());
					arry.add(obj);
				}
				;
				Map<String, Object> bankMap = new HashMap<String, Object>();
				bankMap.put("CashBank", arry);
				bankMap.put("RechargeBank", Constant.RechargeBankType.getLookup());
				payType.setChild(bankMap);
			}else if(payType.getType() == Constant.PAYTYPE4) {//VIP充值
				payType.setPaymentType(null);
				payType.setChild(appPayCofigDao.selectPayConfigList());
			}
		}
		return paymentTypeList;
	}
	
	

	

}
