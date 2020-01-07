package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordlonghu.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordlonghu.entity.GameRecordLonghuEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordlonghu.service.GameRecordLonghuService;

/**
 * 游戏记录-龙虎斗
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-09 15:37:22
 */
@RestController
@RequestMapping("gamerecordlonghu/gamerecordlonghu")
public class GameRecordLonghuController {
	@Autowired
	private GameRecordLonghuService gameRecordLonghuService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("gamerecordlonghu:gamerecordlonghu:list")
	public R list(GameRecordLonghuEntity param, PageParam pageParam) {
		Page<GameRecordLonghuEntity> result = new Page<GameRecordLonghuEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<GameRecordLonghuEntity> entityWrapper = new EntityWrapper<GameRecordLonghuEntity>(param);
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
		for(GameRecordLonghuEntity data:result.getRecords()) {
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
	@RequiresPermissions("gamerecordlonghu:gamerecordlonghu:info")
	public R info(@PathVariable("id") Long id) {
		GameRecordLonghuEntity gameRecordLonghu = gameRecordLonghuService.selectById(id);
		return R.ok().put("gamerecordlonghu", gameRecordLonghu);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("gamerecordlonghu:gamerecordlonghu:save")
	public R save(@RequestBody GameRecordLonghuEntity gamerecordlonghu) {
		gameRecordLonghuService.insert(gamerecordlonghu);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("gamerecordlonghu:gamerecordlonghu:update")
	public R update(@RequestBody GameRecordLonghuEntity gamerecordlonghu) {
		gameRecordLonghuService.updateById(gamerecordlonghu);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("gamerecordlonghu:gamerecordlonghu:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			gameRecordLonghuService.deleteById(id);
		}
		return R.ok();
	}

}
