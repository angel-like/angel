package com.xmsy.server.zxyy.webhome.modules.manager.sysconfig.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.sysconfig.entity.SysConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysconfig.service.SysConfigService;



/**
 * 系统配置表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-05-21 15:26:43
 */
@RestController
@RequestMapping("sysconfig/sysconfig")
public class SysConfigController {
    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sysconfig:sysconfig:list")
    public R list(SysConfigEntity sysconfig, PageParam pageParam){
        Page<SysConfigEntity> result = new Page<SysConfigEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<SysConfigEntity> entityWrapper = new EntityWrapper<SysConfigEntity>(sysconfig);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		sysconfig.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sysconfig:sysconfig:info")
    public R info(@PathVariable("id") Long id){
			SysConfigEntity sysConfig = sysConfigService.selectById(id);
        return R.ok().put("sysconfig", sysConfig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sysconfig:sysconfig:save")
    public R save(@RequestBody SysConfigEntity sysconfig){
			sysConfigService.insert(sysconfig);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sysconfig:sysconfig:update")
    public R update(@RequestBody SysConfigEntity sysconfig){
			sysConfigService.updateById(sysconfig);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sysconfig:sysconfig:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			sysConfigService.deleteById(id);
	}
        return R.ok();
    }
    /**
     * 下拉
     */
    @RequestMapping("/select")
//    @RequiresPermissions("sysconfig:sysconfig:select")
    public R select(){
    	List<SysConfigEntity> list=sysConfigService.selectList(new EntityWrapper<SysConfigEntity>(new SysConfigEntity()));
    	SysConfigEntity entity=new SysConfigEntity();
    	entity.setId(0L);
    	entity.setParamKey("系统管理");
    	list.add(entity);
    	return R.ok().put("select", list);
    }

}
