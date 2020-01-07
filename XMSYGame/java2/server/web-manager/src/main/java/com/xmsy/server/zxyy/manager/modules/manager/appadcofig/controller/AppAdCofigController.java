package com.xmsy.server.zxyy.manager.modules.manager.appadcofig.controller;

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
import com.xmsy.server.zxyy.manager.modules.manager.appadcofig.entity.AppAdCofigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.appadcofig.service.AppAdCofigService;



/**
 * app广告配置
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-04 19:20:35
 */
@RestController
@RequestMapping("appadcofig/appadcofig")
public class AppAdCofigController {
    @Autowired
    private AppAdCofigService appAdCofigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("appadcofig:appadcofig:list")
    public R list(AppAdCofigEntity appadcofig, PageParam pageParam){
        Page<AppAdCofigEntity> result = new Page<AppAdCofigEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<AppAdCofigEntity> entityWrapper = new EntityWrapper<AppAdCofigEntity>(appadcofig);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		appadcofig.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("appadcofig:appadcofig:info")
    public R info(@PathVariable("id") Long id){
			AppAdCofigEntity appAdCofig = appAdCofigService.selectById(id);
        return R.ok().put("appadcofig", appAdCofig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("appadcofig:appadcofig:save")
    public R save(@RequestBody AppAdCofigEntity appadcofig){
			appAdCofigService.insert(appadcofig);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("appadcofig:appadcofig:update")
    public R update(@RequestBody AppAdCofigEntity appadcofig){
			appAdCofigService.updateById(appadcofig);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("appadcofig:appadcofig:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			appAdCofigService.deleteById(id);
	}
        return R.ok();
    }

}
