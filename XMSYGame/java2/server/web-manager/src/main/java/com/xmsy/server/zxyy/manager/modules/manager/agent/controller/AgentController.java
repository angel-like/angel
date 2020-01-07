package com.xmsy.server.zxyy.manager.modules.manager.agent.controller;

import java.util.List;

import com.xmsy.server.zxyy.manager.modules.manager.agent.param.Subordinate;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.annotation.UserLog;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.agent.param.Agent;
import com.xmsy.server.zxyy.manager.modules.manager.agent.param.TeamAgent;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.userbetrecord.service.UserBetRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.entity.UserRecommendationEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.service.UserRecommendationService;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendationgrade.service.UserRecommendationGradeService;

/**
 * 会员信息表
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@RestController
@RequestMapping("agent/agent")
public class AgentController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRecommendationService userRecommendationService;
	@Autowired
	private UserBetRecordService userBetRecordService;
	@Autowired
	private UserRecommendationGradeService userRecommendationGradeService;

	/**
	 * 代理列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("agent:agent:list")
	public R list(UserEntity user, PageParam pageParam) {
		Page<Agent> result = userService.getAgentList(user, pageParam);
		List<Agent> list = result.getRecords();
		if (CollectionUtils.isEmpty(list)) {
			return R.ok().put("page",
					new PageUtils(list, result.getTotal(), result.getSize(), result.getCurrent(), result.getPages()));
		}
		for (Agent entity : list) {
			Long betCoin = userBetRecordService.sumUserBet(entity.getId());// 自身打码
			Long totalBet = (betCoin + entity.getValidBet()) / (Constant.DB_COIN_RATE * Constant.CLIENT_COIN_RATE);
			String gradeName = userRecommendationGradeService.getName(totalBet);// 获取到对应等级名
			entity.setRecommendationHierarchyName(gradeName);
		}
		return R.ok().put("page",
				new PageUtils(list, result.getTotal(), result.getSize(), result.getCurrent(), result.getPages()));
	}

	/**
	 * 团队代理列表
	 */
	@RequestMapping("/teamList")
	@RequiresPermissions("agent:agent:teamList")
	public R teamList(UserEntity user, PageParam pageParam) {
		Page<TeamAgent> result = userService.getAgentTeamList(user, pageParam);
		List<TeamAgent> list = result.getRecords();
		if (CollectionUtils.isEmpty(list)) {
			return R.ok().put("page",
					new PageUtils(list, result.getTotal(), result.getSize(), result.getCurrent(), result.getPages()));
		}
		for (TeamAgent entity : list) {
			Long betCoin = userBetRecordService.sumUserBet(entity.getId());// 自身打码
			Long totalBet = (betCoin + entity.getSumValidBet()) / (Constant.DB_COIN_RATE * Constant.CLIENT_COIN_RATE);
			String gradeName = userRecommendationGradeService.getName(totalBet);// 获取到对应等级名
			entity.setRecommendationHierarchyName(gradeName);
		}
		return R.ok().put("page",
				new PageUtils(list, result.getTotal(), result.getSize(), result.getCurrent(), result.getPages()));
	}

	/**
	 * 代理下级列表
	 */
	@RequestMapping("/SubordinateList")
	@RequiresPermissions("agent:agent:list")
	public R subordinateList(UserEntity user, PageParam pageParam) {
		Page<Subordinate> result = userService.getAgentSubordinateList(user.getId(), user.getAccount(), pageParam);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 代理下级列表
	 */
	@RequestMapping("/SubordinateListForCommission")
	@RequiresPermissions("agent:agent:SubordinateListForCommission")
	public R SubordinateListForCommission(UserEntity user, PageParam pageParam) {
		Page<Subordinate> result = userService.getAgentSubordinateList(user.getId(), user.getAccount(), pageParam);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 取消代理
	 */
	@SysLog("取消代理权限")
	@UserLog(value = "取消代理权限")
	@RequestMapping("/AgencyAuthority/{id}")
	@RequiresPermissions("agent:agent:update")
	public R update(@PathVariable Long id) {
		UserEntity entity = new UserEntity();
		entity.setId(id);
		entity.setAgentEnable(false);
		userService.updateById(entity);
		return R.ok();
	}

	/**
	 * 修改代理权限
	 */
	@SysLog("修改代理权限")
	@UserLog(value = "修改代理权限")
	@RequestMapping("/save")
	@RequiresPermissions("agent:agent:save")
	public R save(@RequestParam("userId") Long userId, @RequestParam("agentId") Long agentId) {
		UserRecommendationEntity entity = new UserRecommendationEntity();
		entity.setAgentHierarchyId(agentId);
		userRecommendationService.update(entity, new EntityWrapper<UserRecommendationEntity>().eq("user_id", userId));
		return R.ok();
	}

	/**
	 * 修改团队权限
	 */
	@SysLog("修改团队权限")
	@UserLog(value = "修改团队权限")
	@RequestMapping("/updateTeamEnable")
	@RequiresPermissions("agent:agent:updateTeamEnable")
	public R updateTeamEnable(@RequestParam("userId") Long userId, @RequestParam("teamEnable") Boolean teamEnable) {
		UserRecommendationEntity entity = new UserRecommendationEntity();
		entity.setTeamEnable(teamEnable);
		userRecommendationService.update(entity, new EntityWrapper<UserRecommendationEntity>().eq("user_id", userId));
		return R.ok();
	}

}
