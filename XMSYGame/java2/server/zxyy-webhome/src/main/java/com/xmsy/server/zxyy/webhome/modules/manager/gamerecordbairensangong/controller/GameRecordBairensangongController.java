package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordbairensangong.controller;


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

import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordbairensangong.entity.GameRecordBairensangongEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordbairensangong.service.GameRecordBairensangongService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;



/**
 * 游戏记录-百人三公
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-04 16:58:30
 */
@RestController
@RequestMapping("gamerecordbairensangong/gamerecordbairensangong")
public class GameRecordBairensangongController {
    @Autowired
    private GameRecordBairensangongService gameRecordBairensangongService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordbairensangong:gamerecordbairensangong:list")
    public R list(GameRecordBairensangongEntity gamerecordbairensangong, PageParam pageParam){
        Page<GameRecordBairensangongEntity> result = new Page<GameRecordBairensangongEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordBairensangongEntity> entityWrapper = new EntityWrapper<GameRecordBairensangongEntity>(gamerecordbairensangong);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordbairensangong.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordbairensangong:gamerecordbairensangong:info")
    public R info(@PathVariable("id") Long id){
			GameRecordBairensangongEntity gameRecordBairensangong = gameRecordBairensangongService.selectById(id);
        return R.ok().put("gamerecordbairensangong", gameRecordBairensangong);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordbairensangong:gamerecordbairensangong:save")
    public R save(@RequestBody GameRecordBairensangongEntity gamerecordbairensangong){
			gameRecordBairensangongService.insert(gamerecordbairensangong);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordbairensangong:gamerecordbairensangong:update")
    public R update(@RequestBody GameRecordBairensangongEntity gamerecordbairensangong){
			gameRecordBairensangongService.updateById(gamerecordbairensangong);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordbairensangong:gamerecordbairensangong:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordBairensangongService.deleteById(id);
	}
        return R.ok();
    }

}
