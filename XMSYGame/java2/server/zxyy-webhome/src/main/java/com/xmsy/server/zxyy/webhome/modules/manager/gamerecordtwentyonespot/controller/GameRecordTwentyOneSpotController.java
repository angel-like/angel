package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordtwentyonespot.controller;


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

import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordtwentyonespot.entity.GameRecordTwentyOneSpotEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordtwentyonespot.service.GameRecordTwentyOneSpotService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;



/**
 * 游戏记录-21点
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-04 17:05:40
 */
@RestController
@RequestMapping("gamerecordtwentyonespot/gamerecordtwentyonespot")
public class GameRecordTwentyOneSpotController {
    @Autowired
    private GameRecordTwentyOneSpotService gameRecordTwentyOneSpotService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordtwentyonespot:gamerecordtwentyonespot:list")
    public R list(GameRecordTwentyOneSpotEntity gamerecordtwentyonespot, PageParam pageParam){
        Page<GameRecordTwentyOneSpotEntity> result = new Page<GameRecordTwentyOneSpotEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordTwentyOneSpotEntity> entityWrapper = new EntityWrapper<GameRecordTwentyOneSpotEntity>(gamerecordtwentyonespot);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordtwentyonespot.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordtwentyonespot:gamerecordtwentyonespot:info")
    public R info(@PathVariable("id") Long id){
			GameRecordTwentyOneSpotEntity gameRecordTwentyOneSpot = gameRecordTwentyOneSpotService.selectById(id);
        return R.ok().put("gamerecordtwentyonespot", gameRecordTwentyOneSpot);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordtwentyonespot:gamerecordtwentyonespot:save")
    public R save(@RequestBody GameRecordTwentyOneSpotEntity gamerecordtwentyonespot){
			gameRecordTwentyOneSpotService.insert(gamerecordtwentyonespot);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordtwentyonespot:gamerecordtwentyonespot:update")
    public R update(@RequestBody GameRecordTwentyOneSpotEntity gamerecordtwentyonespot){
			gameRecordTwentyOneSpotService.updateById(gamerecordtwentyonespot);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordtwentyonespot:gamerecordtwentyonespot:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordTwentyOneSpotService.deleteById(id);
	}
        return R.ok();
    }

}
