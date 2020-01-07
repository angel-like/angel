package com.xmsy.server.zxyy.webhome.modules.manager.batchrechargeprop.controller;


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

import com.xmsy.server.zxyy.webhome.modules.manager.batchrechargeprop.entity.BatchRechargePropEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.batchrechargeprop.service.BatchRechargePropService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;



/**
 * 批量充值会员道具
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-27 11:42:39
 */
@RestController
@RequestMapping("batchrechargeprop/batchrechargeprop")
public class BatchRechargePropController {
    @Autowired
    private BatchRechargePropService batchRechargePropService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("batchrechargeprop:batchrechargeprop:list")
    public R list(BatchRechargePropEntity batchrechargeprop, PageParam pageParam){
        Page<BatchRechargePropEntity> result = new Page<BatchRechargePropEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<BatchRechargePropEntity> entityWrapper = new EntityWrapper<BatchRechargePropEntity>(batchrechargeprop);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		batchrechargeprop.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("batchrechargeprop:batchrechargeprop:info")
    public R info(@PathVariable("id") Long id){
			BatchRechargePropEntity batchRechargeProp = batchRechargePropService.selectById(id);
        return R.ok().put("batchrechargeprop", batchRechargeProp);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("batchrechargeprop:batchrechargeprop:save")
    public R save(@RequestBody BatchRechargePropEntity batchrechargeprop){
			batchRechargePropService.insert(batchrechargeprop);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("batchrechargeprop:batchrechargeprop:update")
    public R update(@RequestBody BatchRechargePropEntity batchrechargeprop){
			batchRechargePropService.updateById(batchrechargeprop);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("batchrechargeprop:batchrechargeprop:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			batchRechargePropService.deleteById(id);
	}
        return R.ok();
    }

}
