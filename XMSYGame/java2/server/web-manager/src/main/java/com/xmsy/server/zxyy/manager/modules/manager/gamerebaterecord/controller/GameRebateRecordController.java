package com.xmsy.server.zxyy.manager.modules.manager.gamerebaterecord.controller;

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
import com.xmsy.server.zxyy.manager.modules.manager.gamerebaterecord.entity.GameRebateRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerebaterecord.service.GameRebateRecordService;



/**
 * 游戏返利记录表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-02-21 20:14:13
 */
@RestController
@RequestMapping("gamerebaterecord/gamerebaterecord")
public class GameRebateRecordController {
    @Autowired
    private GameRebateRecordService gameRebateRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerebaterecord:gamerebaterecord:list")
    public R list(GameRebateRecordEntity gamerebaterecord, PageParam pageParam){
        Page<GameRebateRecordEntity> result = new Page<GameRebateRecordEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRebateRecordEntity> entityWrapper = new EntityWrapper<GameRebateRecordEntity>(gamerebaterecord);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerebaterecord.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerebaterecord:gamerebaterecord:info")
    public R info(@PathVariable("id") Long id){
			GameRebateRecordEntity gameRebateRecord = gameRebateRecordService.selectById(id);
        return R.ok().put("gamerebaterecord", gameRebateRecord);
    }

    /**
     * 保存
     */
    @SysLog("游戏返利新增")
    @RequestMapping("/save")
    @RequiresPermissions("gamerebaterecord:gamerebaterecord:save")
    public R save(@RequestBody GameRebateRecordEntity gamerebaterecord){
			gameRebateRecordService.insert(gamerebaterecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("游戏返利修改")
    @RequestMapping("/update")
    @RequiresPermissions("gamerebaterecord:gamerebaterecord:update")
    public R update(@RequestBody GameRebateRecordEntity gamerebaterecord){
			gameRebateRecordService.updateById(gamerebaterecord);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("游戏返利删除")
    @RequestMapping("/delete")
    @RequiresPermissions("gamerebaterecord:gamerebaterecord:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRebateRecordService.deleteById(id);
	}
        return R.ok();
    }

}
