package com.xmsy.server.zxyy.manager.modules.manager.gamerecordbairenjingdianniuniu.controller;

import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordbairenjingdianniuniu.entity.GameRecordBairenjingdianniuniuEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordbairenjingdianniuniu.service.GameRecordBairenjingdianniuniuService;

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



/**
 * 游戏记录-百人经典牛牛
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-29 17:19:22
 */
@RestController
@RequestMapping("gamerecordbairenjingdianniuniu/gamerecordbairenjingdianniuniu")
public class GameRecordBairenjingdianniuniuController {
    @Autowired
    private GameRecordBairenjingdianniuniuService gameRecordBairenjingdianniuniuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordbairenjingdianniuniu:gamerecordbairenjingdianniuniu:list")
    public R list(GameRecordBairenjingdianniuniuEntity gamerecordbairenjingdianniuniu, PageParam pageParam){
    	Page<GameRecordBairenjingdianniuniuEntity> result = new Page<GameRecordBairenjingdianniuniuEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<GameRecordBairenjingdianniuniuEntity> entityWrapper = new EntityWrapper<GameRecordBairenjingdianniuniuEntity>(gamerecordbairenjingdianniuniu);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordbairenjingdianniuniu.getStartTime()), "create_time", gamerecordbairenjingdianniuniu.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordbairenjingdianniuniu.getEndTime()), "create_time", gamerecordbairenjingdianniuniu.getEndTime());
		if (StringUtils.isEmpty(pageParam.getSort())) {
			pageParam.setSort("id");
		}
		if (null != pageParam.getDirection()) {
			pageParam.setDirection(false);
		}
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordbairenjingdianniuniu.selectPage(result, entityWrapper);
		for(GameRecordBairenjingdianniuniuEntity data:result.getRecords()) {
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
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gamerecordbairenjingdianniuniu:gamerecordbairenjingdianniuniu:info")
    public R info(@PathVariable("id") Long id){
			GameRecordBairenjingdianniuniuEntity gameRecordBairenjingdianniuniu = gameRecordBairenjingdianniuniuService.selectById(id);
        return R.ok().put("gamerecordbairenjingdianniuniu", gameRecordBairenjingdianniuniu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordbairenjingdianniuniu:gamerecordbairenjingdianniuniu:save")
    public R save(@RequestBody GameRecordBairenjingdianniuniuEntity gamerecordbairenjingdianniuniu){
			gameRecordBairenjingdianniuniuService.insert(gamerecordbairenjingdianniuniu);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordbairenjingdianniuniu:gamerecordbairenjingdianniuniu:update")
    public R update(@RequestBody GameRecordBairenjingdianniuniuEntity gamerecordbairenjingdianniuniu){
			gameRecordBairenjingdianniuniuService.updateById(gamerecordbairenjingdianniuniu);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordbairenjingdianniuniu:gamerecordbairenjingdianniuniu:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordBairenjingdianniuniuService.deleteById(id);
	}
        return R.ok();
    }

}
