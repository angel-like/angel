package com.xmsy.server.zxyy.webhome.modules.manager.userrecommendation.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.annotation.UserLog;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.app.user.param.UserRecommenderResultParam;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendation.entity.UserRecommendationEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendation.service.UserRecommendationService;

/**
 * 用户推荐码表
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 19:15:50
 */
@RestController
@RequestMapping("recommendationcode/recommendationcode")
public class UserRecommendationController {
	@Autowired
	private UserRecommendationService userRecommendationService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recommendationcode:recommendationcode:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = userRecommendationService.queryPage(params);
		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recommendationcode:recommendationcode:info")
	public R info(@PathVariable("id") Long id) {
		UserRecommendationEntity recommendationCode = userRecommendationService.selectById(id);
		return R.ok().put("recommendationCode", recommendationCode);
	}

	/**
	 * 检查邀请码是否唯一
	 */
	@RequestMapping("/checkCode/{code}/{id}")
	@RequiresPermissions("recommendationcode:recommendationcode:info")
	public R checkCode(@PathVariable("code") String code, @PathVariable("id") Long id) {
		return R.ok().put("isOK", userRecommendationService.checkCode(code, id));
	}

	/**
	 * 没有邀请码的会员列表
	 */
	@RequestMapping("/getNotRecommendationCodeUser")
	@RequiresPermissions("recommendationcode:recommendationcode:info")
	public R getNotRecommendationCodeUser() {
		return R.ok().put("userList", userRecommendationService.findNotRecommendationCodeUser());
	}

	/**
	 * 保存
	 */
	@SysLog("新增会员邀请码")
	@UserLog(value = "新增会员邀请码")
	@RequestMapping("/save")
	@RequiresPermissions("recommendationcode:recommendationcode:save")
	public R save(@RequestBody UserRecommendationEntity recommendationCode) {
		userRecommendationService.save(recommendationCode);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@SysLog("修改会员邀请码")
	@UserLog(value = "修改会员邀请码")
	@RequestMapping("/update")
	@RequiresPermissions("recommendationcode:recommendationcode:update")
	public R update(@RequestBody UserRecommendationEntity recommendationCode) {
		userRecommendationService.save(recommendationCode);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@SysLog("删除会员邀请码")
	@UserLog(value = "删除会员邀请码")
	@RequestMapping("/delete")
	@RequiresPermissions("recommendationcode:recommendationcode:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			userRecommendationService.deleteById(id);
		}
		return R.ok();
	}

	/**
	 * 删除
	 */
	@SysLog("删除会员邀请码")
	@UserLog(value = "删除会员邀请码")
	@RequestMapping("/deleteByEntity")
	@RequiresPermissions("recommendationcode:recommendationcode:delete")
	public R deleteByEntity(@RequestBody UserRecommendationEntity recommendationCode) {
		userRecommendationService.deletePhysicalById(recommendationCode.getId());
		return R.ok();
	}

	/**
	 * 根据ID获取代理信息
	 */
	@RequestMapping("/userRecommendationDetails")
	@RequiresPermissions("recommendationcode:recommendationcode:userRecommendationDetails")
	public R userRecommendationDetails(Long userId) {
		UserRecommenderResultParam entity = userRecommendationService.userRecommendationDetails(userId);
		return R.ok().put("data", entity);
	}

}
