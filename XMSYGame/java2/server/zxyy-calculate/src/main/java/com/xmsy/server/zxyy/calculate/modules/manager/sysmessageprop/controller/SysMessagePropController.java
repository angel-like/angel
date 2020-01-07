package com.xmsy.server.zxyy.calculate.modules.manager.sysmessageprop.controller;

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
import com.xmsy.server.zxyy.calculate.common.utils.PageUtils;
import com.xmsy.server.zxyy.calculate.common.utils.R;
import com.xmsy.server.zxyy.calculate.modules.manager.sysmessageprop.entity.SysMessagePropEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.sysmessageprop.service.SysMessagePropService;



/**
 * 站内信-道具列表
 *
 * @author adu
 * @email xxxxx
 * @date 2019-05-23 19:08:25
 */
@RestController
@RequestMapping("sysmessageprop/sysmessageprop")
public class SysMessagePropController {
    @Autowired
    private SysMessagePropService sysMessagePropService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sysmessageprop:sysmessageprop:list")
    public R list(SysMessagePropEntity sysmessageprop, PageParam pageParam){
        Page<SysMessagePropEntity> result = new Page<SysMessagePropEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<SysMessagePropEntity> entityWrapper = new EntityWrapper<SysMessagePropEntity>(sysmessageprop);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		sysmessageprop.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sysmessageprop:sysmessageprop:info")
    public R info(@PathVariable("id") Long id){
			SysMessagePropEntity sysMessageProp = sysMessagePropService.selectById(id);
        return R.ok().put("sysmessageprop", sysMessageProp);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sysmessageprop:sysmessageprop:save")
    public R save(@RequestBody SysMessagePropEntity sysmessageprop){
			sysMessagePropService.insert(sysmessageprop);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sysmessageprop:sysmessageprop:update")
    public R update(@RequestBody SysMessagePropEntity sysmessageprop){
			sysMessagePropService.updateById(sysmessageprop);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sysmessageprop:sysmessageprop:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			sysMessagePropService.deleteById(id);
	}
        return R.ok();
    }

}
