package com.xmsy.server.zxyy.manager.modules.manager.gamerecordhaochehui.controller;


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
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordhaochehui.entity.GameRecordHaochehuiEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordhaochehui.service.GameRecordHaochehuiService;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;



/**
 * 游戏记录-豪车会
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-08-29 09:42:47
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
		entityWrapper.ge(!StringUtils.isBlank(gamerecordhaochehui.getStartTime()), "create_time", gamerecordhaochehui.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordhaochehui.getEndTime()), "create_time", gamerecordhaochehui.getEndTime());
		if (StringUtils.isEmpty(pageParam.getSort())) {
			pageParam.setSort("id");
		}
		if (null != pageParam.getDirection()) {
			pageParam.setDirection(false);
		}
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordhaochehui.selectPage(result, entityWrapper);
		for(GameRecordHaochehuiEntity data:result.getRecords()) {
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
