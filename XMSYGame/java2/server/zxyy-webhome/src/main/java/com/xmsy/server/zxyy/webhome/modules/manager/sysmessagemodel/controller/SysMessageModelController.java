package com.xmsy.server.zxyy.webhome.modules.manager.sysmessagemodel.controller;


import com.xmsy.common.define.page.PageParam;

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

import com.xmsy.server.zxyy.webhome.modules.manager.sysmessagemodel.entity.SysMessageModelEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysmessagemodel.service.SysMessageModelService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;



/**
 * 站内信
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-22 16:15:53
 */
@RestController
@RequestMapping("sysmessagemodel/sysmessagemodel")
public class SysMessageModelController {
    @Autowired
    private SysMessageModelService sysMessageModelService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sysmessagemodel:sysmessagemodel:list")
    public R list(SysMessageModelEntity sysmessagemodel, PageParam pageParam){
        Page<SysMessageModelEntity> result = new Page<SysMessageModelEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<SysMessageModelEntity> entityWrapper = new EntityWrapper<SysMessageModelEntity>(sysmessagemodel);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		sysmessagemodel.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sysmessagemodel:sysmessagemodel:info")
    public R info(@PathVariable("id") Long id){
			SysMessageModelEntity sysMessageModel = sysMessageModelService.selectById(id);
        return R.ok().put("sysmessagemodel", sysMessageModel);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sysmessagemodel:sysmessagemodel:save")
    public R save(@RequestBody SysMessageModelEntity sysmessagemodel){
			sysMessageModelService.insert(sysmessagemodel);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sysmessagemodel:sysmessagemodel:update")
    public R update(@RequestBody SysMessageModelEntity sysmessagemodel){
			sysMessageModelService.updateById(sysmessagemodel);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sysmessagemodel:sysmessagemodel:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			sysMessageModelService.deleteById(id);
	}
        return R.ok();
    }
    
    /**
 	 * 模板信息
 	 */
 	@RequestMapping("/getTemplate")
 	public R getTemplate(SysMessageModelEntity sysMessageModelEntity) {
 		List<SysMessageModelEntity> dataList = sysMessageModelService.selectList(new EntityWrapper<SysMessageModelEntity>(sysMessageModelEntity));
 		return R.ok().put("templateList", dataList);
 	}

}
