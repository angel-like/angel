package com.xmsy.server.zxyy.webhome.modules.manager.pushdispatchdetail.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.annotation.PushOrderRepeat;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.ThirdPartyDef;
import com.xmsy.server.zxyy.webhome.modules.manager.pushdispatchdetail.entity.PushDispatchDetailEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.pushdispatchdetail.service.PushDispatchDetailService;
import com.xmsy.server.zxyy.webhome.modules.manager.sys.controller.AbstractController;

/**
 * 
 * .推送信息模块
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-05-22 19:49:20
 */
@RestController
@RequestMapping("pushdispatchdetail/pushdispatchdetail")
public class PushDispatchDetailController extends AbstractController {

	@Autowired
	private PushDispatchDetailService pushDispatchDetailService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("pushdispatchdetail:pushdispatchdetail:list")
	public R list(PushDispatchDetailEntity pushdispatchdetail, PageParam pageParam) {
		Page<PushDispatchDetailEntity> result = new Page<PushDispatchDetailEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<PushDispatchDetailEntity> entityWrapper = new EntityWrapper<PushDispatchDetailEntity>(
				pushdispatchdetail);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		pushdispatchdetail.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("pushdispatchdetail:pushdispatchdetail:info")
	public R info(@PathVariable("id") Long id) {
		PushDispatchDetailEntity pushDispatchDetail = pushDispatchDetailService.selectById(id);
		return R.ok().put("pushdispatchdetail", pushDispatchDetail);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@PushOrderRepeat("线下充值订单重复确认校验")
	@RequiresPermissions("pushdispatchdetail:pushdispatchdetail:save")
	public R save(@RequestBody PushDispatchDetailEntity pushdispatchdetail) {
		pushdispatchdetail.setOperator(getUser().getUsername());
		// 定时推送
		if (pushdispatchdetail.getType() != ThirdPartyDef.IMMEDIATELY) {
			if (pushdispatchdetail.getScope() == ThirdPartyDef.HIERARCHY) {
				// 按个人推送
				List<String> userHierarchyIdParams = Lists
						.newArrayList(pushdispatchdetail.getHierarchyIds().split(","));
				if (CollectionUtils.isEmpty(userHierarchyIdParams)) {
					return R.error("指定层级推送，层级不能为空");
				}
				// 层级名称
				List<String> getUserHierarchyNames = pushDispatchDetailService
						.getUserHierarchyNames(userHierarchyIdParams);
				pushdispatchdetail.setRecipient(Joiner.on(",").join(getUserHierarchyNames));
			}
			if (pushdispatchdetail.getScope() == ThirdPartyDef.BROADCAST) {
				pushdispatchdetail.setRecipient("所有用户");
			}
			pushDispatchDetailService.insert(pushdispatchdetail);
			return R.ok();
		}
		return pushDispatchDetailService.push(pushdispatchdetail);
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("pushdispatchdetail:pushdispatchdetail:update")
	public R update(@RequestBody PushDispatchDetailEntity pushdispatchdetail) {
		pushDispatchDetailService.updateById(pushdispatchdetail);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("pushdispatchdetail:pushdispatchdetail:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			pushDispatchDetailService.deleteById(id);
		}
		return R.ok();
	}

}
