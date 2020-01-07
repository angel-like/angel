package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordxuezhanmajiangfaka.controller;


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

import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordxuezhanmajiangfaka.entity.GameRecordXuezhanmajiangFakaEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordxuezhanmajiangfaka.service.GameRecordXuezhanmajiangFakaService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;



/**
 * 游戏记录-房卡血战麻将
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-12-06 14:50:44
 */
@RestController
@RequestMapping("gamerecordxuezhanmajiangfaka/gamerecordxuezhanmajiangfaka")
public class GameRecordXuezhanmajiangFakaController {
    @Autowired
    private GameRecordXuezhanmajiangFakaService gameRecordXuezhanmajiangFakaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordxuezhanmajiangfaka:gamerecordxuezhanmajiangfaka:list")
    public R list(GameRecordXuezhanmajiangFakaEntity gamerecordxuezhanmajiangfaka, PageParam pageParam){
        Page<GameRecordXuezhanmajiangFakaEntity> result = new Page<GameRecordXuezhanmajiangFakaEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordXuezhanmajiangFakaEntity> entityWrapper = new EntityWrapper<GameRecordXuezhanmajiangFakaEntity>(gamerecordxuezhanmajiangfaka);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordxuezhanmajiangfaka.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordxuezhanmajiangfaka:gamerecordxuezhanmajiangfaka:info")
    public R info(@PathVariable("id") Long id){
			GameRecordXuezhanmajiangFakaEntity gameRecordXuezhanmajiangFaka = gameRecordXuezhanmajiangFakaService.selectById(id);
        return R.ok().put("gamerecordxuezhanmajiangfaka", gameRecordXuezhanmajiangFaka);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordxuezhanmajiangfaka:gamerecordxuezhanmajiangfaka:save")
    public R save(@RequestBody GameRecordXuezhanmajiangFakaEntity gamerecordxuezhanmajiangfaka){
			gameRecordXuezhanmajiangFakaService.insert(gamerecordxuezhanmajiangfaka);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordxuezhanmajiangfaka:gamerecordxuezhanmajiangfaka:update")
    public R update(@RequestBody GameRecordXuezhanmajiangFakaEntity gamerecordxuezhanmajiangfaka){
			gameRecordXuezhanmajiangFakaService.updateById(gamerecordxuezhanmajiangfaka);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordxuezhanmajiangfaka:gamerecordxuezhanmajiangfaka:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordXuezhanmajiangFakaService.deleteById(id);
	}
        return R.ok();
    }

}
