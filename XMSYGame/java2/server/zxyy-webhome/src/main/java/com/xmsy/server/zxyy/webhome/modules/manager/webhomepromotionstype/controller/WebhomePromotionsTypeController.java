package com.xmsy.server.zxyy.webhome.modules.manager.webhomepromotionstype.controller;

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
import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomepromotionstype.entity.WebhomePromotionsTypeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomepromotionstype.service.WebhomePromotionsTypeService;



/**
 * 官网优惠活动类型
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-28 16:22:11
 */
@RestController
@RequestMapping("webhomepromotionstype/webhomepromotionstype")
public class WebhomePromotionsTypeController {
    @Autowired
    private WebhomePromotionsTypeService webhomePromotionsTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("webhomepromotionstype:webhomepromotionstype:list")
    public R list(WebhomePromotionsTypeEntity webhomepromotionstype, PageParam pageParam){
    	String name=webhomepromotionstype.getName();
    	webhomepromotionstype.setName(null);
        Page<WebhomePromotionsTypeEntity> result = new Page<WebhomePromotionsTypeEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<WebhomePromotionsTypeEntity> entityWrapper = new EntityWrapper<WebhomePromotionsTypeEntity>(webhomepromotionstype);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		webhomepromotionstype.selectPage(result, entityWrapper.like("name", name));
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("webhomepromotionstype:webhomepromotionstype:info")
    public R info(@PathVariable("id") Long id){
			WebhomePromotionsTypeEntity webhomePromotionsType = webhomePromotionsTypeService.selectById(id);
        return R.ok().put("webhomepromotionstype", webhomePromotionsType);
    }

    /**
     * 保存
     */
    @SysLog("官网优惠活动类型新增")
    @RequestMapping("/save")
    @RequiresPermissions("webhomepromotionstype:webhomepromotionstype:save")
    public R save(@RequestBody WebhomePromotionsTypeEntity webhomepromotionstype){
			webhomePromotionsTypeService.insert(webhomepromotionstype);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("官网优惠活动类型修改")
    @RequestMapping("/update")
    @RequiresPermissions("webhomepromotionstype:webhomepromotionstype:update")
    public R update(@RequestBody WebhomePromotionsTypeEntity webhomepromotionstype){
			webhomePromotionsTypeService.updateById(webhomepromotionstype);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("官网优惠活动类型删除")
    @RequestMapping("/delete")
    @RequiresPermissions("webhomepromotionstype:webhomepromotionstype:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			webhomePromotionsTypeService.deleteById(id);
	}
        return R.ok();
    }
    /**
     * 下拉
     */
    @RequestMapping("/select")
    public R select(){
			List<WebhomePromotionsTypeEntity> list=webhomePromotionsTypeService.selectList(new EntityWrapper<WebhomePromotionsTypeEntity>(null));
        return R.ok().put("list", list);
    }

}
