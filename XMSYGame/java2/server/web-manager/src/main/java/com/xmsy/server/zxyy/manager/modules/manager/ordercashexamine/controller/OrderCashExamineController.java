package com.xmsy.server.zxyy.manager.modules.manager.ordercashexamine.controller;

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
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.ordercashexamine.entity.OrderCashExamineEntity;
import com.xmsy.server.zxyy.manager.modules.manager.ordercashexamine.service.OrderCashExamineService;

/**
 * 取款稽查
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-31 16:25:31
 */
@RestController
@RequestMapping("ordercashexamine/ordercashexamine")
public class OrderCashExamineController {
	@Autowired
	private OrderCashExamineService orderCashExamineService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ordercashexamine:ordercashexamine:list")
	public R list(OrderCashExamineEntity ordercashexamine, PageParam pageParam) {
		Page<OrderCashExamineEntity> result = new Page<OrderCashExamineEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<OrderCashExamineEntity> entityWrapper = new EntityWrapper<OrderCashExamineEntity>(ordercashexamine);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		ordercashexamine.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("ordercashexamine:ordercashexamine:info")
	public R info(@PathVariable("id") Long id) {
		OrderCashExamineEntity orderCashExamine = orderCashExamineService.selectById(id);
		return R.ok().put("ordercashexamine", orderCashExamine);
	}

	/**
	 * 保存
	 */
	@SysLog("创建稽查订单")
	@RequestMapping("/save")
	@RequiresPermissions("ordercashexamine:ordercashexamine:save")
	public R save(@RequestBody OrderCashExamineEntity ordercashexamine) {
		orderCashExamineService.insert(ordercashexamine);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@SysLog("修改稽查订单")
	@RequestMapping("/update")
	@RequiresPermissions("ordercashexamine:ordercashexamine:update")
	public R update(@RequestBody OrderCashExamineEntity ordercashexamine) {
		orderCashExamineService.updateById(ordercashexamine);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@SysLog("删除稽查订单")
	@RequestMapping("/delete")
	@RequiresPermissions("ordercashexamine:ordercashexamine:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			orderCashExamineService.deleteById(id);
		}
		return R.ok();
	}

}
