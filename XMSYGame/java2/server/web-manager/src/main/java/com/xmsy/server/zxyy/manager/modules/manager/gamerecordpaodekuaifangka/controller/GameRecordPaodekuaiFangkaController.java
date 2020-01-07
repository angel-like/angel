package com.xmsy.server.zxyy.manager.modules.manager.gamerecordpaodekuaifangka.controller;


import com.xmsy.common.define.page.PageParam;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import com.xmsy.server.zxyy.manager.modules.manager.gamerecordpaodekuaifangka.entity.GameRecordPaodekuaiFangkaEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordpaodekuaifangka.service.GameRecordPaodekuaiFangkaService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;



/**
 * 游戏记录-房卡跑得快
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-06 10:47:28
 */
@RestController
@RequestMapping("gamerecordpaodekuaifangka/gamerecordpaodekuaifangka")
public class GameRecordPaodekuaiFangkaController {
    @Autowired
    private GameRecordPaodekuaiFangkaService gameRecordPaodekuaiFangkaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordpaodekuaifangka:gamerecordpaodekuaifangka:list")
    public R list(GameRecordPaodekuaiFangkaEntity gamerecordpaodekuaifangka, PageParam pageParam){
        Page<GameRecordPaodekuaiFangkaEntity> result = new Page<GameRecordPaodekuaiFangkaEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordPaodekuaiFangkaEntity> entityWrapper = new EntityWrapper<GameRecordPaodekuaiFangkaEntity>(gamerecordpaodekuaifangka);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordpaodekuaifangka.getStartTime()),"create_time", gamerecordpaodekuaifangka.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordpaodekuaifangka.getEndTime()),"create_time", gamerecordpaodekuaifangka.getEndTime());
		if (StringUtils.isEmpty(pageParam.getSort())) {
			pageParam.setSort("id");
		}
		if (null != pageParam.getDirection()) {
			pageParam.setDirection(false);
		}
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordpaodekuaifangka.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordpaodekuaifangka:gamerecordpaodekuaifangka:info")
    public R info(@PathVariable("id") Long id){
			GameRecordPaodekuaiFangkaEntity gameRecordPaodekuaiFangka = gameRecordPaodekuaiFangkaService.selectById(id);
        return R.ok().put("gamerecordpaodekuaifangka", gameRecordPaodekuaiFangka);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordpaodekuaifangka:gamerecordpaodekuaifangka:save")
    public R save(@RequestBody GameRecordPaodekuaiFangkaEntity gamerecordpaodekuaifangka){
			gameRecordPaodekuaiFangkaService.insert(gamerecordpaodekuaifangka);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordpaodekuaifangka:gamerecordpaodekuaifangka:update")
    public R update(@RequestBody GameRecordPaodekuaiFangkaEntity gamerecordpaodekuaifangka){
			gameRecordPaodekuaiFangkaService.updateById(gamerecordpaodekuaifangka);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordpaodekuaifangka:gamerecordpaodekuaifangka:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordPaodekuaiFangkaService.deleteById(id);
	}
        return R.ok();
    }

}
