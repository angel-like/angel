package com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.annotation.UserLog;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.Query;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.app.user.param.UserRecommenderResultParam;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.entity.AgencyAuthorityParam;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.entity.UserRecommendationEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.service.UserRecommendationService;
import com.xmsy.server.zxyy.manager.utils.DateUtils;

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
	/**/@Autowired
	private UserService userService;
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recommendationcode:recommendationcode:list")
	public R list(@RequestParam Map<String, Object> params) {
		//PageUtils page = userRecommendationService.queryPage(params);
		//return R.ok().put("page", page);
		UserRecommendationEntity entity = new UserRecommendationEntity();
		if (params != null) {
			if (params.get("userAccount") != null && !"".equals(params.get("userAccount"))) {
				entity.setUserAccount(params.get("userAccount").toString());
			}
			if (params.get("recommendationCode") != null && !"".equals(params.get("recommendationCode"))) {
				entity.setRecommendationCode(params.get("recommendationCode").toString());
			}
		}
		Wrapper<UserRecommendationEntity> wrapper = new EntityWrapper<UserRecommendationEntity>(entity);
		String sort = params.get("sort") != null ? params.get("sort").toString() : "";
		if (!StringUtils.isEmpty(sort)) {
			String direction = params.get("direction") != null ? params.get("direction").toString() : "";
			if ("desc".equalsIgnoreCase(direction)) {
				wrapper.orderDesc(Arrays.asList(sort.split(",")));
			} else {
				wrapper.orderAsc(Arrays.asList(sort.split(",")));
			}
		}
		Page<UserRecommendationEntity> page = userRecommendationService.selectPage(new Query<UserRecommendationEntity>(params).getPage(),
				wrapper);
		List<UserRecommendationEntity> list = page.getRecords();
		for(UserRecommendationEntity userRecommendation:list) {
			UserEntity user = userService.selectById(userRecommendation.getUserId());
			if(user == null) {
				userRecommendation.setAgentEnable(false);
				continue;
			}
			userRecommendation.setAgentEnable(user.getAgentEnable());
		}
		
		return R.ok().put("page", new PageUtils(page));
	}
	/**
     * 导出csv的数据
     */
	@SysLog("导出csv的数据")
    @RequestMapping("/exportCSVData")
    @RequiresPermissions("recommendationcode:recommendationcode:list")
	 public R exportCSVData(@RequestParam Map<String, Object> params) {
		UserRecommendationEntity entity = new UserRecommendationEntity();
		if (params != null) {
			if (params.get("userAccount") != null) {
				entity.setUserAccount(params.get("userAccount").toString());
			}
			if (params.get("recommendationCode") != null) {
				entity.setRecommendationCode(params.get("recommendationCode").toString());
			}
		}
		Wrapper<UserRecommendationEntity> wrapper = new EntityWrapper<UserRecommendationEntity>(entity);
		String sort = params.get("sort") != null ? params.get("sort").toString() : "";
		if (!StringUtils.isEmpty(sort)) {
			String direction = params.get("direction") != null ? params.get("direction").toString() : "";
			if ("desc".equalsIgnoreCase(direction)) {
				wrapper.orderDesc(Arrays.asList(sort.split(",")));
			} else {
				wrapper.orderAsc(Arrays.asList(sort.split(",")));
			}
		}
		List<UserRecommendationEntity> list = userRecommendationService.selectList(wrapper);
		for(UserRecommendationEntity UserRecommendationEntity:list) {
			UserRecommendationEntity.setCoin(UserRecommendationEntity.getCoin()/100);
		}
		return R.ok().put("dataList", list);
	}
	/**
	 * 关闭或开启代理权限
	 */
	@SysLog("关闭或开启代理权限")
	@UserLog(value = "关闭或开启代理权限")
	@RequestMapping("/AgencyAuthority")
	public R updateAgentEnable(@RequestBody AgencyAuthorityParam agencyAuthorityParam) {
		UserEntity entity = new UserEntity();
		entity.setId(agencyAuthorityParam.getUserId());
		entity.setAgentEnable(agencyAuthorityParam.getAgentEnable());
		boolean flag = userService.updateById(entity);
		if(!flag) {
			throw new RRException(ErrorCode.UserErrorCode.USER_INFO_ISNOTNULL_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_INFO_ISNOTNULL_ERRO.getCode());
		}
		//修改邀请码的修改时间
		UserRecommendationEntity userRecommendation = new UserRecommendationEntity();
		userRecommendation.setId(agencyAuthorityParam.getId());
		userRecommendation.setUpdateTime(DateUtils.getDay(0));
		userRecommendationService.updateById(userRecommendation);
		return R.ok();
	}
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recommendationcode:recommendationcode:info")
	public R info(@PathVariable("id") Long id) {
		UserRecommendationEntity recommendationCode = userRecommendationService.selectById(id);
		UserEntity user = userService.selectById(recommendationCode.getUserId());
		recommendationCode.setAgentEnable(user != null?user.getAgentEnable():false);
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
