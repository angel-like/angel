package com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattltongbi.controller;

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
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattltongbi.entity.GameRecordCattlTongbiEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattltongbi.service.GameRecordCattlTongbiService;



/**
 * 游戏记录-通比牛牛
 *
 * @author adu
 * @email xxxxx
 * @date 2019-02-28 15:32:44
 */
@RestController
@RequestMapping("gamerecordcattltongbi/gamerecordcattltongbi")
public class GameRecordCattlTongbiController {
    @Autowired
    private GameRecordCattlTongbiService gameRecordCattlTongbiService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordcattltongbi:gamerecordcattltongbi:list")
    public R list(GameRecordCattlTongbiEntity gamerecordcattltongbi, PageParam pageParam){
        Page<GameRecordCattlTongbiEntity> result = new Page<GameRecordCattlTongbiEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordCattlTongbiEntity> entityWrapper = new EntityWrapper<GameRecordCattlTongbiEntity>(gamerecordcattltongbi);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordcattltongbi.getStartTime()),"create_time", gamerecordcattltongbi.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordcattltongbi.getEndTime()),"create_time", gamerecordcattltongbi.getEndTime());
		if (StringUtils.isEmpty(pageParam.getSort())) {
			pageParam.setSort("id");
		}
		if (null != pageParam.getDirection()) {
			pageParam.setDirection(false);
		}
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordcattltongbi.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordcattltongbi:gamerecordcattltongbi:info")
    public R info(@PathVariable("id") Long id){
			GameRecordCattlTongbiEntity gameRecordCattlTongbi = gameRecordCattlTongbiService.selectById(id);
        return R.ok().put("gamerecordcattltongbi", gameRecordCattlTongbi);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordcattltongbi:gamerecordcattltongbi:save")
    public R save(@RequestBody GameRecordCattlTongbiEntity gamerecordcattltongbi){
			gameRecordCattlTongbiService.insert(gamerecordcattltongbi);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordcattltongbi:gamerecordcattltongbi:update")
    public R update(@RequestBody GameRecordCattlTongbiEntity gamerecordcattltongbi){
			gameRecordCattlTongbiService.updateById(gamerecordcattltongbi);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordcattltongbi:gamerecordcattltongbi:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordCattlTongbiService.deleteById(id);
	}
        return R.ok();
    }

}
