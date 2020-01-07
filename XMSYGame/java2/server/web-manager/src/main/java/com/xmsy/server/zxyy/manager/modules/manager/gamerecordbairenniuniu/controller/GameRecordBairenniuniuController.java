package com.xmsy.server.zxyy.manager.modules.manager.gamerecordbairenniuniu.controller;

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
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordbairenniuniu.entity.GameRecordBairenniuniuEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordbairenniuniu.service.GameRecordBairenniuniuService;

/**
 * 游戏记录-百人牛牛
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-28 16:28:26
 */
@RestController
@RequestMapping("gamerecordbairenniuniu/gamerecordbairenniuniu")
public class GameRecordBairenniuniuController {
	@Autowired
	private GameRecordBairenniuniuService gameRecordBairenniuniuService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("gamerecordbairenniuniu:gamerecordbairenniuniu:list")
	public R list(GameRecordBairenniuniuEntity param, PageParam pageParam) {
		Page<GameRecordBairenniuniuEntity> result = new Page<GameRecordBairenniuniuEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<GameRecordBairenniuniuEntity> entityWrapper = new EntityWrapper<GameRecordBairenniuniuEntity>(param);
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
		for(GameRecordBairenniuniuEntity data:result.getRecords()) {
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
	@RequiresPermissions("gamerecordbairenniuniu:gamerecordbairenniuniu:info")
	public R info(@PathVariable("id") Long id) {
		GameRecordBairenniuniuEntity gameRecordBairenniuniu = gameRecordBairenniuniuService.selectById(id);
		return R.ok().put("gamerecordbairenniuniu", gameRecordBairenniuniu);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("gamerecordbairenniuniu:gamerecordbairenniuniu:save")
	public R save(@RequestBody GameRecordBairenniuniuEntity gamerecordbairenniuniu) {
		gameRecordBairenniuniuService.insert(gamerecordbairenniuniu);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("gamerecordbairenniuniu:gamerecordbairenniuniu:update")
	public R update(@RequestBody GameRecordBairenniuniuEntity gamerecordbairenniuniu) {
		gameRecordBairenniuniuService.updateById(gamerecordbairenniuniu);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("gamerecordbairenniuniu:gamerecordbairenniuniu:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			gameRecordBairenniuniuService.deleteById(id);
		}
		return R.ok();
	}

}
