package com.xmsy.server.zxyy.webhome.modules.manager.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.cache.LocalContentCache;
import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.annotation.UserLog;
import com.xmsy.server.zxyy.webhome.common.utils.Dictionary;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.gamemanage.GameSelectParam;
import com.xmsy.server.zxyy.webhome.modules.gamemanage.gameinfo.server.GameInfoInterface;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.dao.GameRecordDao;
import com.xmsy.server.zxyy.webhome.modules.manager.sysdictionary.entity.SysDictionaryEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysdictionary.service.SysDictionaryService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.param.UserParam;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.entity.UserHierarchyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.service.UserHierarchyService;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.service.UserInfoService;
import com.xmsy.server.zxyy.webhome.modules.manager.userlogin.service.UserLoginService;

import lombok.extern.slf4j.Slf4j;

/**
 * 代理信息
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@Slf4j
@RestController
@RequestMapping("user/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserHierarchyService userHierarchyService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private LocalContentCache localContentCache;
	@Autowired
	private GameRecordDao gameRecordDao;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("user:user:list")
	public R list(UserParam user, PageParam pageParam) {
		Map<String, Object> map = userLoginService.getLoginCountByDeviceType();
		int totalNum = userService
				.selectCount(new EntityWrapper<UserEntity>().eq("delete_status", 0).ne("user_type", "TRIAL"));
		map.put("totalNum", totalNum);
		return R.ok().put("page", userService.findUserPage(user, pageParam)).put("onlineData", map);
	}

	/**
	 * 列表
	 */
	@RequestMapping("/risklist")
	@RequiresPermissions("user:user:risklist")
	public R risklist(UserParam user, PageParam pageParam) {
		return R.ok().put("page", userService.findRiskUserPage(user, pageParam));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("user:user:info")
	public R info(@PathVariable("id") Long id) {
		log.info("[info] id {}", id);
		UserEntity user = userService.selectById(id);
		log.info("[info] user {}", user);
		return R.ok().put("user", user);
	}

	/**
	 * 上级信息
	 */
	@RequestMapping("/surperinfo/{id}")
	@RequiresPermissions("user:user:info")
	public R surperinfo(@PathVariable("id") Long id) {
		log.info("[surperinfo] id {}", id);
		Map<String, Object> userMap = new HashMap<>();
		UserEntity user = userService.selectById(id);
		log.info("[surperinfo] user {}", user);
		if (user != null) {
			userMap.put("id", user.getId());
			userMap.put("account", user.getAccount());
			if (user.getSuperiorsId() != null) {
				userMap.put("superiorsId", user.getSuperiorsId());
				user = userService.selectById(user.getSuperiorsId());
				if (user != null) {
					userMap.put("superiorsAccount", user.getAccount());
				}
			} else {
				userMap.put("superiorsId", 0);
			}
		}
		log.info("[surperinfo] userMap {}", userMap);
		return R.ok().put("user", userMap);
	}

	/**
	 * 基本信息
	 */
	@RequestMapping("/allinfo/{id}")
	@RequiresPermissions("user:user:info")
	public R getUserAllinfo(@PathVariable("id") Long id) {
		log.info("[getUserAllinfo] id {}", id);
		UserEntity user = userService.selectById(id);
		log.info("[getUserAllinfo] user {}", user);
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setUserId(user.getId());
		userInfo = userInfoService.selectOne(new EntityWrapper<UserInfoEntity>(userInfo));
		log.info("[userInfo] user {}", userInfo);
		return R.ok().put("user", user).put("userinfo", userInfo);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("user:user:save")
	public R save(@RequestBody UserEntity user) {
		log.info("[save] user {}", user);
		userService.insert(user);
		return R.ok();
	}

	/**
	 * 层级信息
	 */
	@RequestMapping("/getHierarchy")
	@RequiresPermissions("user:user:info")
	public R getHierarchy() {
		List<UserHierarchyEntity> dataList = userHierarchyService
				.selectList(new EntityWrapper<UserHierarchyEntity>(new UserHierarchyEntity()));
		log.info("[getHierarchy] dataList {}", dataList);
		return R.ok().put("hierarchyList", dataList);
	}

	/**
	 * 会员类型信息
	 */
	@RequestMapping("/getUseType")
	@RequiresPermissions("user:user:info")
	public R getUseType() {
		List<SysDictionaryEntity> userTypeList = sysDictionaryService
				.findListByParentCode(Dictionary.USERTYPE.getValue());
		log.info("[getUseType] dataList {}", userTypeList);
		return R.ok().put("userTypeList", userTypeList);
	}

	/**
	 * 银行信息
	 */
	@RequestMapping("/getBank")
	@RequiresPermissions("user:user:info")
	public R getBank() {
		List<SysDictionaryEntity> bankList = sysDictionaryService.findListByParentCode(Dictionary.BANK.getValue());
		log.info("[getBank] bankList {}", bankList);
		return R.ok().put("bankList", bankList);
	}

	/**
	 * 修改
	 */
	@SysLog("修改会员备注")
	@UserLog(value = "修改会员备注")
	@RequestMapping("/update")
	@RequiresPermissions("user:user:update")
	public R update(@RequestBody UserEntity user) {
		log.info("[update] user {}", user);
		userService.updateUserBaseInfo(user);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("user:user:delete")
	public R delete(@RequestBody Long[] ids) {
		if (null == ids || ids.length == 0) {
			return R.ok();
		}
		List<String> userUnForbiddens = Lists.newArrayList();
		List<UserEntity> userEntitys = userService
				.selectList(new EntityWrapper<UserEntity>().in("id", Lists.newArrayList(ids)));
		for (UserEntity user : userEntitys) {
			if (!user.getForbiddenEnable()) {
				userUnForbiddens.add(user.getAccount());
				continue;
			}
			userService.deleteById(user.getId());
		}
		if (!CollectionUtils.isEmpty(userUnForbiddens)) {
			return R.error("非黑名单用户:" + Joiner.on(",").join(userUnForbiddens) + "未删除，黑名单用户已经删除");
		}
		return R.ok();
	}

	/**
	 * 删除用户密码错误次数
	 */
	@RequestMapping("/deletePassWordNum")
	@RequiresPermissions("user:user:deletePassWordNum")
	public R deletePassWordNum(@RequestBody Long[] ids) {
		for (Long id : ids) {
			localContentCache.remove(SysConstant.PASSWORD_ERROR + id);
		}
		return R.ok();
	}

	/**
	 * 输赢
	 */
	@RequestMapping("/userGameRecordStatics")
	@RequiresPermissions("user:user:userGameRecordStatics")
	public R userGameRecordStatics(Long userId) {
		List<GameSelectParam> matchGameList = GameInfoInterface.gameSelect(SysConstant.ROOM_ID_1);
		List<GameSelectParam> hundredGameList = GameInfoInterface.gameSelect(SysConstant.ROOM_ID_3);
		List<Long> matchList = new ArrayList<Long>();
		List<Long> hundredList = new ArrayList<Long>();
		// 获取到所有匹配场游戏ID
		for (GameSelectParam entity : matchGameList) {
			if (null != entity && null != entity.getId()) {
				matchList.add(entity.getId());
			}
		}
		// 获取到所有百人场游戏ID
		for (GameSelectParam entity : hundredGameList) {
			if (null != entity && null != entity.getId()) {
				matchList.add(entity.getId());
			}
		}
		Map<String, Object> matchMap = gameRecordDao.queryGameStatistics(matchList, userId);
		Map<String, Object> hundredMap = gameRecordDao.queryGameStatistics(hundredList, userId);
		return R.ok().put("matchMap", matchMap).put("hundredMap", hundredMap);
	}
}
