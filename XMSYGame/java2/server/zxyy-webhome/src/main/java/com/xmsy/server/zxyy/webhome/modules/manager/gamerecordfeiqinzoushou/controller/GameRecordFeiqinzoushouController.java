package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfeiqinzoushou.controller;


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

import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfeiqinzoushou.entity.GameRecordFeiqinzoushouEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfeiqinzoushou.service.GameRecordFeiqinzoushouService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;



/**
 * 游戏记录-飞禽走兽
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-06 10:49:17
 */
@RestController
@RequestMapping("gamerecordfeiqinzoushou/gamerecordfeiqinzoushou")
public class GameRecordFeiqinzoushouController {
    @Autowired
    private GameRecordFeiqinzoushouService gameRecordFeiqinzoushouService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordfeiqinzoushou:gamerecordfeiqinzoushou:list")
    public R list(GameRecordFeiqinzoushouEntity gamerecordfeiqinzoushou, PageParam pageParam){
        Page<GameRecordFeiqinzoushouEntity> result = new Page<GameRecordFeiqinzoushouEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordFeiqinzoushouEntity> entityWrapper = new EntityWrapper<GameRecordFeiqinzoushouEntity>(gamerecordfeiqinzoushou);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordfeiqinzoushou.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordfeiqinzoushou:gamerecordfeiqinzoushou:info")
    public R info(@PathVariable("id") Long id){
			GameRecordFeiqinzoushouEntity gameRecordFeiqinzoushou = gameRecordFeiqinzoushouService.selectById(id);
        return R.ok().put("gamerecordfeiqinzoushou", gameRecordFeiqinzoushou);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordfeiqinzoushou:gamerecordfeiqinzoushou:save")
    public R save(@RequestBody GameRecordFeiqinzoushouEntity gamerecordfeiqinzoushou){
			gameRecordFeiqinzoushouService.insert(gamerecordfeiqinzoushou);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordfeiqinzoushou:gamerecordfeiqinzoushou:update")
    public R update(@RequestBody GameRecordFeiqinzoushouEntity gamerecordfeiqinzoushou){
			gameRecordFeiqinzoushouService.updateById(gamerecordfeiqinzoushou);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordfeiqinzoushou:gamerecordfeiqinzoushou:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordFeiqinzoushouService.deleteById(id);
	}
        return R.ok();
    }

}
