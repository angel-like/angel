package com.xmsy.server.zxyy.manager.modules.manager.gamerecordzhajinhua.controller;

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
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordzhajinhua.entity.GameRecordZhajinhuaEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordzhajinhua.service.GameRecordZhajinhuaService;

/**
 * 游戏记录-诈金花
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-10 11:13:28
 */
@RestController
@RequestMapping("gamerecordzhajinhua/gamerecordzhajinhua")
public class GameRecordZhajinhuaController {
	@Autowired
	private GameRecordZhajinhuaService gameRecordZhajinhuaService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("gamerecordzhajinhua:gamerecordzhajinhua:list")
	public R list(GameRecordZhajinhuaEntity param, PageParam pageParam) {
		Page<GameRecordZhajinhuaEntity> result = new Page<GameRecordZhajinhuaEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<GameRecordZhajinhuaEntity> entityWrapper = new EntityWrapper<GameRecordZhajinhuaEntity>(param);
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
		for(GameRecordZhajinhuaEntity data:result.getRecords()) {
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
	@RequiresPermissions("gamerecordzhajinhua:gamerecordzhajinhua:info")
	public R info(@PathVariable("id") Long id) {
		GameRecordZhajinhuaEntity gameRecordZhajinhua = gameRecordZhajinhuaService.selectById(id);
		return R.ok().put("gamerecordzhajinhua", gameRecordZhajinhua);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("gamerecordzhajinhua:gamerecordzhajinhua:save")
	public R save(@RequestBody GameRecordZhajinhuaEntity gamerecordzhajinhua) {
		gameRecordZhajinhuaService.insert(gamerecordzhajinhua);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("gamerecordzhajinhua:gamerecordzhajinhua:update")
	public R update(@RequestBody GameRecordZhajinhuaEntity gamerecordzhajinhua) {
		gameRecordZhajinhuaService.updateById(gamerecordzhajinhua);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("gamerecordzhajinhua:gamerecordzhajinhua:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			gameRecordZhajinhuaService.deleteById(id);
		}
		return R.ok();
	}

}
