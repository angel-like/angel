package com.xmsy.server.zxyy.webhome.modules.manager.webhomehead.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.webhomehead.entity.WebhomeHeadEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomehead.service.WebhomeHeadService;



/**
 * 官网头部管理
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-01-24 20:28:36
 */
@RestController
@RequestMapping("webhomehead/webhomehead")
public class WebhomeHeadController {
    @Autowired
    private WebhomeHeadService webhomeHeadService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("webhomehead:webhomehead:list")
    public R list(WebhomeHeadEntity gameconfig, PageParam pageParam) {
		Page<WebhomeHeadEntity> result = new Page<WebhomeHeadEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<WebhomeHeadEntity> entityWrapper = new EntityWrapper<WebhomeHeadEntity>(gameconfig);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gameconfig.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("webhomehead:webhomehead:info")
    public R info(@PathVariable("id") Long id){
			WebhomeHeadEntity webhomeHead = webhomeHeadService.selectById(id);
        return R.ok().put("webhomehead", webhomeHead);
    }

    /**
     * 保存
     */
    @SysLog("官网头部新增")
    @RequestMapping("/save")
    @RequiresPermissions("webhomehead:webhomehead:save")
    public R save(@RequestBody WebhomeHeadEntity webhomeHead){
			webhomeHeadService.insert(webhomeHead);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("官网头部修改")
    @RequestMapping("/update")
    @RequiresPermissions("webhomehead:webhomehead:update")
    public R update(@RequestBody WebhomeHeadEntity webhomeHead){
			webhomeHeadService.updateById(webhomeHead);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("官网头部删除")
    @RequestMapping("/delete")
    @RequiresPermissions("webhomehead:webhomehead:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			webhomeHeadService.deleteById(id);
	}
        return R.ok();
    }

}
