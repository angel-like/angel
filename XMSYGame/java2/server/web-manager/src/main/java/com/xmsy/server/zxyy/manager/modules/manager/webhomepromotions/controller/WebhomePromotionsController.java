package com.xmsy.server.zxyy.manager.modules.manager.webhomepromotions.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.webhomepromotions.entity.WebhomePromotionsEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomepromotions.service.WebhomePromotionsService;
import com.xmsy.server.zxyy.manager.modules.manager.webhomepromotionstype.entity.WebhomePromotionsTypeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomepromotionstype.service.WebhomePromotionsTypeService;

/**
 * 官网优惠活动
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-28 16:22:11
 */
@RestController
@RequestMapping("webhomepromotions/webhomepromotions")
public class WebhomePromotionsController {
	@Autowired
	private WebhomePromotionsService webhomePromotionsService;
	@Autowired
	private WebhomePromotionsTypeService webhomePromotionsTypeService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("webhomepromotions:webhomepromotions:list")
	public R list(WebhomePromotionsEntity webhomepromotions, PageParam pageParam) {
		String content = webhomepromotions.getContent();
		webhomepromotions.setContent(null);
		Page<WebhomePromotionsEntity> result = new Page<WebhomePromotionsEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<WebhomePromotionsEntity> entityWrapper = new EntityWrapper<WebhomePromotionsEntity>(webhomepromotions);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		webhomepromotions.selectPage(result, entityWrapper.like("content", content));
		if (!CollectionUtils.isEmpty(result.getRecords())) {
			for (WebhomePromotionsEntity entity : result.getRecords()) {
				if (entity.getTypeId() != null || entity.getTypeId() != 0) {
					WebhomePromotionsTypeEntity promotionsType = webhomePromotionsTypeService
							.selectById(entity.getTypeId());
					if (promotionsType != null) {
						entity.setTypeName(promotionsType.getName());
					}

				}
			}
		}
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("webhomepromotions:webhomepromotions:info")
	public R info(@PathVariable("id") Long id) {
		WebhomePromotionsEntity webhomePromotions = webhomePromotionsService.selectById(id);
		return R.ok().put("webhomepromotions", webhomePromotions);
	}

	/**
	 * 保存
	 */
	@SysLog("官网优惠活动新增")
	@RequestMapping("/save")
	@RequiresPermissions("webhomepromotions:webhomepromotions:save")
	public R save(@RequestBody WebhomePromotionsEntity webhomepromotions) {
		webhomePromotionsService.insert(webhomepromotions);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@SysLog("官网优惠活动修改")
	@RequestMapping("/update")
	@RequiresPermissions("webhomepromotions:webhomepromotions:update")
	public R update(@RequestBody WebhomePromotionsEntity webhomepromotions) {
		webhomePromotionsService.updateById(webhomepromotions);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@SysLog("官网优惠活动删除")
	@RequestMapping("/delete")
	@RequiresPermissions("webhomepromotions:webhomepromotions:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			webhomePromotionsService.deleteById(id);
		}
		return R.ok();
	}

}
