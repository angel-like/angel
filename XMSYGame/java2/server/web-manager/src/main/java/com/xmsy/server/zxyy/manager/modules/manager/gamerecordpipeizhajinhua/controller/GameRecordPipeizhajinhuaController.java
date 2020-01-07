package com.xmsy.server.zxyy.manager.modules.manager.gamerecordpipeizhajinhua.controller;

import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordpipeizhajinhua.entity.GameRecordPipeizhajinhuaEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordpipeizhajinhua.service.GameRecordPipeizhajinhuaService;

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
 * 游戏记录-匹配炸金花
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-03-29 09:33:49
 */
@RestController
@RequestMapping("gamerecordpipeizhajinhua/gamerecordpipeizhajinhua")
public class GameRecordPipeizhajinhuaController {
    @Autowired
    private GameRecordPipeizhajinhuaService gameRecordPipeizhajinhuaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordpipeizhajinhua:gamerecordpipeizhajinhua:list")
    public R list(GameRecordPipeizhajinhuaEntity gameRecordPipeizhajinhua, PageParam pageParam){
        Page<GameRecordPipeizhajinhuaEntity> result = new Page<GameRecordPipeizhajinhuaEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordPipeizhajinhuaEntity> entityWrapper = new EntityWrapper<GameRecordPipeizhajinhuaEntity>(gameRecordPipeizhajinhua);
		entityWrapper.ge(!StringUtils.isBlank(gameRecordPipeizhajinhua.getStartTime()),"create_time", gameRecordPipeizhajinhua.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gameRecordPipeizhajinhua.getEndTime()),"create_time", gameRecordPipeizhajinhua.getEndTime());
		if (StringUtils.isEmpty(pageParam.getSort())) {
			pageParam.setSort("id");
		}
		if (null != pageParam.getDirection()) {
			pageParam.setDirection(false);
		}
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gameRecordPipeizhajinhua.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordpipeizhajinhua:gamerecordpipeizhajinhua:info")
    public R info(@PathVariable("id") Long id){
			GameRecordPipeizhajinhuaEntity gameRecordPipeizhajinhua = gameRecordPipeizhajinhuaService.selectById(id);
        return R.ok().put("gamerecordpipeizhajinhua", gameRecordPipeizhajinhua);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordpipeizhajinhua:gamerecordpipeizhajinhua:save")
    public R save(@RequestBody GameRecordPipeizhajinhuaEntity gamerecordpipeizhajinhua){
			gameRecordPipeizhajinhuaService.insert(gamerecordpipeizhajinhua);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordpipeizhajinhua:gamerecordpipeizhajinhua:update")
    public R update(@RequestBody GameRecordPipeizhajinhuaEntity gamerecordpipeizhajinhua){
			gameRecordPipeizhajinhuaService.updateById(gamerecordpipeizhajinhua);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordpipeizhajinhua:gamerecordpipeizhajinhua:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordPipeizhajinhuaService.deleteById(id);
	}
        return R.ok();
    }

}
