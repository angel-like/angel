package com.xmsy.server.zxyy.webhome.modules.manager.gamerecorddaily.controller;

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
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecorddaily.entity.GameRecordDailyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecorddaily.service.GameRecordDailyService;



/**
 * 每日会员游戏总下注记录
 *
 * @author adu
 * @email xxxxx
 * @date 2019-04-11 20:30:48
 */
@RestController
@RequestMapping("gamerecorddaily/gamerecorddaily")
public class GameRecordDailyController {
    @Autowired
    private GameRecordDailyService gameRecordDailyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecorddaily:gamerecorddaily:list")
    public R list(GameRecordDailyEntity gamerecorddaily, PageParam pageParam){
        Page<GameRecordDailyEntity> result = new Page<GameRecordDailyEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordDailyEntity> entityWrapper = new EntityWrapper<GameRecordDailyEntity>(gamerecorddaily);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecorddaily.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecorddaily:gamerecorddaily:info")
    public R info(@PathVariable("id") Long id){
			GameRecordDailyEntity gameRecordDaily = gameRecordDailyService.selectById(id);
        return R.ok().put("gamerecorddaily", gameRecordDaily);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecorddaily:gamerecorddaily:save")
    public R save(@RequestBody GameRecordDailyEntity gamerecorddaily){
			gameRecordDailyService.insert(gamerecorddaily);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecorddaily:gamerecorddaily:update")
    public R update(@RequestBody GameRecordDailyEntity gamerecorddaily){
			gameRecordDailyService.updateById(gamerecorddaily);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecorddaily:gamerecorddaily:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordDailyService.deleteById(id);
	}
        return R.ok();
    }

}
