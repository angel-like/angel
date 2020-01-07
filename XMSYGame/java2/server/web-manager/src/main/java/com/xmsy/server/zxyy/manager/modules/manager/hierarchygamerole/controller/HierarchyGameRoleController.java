package com.xmsy.server.zxyy.manager.modules.manager.hierarchygamerole.controller;

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
import com.xmsy.server.zxyy.manager.modules.manager.hierarchygamerole.entity.HierarchyGameRoleEntity;
import com.xmsy.server.zxyy.manager.modules.manager.hierarchygamerole.service.HierarchyGameRoleService;



/**
 * 层级游戏权限关联表
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-09 14:26:36
 */
@RestController
@RequestMapping("hierarchygamerole/hierarchygamerole")
public class HierarchyGameRoleController {
    @Autowired
    private HierarchyGameRoleService hierarchyGameRoleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hierarchygamerole:hierarchygamerole:list")
    public R list(HierarchyGameRoleEntity hierarchygamerole, PageParam pageParam){
        Page<HierarchyGameRoleEntity> result = new Page<HierarchyGameRoleEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<HierarchyGameRoleEntity> entityWrapper = new EntityWrapper<HierarchyGameRoleEntity>(hierarchygamerole);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		hierarchygamerole.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hierarchygamerole:hierarchygamerole:info")
    public R info(@PathVariable("id") Long id){
			HierarchyGameRoleEntity hierarchyGameRole = hierarchyGameRoleService.selectById(id);
        return R.ok().put("hierarchygamerole", hierarchyGameRole);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("hierarchygamerole:hierarchygamerole:save")
    public R save(@RequestBody HierarchyGameRoleEntity hierarchygamerole){
			hierarchyGameRoleService.insert(hierarchygamerole);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("hierarchygamerole:hierarchygamerole:update")
    public R update(@RequestBody HierarchyGameRoleEntity hierarchygamerole){
			hierarchyGameRoleService.updateById(hierarchygamerole);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("hierarchygamerole:hierarchygamerole:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			hierarchyGameRoleService.deleteById(id);
	}
        return R.ok();
    }

}
