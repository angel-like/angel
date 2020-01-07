package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordshisanshuifangka.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordshisanshuifangka.entity.GameRecordShisanshuiFangkaEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordshisanshuifangka.service.GameRecordShisanshuiFangkaService;



/**
 * 游戏记录-房卡十三水
 *
 * @author adu
 * @email xxxxx
 * @date 2019-03-28 16:17:54
 */
@RestController
@RequestMapping("gamerecordshisanshuifangka/gamerecordshisanshuifangka")
public class GameRecordShisanshuiFangkaController {
    @Autowired
    private GameRecordShisanshuiFangkaService gameRecordShisanshuiFangkaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordshisanshuifangka:gamerecordshisanshuifangka:list")
    public R list(@RequestParam(required = false, value = "startTime") String startTime,
			@RequestParam(required = false, value = "endTime") String endTime,GameRecordShisanshuiFangkaEntity gamerecordshisanshuifangka, PageParam pageParam){
        Page<GameRecordShisanshuiFangkaEntity> result = new Page<GameRecordShisanshuiFangkaEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordShisanshuiFangkaEntity> entityWrapper = new EntityWrapper<GameRecordShisanshuiFangkaEntity>(gamerecordshisanshuifangka);
		entityWrapper.gt(!StringUtils.isEmpty(startTime), "create_time", startTime);
		entityWrapper.lt(!StringUtils.isEmpty(endTime), "create_time", endTime);
		if (StringUtils.isEmpty(pageParam.getSort())) {
			pageParam.setSort("id");
		}
		if (null != pageParam.getDirection()) {
			pageParam.setDirection(false);
		}
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordshisanshuifangka.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordshisanshuifangka:gamerecordshisanshuifangka:info")
    public R info(@PathVariable("id") Long id){
			GameRecordShisanshuiFangkaEntity gameRecordShisanshuiFangka = gameRecordShisanshuiFangkaService.selectById(id);
        return R.ok().put("gamerecordshisanshuifangka", gameRecordShisanshuiFangka);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordshisanshuifangka:gamerecordshisanshuifangka:save")
    public R save(@RequestBody GameRecordShisanshuiFangkaEntity gamerecordshisanshuifangka){
			gameRecordShisanshuiFangkaService.insert(gamerecordshisanshuifangka);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordshisanshuifangka:gamerecordshisanshuifangka:update")
    public R update(@RequestBody GameRecordShisanshuiFangkaEntity gamerecordshisanshuifangka){
			gameRecordShisanshuiFangkaService.updateById(gamerecordshisanshuifangka);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordshisanshuifangka:gamerecordshisanshuifangka:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordShisanshuiFangkaService.deleteById(id);
	}
        return R.ok();
    }

}
