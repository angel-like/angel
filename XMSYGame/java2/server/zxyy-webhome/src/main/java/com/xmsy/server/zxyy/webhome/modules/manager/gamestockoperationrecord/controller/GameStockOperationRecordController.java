package com.xmsy.server.zxyy.webhome.modules.manager.gamestockoperationrecord.controller;

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

import com.xmsy.server.zxyy.webhome.modules.manager.gamestockoperationrecord.entity.GameStockOperationRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamestockoperationrecord.service.GameStockOperationRecordService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;



/**
 * 游戏库存操作记录
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-12-26 10:20:02
 */
@RestController
@RequestMapping("gamestockoperationrecord/gamestockoperationrecord")
public class GameStockOperationRecordController {
    @Autowired
    private GameStockOperationRecordService gameStockOperationRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamestockoperationrecord:gamestockoperationrecord:list")
    public R list(GameStockOperationRecordEntity gamestockoperationrecord, PageParam pageParam){
        Page<GameStockOperationRecordEntity> result = new Page<GameStockOperationRecordEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameStockOperationRecordEntity> entityWrapper = new EntityWrapper<GameStockOperationRecordEntity>(gamestockoperationrecord);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamestockoperationrecord.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamestockoperationrecord:gamestockoperationrecord:info")
    public R info(@PathVariable("id") Long id){
			GameStockOperationRecordEntity gameStockOperationRecord = gameStockOperationRecordService.selectById(id);
        return R.ok().put("gamestockoperationrecord", gameStockOperationRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamestockoperationrecord:gamestockoperationrecord:save")
    public R save(@RequestBody GameStockOperationRecordEntity gamestockoperationrecord){
			gameStockOperationRecordService.insert(gamestockoperationrecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamestockoperationrecord:gamestockoperationrecord:update")
    public R update(@RequestBody GameStockOperationRecordEntity gamestockoperationrecord){
			gameStockOperationRecordService.updateById(gamestockoperationrecord);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamestockoperationrecord:gamestockoperationrecord:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameStockOperationRecordService.deleteById(id);
	}
        return R.ok();
    }

}
