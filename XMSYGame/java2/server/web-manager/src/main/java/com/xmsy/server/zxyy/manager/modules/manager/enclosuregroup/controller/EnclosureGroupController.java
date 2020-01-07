package com.xmsy.server.zxyy.manager.modules.manager.enclosuregroup.controller;

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
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.enclosuregroup.entity.EnclosureGroupEntity;
import com.xmsy.server.zxyy.manager.modules.manager.enclosuregroup.service.EnclosureGroupService;



/**
 * 图片分组
 *
 * @author ayang
 * @email xxxxx
 * @date 2019-10-28 15:43:09
 */
@RestController
@RequestMapping("enclosuregroup/enclosuregroup")
public class EnclosureGroupController {
    @Autowired
    private EnclosureGroupService enclosureGroupService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("enclosuregroup:enclosuregroup:list")
    public R list(EnclosureGroupEntity enclosuregroup, PageParam pageParam){
        Page<EnclosureGroupEntity> result = new Page<EnclosureGroupEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<EnclosureGroupEntity> entityWrapper = new EntityWrapper<EnclosureGroupEntity>(enclosuregroup);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		enclosuregroup.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("enclosuregroup:enclosuregroup:info")
    public R info(@PathVariable("id") Long id){
			EnclosureGroupEntity enclosureGroup = enclosureGroupService.selectById(id);
        return R.ok().put("enclosuregroup", enclosureGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("enclosuregroup:enclosuregroup:save")
    public R save(@RequestBody EnclosureGroupEntity enclosuregroup){
        EnclosureGroupEntity GroupEntity = new EnclosureGroupEntity();
      GroupEntity.setName(enclosuregroup.getName());
        Wrapper<EnclosureGroupEntity> entityWrapper = new EntityWrapper<EnclosureGroupEntity>(GroupEntity);
        List<EnclosureGroupEntity> list = enclosureGroupService.selectList(entityWrapper);
        if(list.size()>0){
            return  R.error("分组名称已存在");
        }
        enclosureGroupService.insert(enclosuregroup);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("enclosuregroup:enclosuregroup:update")
    public R update(@RequestBody EnclosureGroupEntity enclosuregroup){
			enclosureGroupService.updateById(enclosuregroup);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("enclosuregroup:enclosuregroup:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			enclosureGroupService.deleteById(id);
	}
        return R.ok();
    }


    /**
     * 分组下拉
     */
    @RequestMapping("/select")
    public R select(){
        List<EnclosureGroupEntity> list=enclosureGroupService.selectList(new EntityWrapper<EnclosureGroupEntity>(new EnclosureGroupEntity()));
        return R.ok().put("list", list);
    }



}
