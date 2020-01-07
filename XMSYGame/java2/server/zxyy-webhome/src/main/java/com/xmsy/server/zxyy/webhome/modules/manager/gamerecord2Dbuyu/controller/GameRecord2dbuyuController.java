package com.xmsy.server.zxyy.webhome.modules.manager.gamerecord2Dbuyu.controller;


import com.xmsy.common.define.page.PageParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord2Dbuyu.entity.GameRecord2dbuyuEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord2Dbuyu.service.GameRecord2dbuyuService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;



/**
 * 游戏记录-2D捕鱼
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-12-30 11:21:16
 */
@RestController
@RequestMapping("gamerecord2Dbuyu/gamerecord2dbuyu")
public class GameRecord2dbuyuController {
    @Autowired
    private GameRecord2dbuyuService gameRecord2dbuyuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecord2Dbuyu:gamerecord2dbuyu:list")
    public R list(GameRecord2dbuyuEntity gamerecord2dbuyu, PageParam pageParam){
        Page<GameRecord2dbuyuEntity> result = new Page<GameRecord2dbuyuEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecord2dbuyuEntity> entityWrapper = new EntityWrapper<GameRecord2dbuyuEntity>(gamerecord2dbuyu);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecord2dbuyu.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecord2Dbuyu:gamerecord2dbuyu:info")
    public R info(@PathVariable("id") Long id){
			GameRecord2dbuyuEntity gameRecord2dbuyu = gameRecord2dbuyuService.selectById(id);
        return R.ok().put("gamerecord2dbuyu", gameRecord2dbuyu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecord2Dbuyu:gamerecord2dbuyu:save")
    public R save(@RequestBody GameRecord2dbuyuEntity gamerecord2dbuyu){
			gameRecord2dbuyuService.insert(gamerecord2dbuyu);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecord2Dbuyu:gamerecord2dbuyu:update")
    public R update(@RequestBody GameRecord2dbuyuEntity gamerecord2dbuyu){
			gameRecord2dbuyuService.updateById(gamerecord2dbuyu);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecord2Dbuyu:gamerecord2dbuyu:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecord2dbuyuService.deleteById(id);
	}
        return R.ok();
    }

}
