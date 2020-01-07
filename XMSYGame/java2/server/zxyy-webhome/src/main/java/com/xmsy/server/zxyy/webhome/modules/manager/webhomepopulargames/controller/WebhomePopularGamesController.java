package com.xmsy.server.zxyy.webhome.modules.manager.webhomepopulargames.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.webhomepopulargames.entity.WebhomePopularGamesEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomepopulargames.service.WebhomePopularGamesService;



/**
 * 官网热门游戏
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-01-29 16:07:37
 */
@RestController
@RequestMapping("webhomepopulargames/webhomepopulargames")
public class WebhomePopularGamesController {
    @Autowired
    private WebhomePopularGamesService webhomePopularGamesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("webhomepopulargames:webhomepopulargames:list")
    public R list(WebhomePopularGamesEntity webhomepopulargames, PageParam pageParam){
    	String name=webhomepopulargames.getName();
    	webhomepopulargames.setName(null);
        Page<WebhomePopularGamesEntity> result = new Page<WebhomePopularGamesEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<WebhomePopularGamesEntity> entityWrapper = new EntityWrapper<WebhomePopularGamesEntity>(webhomepopulargames).like("name", name).orderBy("order_num");
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		webhomepopulargames.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("webhomepopulargames:webhomepopulargames:info")
    public R info(@PathVariable("id") Long id){
			WebhomePopularGamesEntity webhomePopularGames = webhomePopularGamesService.selectById(id);
        return R.ok().put("webhomepopulargames", webhomePopularGames);
    }

    /**
     * 保存
     */
    @SysLog("官网热门游戏新增")
    @RequestMapping("/save")
    @RequiresPermissions("webhomepopulargames:webhomepopulargames:save")
    public R save(@RequestBody WebhomePopularGamesEntity webhomepopulargames){
			webhomePopularGamesService.insert(webhomepopulargames);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("官网热门游戏修改")
    @RequestMapping("/update")
    @RequiresPermissions("webhomepopulargames:webhomepopulargames:update")
    public R update(@RequestBody WebhomePopularGamesEntity webhomepopulargames){
			webhomePopularGamesService.updateById(webhomepopulargames);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("官网热门游戏删除")
    @RequestMapping("/delete")
    @RequiresPermissions("webhomepopulargames:webhomepopulargames:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			webhomePopularGamesService.deleteById(id);
	}
        return R.ok();
    }

}
