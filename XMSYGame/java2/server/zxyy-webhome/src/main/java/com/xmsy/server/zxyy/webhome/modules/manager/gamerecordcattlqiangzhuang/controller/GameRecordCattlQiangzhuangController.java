package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordcattlqiangzhuang.controller;

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
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordcattlqiangzhuang.entity.GameRecordCattlQiangzhuangEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordcattlqiangzhuang.service.GameRecordCattlQiangzhuangService;



/**
 * 游戏记录-抢庄牛牛
 *
 * @author adu
 * @email xxxxx
 * @date 2019-03-16 14:36:32
 */
@RestController
@RequestMapping("gamerecordcattlqiangzhuang/gamerecordcattlqiangzhuang")
public class GameRecordCattlQiangzhuangController {
    @Autowired
    private GameRecordCattlQiangzhuangService gameRecordCattlQiangzhuangService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordcattlqiangzhuang:gamerecordcattlqiangzhuang:list")
    public R list(GameRecordCattlQiangzhuangEntity gamerecordcattlqiangzhuang, PageParam pageParam){
        Page<GameRecordCattlQiangzhuangEntity> result = new Page<GameRecordCattlQiangzhuangEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordCattlQiangzhuangEntity> entityWrapper = new EntityWrapper<GameRecordCattlQiangzhuangEntity>(gamerecordcattlqiangzhuang);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordcattlqiangzhuang.getStartTime()),"create_time", gamerecordcattlqiangzhuang.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordcattlqiangzhuang.getEndTime()),"create_time", gamerecordcattlqiangzhuang.getEndTime());
		if (StringUtils.isEmpty(pageParam.getSort())) {
			pageParam.setSort("id");
		}
		if (null != pageParam.getDirection()) {
			pageParam.setDirection(false);
		}
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordcattlqiangzhuang.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordcattlqiangzhuang:gamerecordcattlqiangzhuang:info")
    public R info(@PathVariable("id") Long id){
			GameRecordCattlQiangzhuangEntity gameRecordCattlQiangzhuang = gameRecordCattlQiangzhuangService.selectById(id);
        return R.ok().put("gamerecordcattlqiangzhuang", gameRecordCattlQiangzhuang);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordcattlqiangzhuang:gamerecordcattlqiangzhuang:save")
    public R save(@RequestBody GameRecordCattlQiangzhuangEntity gamerecordcattlqiangzhuang){
			gameRecordCattlQiangzhuangService.insert(gamerecordcattlqiangzhuang);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordcattlqiangzhuang:gamerecordcattlqiangzhuang:update")
    public R update(@RequestBody GameRecordCattlQiangzhuangEntity gamerecordcattlqiangzhuang){
			gameRecordCattlQiangzhuangService.updateById(gamerecordcattlqiangzhuang);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordcattlqiangzhuang:gamerecordcattlqiangzhuang:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordCattlQiangzhuangService.deleteById(id);
	}
        return R.ok();
    }

}
