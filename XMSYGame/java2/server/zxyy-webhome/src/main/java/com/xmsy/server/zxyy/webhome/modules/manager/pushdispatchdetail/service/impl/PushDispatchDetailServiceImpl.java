package com.xmsy.server.zxyy.webhome.modules.manager.pushdispatchdetail.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.common.define.result.ResultUtils;
import com.xmsy.network.jpush.push.JiguangPush;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.ThirdPartyDef;
import com.xmsy.server.zxyy.webhome.modules.manager.pushdispatchdetail.dao.PushDispatchDetailDao;
import com.xmsy.server.zxyy.webhome.modules.manager.pushdispatchdetail.entity.PushDispatchDetailEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.pushdispatchdetail.service.PushDispatchDetailService;
import com.xmsy.server.zxyy.webhome.modules.manager.sysconfig.service.SysConfigService;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.entity.UserHierarchyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.service.UserHierarchyService;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.entity.result.UserPushInfo;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.service.UserInfoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("pushDispatchDetailService")
public class PushDispatchDetailServiceImpl extends ServiceImpl<PushDispatchDetailDao, PushDispatchDetailEntity>
		implements PushDispatchDetailService {

	@Autowired
	private UserHierarchyService userHierarchyService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private UserInfoService userInfoService;

	@Override
	public R push(PushDispatchDetailEntity pushdispatchdetail) {
		String appKey = sysConfigService.selectByParamKey(ThirdPartyDef.JPUSH, ThirdPartyDef.JPUSH_APPKEY);
		String appSecret = sysConfigService.selectByParamKey(ThirdPartyDef.JPUSH, ThirdPartyDef.JPUSH_APPSECRET);
		String batch = sysConfigService.selectByParamKey(ThirdPartyDef.JPUSH, ThirdPartyDef.JPUSH_BATCH);
		Integer pushBatch = 0; // 最大单次推送次数
		// app推送key
		if (StringUtils.isEmpty(appKey)) {
			pushdispatchdetail.setFailReason("appkey为空");
			pushdispatchdetail.setStatus(ThirdPartyDef.PUSH_FIAL);
			insertOrUpdate(pushdispatchdetail);
			return R.ok();
		}
		// app推送没有
		if (StringUtils.isEmpty(appSecret)) {
			pushdispatchdetail.setFailReason("appSecret为空");
			pushdispatchdetail.setStatus(ThirdPartyDef.PUSH_FIAL);
			insertOrUpdate(pushdispatchdetail);
			return R.ok();
		}
		// 单次推送最大pushId个数限制
		if (!StringUtils.isEmpty(batch)) {
			try {
				pushBatch = Integer.parseInt(batch);
			} catch (Exception e) {
				pushBatch = ThirdPartyDef.JPUSH_BATCH_DEFAULT;
				log.error("【PushDispatchDetailServiceImpl】->push batch {}", batch);
			}
		} else {
			pushBatch = ThirdPartyDef.JPUSH_BATCH_DEFAULT;
		}
		// 非定时推送
		if (pushdispatchdetail.getScope() == ThirdPartyDef.PERSONAL) {
			// 按个人推送
			List<String> userAccountParams = Lists.newArrayList(pushdispatchdetail.getRecipient().split(","));
			if (CollectionUtils.isEmpty(userAccountParams)) {
				return R.error("指定账号推送，账号id不能为空");
			}
			List<UserPushInfo> userPushInfos = userInfoService.findUserInfoListByAccount(userAccountParams);
			List<String> retainAccounts = Lists.newArrayList();// 系统不存在的账号集合
			List<String> notExistJpushIdAccounts = Lists.newArrayList();// 账号存在但是没有pushid的集合
			List<String> jpushIds = Lists.newArrayList();
			for (String account : userAccountParams) {
				for (UserPushInfo userPushInfo : userPushInfos) {
					if (!account.equals(userPushInfo.getAccount())) {
						continue;
					}
					retainAccounts.add(account);
					if (StringUtils.isEmpty(userPushInfo.getJpushRegId())) {
						notExistJpushIdAccounts.add(account);
					} else {
						jpushIds.add(userPushInfo.getJpushRegId());
					}
					break;
				}
			}
			userAccountParams.removeAll(retainAccounts);
			StringBuilder stringBuilder = new StringBuilder("账号:");
			// 系统不存在账号
			if (!CollectionUtils.isEmpty(userAccountParams)) {
				stringBuilder.append(Joiner.on(",").join(userAccountParams));
				stringBuilder.append("在系统中找不到，未推送成功。");
			}
			if (!CollectionUtils.isEmpty(notExistJpushIdAccounts)) {
				// 系统推送账号未绑定pushId
				stringBuilder.append(Joiner.on(",").join(notExistJpushIdAccounts));
				stringBuilder.append("未绑定推送id，未推送成功。");
			}
			if (CollectionUtils.isEmpty(jpushIds)) {
				return R.error(stringBuilder.toString());
			}
			// 推送
			GlobalResult<String> result = JiguangPush.pushNotifications(appKey, appSecret, jpushIds,
					pushdispatchdetail.getContent());
			if (!ResultUtils.isSuccess(result)) {
				pushdispatchdetail.setStatus(ThirdPartyDef.PUSH_FIAL);
				pushdispatchdetail.setFailReason(result.getErrorMsg());
				insertOrUpdate(pushdispatchdetail);
				return R.error(result.getErrorMsg());
			} else {
				stringBuilder.append(Joiner.on(",").join(retainAccounts));
				stringBuilder.append("推送成功。");
				pushdispatchdetail.setFailReason(stringBuilder.toString());
				pushdispatchdetail.setStatus(ThirdPartyDef.PUSH_SUCCESS);
				pushdispatchdetail.setExecuteTime(new Date());
				insertOrUpdate(pushdispatchdetail);
				return R.ok();
			}
		} else if (pushdispatchdetail.getScope() == ThirdPartyDef.HIERARCHY) {
			// 按层级推送
			List<String> userHierarchyIdParams = Lists.newArrayList(pushdispatchdetail.getHierarchyIds().split(","));
			if (CollectionUtils.isEmpty(userHierarchyIdParams)) {
				return R.error("指定层级推送，层级不能为空");
			}
			// 层级名称
			List<String> getUserHierarchyNames = getUserHierarchyNames(userHierarchyIdParams);
			pushdispatchdetail.setRecipient(Joiner.on(",").join(getUserHierarchyNames));
			List<String> jpushIds = userInfoService.findUserInfoListByhierarchyId(userHierarchyIdParams);
			if (CollectionUtils.isEmpty(jpushIds)) {
				return R.ok();
			}
			GlobalResult<String> result = batchPush(appKey, appSecret, jpushIds, pushdispatchdetail.getContent(), pushBatch);
			if (!ResultUtils.isSuccess(result)) {
				pushdispatchdetail.setStatus(ThirdPartyDef.PUSH_FIAL);
				pushdispatchdetail.setFailReason(result.getErrorMsg());
				insertOrUpdate(pushdispatchdetail);
				return R.error(result.getErrorMsg());
			}
		} else {
			// 发送所有用户
			GlobalResult<String> result = JiguangPush.pushNotificationBroadcast(appKey, appSecret,
					pushdispatchdetail.getContent());
			if (!ResultUtils.isSuccess(result)) {
				pushdispatchdetail.setStatus(ThirdPartyDef.PUSH_FIAL);
				pushdispatchdetail.setFailReason(result.getErrorMsg());
				insertOrUpdate(pushdispatchdetail);
				return R.error(result.getErrorMsg());
			}
			pushdispatchdetail.setRecipient("所有用户");
		}
		pushdispatchdetail.setFailReason("推送成功");
		pushdispatchdetail.setStatus(ThirdPartyDef.PUSH_SUCCESS);
		if (null == pushdispatchdetail.getExecuteTime()) {
			pushdispatchdetail.setExecuteTime(new Date());
		}
		insertOrUpdate(pushdispatchdetail);
		return R.ok();

	}

	public List<String> getUserHierarchyNames(List<String> userHierarchyIds) {
		UserHierarchyEntity userHierarchyEntity = null;
		List<String> hierarchyNames = Lists.newArrayList();
		for (String hierarchyId : userHierarchyIds) {
			userHierarchyEntity = userHierarchyService.selectById(hierarchyId);
			if (null == userHierarchyEntity || StringUtils.isEmpty(userHierarchyEntity.getName())) {
				continue;
			}
			hierarchyNames.add(userHierarchyEntity.getName());
		}
		return hierarchyNames;
	}

	// 批量发送
	private GlobalResult<String> batchPush(String appKey, String appSecret, List<String> jpushIds, String content,
			Integer pushBatch) {
		if (jpushIds.size() < pushBatch) {
			GlobalResult<String> result = JiguangPush.pushNotifications(appKey, appSecret, jpushIds, content);
			return result;
		}
		GlobalResult<String> result = null;
		int part = jpushIds.size() / pushBatch;// 分批数
		for (int i = 0; i < part; i++) {
			List<String> listPage = jpushIds.subList(0, pushBatch);
			result = JiguangPush.pushNotifications(appKey, appSecret, listPage, content);
			// 剔除
			jpushIds.subList(0, pushBatch).clear();
		}
		return result;
	}

}
