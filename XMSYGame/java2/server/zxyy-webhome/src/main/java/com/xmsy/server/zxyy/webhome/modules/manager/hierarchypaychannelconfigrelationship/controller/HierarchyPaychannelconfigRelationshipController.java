package com.xmsy.server.zxyy.webhome.modules.manager.hierarchypaychannelconfigrelationship.controller;


import com.xmsy.common.define.page.PageParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import com.xmsy.server.zxyy.webhome.modules.manager.hierarchypaychannelconfigrelationship.entity.HierarchyPaychannelconfigRelationshipEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.hierarchypaychannelconfigrelationship.service.HierarchyPaychannelconfigRelationshipService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;



/**
 * 层级支付限额关系表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-03 11:02:14
 */
@RestController
@RequestMapping("hierarchypaychannelconfigrelationship/hierarchypaychannelconfigrelationship")
public class HierarchyPaychannelconfigRelationshipController {
    @Autowired
    private HierarchyPaychannelconfigRelationshipService hierarchyPaychannelconfigRelationshipService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hierarchypaychannelconfigrelationship:hierarchypaychannelconfigrelationship:list")
    public R list(HierarchyPaychannelconfigRelationshipEntity hierarchypaychannelconfigrelationship, PageParam pageParam){
        Page<HierarchyPaychannelconfigRelationshipEntity> result = new Page<HierarchyPaychannelconfigRelationshipEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<HierarchyPaychannelconfigRelationshipEntity> entityWrapper = new EntityWrapper<HierarchyPaychannelconfigRelationshipEntity>(hierarchypaychannelconfigrelationship);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		hierarchypaychannelconfigrelationship.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hierarchypaychannelconfigrelationship:hierarchypaychannelconfigrelationship:info")
    public R info(@PathVariable("id") Long id){
			HierarchyPaychannelconfigRelationshipEntity hierarchyPaychannelconfigRelationship = hierarchyPaychannelconfigRelationshipService.selectById(id);
        return R.ok().put("hierarchypaychannelconfigrelationship", hierarchyPaychannelconfigRelationship);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("hierarchypaychannelconfigrelationship:hierarchypaychannelconfigrelationship:save")
    public R save(@RequestBody HierarchyPaychannelconfigRelationshipEntity hierarchypaychannelconfigrelationship){
			hierarchyPaychannelconfigRelationshipService.insert(hierarchypaychannelconfigrelationship);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("hierarchypaychannelconfigrelationship:hierarchypaychannelconfigrelationship:update")
    public R update(@RequestBody HierarchyPaychannelconfigRelationshipEntity hierarchypaychannelconfigrelationship){
			hierarchyPaychannelconfigRelationshipService.updateById(hierarchypaychannelconfigrelationship);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("hierarchypaychannelconfigrelationship:hierarchypaychannelconfigrelationship:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			hierarchyPaychannelconfigRelationshipService.deleteById(id);
	}
        return R.ok();
    }

}
