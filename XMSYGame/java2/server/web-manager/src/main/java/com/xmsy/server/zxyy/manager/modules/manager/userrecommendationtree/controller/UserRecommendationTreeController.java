package com.xmsy.server.zxyy.manager.modules.manager.userrecommendationtree.controller;

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
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendationtree.entity.UserRecommendationTreeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendationtree.service.UserRecommendationTreeService;

/**
 * 用户推荐关系表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-06-05 15:35:08
 */
@RestController
@RequestMapping("userrecommendationtree/userrecommendationtree")
public class UserRecommendationTreeController {
	@Autowired
	private UserRecommendationTreeService userRecommendationTreeService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("userrecommendationtree:userrecommendationtree:list")
	public R list(UserRecommendationTreeEntity userrecommendationtree, PageParam pageParam) {
		Page<UserRecommendationTreeEntity> result = new Page<UserRecommendationTreeEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<UserRecommendationTreeEntity> entityWrapper = new EntityWrapper<UserRecommendationTreeEntity>(
				userrecommendationtree);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		userrecommendationtree.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("userrecommendationtree:userrecommendationtree:info")
	public R info(@PathVariable("id") Long id) {
		UserRecommendationTreeEntity userRecommendationTree = userRecommendationTreeService.selectById(id);
		return R.ok().put("userrecommendationtree", userRecommendationTree);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("userrecommendationtree:userrecommendationtree:save")
	public R save(@RequestBody UserRecommendationTreeEntity userrecommendationtree) {
		userRecommendationTreeService.insert(userrecommendationtree);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("userrecommendationtree:userrecommendationtree:update")
	public R update(@RequestBody UserRecommendationTreeEntity userrecommendationtree) {
		userRecommendationTreeService.updateById(userrecommendationtree);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("userrecommendationtree:userrecommendationtree:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			userRecommendationTreeService.deleteById(id);
		}
		return R.ok();
	}

}
