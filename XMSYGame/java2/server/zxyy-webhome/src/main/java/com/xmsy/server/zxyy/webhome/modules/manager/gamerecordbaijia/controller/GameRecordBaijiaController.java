package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordbaijia.controller;

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
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.MathUtil;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordbaijia.entity.GameRecordBaijiaEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordbaijia.service.GameRecordBaijiaService;



/**
 * 游戏记录-百家乐
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-09 15:37:22
 */
@RestController
@RequestMapping("gamerecordbaijia/gamerecordbaijia")
public class GameRecordBaijiaController {
    @Autowired
    private GameRecordBaijiaService gameRecordBaijiaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordbaijia:gamerecordbaijia:list")
    public R list(GameRecordBaijiaEntity param, PageParam pageParam){
        Page<GameRecordBaijiaEntity> result = new Page<GameRecordBaijiaEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordBaijiaEntity> entityWrapper = new EntityWrapper<GameRecordBaijiaEntity>(param);
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
		for(GameRecordBaijiaEntity data:result.getRecords()) {
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
    @RequiresPermissions("gamerecordbaijia:gamerecordbaijia:info")
    public R info(@PathVariable("id") Long id){
			GameRecordBaijiaEntity gameRecordBaijia = gameRecordBaijiaService.selectById(id);
        return R.ok().put("gamerecordbaijia", gameRecordBaijia);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordbaijia:gamerecordbaijia:save")
    public R save(@RequestBody GameRecordBaijiaEntity gamerecordbaijia){
			gameRecordBaijiaService.insert(gamerecordbaijia);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordbaijia:gamerecordbaijia:update")
    public R update(@RequestBody GameRecordBaijiaEntity gamerecordbaijia){
			gameRecordBaijiaService.updateById(gamerecordbaijia);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordbaijia:gamerecordbaijia:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordBaijiaService.deleteById(id);
	}
        return R.ok();
    }

}
