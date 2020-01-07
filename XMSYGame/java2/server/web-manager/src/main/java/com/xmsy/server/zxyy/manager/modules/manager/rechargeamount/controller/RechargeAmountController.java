package com.xmsy.server.zxyy.manager.modules.manager.rechargeamount.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.payconfig.entity.PayConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.payconfig.service.PayConfigService;
import com.xmsy.server.zxyy.manager.modules.manager.rechargeamount.entity.RechargeAmountEntity;
import com.xmsy.server.zxyy.manager.modules.manager.rechargeamount.service.RechargeAmountService;
import com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.entity.RechargeChannelEntity;
import com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.service.RechargeChannelService;



/**
 * 充值内容（金额）
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-27 10:16:53
 */
@RestController
@RequestMapping("rechargeamount/rechargeamount")
public class RechargeAmountController {
    @Autowired
    private RechargeAmountService rechargeAmountService;
    
    @Autowired
    private RechargeChannelService rechargeChannelService;
    
    @Autowired
    private PayConfigService payConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("rechargeamount:rechargeamount:list")
    public R list(RechargeAmountEntity rechargeamount, PageParam pageParam){
    	Page<RechargeAmountEntity> result = rechargeAmountService.selectNewPage(rechargeamount,pageParam);
    	Wrapper<RechargeAmountEntity> entityWrapper = new EntityWrapper<RechargeAmountEntity>(rechargeamount);
 		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
 		rechargeamount.selectPage(result, entityWrapper);
 		List<RechargeAmountEntity> list=result.getRecords();
 		if(!CollectionUtils.isEmpty(list)) {
 			for(RechargeAmountEntity entity:list) {
 				if(entity!=null) {
 					if(entity.getPaymentMethodId()!=null&&entity.getPaymentMethodId()!=0) {
 						RechargeChannelEntity rechargeChannelEntity=rechargeChannelService.selectById(entity.getPaymentMethodId());
 						if(rechargeChannelEntity!=null) {
 							if(rechargeChannelEntity.getName()!=null) {
 								entity.setPaymentMethodName(rechargeChannelEntity.getName());
 							}
 						}
 					}
 					if(entity.getPayId()!=null&&entity.getPayId()!=0) {
 						PayConfigEntity payConfigEntity=payConfigService.selectById(entity.getPayId());
 						if(payConfigEntity!=null) {
 							if(payConfigEntity.getName()!=null) {
 								entity.setPayName(payConfigEntity.getName());
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
    @RequiresPermissions("rechargeamount:rechargeamount:info")
    public R info(@PathVariable("id") Long id){
			RechargeAmountEntity rechargeAmount = rechargeAmountService.selectById(id);
        return R.ok().put("rechargeamount", rechargeAmount);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("rechargeamount:rechargeamount:save")
    public R save(@RequestBody RechargeAmountEntity rechargeamount){
			rechargeAmountService.insert(rechargeamount);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("rechargeamount:rechargeamount:update")
    public R update(@RequestBody RechargeAmountEntity rechargeamount){
			rechargeAmountService.updateById(rechargeamount);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("rechargeamount:rechargeamount:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			rechargeAmountService.deleteById(id);
	}
        return R.ok();
    }

}
