package com.xmsy.server.zxyy.manager.modules.manager.apppromotionstype.controller;

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
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.apppromotionstype.entity.AppPromotionsTypeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.apppromotionstype.service.AppPromotionsTypeService;



/**
 * 官网优惠活动类型
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-28 16:22:11
 */
@RestController
@RequestMapping("apppromotionstype/apppromotionstype")
public class AppPromotionsTypeController {
    @Autowired
    private AppPromotionsTypeService appPromotionsTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("apppromotionstype:apppromotionstype:list")
    public R list(AppPromotionsTypeEntity apppromotionstype, PageParam pageParam){
    	String name=apppromotionstype.getName();
    	apppromotionstype.setName(null);
        Page<AppPromotionsTypeEntity> result = new Page<AppPromotionsTypeEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<AppPromotionsTypeEntity> entityWrapper = new EntityWrapper<AppPromotionsTypeEntity>(apppromotionstype);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		apppromotionstype.selectPage(result, entityWrapper.like("name", name));
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("apppromotionstype:apppromotionstype:info")
    public R info(@PathVariable("id") Long id){
			AppPromotionsTypeEntity appPromotionsType = appPromotionsTypeService.selectById(id);
        return R.ok().put("apppromotionstype", appPromotionsType);
    }

    /**
     * 保存
     */
    @SysLog("官网优惠活动类型新增")
    @RequestMapping("/save")
    @RequiresPermissions("apppromotionstype:apppromotionstype:save")
    public R save(@RequestBody AppPromotionsTypeEntity apppromotionstype){
			appPromotionsTypeService.insert(apppromotionstype);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("官网优惠活动类型修改")
    @RequestMapping("/update")
    @RequiresPermissions("apppromotionstype:apppromotionstype:update")
    public R update(@RequestBody AppPromotionsTypeEntity apppromotionstype){
			appPromotionsTypeService.updateById(apppromotionstype);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("官网优惠活动类型删除")
    @RequestMapping("/delete")
    @RequiresPermissions("apppromotionstype:apppromotionstype:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			appPromotionsTypeService.deleteById(id);
	}
        return R.ok();
    }
    /**
     * 下拉
     */
    @RequestMapping("/select")
    public R select(){
			List<AppPromotionsTypeEntity> list=appPromotionsTypeService.selectList(new EntityWrapper<AppPromotionsTypeEntity>(new AppPromotionsTypeEntity()));
        return R.ok().put("list", list);
    }

}
