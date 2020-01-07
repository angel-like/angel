package com.xmsy.server.zxyy.manager.modules.manager.gamerecordtwoeightbar.controller;

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
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordtwoeightbar.entity.GameRecordTwoeightbarEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordtwoeightbar.service.GameRecordTwoeightbarService;



/**
 * 游戏记录-二八杠
 *
 * @author adu
 * @email xxxxx
 * @date 2019-03-16 14:36:32
 */
@RestController
@RequestMapping("gamerecordtwoeightbar/gamerecordtwoeightbar")
public class GameRecordTwoeightbarController {
    @Autowired
    private GameRecordTwoeightbarService gameRecordTwoeightbarService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordtwoeightbar:gamerecordtwoeightbar:list")
    public R list(GameRecordTwoeightbarEntity gamerecordtwoeightbar, PageParam pageParam){
        Page<GameRecordTwoeightbarEntity> result = new Page<GameRecordTwoeightbarEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordTwoeightbarEntity> entityWrapper = new EntityWrapper<GameRecordTwoeightbarEntity>(gamerecordtwoeightbar);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordtwoeightbar.getStartTime()),"create_time", gamerecordtwoeightbar.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordtwoeightbar.getEndTime()),"create_time", gamerecordtwoeightbar.getEndTime());
		if (StringUtils.isEmpty(pageParam.getSort())) {
			pageParam.setSort("id");
		}
		if (null != pageParam.getDirection()) {
			pageParam.setDirection(false);
		}
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordtwoeightbar.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordtwoeightbar:gamerecordtwoeightbar:info")
    public R info(@PathVariable("id") Long id){
			GameRecordTwoeightbarEntity gameRecordTwoeightbar = gameRecordTwoeightbarService.selectById(id);
        return R.ok().put("gamerecordtwoeightbar", gameRecordTwoeightbar);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordtwoeightbar:gamerecordtwoeightbar:save")
    public R save(@RequestBody GameRecordTwoeightbarEntity gamerecordtwoeightbar){
			gameRecordTwoeightbarService.insert(gamerecordtwoeightbar);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordtwoeightbar:gamerecordtwoeightbar:update")
    public R update(@RequestBody GameRecordTwoeightbarEntity gamerecordtwoeightbar){
			gameRecordTwoeightbarService.updateById(gamerecordtwoeightbar);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordtwoeightbar:gamerecordtwoeightbar:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordTwoeightbarService.deleteById(id);
	}
        return R.ok();
    }

}
