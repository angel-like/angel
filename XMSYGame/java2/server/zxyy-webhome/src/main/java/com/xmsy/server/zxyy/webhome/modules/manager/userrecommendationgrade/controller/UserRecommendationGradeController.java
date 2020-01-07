package com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationgrade.controller;

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
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationgrade.entity.UserRecommendationGradeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationgrade.service.UserRecommendationGradeService;

/**
 * 用户推荐等级
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-06-06 15:36:01
 */
@RestController
@RequestMapping("userrecommendationgrade/userrecommendationgrade")
public class UserRecommendationGradeController {
	@Autowired
	private UserRecommendationGradeService userRecommendationGradeService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("userrecommendationgrade:userrecommendationgrade:list")
	public R list(UserRecommendationGradeEntity userrecommendationgrade, PageParam pageParam) {
		Page<UserRecommendationGradeEntity> result = new Page<UserRecommendationGradeEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<UserRecommendationGradeEntity> entityWrapper = new EntityWrapper<UserRecommendationGradeEntity>(
				userrecommendationgrade);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		userrecommendationgrade.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("userrecommendationgrade:userrecommendationgrade:info")
	public R info(@PathVariable("id") Long id) {
		UserRecommendationGradeEntity userRecommendationGrade = userRecommendationGradeService.selectById(id);
		return R.ok().put("userrecommendationgrade", userRecommendationGrade);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("userrecommendationgrade:userrecommendationgrade:save")
	public R save(@RequestBody UserRecommendationGradeEntity userrecommendationgrade) {
		userRecommendationGradeService.insert(userrecommendationgrade);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("userrecommendationgrade:userrecommendationgrade:update")
	public R update(@RequestBody UserRecommendationGradeEntity userrecommendationgrade) {
		userRecommendationGradeService.updateById(userrecommendationgrade);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("userrecommendationgrade:userrecommendationgrade:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			userRecommendationGradeService.deleteById(id);
		}
		return R.ok();
	}

}
