package com.xmsy.server.zxyy.webhome.modules.manager.paymenttypeconfiguration.controller;

import com.xmsy.common.define.page.PageParam;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import com.xmsy.server.zxyy.webhome.modules.manager.paymenttypeconfiguration.entity.PaymentTypeConfigurationEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.paymenttypeconfiguration.service.PaymentTypeConfigurationService;
import com.xmsy.server.zxyy.webhome.modules.manager.sysdictionary.entity.SysDictionaryEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysdictionary.service.SysDictionaryService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;



/**
 * 支付类型配置表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-03 14:38:14
 */
@RestController
@RequestMapping("paymenttypeconfiguration/paymenttypeconfiguration")
public class PaymentTypeConfigurationController {
    @Autowired
    private PaymentTypeConfigurationService paymentTypeConfigurationService;
    
    @Autowired
    private SysDictionaryService sysDictionaryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("paymenttypeconfiguration:paymenttypeconfiguration:list")
    public R list(PaymentTypeConfigurationEntity paymenttypeconfiguration, PageParam pageParam){
        Page<PaymentTypeConfigurationEntity> result = new Page<PaymentTypeConfigurationEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<PaymentTypeConfigurationEntity> entityWrapper = new EntityWrapper<PaymentTypeConfigurationEntity>(paymenttypeconfiguration);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		paymenttypeconfiguration.selectPage(result, entityWrapper);
		List<PaymentTypeConfigurationEntity> list=result.getRecords();
		if(!CollectionUtils.isEmpty(list)) {
			for(PaymentTypeConfigurationEntity entity:list) {
				if(entity!=null) {
					if(entity.getType()!=null&&entity.getType()!=0) {
						SysDictionaryEntity dictionaryEntity=sysDictionaryService.selectOne(new EntityWrapper<SysDictionaryEntity>(null).eq("parent_code", "RechargeClassify").eq("code", entity.getType().toString()));
						if(dictionaryEntity!=null) {
							if(dictionaryEntity.getName()!=null) {
								entity.setTypeName(dictionaryEntity.getName());
							}
						}
					}
					if(entity.getPaymentType()!=null&&entity.getPaymentType()!=0) {
						SysDictionaryEntity dictionaryEntity=sysDictionaryService.selectOne(new EntityWrapper<SysDictionaryEntity>(null).eq("parent_code", "qrcodeType").eq("code", entity.getPaymentType().toString()));
						if(dictionaryEntity!=null) {
							if(dictionaryEntity.getName()!=null) {
								entity.setPaymentTypeName(dictionaryEntity.getName());
							}
						}
					}
				}
			}
		}
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("paymenttypeconfiguration:paymenttypeconfiguration:info")
    public R info(@PathVariable("id") Long id){
			PaymentTypeConfigurationEntity paymentTypeConfiguration = paymentTypeConfigurationService.selectById(id);
        return R.ok().put("paymenttypeconfiguration", paymentTypeConfiguration);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("paymenttypeconfiguration:paymenttypeconfiguration:save")
    public R save(@RequestBody PaymentTypeConfigurationEntity paymenttypeconfiguration){
			paymentTypeConfigurationService.insert(paymenttypeconfiguration);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("paymenttypeconfiguration:paymenttypeconfiguration:update")
    public R update(@RequestBody PaymentTypeConfigurationEntity paymenttypeconfiguration){
			paymentTypeConfigurationService.updateById(paymenttypeconfiguration);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("paymenttypeconfiguration:paymenttypeconfiguration:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			paymentTypeConfigurationService.deleteById(id);
	}
        return R.ok();
    }
    
    /**
	 * 角色列表
	 */
	@GetMapping("/select")
	@RequiresPermissions("paymenttypeconfiguration:paymenttypeconfiguration:select")
	public R select() {
		List<PaymentTypeConfigurationEntity> dataList = paymentTypeConfigurationService.selectList(new EntityWrapper<PaymentTypeConfigurationEntity>(new PaymentTypeConfigurationEntity()));
		List<Long> ids = new ArrayList<>();
		if (dataList != null && !dataList.isEmpty()) {
			for (PaymentTypeConfigurationEntity data : dataList) {
				ids.add(data.getId());
			}
		}
		return R.ok().put("dataList", dataList).put("ids", ids);
	}

}
