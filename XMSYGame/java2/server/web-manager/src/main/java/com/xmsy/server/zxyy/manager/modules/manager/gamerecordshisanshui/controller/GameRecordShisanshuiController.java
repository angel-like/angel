package com.xmsy.server.zxyy.manager.modules.manager.gamerecordshisanshui.controller;

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
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordshisanshui.entity.GameRecordShisanshuiEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordshisanshui.service.GameRecordShisanshuiService;



/**
 * 游戏记录-十三水
 *
 * @author adu
 * @email xxxxx
 * @date 2019-03-16 14:36:32
 */
@RestController
@RequestMapping("gamerecordshisanshui/gamerecordshisanshui")
public class GameRecordShisanshuiController {
    @Autowired
    private GameRecordShisanshuiService gameRecordShisanshuiService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordshisanshui:gamerecordshisanshui:list")
    public R list(GameRecordShisanshuiEntity gamerecordshisanshui, PageParam pageParam){
        Page<GameRecordShisanshuiEntity> result = new Page<GameRecordShisanshuiEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordShisanshuiEntity> entityWrapper = new EntityWrapper<GameRecordShisanshuiEntity>(gamerecordshisanshui);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordshisanshui.getStartTime()),"create_time", gamerecordshisanshui.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordshisanshui.getEndTime()),"create_time", gamerecordshisanshui.getEndTime());
		if (StringUtils.isEmpty(pageParam.getSort())) {
			pageParam.setSort("id");
		}
		if (null != pageParam.getDirection()) {
			pageParam.setDirection(false);
		}
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordshisanshui.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordshisanshui:gamerecordshisanshui:info")
    public R info(@PathVariable("id") Long id){
			GameRecordShisanshuiEntity gameRecordShisanshui = gameRecordShisanshuiService.selectById(id);
        return R.ok().put("gamerecordshisanshui", gameRecordShisanshui);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordshisanshui:gamerecordshisanshui:save")
    public R save(@RequestBody GameRecordShisanshuiEntity gamerecordshisanshui){
			gameRecordShisanshuiService.insert(gamerecordshisanshui);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordshisanshui:gamerecordshisanshui:update")
    public R update(@RequestBody GameRecordShisanshuiEntity gamerecordshisanshui){
			gameRecordShisanshuiService.updateById(gamerecordshisanshui);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordshisanshui:gamerecordshisanshui:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordShisanshuiService.deleteById(id);
	}
        return R.ok();
    }

}
