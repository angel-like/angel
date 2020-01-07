package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordhongheidazhan.controller;


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

import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordhongheidazhan.entity.GameRecordHongheidazhanEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordhongheidazhan.service.GameRecordHongheidazhanService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;



/**
 * 游戏记录-红黑大战
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-08-29 09:49:39
 */
@RestController
@RequestMapping("gamerecordhongheidazhan/gamerecordhongheidazhan")
public class GameRecordHongheidazhanController {
    @Autowired
    private GameRecordHongheidazhanService gameRecordHongheidazhanService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordhongheidazhan:gamerecordhongheidazhan:list")
    public R list(GameRecordHongheidazhanEntity gamerecordhongheidazhan, PageParam pageParam){
        Page<GameRecordHongheidazhanEntity> result = new Page<GameRecordHongheidazhanEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordHongheidazhanEntity> entityWrapper = new EntityWrapper<GameRecordHongheidazhanEntity>(gamerecordhongheidazhan);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordhongheidazhan.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordhongheidazhan:gamerecordhongheidazhan:info")
    public R info(@PathVariable("id") Long id){
			GameRecordHongheidazhanEntity gameRecordHongheidazhan = gameRecordHongheidazhanService.selectById(id);
        return R.ok().put("gamerecordhongheidazhan", gameRecordHongheidazhan);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordhongheidazhan:gamerecordhongheidazhan:save")
    public R save(@RequestBody GameRecordHongheidazhanEntity gamerecordhongheidazhan){
			gameRecordHongheidazhanService.insert(gamerecordhongheidazhan);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordhongheidazhan:gamerecordhongheidazhan:update")
    public R update(@RequestBody GameRecordHongheidazhanEntity gamerecordhongheidazhan){
			gameRecordHongheidazhanService.updateById(gamerecordhongheidazhan);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordhongheidazhan:gamerecordhongheidazhan:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordHongheidazhanService.deleteById(id);
	}
        return R.ok();
    }

}
