package com.xmsy.server.zxyy.manager.modules.manager.gameanalysisdaily.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.apache.commons.lang3.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.gameanalysisdaily.entity.GameAnalysisDailyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gameanalysisdaily.service.GameAnalysisDailyService;



/**
 * 每日游戏总下注记录
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-04-18 14:59:53
 */
@RestController
@RequestMapping("gameanalysisdaily/gameanalysisdaily")
public class GameAnalysisDailyController {
    @Autowired
    private GameAnalysisDailyService gameAnalysisDailyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gameanalysisdaily:gameanalysisdaily:list")
    public R list(GameAnalysisDailyEntity gameanalysisdaily, PageParam pageParam){
        Page<GameAnalysisDailyEntity> result = new Page<GameAnalysisDailyEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameAnalysisDailyEntity> entityWrapper = new EntityWrapper<GameAnalysisDailyEntity>(gameanalysisdaily);
		entityWrapper.ge(!StringUtils.isBlank(gameanalysisdaily.getStartTime()),"count_day", gameanalysisdaily.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gameanalysisdaily.getEndTime()),"count_day", gameanalysisdaily.getEndTime());
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gameanalysisdaily.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gameanalysisdaily:gameanalysisdaily:info")
    public R info(@PathVariable("id") Long id){
			GameAnalysisDailyEntity gameAnalysisDaily = gameAnalysisDailyService.selectById(id);
        return R.ok().put("gameanalysisdaily", gameAnalysisDaily);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gameanalysisdaily:gameanalysisdaily:save")
    public R save(@RequestBody GameAnalysisDailyEntity gameanalysisdaily){
			gameAnalysisDailyService.insert(gameanalysisdaily);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gameanalysisdaily:gameanalysisdaily:update")
    public R update(@RequestBody GameAnalysisDailyEntity gameanalysisdaily){
			gameAnalysisDailyService.updateById(gameanalysisdaily);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gameanalysisdaily:gameanalysisdaily:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameAnalysisDailyService.deleteById(id);
	}
        return R.ok();
    }

}
