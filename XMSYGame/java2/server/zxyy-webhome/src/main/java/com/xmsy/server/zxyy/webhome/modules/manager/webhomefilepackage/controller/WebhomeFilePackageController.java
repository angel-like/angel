package com.xmsy.server.zxyy.webhome.modules.manager.webhomefilepackage.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.webhomefilepackage.entity.WebhomeFilePackageEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomefilepackage.service.WebhomeFilePackageService;



/**
 * 安装包
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-08-22 17:49:18
 */
@RestController
@RequestMapping("webhomefilepackage/webhomefilepackage")
public class WebhomeFilePackageController {
    @Autowired
    private WebhomeFilePackageService webhomeFilePackageService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("webhomefilepackage:webhomefilepackage:list")
    public R list(WebhomeFilePackageEntity webhomefilepackage, PageParam pageParam){
        Page<WebhomeFilePackageEntity> result = new Page<WebhomeFilePackageEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<WebhomeFilePackageEntity> entityWrapper = new EntityWrapper<WebhomeFilePackageEntity>(webhomefilepackage);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		webhomefilepackage.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("webhomefilepackage:webhomefilepackage:info")
    public R info(@PathVariable("id") Long id){
			WebhomeFilePackageEntity webhomeFilePackage = webhomeFilePackageService.selectById(id);
        return R.ok().put("webhomefilepackage", webhomeFilePackage);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("webhomefilepackage:webhomefilepackage:save")
    public R save(@RequestBody WebhomeFilePackageEntity webhomefilepackage){
			webhomeFilePackageService.insert(webhomefilepackage);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("webhomefilepackage:webhomefilepackage:update")
    public R update(@RequestBody WebhomeFilePackageEntity webhomefilepackage){
			webhomeFilePackageService.updateById(webhomefilepackage);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("webhomefilepackage:webhomefilepackage:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			webhomeFilePackageService.deleteById(id);
	}
        return R.ok();
    }

}
