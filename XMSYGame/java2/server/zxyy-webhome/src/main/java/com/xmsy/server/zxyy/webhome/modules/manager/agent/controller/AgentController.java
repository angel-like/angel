package com.xmsy.server.zxyy.webhome.modules.manager.agent.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.annotation.UserLog;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.agent.param.Agent;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendation.entity.UserRecommendationEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendation.service.UserRecommendationService;

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

	/**
	 * 代理列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("agent:agent:list")
	public R list(UserEntity user, PageParam pageParam) {
		Page<Agent> result = userService.getAgentList(user, pageParam);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 代理下级列表
	 */
	@RequestMapping("/SubordinateList")
	@RequiresPermissions("agent:agent:list")
	public R subordinateList(UserEntity user, PageParam pageParam) {
		Page<UserEntity> result = userService.getAgentSubordinateList(user.getId(), user.getAccount(), pageParam);
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

}
