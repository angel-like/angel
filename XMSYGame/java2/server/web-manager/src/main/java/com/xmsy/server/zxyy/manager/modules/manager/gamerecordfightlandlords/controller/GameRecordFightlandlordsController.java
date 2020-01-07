package com.xmsy.server.zxyy.manager.modules.manager.gamerecordfightlandlords.controller;


import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordfightlandlords.entity.GameRecordFightlandlordsEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordfightlandlords.service.GameRecordFightlandlordsService;



/**
 * 游戏记录-斗地主
 *
 * @author adu
 * @email xxxxx
 * @date 2019-02-21 15:08:39
 */
@RestController
@RequestMapping("gamerecordfightlandlords/gamerecordfightlandlords")
public class GameRecordFightlandlordsController {
    @Autowired
    private GameRecordFightlandlordsService gameRecordFightlandlordsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordfightlandlords:gamerecordfightlandlords:list")
    public R list(GameRecordFightlandlordsEntity gamerecordfightlandlords, PageParam pageParam){
        Page<GameRecordFightlandlordsEntity> result = new Page<GameRecordFightlandlordsEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordFightlandlordsEntity> entityWrapper = new EntityWrapper<GameRecordFightlandlordsEntity>(gamerecordfightlandlords);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordfightlandlords.getStartTime()),"create_time", gamerecordfightlandlords.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordfightlandlords.getEndTime()),"create_time", gamerecordfightlandlords.getEndTime());
		if (StringUtils.isEmpty(pageParam.getSort())) {
			pageParam.setSort("id");
		}
		if (null != pageParam.getDirection()) {
			pageParam.setDirection(false);
		}
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordfightlandlords.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }
    
    /**
	 * 局号相同的游戏记录信息
	 */
	@RequestMapping("/getGameList")
	@RequiresPermissions("gamerecordfightlandlords:gamerecordfightlandlords:list")
	public R getGamerecordList(@RequestParam(value = "gameRoundNo") String gameRoundNo) {
		return R.ok()
				.put("gamerecordList", gameRecordFightlandlordsService.getGameList(gameRoundNo))
				.put("gameRoundNo", gameRoundNo);
		         
	}
	
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordfightlandlords:gamerecordfightlandlords:info")
    public R info(@PathVariable("id") Long id){
			GameRecordFightlandlordsEntity gameRecordFightlandlords = gameRecordFightlandlordsService.selectById(id);
        return R.ok().put("gamerecordfightlandlords", gameRecordFightlandlords);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordfightlandlords:gamerecordfightlandlords:save")
    public R save(@RequestBody GameRecordFightlandlordsEntity gamerecordfightlandlords){
			gameRecordFightlandlordsService.insert(gamerecordfightlandlords);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordfightlandlords:gamerecordfightlandlords:update")
    public R update(@RequestBody GameRecordFightlandlordsEntity gamerecordfightlandlords){
			gameRecordFightlandlordsService.updateById(gamerecordfightlandlords);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordfightlandlords:gamerecordfightlandlords:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordFightlandlordsService.deleteById(id);
	}
        return R.ok();
    }

}
