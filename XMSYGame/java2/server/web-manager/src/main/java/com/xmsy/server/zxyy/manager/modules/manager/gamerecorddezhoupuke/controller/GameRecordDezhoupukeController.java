package com.xmsy.server.zxyy.manager.modules.manager.gamerecorddezhoupuke.controller;


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
import com.xmsy.server.zxyy.manager.modules.manager.gamerecorddezhoupuke.entity.GameRecordDezhoupukeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecorddezhoupuke.service.GameRecordDezhoupukeService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;



/**
 * 游戏记录-德州扑克
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-11-22 17:21:28
 */
@RestController
@RequestMapping("gamerecorddezhoupuke/gamerecorddezhoupuke")
public class GameRecordDezhoupukeController {
    @Autowired
    private GameRecordDezhoupukeService gameRecordDezhoupukeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecorddezhoupuke:gamerecorddezhoupuke:list")
    public R list(GameRecordDezhoupukeEntity gamerecorddezhoupuke, PageParam pageParam){
        Page<GameRecordDezhoupukeEntity> result = new Page<GameRecordDezhoupukeEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordDezhoupukeEntity> entityWrapper = new EntityWrapper<GameRecordDezhoupukeEntity>(gamerecorddezhoupuke);
		entityWrapper.ge(!StringUtils.isBlank(gamerecorddezhoupuke.getStartTime()),"create_time", gamerecorddezhoupuke.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecorddezhoupuke.getEndTime()),"create_time", gamerecorddezhoupuke.getEndTime());
		if (StringUtils.isEmpty(pageParam.getSort())) {
			pageParam.setSort("id");
		}
		if (null != pageParam.getDirection()) {
			pageParam.setDirection(false);
		}
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecorddezhoupuke.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecorddezhoupuke:gamerecorddezhoupuke:info")
    public R info(@PathVariable("id") Long id){
			GameRecordDezhoupukeEntity gameRecordDezhoupuke = gameRecordDezhoupukeService.selectById(id);
        return R.ok().put("gamerecorddezhoupuke", gameRecordDezhoupuke);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecorddezhoupuke:gamerecorddezhoupuke:save")
    public R save(@RequestBody GameRecordDezhoupukeEntity gamerecorddezhoupuke){
			gameRecordDezhoupukeService.insert(gamerecorddezhoupuke);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecorddezhoupuke:gamerecorddezhoupuke:update")
    public R update(@RequestBody GameRecordDezhoupukeEntity gamerecorddezhoupuke){
			gameRecordDezhoupukeService.updateById(gamerecorddezhoupuke);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecorddezhoupuke:gamerecorddezhoupuke:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordDezhoupukeService.deleteById(id);
	}
        return R.ok();
    }

}
