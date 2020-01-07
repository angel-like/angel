package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordsangongqiangzhuang.controller;


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

import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordsangongqiangzhuang.entity.GameRecordSangongQiangzhuangEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordsangongqiangzhuang.service.GameRecordSangongQiangzhuangService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;



/**
 * 游戏记录-抢庄三公
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-04 15:11:29
 */
@RestController
@RequestMapping("gamerecordsangongqiangzhuang/gamerecordsangongqiangzhuang")
public class GameRecordSangongQiangzhuangController {
    @Autowired
    private GameRecordSangongQiangzhuangService gameRecordSangongQiangzhuangService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordsangongqiangzhuang:gamerecordsangongqiangzhuang:list")
    public R list(GameRecordSangongQiangzhuangEntity gamerecordsangongqiangzhuang, PageParam pageParam){
        Page<GameRecordSangongQiangzhuangEntity> result = new Page<GameRecordSangongQiangzhuangEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordSangongQiangzhuangEntity> entityWrapper = new EntityWrapper<GameRecordSangongQiangzhuangEntity>(gamerecordsangongqiangzhuang);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordsangongqiangzhuang.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordsangongqiangzhuang:gamerecordsangongqiangzhuang:info")
    public R info(@PathVariable("id") Long id){
			GameRecordSangongQiangzhuangEntity gameRecordSangongQiangzhuang = gameRecordSangongQiangzhuangService.selectById(id);
        return R.ok().put("gamerecordsangongqiangzhuang", gameRecordSangongQiangzhuang);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordsangongqiangzhuang:gamerecordsangongqiangzhuang:save")
    public R save(@RequestBody GameRecordSangongQiangzhuangEntity gamerecordsangongqiangzhuang){
			gameRecordSangongQiangzhuangService.insert(gamerecordsangongqiangzhuang);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordsangongqiangzhuang:gamerecordsangongqiangzhuang:update")
    public R update(@RequestBody GameRecordSangongQiangzhuangEntity gamerecordsangongqiangzhuang){
			gameRecordSangongQiangzhuangService.updateById(gamerecordsangongqiangzhuang);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordsangongqiangzhuang:gamerecordsangongqiangzhuang:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordSangongQiangzhuangService.deleteById(id);
	}
        return R.ok();
    }

}
