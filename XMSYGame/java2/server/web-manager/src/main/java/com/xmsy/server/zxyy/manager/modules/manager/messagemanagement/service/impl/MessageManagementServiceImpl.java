package com.xmsy.server.zxyy.manager.modules.manager.messagemanagement.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.DateUtils;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.Query;
import com.xmsy.server.zxyy.manager.common.utils.Constant.MessageContentType;
import com.xmsy.server.zxyy.manager.common.utils.Constant.MessageTarget;
import com.xmsy.server.zxyy.manager.modules.manager.messagemanagement.dao.MessageManagementDao;
import com.xmsy.server.zxyy.manager.modules.manager.messagemanagement.entity.MessageManagementEntity;
import com.xmsy.server.zxyy.manager.modules.manager.messagemanagement.service.MessageManagementService;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.dao.MessageUserDao;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.entity.MessageUserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.param.MessageRequestVo;
import com.xmsy.server.zxyy.manager.modules.manager.sys.dao.SysUserDao;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysUserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysmessagemodel.dao.SysMessageModelDao;
import com.xmsy.server.zxyy.manager.modules.manager.sysmessagemodel.entity.SysMessageModelEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysmessagemodelprop.dao.SysMessageModelPropDao;
import com.xmsy.server.zxyy.manager.modules.manager.sysmessagemodelprop.entity.SysMessageModelPropEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysmessageprop.dao.SysMessagePropDao;
import com.xmsy.server.zxyy.manager.modules.manager.sysmessageprop.entity.SysMessagePropEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("messageManagementService")
public class MessageManagementServiceImpl extends ServiceImpl<MessageManagementDao, MessageManagementEntity>
		implements MessageManagementService {
	@Autowired
	private MessageUserDao messageUserDao;
	@Autowired
	private SysMessageModelDao sysMessageModelDao;
	@Autowired
	private SysMessageModelPropDao sysMessageModelPropDao;
	@Autowired
	private SysMessagePropDao sysMessagePropDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private SysUserDao sysUserDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		MessageManagementEntity entity = new MessageManagementEntity();
		if (params != null) {
			if (params.get("key") != null) {
				entity.setTitle(params.get("key").toString());
			}
			if (params.get("contentType") != null) {
				entity.setContentType(Integer.parseInt(params.get("contentType").toString()));
			}
		}
		Wrapper<MessageManagementEntity> wrapper = new EntityWrapper<MessageManagementEntity>(entity);
		String sort = params.get("sort") != null ? params.get("sort").toString() : "";
		if (!StringUtils.isEmpty(sort)) {
			String direction = params.get("direction") != null ? params.get("direction").toString() : "";
			if ("desc".equalsIgnoreCase(direction)) {
				wrapper.orderDesc(Arrays.asList(sort.split(",")));
			} else {
				wrapper.orderAsc(Arrays.asList(sort.split(",")));
			}
		}
		Page<MessageManagementEntity> page = this.selectPage(new Query<MessageManagementEntity>(params).getPage(),
				wrapper);

		return new PageUtils(page);
	}

	@Override
	@Transactional
	public boolean save(MessageManagementEntity messageManagement) {
		log.info("[MessageManagementServiceImpl] messageManagement {}", messageManagement);
		messageManagement.setContentType(MessageContentType.USER_CONTENT.getValue());
		if(messageManagement.getReadonly()==null) {
			messageManagement.setReadonly(true);
		}
		this.baseMapper.insertEntityReturnId(messageManagement);
		// 1：指定用户id 2:指定用户层次 3：所有用户
		List<UserEntity> userList = new ArrayList<>();
		if (MessageTarget.USER.getValue() != messageManagement.getTargetObject()) {
			return true;
		}
		userList = userDao.findUserListByAccount(messageManagement.getUserAccount().split("\n"));
		if (CollectionUtils.isEmpty(userList)) {
			throw new RRException(ErrorCode.MessagemanagementErrorCode.NOCHOICE_ACCOUNT_ERRO.getErrMsg(),
					ErrorCode.MessagemanagementErrorCode.NOCHOICE_ACCOUNT_ERRO.getCode());
		}
		//保存用户和站内信的关系
		String[] accountList = messageManagement.getUserAccount().split("\n");
		if (accountList.length <= userList.size()) {
			List<MessageUserEntity> messageUserList = new ArrayList<>();
			for (UserEntity user : userList) {
				MessageUserEntity messageUser = new MessageUserEntity();
				messageUser.setMessageId(messageManagement.getId());
				messageUser.setUserAccount(user.getAccount());
				messageUser.setUserId(user.getId());
				messageUser.setStatus(false);
				messageUser.setDeleteMessage(false);
				messageUser.setReceive(true);
				messageUser.setContentType(messageManagement.getContentType());
				messageUser.setTitle( messageManagement.getTitle());
				messageUser.setContent(messageManagement.getContent());
				messageUser.setFailureTime(messageManagement.getFailureTime());
				messageUser.setEffectTime(messageManagement.getEffectTime());
				messageUserList.add(messageUser);
			}
			messageUserDao.insertBatch(messageUserList);
			return true;
		}
		//刷选不存在的账号返回（管理员输错用户账号的时候提示）
		String accountStr = "";// 不存在的账号
		for (String account : accountList) {
			for (UserEntity user : userList) {
				if (user.getAccount().equals(account)) {
					continue;
				}
				accountStr += "," + account;
			}
		}
		log.error("[MessageManagementServiceImpl] 不存在的账号 accountStr  {} ", accountStr);
		throw new RRException(accountStr.replaceFirst("\n", "") + ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
				ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
	}

	@Override
	public List<Map<String, Object>> findMessageById(Long userId) {
		UserEntity user = this.userDao.selectById(userId);
		if (user == null || user.getId() <= 0) {

			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		List<MessageManagementEntity> messageList = this.baseMapper.findMemberMessageByEffect(1, userId);
		List<Map<String, Object>> resultList = new ArrayList<>();
		Map<String, Object> resultMap = new HashMap<>();
		Set<Long> messageId = new HashSet<>();
		for (MessageManagementEntity message : messageList) {
			if (message.getTargetObject() == 1) {
				String userArray[] = message.getUserAccount().split(",");
				if (!Arrays.asList(userArray).contains(user.getAccount())) {
					continue;
				}
			} else if (message.getTargetObject() == 2) {
				String hierarchyArray[] = message.getHierarchyIds().split(",");
				if (user.getHierarchyId() != null
						&& !Arrays.asList(hierarchyArray).contains(user.getHierarchyId() + "")) {
					continue;
				}
			}
			resultMap.put("title", message.getTitle());
			resultMap.put("messageId", message.getId());
			messageId.add(message.getId());
			resultList.add(resultMap);
			resultMap = new HashMap<>();
		}
		List<MessageUserEntity> messageUserList = this.messageUserDao.findMessageUser(messageId, user.getId());
		Map<String, Object> muserMap = new HashMap<>();
		for (MessageUserEntity muser : messageUserList) {
			muserMap.put(muser.getMessageId() + "_status", muser.getStatus());
			muserMap.put(muser.getMessageId() + "_statusId", muser.getId());
		}
		for (Map<String, Object> rmap : resultList) {
			String mid = rmap.get("messageId").toString();
			if (muserMap.get(mid + "_status") != null) {
				rmap.put("status", muserMap.get(mid + "_status"));
				rmap.put("statusId", muserMap.get(mid + "_statusId"));
			} else {
				rmap.put("status", 0);
				rmap.put("statusId", 0);
			}
		}
		return resultList;
	}

	@Override
	@Transactional
	public boolean saveAdminMessage(MessageManagementEntity messageManagement) {
		boolean isOk = false;
		if (messageManagement.getId() != null && messageManagement.getId() > 0) {
			isOk = this.updateAllColumnById(messageManagement);
			if (messageManagement.getTargetObject() == 1) {
				messageUserDao.deleteUserByMessageId(messageManagement.getId());
			}
		} else {
			messageManagement.setContentType(2);
			if(messageManagement.getReadonly()==null) {
				messageManagement.setReadonly(true);
			}
			isOk = this.baseMapper.insertEntityReturnId(messageManagement) == 1;
		}
		if (!isOk) {
			throw new RRException(ErrorCode.MessagemanagementErrorCode.SAVE_IS_ERRO.getErrMsg(),
					ErrorCode.MessagemanagementErrorCode.SAVE_IS_ERRO.getCode());
		}
		// 1：指定用户id 2:指定角色 3：所有用户
		List<SysUserEntity> userList = new ArrayList<>();
		if (messageManagement.getTargetObject() == 1) {
			userList = sysUserDao.queryByUserNameList(messageManagement.getUserAccount().split(","));
			if (userList != null && userList.size() > 0) {
				if (messageManagement.getTargetObject() == 1) {
					String[] accountList = messageManagement.getUserAccount().split(",");
					String accountStr = "";// 不存在的账号
					if (accountList.length > userList.size()) {
						for (String account : accountList) {
							boolean isExist = false;
							for (SysUserEntity user : userList) {
								if (user.getUsername().equals(account)) {
									isExist = true;
									break;
								}
							}
							if (!isExist) {
								accountStr += "," + account;
							}
						}
						throw new RRException(
								accountStr.replaceFirst(",", "") + ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
								ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
					}
				}
				List<MessageUserEntity> messageUserList = new ArrayList<>();
				for (SysUserEntity user : userList) {
					MessageUserEntity messageUser = new MessageUserEntity();
					messageUser.setMessageId(messageManagement.getId());
					messageUser.setContentType(messageManagement.getContentType());
					messageUser.setContent(messageManagement.getContent());
					messageUser.setTitle(messageManagement.getTitle());
					messageUser.setEffectTime(messageManagement.getEffectTime());
					messageUser.setFailureTime(messageManagement.getFailureTime());
					messageUser.setUserAccount(user.getUsername());
					messageUser.setUserId(user.getUserId());
					messageUser.setStatus(false);
					messageUser.setDeleteMessage(false);
					messageUserList.add(messageUser);
				}
				messageUserDao.insertBatch(messageUserList);
			} else {
				throw new RRException(ErrorCode.MessagemanagementErrorCode.NOCHOICE_ACCOUNT_ERRO.getErrMsg(),
						ErrorCode.MessagemanagementErrorCode.NOCHOICE_ACCOUNT_ERRO.getCode());
			}
		}
		return isOk;
	}

	@Override
	public List<Map<String, Object>> findAdminMessageById(Long userId) {
		SysUserEntity user = this.sysUserDao.selectById(userId);
		if (user == null || user.getUserId() <= 0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		List<MessageManagementEntity> messageList = this.baseMapper.findMemberMessageByEffect(2, userId);
		List<Map<String, Object>> resultList = new ArrayList<>();
		Map<String, Object> resultMap = new HashMap<>();
		Set<Long> messageId = new HashSet<>();
		for (MessageManagementEntity message : messageList) {
			if (message.getTargetObject() == 1) {
				String userArray[] = message.getUserAccount().split(",");
				if (!Arrays.asList(userArray).contains(user.getUsername())) {
					continue;
				}
			} else if (message.getTargetObject() == 2) {
				String hierarchyArray[] = message.getHierarchyIds().split(",");
				if (user.getRoleIds() != null) {
					String[] roleIdArr = user.getRoleIds().split(",");
					boolean isE = false;
					for (String roleId : roleIdArr) {
						if (Arrays.asList(hierarchyArray).contains(roleId)) {
							isE = true;
							break;
						}
					}
					if (!isE) {
						continue;
					}

				}
			}
			resultMap.put("title", message.getTitle());
			resultMap.put("messageId", message.getId());
			messageId.add(message.getId());
			resultList.add(resultMap);
			resultMap = new HashMap<>();
		}
		List<MessageUserEntity> messageUserList = this.messageUserDao.findMessageUser(messageId, user.getUserId());
		Map<String, Object> muserMap = new HashMap<>();
		for (MessageUserEntity muser : messageUserList) {
			muserMap.put(muser.getMessageId() + "_status", muser.getStatus());
			muserMap.put(muser.getMessageId() + "_statusId", muser.getId());
		}
		for (Map<String, Object> rmap : resultList) {
			String mid = rmap.get("messageId").toString();
			if (muserMap.get(mid + "_status") != null) {
				rmap.put("status", muserMap.get(mid + "_status"));
				rmap.put("statusId", muserMap.get(mid + "_statusId"));
			} else {
				rmap.put("status", 0);
				rmap.put("statusId", 0);
			}
		}
		return resultList;
	}

	@Override
	public Page<Map<String, Object>> findMessagePage(PageParam pageParam, MessageRequestVo requestVo) {
		Page<Map<String, Object>> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数
		return page.setRecords(this.baseMapper.findMemberMessageByEffectPage(page, requestVo));
	}

	@Override
	public Integer countUnreadNumber(MessageRequestVo vo) {
		return this.baseMapper.countUnreadNumber(vo);
	}

	@Override
	public boolean saveUserMessage(Long activityId,boolean receive, int activityType, Long messageModelId, Long userId, String account) {
		SysMessageModelEntity messageModel = sysMessageModelDao.selectById(messageModelId);
		if(messageModel==null || messageModel.getId()==null) {
			return false;
		}

		Date EFFECT_TIME = new Date(); // 邮件生效时间
		Date FAIL_TIME =messageModel.getEffectTime()==0?null:DateUtils.addDateDays(EFFECT_TIME, messageModel.getEffectTime()); // 邮件失效时间
		MessageManagementEntity messageManagement=new MessageManagementEntity();
		messageManagement.setContentType(MessageContentType.USER_CONTENT.getValue());
		messageManagement.setTargetObject(MessageTarget.USER.getValue());
		messageManagement.setTitle(messageModel.getTitle());
		messageManagement.setContent(messageModel.getContent());
		messageManagement.setEnable(true);
		messageManagement.setUserAccount(account);
		messageManagement.setReadonly(messageModel.getReadonly());
		messageManagement.setEffectTime(EFFECT_TIME);
		messageManagement.setFailureTime(FAIL_TIME);
		log.info("messageModel:{} messageManagement:{}",messageModel,messageManagement);
		this.baseMapper.insertEntityReturnId(messageManagement);

		MessageUserEntity messageUser = new MessageUserEntity();
		messageUser.setMessageId(messageManagement.getId());
		messageUser.setUserAccount(account);
		messageUser.setUserId(userId);
		messageUser.setStatus(false);
		messageUser.setDeleteMessage(false);
		messageUser.setActivityType(activityType);
		messageUser.setReceive(receive);

		messageUser.setActivityId(activityId);
		messageUserDao.insert(messageUser);
		SysMessageModelPropEntity modelProp =new SysMessageModelPropEntity();
		modelProp.setMessageId(messageModelId);
		List<SysMessageModelPropEntity> propList = sysMessageModelPropDao.selectList(new EntityWrapper<SysMessageModelPropEntity>(modelProp));
		if(propList!=null && !propList.isEmpty()) {
			List<SysMessagePropEntity> messagePropList = new ArrayList<>();
			SysMessagePropEntity messageProp=null;
			for(SysMessageModelPropEntity prop : propList) {
				messageProp=new SysMessagePropEntity();
				messageProp.setMessageId(messageManagement.getId());
				messageProp.setPropId(prop.getPropId());
				messageProp.setPropNum(prop.getPropNum());
				messagePropList.add(messageProp);
			}
			sysMessagePropDao.insertBatch(messagePropList);
		}
		return true;
	}

}
