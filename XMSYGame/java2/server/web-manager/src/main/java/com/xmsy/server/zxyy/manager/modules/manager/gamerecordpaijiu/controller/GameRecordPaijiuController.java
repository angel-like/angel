package com.xmsy.server.zxyy.manager.modules.manager.gamerecordpaijiu.controller;

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
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordpaijiu.entity.GameRecordPaijiuEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordpaijiu.service.GameRecordPaijiuService;



/**
 * 游戏记录-百人牌九
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-10 11:32:57
 */
@RestController
@RequestMapping("gamerecordpaijiu/gamerecordpaijiu")
public class GameRecordPaijiuController {
    @Autowired
    private GameRecordPaijiuService gameRecordPaijiuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordpaijiu:gamerecordpaijiu:list")
    public R list(GameRecordPaijiuEntity param, PageParam pageParam){
        Page<GameRecordPaijiuEntity> result = new Page<GameRecordPaijiuEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordPaijiuEntity> entityWrapper = new EntityWrapper<GameRecordPaijiuEntity>(param);
		entityWrapper.ge(!StringUtils.isBlank(param.getStartTime()), "create_time", param.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(param.getEndTime()), "create_time", param.getEndTime());
		if (StringUtils.isEmpty(pageParam.getSort())) {
			pageParam.setSort("id");
		}
		if (null != pageParam.getDirection()) {
			pageParam.setDirection(false);
		}
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		param.selectPage(result, entityWrapper);
		for(GameRecordPaijiuEntity data:result.getRecords()) {
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
    @RequiresPermissions("gamerecordpaijiu:gamerecordpaijiu:info")
    public R info(@PathVariable("id") Long id){
			GameRecordPaijiuEntity gameRecordPaijiu = gameRecordPaijiuService.selectById(id);
        return R.ok().put("gamerecordpaijiu", gameRecordPaijiu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordpaijiu:gamerecordpaijiu:save")
    public R save(@RequestBody GameRecordPaijiuEntity gamerecordpaijiu){
			gameRecordPaijiuService.insert(gamerecordpaijiu);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordpaijiu:gamerecordpaijiu:update")
    public R update(@RequestBody GameRecordPaijiuEntity gamerecordpaijiu){
			gameRecordPaijiuService.updateById(gamerecordpaijiu);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordpaijiu:gamerecordpaijiu:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordPaijiuService.deleteById(id);
	}
        return R.ok();
    }

}
