package com.xmsy.server.zxyy.manager.modules.manager.gamerecordpaodekuai.controller;


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

import com.xmsy.server.zxyy.manager.modules.manager.gamerecordpaodekuai.entity.GameRecordPaodekuaiEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordpaodekuai.service.GameRecordPaodekuaiService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;



/**
 * 游戏记录-跑得快
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-04 11:33:22
 */
@RestController
@RequestMapping("gamerecordpaodekuai/gamerecordpaodekuai")
public class GameRecordPaodekuaiController {
    @Autowired
    private GameRecordPaodekuaiService gameRecordPaodekuaiService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordpaodekuai:gamerecordpaodekuai:list")
    public R list(GameRecordPaodekuaiEntity gamerecordpaodekuai, PageParam pageParam){
        Page<GameRecordPaodekuaiEntity> result = new Page<GameRecordPaodekuaiEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordPaodekuaiEntity> entityWrapper = new EntityWrapper<GameRecordPaodekuaiEntity>(gamerecordpaodekuai);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordpaodekuai.getStartTime()),"create_time", gamerecordpaodekuai.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordpaodekuai.getEndTime()),"create_time", gamerecordpaodekuai.getEndTime());
		if (StringUtils.isEmpty(pageParam.getSort())) {
			pageParam.setSort("id");
		}
		if (null != pageParam.getDirection()) {
			pageParam.setDirection(false);
		}
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordpaodekuai.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordpaodekuai:gamerecordpaodekuai:info")
    public R info(@PathVariable("id") Long id){
			GameRecordPaodekuaiEntity gameRecordPaodekuai = gameRecordPaodekuaiService.selectById(id);
        return R.ok().put("gamerecordpaodekuai", gameRecordPaodekuai);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordpaodekuai:gamerecordpaodekuai:save")
    public R save(@RequestBody GameRecordPaodekuaiEntity gamerecordpaodekuai){
			gameRecordPaodekuaiService.insert(gamerecordpaodekuai);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordpaodekuai:gamerecordpaodekuai:update")
    public R update(@RequestBody GameRecordPaodekuaiEntity gamerecordpaodekuai){
			gameRecordPaodekuaiService.updateById(gamerecordpaodekuai);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordpaodekuai:gamerecordpaodekuai:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordPaodekuaiService.deleteById(id);
	}
        return R.ok();
    }

}
