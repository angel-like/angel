package com.xmsy.server.zxyy.manager.modules.manager.sysprop.controller;


import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.sysprop.entity.SysPropEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysprop.service.SysPropService;

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



/**
 * 系统道具
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-23 10:18:28
 */
@RestController
@RequestMapping("sysprop/sysprop")
public class SysPropController {
    @Autowired
    private SysPropService sysPropService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sysprop:sysprop:list")
    public R list(SysPropEntity sysprop, PageParam pageParam){
        Page<SysPropEntity> result = new Page<SysPropEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<SysPropEntity> entityWrapper = new EntityWrapper<SysPropEntity>(sysprop);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		sysprop.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sysprop:sysprop:info")
    public R info(@PathVariable("id") Long id){
			SysPropEntity sysProp = sysPropService.selectById(id);
        return R.ok().put("sysprop", sysProp);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sysprop:sysprop:save")
    public R save(@RequestBody SysPropEntity sysprop){
			sysPropService.insert(sysprop);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sysprop:sysprop:update")
    public R update(@RequestBody SysPropEntity sysprop){
			sysPropService.updateById(sysprop);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sysprop:sysprop:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			sysPropService.deleteById(id);
	}
        return R.ok();
    }
    
    /**
     * 获取道具
     * @param sysPropEntity
     * @return
     */
    @RequestMapping("/getProp")
    public R getProp(SysPropEntity sysPropEntity) {
    	List<SysPropEntity> dataList = sysPropService.selectList(new EntityWrapper<SysPropEntity>(sysPropEntity));
    	return R.ok().put("propList", dataList);
    }
    
    /**
     * 获取道具
     * @param sysPropEntity
     * @return
     */
    @RequestMapping("/getPropNotCoin")
    public R getPropNotCoin(SysPropEntity sysPropEntity) {
    	EntityWrapper<SysPropEntity> entityWrapper =  new EntityWrapper<SysPropEntity>();
    	entityWrapper.ne("name", "金币");
    	List<SysPropEntity> dataList = sysPropService.selectList(entityWrapper);
    	return R.ok().put("propList", dataList);
    }

}
