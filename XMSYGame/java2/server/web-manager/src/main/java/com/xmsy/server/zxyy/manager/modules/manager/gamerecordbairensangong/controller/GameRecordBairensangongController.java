package com.xmsy.server.zxyy.manager.modules.manager.gamerecordbairensangong.controller;


import com.xmsy.common.define.page.PageParam;

import java.math.BigDecimal;

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

import com.xmsy.server.zxyy.manager.modules.manager.gamerecordbairensangong.entity.GameRecordBairensangongEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordbairensangong.service.GameRecordBairensangongService;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;



/**
 * 游戏记录-百人三公
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-04 16:44:38
 */
@RestController
@RequestMapping("gamerecordbairensangong/gamerecordbairensangong")
public class GameRecordBairensangongController {
    @Autowired
    private GameRecordBairensangongService gameRecordBairensangongService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordbairensangong:gamerecordbairensangong:list")
    public R list(GameRecordBairensangongEntity gamerecordbairensangong, PageParam pageParam){
        Page<GameRecordBairensangongEntity> result = new Page<GameRecordBairensangongEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordBairensangongEntity> entityWrapper = new EntityWrapper<GameRecordBairensangongEntity>(gamerecordbairensangong);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordbairensangong.getStartTime()), "create_time", gamerecordbairensangong.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordbairensangong.getEndTime()), "create_time", gamerecordbairensangong.getEndTime());
		if (StringUtils.isEmpty(pageParam.getSort())) {
			pageParam.setSort("id");
		}
		if (null != pageParam.getDirection()) {
			pageParam.setDirection(false);
		}
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordbairensangong.selectPage(result, entityWrapper);
		for(GameRecordBairensangongEntity data:result.getRecords()) {
			String sourceStr = data.getBetAreaStr();
	        String[] sourceStrArray = sourceStr.split(",");
	        String str="";
	        for (int i = 0; i < sourceStrArray.length; i++) {
	            String[] sumStrArray = sourceStrArray[i].split(":");
	            str += sumStrArray[0]+":"+MathUtil.getBigDecimal(sumStrArray[1])
	            .divide(new BigDecimal(Constant.CLIENT_COIN_RATE), 2,BigDecimal.ROUND_HALF_UP);
	           
	        }
	        data.setBetAreaStr(str);
		}
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordbairensangong.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordbairensangong:gamerecordbairensangong:info")
    public R info(@PathVariable("id") Long id){
			GameRecordBairensangongEntity gameRecordBairensangong = gameRecordBairensangongService.selectById(id);
        return R.ok().put("gamerecordbairensangong", gameRecordBairensangong);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordbairensangong:gamerecordbairensangong:save")
    public R save(@RequestBody GameRecordBairensangongEntity gamerecordbairensangong){
			gameRecordBairensangongService.insert(gamerecordbairensangong);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordbairensangong:gamerecordbairensangong:update")
    public R update(@RequestBody GameRecordBairensangongEntity gamerecordbairensangong){
			gameRecordBairensangongService.updateById(gamerecordbairensangong);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordbairensangong:gamerecordbairensangong:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordBairensangongService.deleteById(id);
	}
        return R.ok();
    }

}
