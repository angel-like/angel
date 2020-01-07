package com.xmsy.server.zxyy.manager.modules.manager.gamerecordhongheidazhan.controller;


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
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordhongheidazhan.entity.GameRecordHongheidazhanEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordhongheidazhan.service.GameRecordHongheidazhanService;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;



/**
 * 游戏记录-红黑大战
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-08-29 09:42:48
 */
@RestController
@RequestMapping("gamerecordhongheidazhan/gamerecordhongheidazhan")
public class GameRecordHongheidazhanController {
    @Autowired
    private GameRecordHongheidazhanService gameRecordHongheidazhanService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordhongheidazhan:gamerecordhongheidazhan:list")
    public R list(GameRecordHongheidazhanEntity gamerecordhongheidazhan, PageParam pageParam){
        Page<GameRecordHongheidazhanEntity> result = new Page<GameRecordHongheidazhanEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordHongheidazhanEntity> entityWrapper = new EntityWrapper<GameRecordHongheidazhanEntity>(gamerecordhongheidazhan);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordhongheidazhan.getStartTime()), "create_time", gamerecordhongheidazhan.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordhongheidazhan.getEndTime()), "create_time", gamerecordhongheidazhan.getEndTime());
		if (StringUtils.isEmpty(pageParam.getSort())) {
			pageParam.setSort("id");
		}
		if (null != pageParam.getDirection()) {
			pageParam.setDirection(false);
		}
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordhongheidazhan.selectPage(result, entityWrapper);
		for(GameRecordHongheidazhanEntity data:result.getRecords()) {
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
    @RequiresPermissions("gamerecordhongheidazhan:gamerecordhongheidazhan:info")
    public R info(@PathVariable("id") Long id){
			GameRecordHongheidazhanEntity gameRecordHongheidazhan = gameRecordHongheidazhanService.selectById(id);
        return R.ok().put("gamerecordhongheidazhan", gameRecordHongheidazhan);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordhongheidazhan:gamerecordhongheidazhan:save")
    public R save(@RequestBody GameRecordHongheidazhanEntity gamerecordhongheidazhan){
			gameRecordHongheidazhanService.insert(gamerecordhongheidazhan);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordhongheidazhan:gamerecordhongheidazhan:update")
    public R update(@RequestBody GameRecordHongheidazhanEntity gamerecordhongheidazhan){
			gameRecordHongheidazhanService.updateById(gamerecordhongheidazhan);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordhongheidazhan:gamerecordhongheidazhan:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordHongheidazhanService.deleteById(id);
	}
        return R.ok();
    }

}
