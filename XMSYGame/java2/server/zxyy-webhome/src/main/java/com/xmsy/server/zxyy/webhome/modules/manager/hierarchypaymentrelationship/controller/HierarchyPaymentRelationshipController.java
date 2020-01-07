package com.xmsy.server.zxyy.webhome.modules.manager.hierarchypaymentrelationship.controller;


import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.hierarchypaymentrelationship.entity.HierarchyPaymentRelationshipEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.hierarchypaymentrelationship.params.HierarchyPaymentRelationshipParams;
import com.xmsy.server.zxyy.webhome.modules.manager.hierarchypaymentrelationship.service.HierarchyPaymentRelationshipService;



/**
 * 层级支付关系表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-03 18:12:50
 */
@RestController
@RequestMapping("hierarchypaymentrelationship/hierarchypaymentrelationship")
public class HierarchyPaymentRelationshipController {
    @Autowired
    private HierarchyPaymentRelationshipService hierarchyPaymentRelationshipService;
    
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hierarchypaymentrelationship:hierarchypaymentrelationship:list")
    public R list(HierarchyPaymentRelationshipEntity hierarchypaymentrelationship, PageParam pageParam){
        Page<HierarchyPaymentRelationshipEntity> result = new Page<HierarchyPaymentRelationshipEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<HierarchyPaymentRelationshipEntity> entityWrapper = new EntityWrapper<HierarchyPaymentRelationshipEntity>(hierarchypaymentrelationship);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		hierarchypaymentrelationship.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hierarchypaymentrelationship:hierarchypaymentrelationship:info")
    public R info(@PathVariable("id") Long id){
		List<Long> hierarchyPaymentRelationship = hierarchyPaymentRelationshipService.selectByHierarchyId(id);
        return R.ok().put("hierarchypaymentrelationship", hierarchyPaymentRelationship);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("hierarchypaymentrelationship:hierarchypaymentrelationship:save")
    public R save(@RequestBody HierarchyPaymentRelationshipParams hierarchypaymentrelationship){
    	if(hierarchypaymentrelationship!=null) {
    		hierarchyPaymentRelationshipService.deleteByHierarchyId(hierarchypaymentrelationship.getHierarchyId());
    	}
     	List<HierarchyPaymentRelationshipEntity> entityList =new ArrayList<>();
    	for(Long pid : hierarchypaymentrelationship.getPaymentId()) {
    		HierarchyPaymentRelationshipEntity hhh=new HierarchyPaymentRelationshipEntity();
    		 hhh.setHierarchyId(hierarchypaymentrelationship.getHierarchyId());
    		 hhh.setPaymentId(pid);
    		 entityList.add(hhh);
    		
    	}
    	 hierarchyPaymentRelationshipService.insertBatch(entityList);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("hierarchypaymentrelationship:hierarchypaymentrelationship:update")
    public R update(@RequestBody HierarchyPaymentRelationshipEntity hierarchypaymentrelationship){
			hierarchyPaymentRelationshipService.updateById(hierarchypaymentrelationship);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("hierarchypaymentrelationship:hierarchypaymentrelationship:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			hierarchyPaymentRelationshipService.deleteById(id);
	}
        return R.ok();
    }

}
