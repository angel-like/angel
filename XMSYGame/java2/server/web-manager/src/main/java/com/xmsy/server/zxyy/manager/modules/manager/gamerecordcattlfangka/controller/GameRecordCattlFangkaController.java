package com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattlfangka.controller;

import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattlfangka.entity.GameRecordCattlFangkaEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattlfangka.service.GameRecordCattlFangkaService;

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



/**
 * 游戏记录-房卡牛牛
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-03-29 14:11:25
 */
@RestController
@RequestMapping("gamerecordcattlfangka/gamerecordcattlfangka")
public class GameRecordCattlFangkaController {
    @Autowired
    private GameRecordCattlFangkaService gameRecordCattlFangkaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordcattlfangka:gamerecordcattlfangka:list")
    public R list(GameRecordCattlFangkaEntity gamerecordcattlfangka, PageParam pageParam){
    	Page<GameRecordCattlFangkaEntity> result = new Page<GameRecordCattlFangkaEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordCattlFangkaEntity> entityWrapper = new EntityWrapper<GameRecordCattlFangkaEntity>(gamerecordcattlfangka);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordcattlfangka.getStartTime()),"create_time", gamerecordcattlfangka.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordcattlfangka.getEndTime()),"create_time", gamerecordcattlfangka.getEndTime());
		if (StringUtils.isEmpty(pageParam.getSort())) {
			pageParam.setSort("id");
		}
		if (null != pageParam.getDirection()) {
			pageParam.setDirection(false);
		}
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordcattlfangka.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordcattlfangka:gamerecordcattlfangka:info")
    public R info(@PathVariable("id") Long id){
			GameRecordCattlFangkaEntity gameRecordCattlFangka = gameRecordCattlFangkaService.selectById(id);
        return R.ok().put("gamerecordcattlfangka", gameRecordCattlFangka);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordcattlfangka:gamerecordcattlfangka:save")
    public R save(@RequestBody GameRecordCattlFangkaEntity gamerecordcattlfangka){
			gameRecordCattlFangkaService.insert(gamerecordcattlfangka);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordcattlfangka:gamerecordcattlfangka:update")
    public R update(@RequestBody GameRecordCattlFangkaEntity gamerecordcattlfangka){
			gameRecordCattlFangkaService.updateById(gamerecordcattlfangka);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordcattlfangka:gamerecordcattlfangka:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordCattlFangkaService.deleteById(id);
	}
        return R.ok();
    }

}
