package com.xmsy.server.zxyy.webhome.modules.manager.playertasks.controller;

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

import com.xmsy.server.zxyy.webhome.modules.manager.playertasks.entity.PlayerTasksEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.playertasks.service.PlayerTasksService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;



/**
 * 玩家任务表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-17 14:53:30
 */
@RestController
@RequestMapping("playertasks/playertasks")
public class PlayerTasksController {
    @Autowired
    private PlayerTasksService playerTasksService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("playertasks:playertasks:list")
    public R list(PlayerTasksEntity playertasks, PageParam pageParam){
        Page<PlayerTasksEntity> result = new Page<PlayerTasksEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<PlayerTasksEntity> entityWrapper = new EntityWrapper<PlayerTasksEntity>(playertasks);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		playertasks.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("playertasks:playertasks:info")
    public R info(@PathVariable("id") Long id){
			PlayerTasksEntity playerTasks = playerTasksService.selectById(id);
        return R.ok().put("playertasks", playerTasks);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("playertasks:playertasks:save")
    public R save(@RequestBody PlayerTasksEntity playertasks){
			playerTasksService.insert(playertasks);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("playertasks:playertasks:update")
    public R update(@RequestBody PlayerTasksEntity playertasks){
			playerTasksService.updateById(playertasks);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("playertasks:playertasks:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			playerTasksService.deleteById(id);
	}
        return R.ok();
    }

}
