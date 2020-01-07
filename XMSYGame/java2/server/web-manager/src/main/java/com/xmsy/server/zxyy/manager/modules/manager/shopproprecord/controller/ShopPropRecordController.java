package com.xmsy.server.zxyy.manager.modules.manager.shopproprecord.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.shopproprecord.entity.ShopPropRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.shopproprecord.service.ShopPropRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.sysprop.entity.SysPropEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysprop.service.SysPropService;

/**
 * 商城道具交易记录表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-12 09:52:03
 */
@RestController
@RequestMapping("shopproprecord/shopproprecord")
public class ShopPropRecordController {
	@Autowired
	private ShopPropRecordService shopPropRecordService;
	@Autowired
	private SysPropService sysPropService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("shopproprecord:shopproprecord:list")
	public R list(ShopPropRecordEntity shopproprecord, PageParam pageParam) {
		Page<ShopPropRecordEntity> result = new Page<ShopPropRecordEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<ShopPropRecordEntity> entityWrapper = new EntityWrapper<ShopPropRecordEntity>(shopproprecord);
		entityWrapper.orderBy("create_time", false);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		shopproprecord.selectPage(result, entityWrapper);
		List<ShopPropRecordEntity> list = result.getRecords();
		if (!CollectionUtils.isEmpty(list)) {
			for (ShopPropRecordEntity entity : list) {
				if (entity != null) {
					if (entity.getSysPropId() != null && entity.getSysPropId() != 0) {
						SysPropEntity sysPropEntity = sysPropService
								.selectOne(new EntityWrapper<SysPropEntity>(null).eq("id", entity.getSysPropId()));
						if (sysPropEntity != null) {
							entity.setSysPropName(sysPropEntity.getName());
						}
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
	@RequiresPermissions("shopproprecord:shopproprecord:info")
	public R info(@PathVariable("id") Long id) {
		ShopPropRecordEntity shopPropRecord = shopPropRecordService.selectById(id);
		return R.ok().put("shopproprecord", shopPropRecord);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("shopproprecord:shopproprecord:save")
	public R save(@RequestBody ShopPropRecordEntity shopproprecord) {
		shopPropRecordService.insert(shopproprecord);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("shopproprecord:shopproprecord:update")
	public R update(@RequestBody ShopPropRecordEntity shopproprecord) {
		shopPropRecordService.updateById(shopproprecord);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("shopproprecord:shopproprecord:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			shopPropRecordService.deleteById(id);
		}
		return R.ok();
	}

}
