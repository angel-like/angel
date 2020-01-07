package com.xmsy.server.zxyy.game.modules.web.gameconfig;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.game.common.utils.PageUtils;
import com.xmsy.server.zxyy.game.common.utils.R;
import com.xmsy.server.zxyy.game.modules.manager.gameconfig.entity.GameConfigEntity;
import com.xmsy.server.zxyy.game.modules.manager.gameconfig.service.GameConfigService;
import com.xmsy.server.zxyy.game.modules.web.gameconfig.param.GameCurrentStockResult;
import com.xmsy.server.zxyy.game.utils.EntityValidateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 *
 * @author xiaoyang
 * @email xxxxx
 * @date 2019-01-26 11:40:23
 */
@Slf4j
@RestController
@RequestMapping("web/gameconfig")
public class ConfigController {
	@Autowired
	private GameConfigService gameConfigService;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public R list(GameConfigEntity gameconfig, PageParam pageParam) {
		Page<GameConfigEntity> result = new Page<GameConfigEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameConfigEntity> entityWrapper = new EntityWrapper<GameConfigEntity>(gameconfig);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gameconfig.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@GetMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		GameConfigEntity gameConfig = gameConfigService.selectById(id);
		return R.ok().put("gameconfig", gameConfig);
	}
	
	/**
	 * 列表
	 */
	@PostMapping("/updateCurrentStock")
	public R updateCurrentStock(@RequestBody GameCurrentStockResult gameCurrentStock) {
		String errorEesult = EntityValidateUtil.validateModel(gameCurrentStock);
		if (StringUtils.isNotEmpty(errorEesult)) {
			log.error("参数校验失败：{}", errorEesult);
			return R.error(errorEesult);
		}
		//调用方法，把当前库存的绝对值  增加到   累计库存
		gameConfigService.updateCumulativeStock(gameCurrentStock);
		gameConfigService.updateCurrentStock(gameCurrentStock);
		return R.ok().put("data", gameCurrentStock);
	}
	
}
