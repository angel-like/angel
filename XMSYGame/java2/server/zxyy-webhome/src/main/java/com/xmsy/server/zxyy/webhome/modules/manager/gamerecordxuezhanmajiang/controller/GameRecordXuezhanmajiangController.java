package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordxuezhanmajiang.controller;

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

import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordxuezhanmajiang.entity.GameRecordXuezhanmajiangEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordxuezhanmajiang.service.GameRecordXuezhanmajiangService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;



/**
 * 游戏记录-血战麻将
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-11-29 10:38:57
 */
@RestController
@RequestMapping("gamerecordxuezhanmajiang/gamerecordxuezhanmajiang")
public class GameRecordXuezhanmajiangController {
    @Autowired
    private GameRecordXuezhanmajiangService gameRecordXuezhanmajiangService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordxuezhanmajiang:gamerecordxuezhanmajiang:list")
    public R list(GameRecordXuezhanmajiangEntity gamerecordxuezhanmajiang, PageParam pageParam){
        Page<GameRecordXuezhanmajiangEntity> result = new Page<GameRecordXuezhanmajiangEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordXuezhanmajiangEntity> entityWrapper = new EntityWrapper<GameRecordXuezhanmajiangEntity>(gamerecordxuezhanmajiang);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordxuezhanmajiang.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordxuezhanmajiang:gamerecordxuezhanmajiang:info")
    public R info(@PathVariable("id") Long id){
			GameRecordXuezhanmajiangEntity gameRecordXuezhanmajiang = gameRecordXuezhanmajiangService.selectById(id);
        return R.ok().put("gamerecordxuezhanmajiang", gameRecordXuezhanmajiang);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordxuezhanmajiang:gamerecordxuezhanmajiang:save")
    public R save(@RequestBody GameRecordXuezhanmajiangEntity gamerecordxuezhanmajiang){
			gameRecordXuezhanmajiangService.insert(gamerecordxuezhanmajiang);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordxuezhanmajiang:gamerecordxuezhanmajiang:update")
    public R update(@RequestBody GameRecordXuezhanmajiangEntity gamerecordxuezhanmajiang){
			gameRecordXuezhanmajiangService.updateById(gamerecordxuezhanmajiang);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordxuezhanmajiang:gamerecordxuezhanmajiang:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordXuezhanmajiangService.deleteById(id);
	}
        return R.ok();
    }

}
