package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordkaiyuan.controller;


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

import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordkaiyuan.entity.GameRecordKaiyuanEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordkaiyuan.service.GameRecordKaiyuanService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;



/**
 * 开源游戏
 *
 * @author sanqian
 * @email xxxxx
 * @date 2019-12-02 14:25:01
 */
@RestController
@RequestMapping("gamerecordkaiyuan/gamerecordkaiyuan")
public class GameRecordKaiyuanController {
    @Autowired
    private GameRecordKaiyuanService gameRecordKaiyuanService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:list")
    public R list(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordkaiyuan.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:info")
    public R info(@PathVariable("id") Long id){
			GameRecordKaiyuanEntity gameRecordKaiyuan = gameRecordKaiyuanService.selectById(id);
        return R.ok().put("gamerecordkaiyuan", gameRecordKaiyuan);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:save")
    public R save(@RequestBody GameRecordKaiyuanEntity gamerecordkaiyuan){
			gameRecordKaiyuanService.insert(gamerecordkaiyuan);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:update")
    public R update(@RequestBody GameRecordKaiyuanEntity gamerecordkaiyuan){
			gameRecordKaiyuanService.updateById(gamerecordkaiyuan);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordKaiyuanService.deleteById(id);
	}
        return R.ok();
    }

}
