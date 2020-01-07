package com.xmsy.server.zxyy.webhome.modules.manager.hierarchypaymentrelationship.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.app.recharge.result.AppPayChannelResult;
import com.xmsy.server.zxyy.webhome.modules.manager.apppaycofig.dao.AppPayCofigDao;
import com.xmsy.server.zxyy.webhome.modules.manager.cashbank.entity.CashBankEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.cashbank.service.CashBankService;
import com.xmsy.server.zxyy.webhome.modules.manager.hierarchypaymentrelationship.dao.HierarchyPaymentRelationshipDao;
import com.xmsy.server.zxyy.webhome.modules.manager.hierarchypaymentrelationship.entity.HierarchyPaymentRelationshipEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.hierarchypaymentrelationship.service.HierarchyPaymentRelationshipService;
import com.xmsy.server.zxyy.webhome.modules.manager.payconfig.dao.PayConfigDao;
import com.xmsy.server.zxyy.webhome.modules.manager.payconfig.entity.PayConfigResultEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.payconfig.entity.PayConfigSelectPayid;
import com.xmsy.server.zxyy.webhome.modules.manager.paymenttypeconfiguration.entity.PaymentTypeConfigurationEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargeamount.entity.RechargeAmountEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargeamount.service.RechargeAmountService;
import com.xmsy.server.zxyy.webhome.modules.manager.sysdictionary.service.SysDictionaryService;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.entity.UserHierarchyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.service.UserHierarchyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("hierarchyPaymentRelationshipService")
public class HierarchyPaymentRelationshipServiceImpl extends ServiceImpl<HierarchyPaymentRelationshipDao, HierarchyPaymentRelationshipEntity> implements HierarchyPaymentRelationshipService {
    @Autowired
    private RechargeAmountService rechargeAmountService;
    @Autowired
    private CashBankService cashBankService;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private AppPayCofigDao appPayCofigDao;
    @Autowired
    private PayConfigDao payConfigDao;
    @Autowired
    private UserHierarchyService userHierarchyService;

    /**
     * 删除（根据层级id）
     *
     * @param hierarchyId
     */
    @Override
    public void deleteByHierarchyId(Long hierarchyId) {
        baseMapper.deleteByHierarchyId(hierarchyId);
    }

    /**
     * 查询（根据层级id）
     *
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
        if (paymentTypeList == null || paymentTypeList.isEmpty()) {
            return new ArrayList<>();
        }
        List<RechargeAmountEntity> rechargeAmountList = null;
        for (PaymentTypeConfigurationEntity payType : paymentTypeList) {
            if (payType.getType() == Constant.PAYTYPE1) {//众享支付
                if (payType.getPaymentType() == Constant.RechargeBankType.WEIXIN.getValue()
                        || payType.getPaymentType() == Constant.RechargeBankType.ALIPAY.getValue()) {
                    payType.setChild(rechargeAmountService.selectList(
                            new EntityWrapper<RechargeAmountEntity>(new RechargeAmountEntity().setPayId(Constant.DEFAULT_PAY))
                                    .orderAsc(Lists.newArrayList("amount"))));
                } else {
                    payType.setChild(new ArrayList<>());
                }
                payType.setType(payType.getType() * 100 + payType.getPaymentType());
            } else if (payType.getType() == Constant.PAYTYPE2) {//快捷支付
                payType.setPayUrl(sysDictionaryService.getName(SysConstant.urlCode, SysConstant.H5PayCode));
                payType.setPaymentType(null);
                List<PayConfigResultEntity> payListNew= new ArrayList<>();
                List<PayConfigResultEntity> payList = payConfigDao.selectListForApp();
                List<PayConfigSelectPayid> selectPayIdList =payConfigDao.selectPayId(hierarchyId);
                for(PayConfigResultEntity ptf:payList) {
                	for(PayConfigSelectPayid pcsp:selectPayIdList) {
                		if(ptf.getId().equals(pcsp.getPayId())) {
                			payListNew.add(ptf);
                			break;
                		}
                	}
                }
                payType.setChild(payListNew);
                
            } else if (payType.getType() == Constant.PAYTYPE3) {//在线充值
                payType.setPaymentType(null);
                CashBankEntity sysBank = new CashBankEntity();
                sysBank.setHierarchyId(hierarchyId.toString());
                Page<CashBankEntity> cashBank = cashBankService.findCashBankPage(new PageParam(), sysBank);
                List<Object> arry = new ArrayList<Object>();
                for (CashBankEntity cashBankEntity : cashBank.getRecords()) {
                    String[] strings = cashBankEntity.getHierarchyId().split(",");
                    StringBuilder hierarchyName=new StringBuilder();
                    for(String s:strings) {
                        UserHierarchyEntity hierarchy= userHierarchyService.selectById(s);
                        hierarchyName.append(hierarchy.getName()+",");
                    };
                    cashBankEntity.setHierarchyName(hierarchyName.toString().substring(0,hierarchyName.toString().length() - 1));
                    JSONObject obj = new JSONObject();
                    obj.put("children", cashBankEntity);
                    obj.put("bank", cashBankEntity.getName());
                    arry.add(obj);
                };
                Map<String, Object> bankMap = new HashMap<String, Object>();
                bankMap.put("CashBank", arry);
                //bankMap.put("RechargeBank", Constant.RechargeBankType.getLookup());//旧的不用
                bankMap.put("RechargeBank", sysDictionaryService.findListByParentCode(Constant.DEPOSITMETHODCODE));
                payType.setChild(bankMap);
            } else if (payType.getType() == Constant.PAYTYPE4) {//VIP充值
                payType.setPaymentType(null);
                payType.setChild(appPayCofigDao.selectPayConfigList());
            } else if (payType.getType() == Constant.PAYTYPE5) {//微信充值
                payType.setPaymentType(null);
//                if (rechargeAmountList == null) {
//                    rechargeAmountList = rechargeAmountService.selectList(
//                            new EntityWrapper<RechargeAmountEntity>(new RechargeAmountEntity())
//                                    .ne("pay_id", Constant.DEFAULT_PAY)
//                                    .orderAsc(Lists.newArrayList("pay_id", "amount")));
//                }
                payType.setChild(getPayListByChangeId(Constant.RechargeBankType.WEIXIN.getValue(), rechargeAmountList,hierarchyId));
            } else if (payType.getType() == Constant.PAYTYPE6) {//支付宝充值
                payType.setPaymentType(null);
//                if (rechargeAmountList == null) {
//                    rechargeAmountList = rechargeAmountService.selectList(
//                            new EntityWrapper<RechargeAmountEntity>(new RechargeAmountEntity())
//                                    .ne("pay_id", Constant.DEFAULT_PAY)
//                                    .orderAsc(Lists.newArrayList("pay_id", "amount")));
//                }
                payType.setChild(getPayListByChangeId(Constant.RechargeBankType.ALIPAY.getValue(), rechargeAmountList,hierarchyId));
            }else {
            	 payType.setPaymentType(null);
            	 payType.setChild(new ArrayList<>());
            }
        }
        return paymentTypeList;
    }

    private Object getPayListByChangeId(int changeId, List<RechargeAmountEntity> rechargeAmountList,Long hierarchyId) {
        List<AppPayChannelResult> dataList = payConfigDao.selectPayListByChangeId(changeId,hierarchyId);
        if (dataList == null || dataList.isEmpty()) {
            return new ArrayList<>();
        }
//        if (rechargeAmountList == null || rechargeAmountList.isEmpty()) {
//            return dataList;
//        }
        RechargeAmountEntity rechargeAmountData = null;
        List<RechargeAmountEntity> rechargeAmount = null;
        for (AppPayChannelResult data : dataList) {
            rechargeAmount = new ArrayList<>();
            String amounts = data.getAmount();
            String[] split = amounts.split(",");
            for (String s : split) {
                if(s!=null&&!s.equals("")) {
                    Long amount;
                    try {
                        amount = Long.valueOf(s);
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                    if (null != data.getLowLimit() && data.getLowLimit() > amount) {
                        continue;
                    }
                    if (null != data.getHighLimit() && data.getHighLimit() < amount) {
                        continue;
                    }
                    rechargeAmountData = new RechargeAmountEntity();
                    rechargeAmountData.setAmount(amount);
                    rechargeAmountData.setPayId(data.getPayId());
                    rechargeAmountData.setPaymentMethodId(data.getChannelId());

                }
              rechargeAmount.add(rechargeAmountData);
            }
//            for (RechargeAmountEntity rdata : rechargeAmountList) {
//                if (!data.getPayId().equals(rdata.getPayId())) {
//                    continue;
//                }
//                if (!data.getChannelId().equals(rdata.getPaymentMethodId())) {
//                    continue;
//                }
//                if (null == rdata.getAmount()) {
//                    continue;
//                }
//                if (null == rdata.getPaymentMethodId()) {
//                    continue;
//                }

//                rechargeAmountData = new RechargeAmountEntity();
//                rechargeAmountData.setAmount(rdata.getAmount());
//                rechargeAmountData.setPayId(rdata.getPayId());
//                rechargeAmountData.setPaymentMethodId(rdata.getPaymentMethodId());
//                rechargeAmount.add(rechargeAmountData);
//            }
            data.setRechargeAmount(rechargeAmount);
        }
        return dataList;
    }


}
