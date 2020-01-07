package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordhaochehui.controller;


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

import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordhaochehui.entity.GameRecordHaochehuiEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordhaochehui.service.GameRecordHaochehuiService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;



/**
 * 游戏记录-豪车会
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-08-29 09:49:38
 */
@RestController
@RequestMapping("gamerecordhaochehui/gamerecordhaochehui")
public class GameRecordHaochehuiController {
    @Autowired
    private GameRecordHaochehuiService gameRecordHaochehuiService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordhaochehui:gamerecordhaochehui:list")
    public R list(GameRecordHaochehuiEntity gamerecordhaochehui, PageParam pageParam){
        Page<GameRecordHaochehuiEntity> result = new Page<GameRecordHaochehuiEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordHaochehuiEntity> entityWrapper = new EntityWrapper<GameRecordHaochehuiEntity>(gamerecordhaochehui);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordhaochehui.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordhaochehui:gamerecordhaochehui:info")
    public R info(@PathVariable("id") Long id){
			GameRecordHaochehuiEntity gameRecordHaochehui = gameRecordHaochehuiService.selectById(id);
        return R.ok().put("gamerecordhaochehui", gameRecordHaochehui);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordhaochehui:gamerecordhaochehui:save")
    public R save(@RequestBody GameRecordHaochehuiEntity gamerecordhaochehui){
			gameRecordHaochehuiService.insert(gamerecordhaochehui);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordhaochehui:gamerecordhaochehui:update")
    public R update(@RequestBody GameRecordHaochehuiEntity gamerecordhaochehui){
			gameRecordHaochehuiService.updateById(gamerecordhaochehui);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordhaochehui:gamerecordhaochehui:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordHaochehuiService.deleteById(id);
	}
        return R.ok();
    }

}
