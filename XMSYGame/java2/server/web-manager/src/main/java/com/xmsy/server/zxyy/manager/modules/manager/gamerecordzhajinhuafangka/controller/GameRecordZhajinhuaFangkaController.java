package com.xmsy.server.zxyy.manager.modules.manager.gamerecordzhajinhuafangka.controller;


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

import com.xmsy.server.zxyy.manager.modules.manager.gamerecordzhajinhuafangka.entity.GameRecordZhajinhuaFangkaEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordzhajinhuafangka.service.GameRecordZhajinhuaFangkaService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;



/**
 * 游戏记录-房卡炸金花
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-07-31 11:05:51
 */
@RestController
@RequestMapping("gamerecordzhajinhuafangka/gamerecordzhajinhuafangka")
public class GameRecordZhajinhuaFangkaController {
    @Autowired
    private GameRecordZhajinhuaFangkaService gameRecordZhajinhuaFangkaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordzhajinhuafangka:gamerecordzhajinhuafangka:list")
    public R list(GameRecordZhajinhuaFangkaEntity gamerecordzhajinhuafangka, PageParam pageParam){
        Page<GameRecordZhajinhuaFangkaEntity> result = new Page<GameRecordZhajinhuaFangkaEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordZhajinhuaFangkaEntity> entityWrapper = new EntityWrapper<GameRecordZhajinhuaFangkaEntity>(gamerecordzhajinhuafangka);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordzhajinhuafangka.getStartTime()),"create_time", gamerecordzhajinhuafangka.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordzhajinhuafangka.getEndTime()),"create_time", gamerecordzhajinhuafangka.getEndTime());
		if (StringUtils.isEmpty(pageParam.getSort())) {
			pageParam.setSort("id");
		}
		if (null != pageParam.getDirection()) {
			pageParam.setDirection(false);
		}
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordzhajinhuafangka.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordzhajinhuafangka:gamerecordzhajinhuafangka:info")
    public R info(@PathVariable("id") Long id){
			GameRecordZhajinhuaFangkaEntity gameRecordZhajinhuaFangka = gameRecordZhajinhuaFangkaService.selectById(id);
        return R.ok().put("gamerecordzhajinhuafangka", gameRecordZhajinhuaFangka);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordzhajinhuafangka:gamerecordzhajinhuafangka:save")
    public R save(@RequestBody GameRecordZhajinhuaFangkaEntity gamerecordzhajinhuafangka){
			gameRecordZhajinhuaFangkaService.insert(gamerecordzhajinhuafangka);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordzhajinhuafangka:gamerecordzhajinhuafangka:update")
    public R update(@RequestBody GameRecordZhajinhuaFangkaEntity gamerecordzhajinhuafangka){
			gameRecordZhajinhuaFangkaService.updateById(gamerecordzhajinhuafangka);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordzhajinhuafangka:gamerecordzhajinhuafangka:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordZhajinhuaFangkaService.deleteById(id);
	}
        return R.ok();
    }

}
