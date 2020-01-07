package com.xmsy.server.zxyy.manager.modules.manager.gamerecordfightlandlordsfangka.controller;


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

import com.xmsy.server.zxyy.manager.modules.manager.gamerecordfightlandlordsfangka.entity.GameRecordFightlandlordsFangkaEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordfightlandlordsfangka.service.GameRecordFightlandlordsFangkaService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;



/**
 * 游戏记录-房卡斗地主
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-07-31 11:05:51
 */
@RestController
@RequestMapping("gamerecordfightlandlordsfangka/gamerecordfightlandlordsfangka")
public class GameRecordFightlandlordsFangkaController {
    @Autowired
    private GameRecordFightlandlordsFangkaService gameRecordFightlandlordsFangkaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordfightlandlordsfangka:gamerecordfightlandlordsfangka:list")
    public R list(GameRecordFightlandlordsFangkaEntity gamerecordfightlandlordsfangka, PageParam pageParam){
        Page<GameRecordFightlandlordsFangkaEntity> result = new Page<GameRecordFightlandlordsFangkaEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordFightlandlordsFangkaEntity> entityWrapper = new EntityWrapper<GameRecordFightlandlordsFangkaEntity>(gamerecordfightlandlordsfangka);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordfightlandlordsfangka.getStartTime()),"create_time", gamerecordfightlandlordsfangka.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordfightlandlordsfangka.getEndTime()),"create_time", gamerecordfightlandlordsfangka.getEndTime());
		if (StringUtils.isEmpty(pageParam.getSort())) {
			pageParam.setSort("id");
		}
		if (null != pageParam.getDirection()) {
			pageParam.setDirection(false);
		}
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordfightlandlordsfangka.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordfightlandlordsfangka:gamerecordfightlandlordsfangka:info")
    public R info(@PathVariable("id") Long id){
			GameRecordFightlandlordsFangkaEntity gameRecordFightlandlordsFangka = gameRecordFightlandlordsFangkaService.selectById(id);
        return R.ok().put("gamerecordfightlandlordsfangka", gameRecordFightlandlordsFangka);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordfightlandlordsfangka:gamerecordfightlandlordsfangka:save")
    public R save(@RequestBody GameRecordFightlandlordsFangkaEntity gamerecordfightlandlordsfangka){
			gameRecordFightlandlordsFangkaService.insert(gamerecordfightlandlordsfangka);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordfightlandlordsfangka:gamerecordfightlandlordsfangka:update")
    public R update(@RequestBody GameRecordFightlandlordsFangkaEntity gamerecordfightlandlordsfangka){
			gameRecordFightlandlordsFangkaService.updateById(gamerecordfightlandlordsfangka);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordfightlandlordsfangka:gamerecordfightlandlordsfangka:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordFightlandlordsFangkaService.deleteById(id);
	}
        return R.ok();
    }

}
