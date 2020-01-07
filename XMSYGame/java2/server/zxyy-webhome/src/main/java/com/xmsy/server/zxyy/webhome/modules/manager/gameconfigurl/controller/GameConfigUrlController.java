package com.xmsy.server.zxyy.webhome.modules.manager.gameconfigurl.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.gameconfigurl.entity.GameConfigUrlEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gameconfigurl.service.GameConfigUrlService;



/**
 * 游戏路径表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-12 14:08:17
 */
@RestController
@RequestMapping("gameconfigurl/gameconfigurl")
public class GameConfigUrlController {
    @Autowired
    private GameConfigUrlService gameConfigUrlService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gameconfigurl:gameconfigurl:list")
    public R list(GameConfigUrlEntity gameconfigurl, PageParam pageParam){
        Page<GameConfigUrlEntity> result = new Page<GameConfigUrlEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameConfigUrlEntity> entityWrapper = new EntityWrapper<GameConfigUrlEntity>(gameconfigurl);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gameconfigurl.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gameconfigurl:gameconfigurl:info")
    public R info(@PathVariable("id") Long id){
			GameConfigUrlEntity gameConfigUrl = gameConfigUrlService.selectById(id);
        return R.ok().put("gameconfigurl", gameConfigUrl);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gameconfigurl:gameconfigurl:save")
    public R save(@RequestBody GameConfigUrlEntity gameconfigurl){
			gameConfigUrlService.insert(gameconfigurl);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gameconfigurl:gameconfigurl:update")
    public R update(@RequestBody GameConfigUrlEntity gameconfigurl){
			gameConfigUrlService.updateById(gameconfigurl);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gameconfigurl:gameconfigurl:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameConfigUrlService.deleteById(id);
	}
        return R.ok();
    }

}
