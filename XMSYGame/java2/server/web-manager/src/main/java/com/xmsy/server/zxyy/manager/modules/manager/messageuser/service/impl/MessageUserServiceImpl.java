package com.xmsy.server.zxyy.manager.modules.manager.messageuser.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xmsy.common.define.page.PageParam;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.Query;
import com.xmsy.server.zxyy.manager.modules.manager.messagemanagement.dao.MessageManagementDao;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.dao.MessageUserDao;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.entity.MessageUserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.param.MessageRequestVo;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.service.MessageUserService;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysUserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.utils.JwtUtil;

@Transactional
@Service("messageUserService")
public class MessageUserServiceImpl extends ServiceImpl<MessageUserDao, MessageUserEntity>
		implements MessageUserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private MessageManagementDao messageManagementDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {

		MessageUserEntity entity = new MessageUserEntity();
		if (params != null) {
			if (params.get("key") != null) {
				entity.setUserAccount(params.get("key").toString());
			}
		}
		Wrapper<MessageUserEntity> wrapper = new EntityWrapper<MessageUserEntity>(entity);
		String sort = params.get("sort") != null ? params.get("sort").toString() : "";
		if (!StringUtils.isEmpty(sort)) {
			String direction = params.get("direction") != null ? params.get("direction").toString() : "";
			if ("desc".equalsIgnoreCase(direction)) {
				wrapper.orderDesc(Arrays.asList(sort.split(",")));
			} else {
				wrapper.orderAsc(Arrays.asList(sort.split(",")));
			}
		}
		Page<MessageUserEntity> page = this.selectPage(new Query<MessageUserEntity>(params).getPage(), wrapper);
		return new PageUtils(page);
	}

	@Override
	public int save(String token, MessageUserEntity entity) {
		int readNum = 0;
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = userDao.selectById(userId);
		if (user == null || user.getId() == null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		entity.setUserId(userId);
		List<MessageUserEntity> dataList = this.baseMapper.selectList(new EntityWrapper<MessageUserEntity>(entity));
		if (!CollectionUtils.isEmpty(dataList)) {
			if (dataList.get(0).getStatus() == false) {
				dataList.get(0).setStatus(true);
				this.updateById(dataList.get(0));
				readNum++;
			}
		} else {
			entity.setUserId(user.getId());
			entity.setStatus(true);
			entity.setUserAccount(user.getAccount());
			entity.setUpdateTime(new Date());
			this.insert(entity);
			readNum++;
		}
		return readNum;
	}

	@Override
	public void setReadBatch(String token) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = userDao.selectById(userId);
		if (user == null || user.getId() == null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		// 1.根据用户信息查询出所有的消息和用户的关系表没有记录的消息id
		MessageRequestVo requestVo = new MessageRequestVo();
		requestVo.setContentType(1);
		String hierachyid = user.getHierarchyId() == null ? "" : user.getHierarchyId().toString().trim();
		requestVo.setHierarchyIds(hierachyid.split(","));
		requestVo.setUserId(user.getId());
		requestVo.setUserAccount(user.getAccount());
		List<Long> messageIdList = messageManagementDao.findMessageIDByUser(requestVo);

		// 2.查询已存在的消息和用户的关系的状态为未读、未删除集合
		MessageUserEntity entity = new MessageUserEntity();
		entity.setUserId(userId);
		entity.setDeleteMessage(false);
		entity.setStatus(false);
		List<MessageUserEntity> list = this.baseMapper.selectList(new EntityWrapper<MessageUserEntity>(entity));

		// 3，状态是未读的把状态更新为已读，
		if (!CollectionUtils.isEmpty(list)) {
			for (MessageUserEntity messageUserEntity : list) {
				messageUserEntity.setStatus(true);
			}
			this.updateBatchById(list);
		}
		// 4.消息和用户的关系表没有记录的消息id放入新增集合做新增
		List<MessageUserEntity> insertList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(messageIdList)) {
			MessageUserEntity messageUserEntity = new MessageUserEntity();
			for (Long messageId : messageIdList) {
				messageUserEntity.setDeleteMessage(false);
				messageUserEntity.setMessageId(messageId);
				messageUserEntity.setUserId(user.getId());
				messageUserEntity.setUserAccount(user.getAccount());
				messageUserEntity.setStatus(true);
				insertList.add(messageUserEntity);
				messageUserEntity = new MessageUserEntity();
			}
			this.insertBatch(insertList);
		}

	}

	@Override
	public void setDeleteBatch(String token, List<Long> messageIds) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = userDao.selectById(userId);
		if (user == null || user.getId() == null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		List<MessageUserEntity> muserList = this.baseMapper.findMessageUser(messageIds, userId);
		if (muserList != null && muserList.size() > 0) {
			for (MessageUserEntity muser : muserList) {
				muser.setDeleteMessage(true);
				if (messageIds.contains(muser.getMessageId())) {
					messageIds.remove(muser.getMessageId());
				}
			}
			this.updateBatchById(muserList, 100);
		}
		List<MessageUserEntity> insertList = new ArrayList<>();
		for (Long mid : messageIds) {
			MessageUserEntity muser = new MessageUserEntity();
			muser.setMessageId(mid);
			muser.setUserId(userId);
			muser.setUserAccount(user.getAccount());
			muser.setStatus(false);
			muser.setDeleteMessage(true);
			muser.setUpdateTime(new Date());
			insertList.add(muser);
		}
		if (!insertList.isEmpty()) {
			this.insertBatch(insertList, 100);
		}

	}

	@Override
	public void setReadBatchForAdmin(SysUserEntity user, List<Long> messageIds) {
		if (user == null || user.getUserId() == null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		List<MessageUserEntity> muserList = this.baseMapper.findMessageUser(messageIds, user.getUserId());
		if (muserList != null && muserList.size() > 0) {
			List<MessageUserEntity> updateList = new ArrayList<>();
			for (MessageUserEntity muser : muserList) {
				if (messageIds.contains(muser.getMessageId())) {
					messageIds.remove(muser.getMessageId());
				}
				if (muser.getStatus() == null | muser.getStatus() == false) {
					muser.setStatus(true);
					updateList.add(muser);
				}
			}
			if (updateList != null && !updateList.isEmpty()) {
				this.updateBatchById(updateList, 100);
			}
		}
		List<MessageUserEntity> insertList = new ArrayList<>();
		for (Long mid : messageIds) {
			MessageUserEntity muser = new MessageUserEntity();
			muser.setMessageId(mid);
			muser.setUserId(user.getUserId());
			muser.setUserAccount(user.getUsername());
			muser.setStatus(true);
			muser.setDeleteMessage(false);
			muser.setUpdateTime(new Date());
			insertList.add(muser);
		}
		if (!insertList.isEmpty()) {
			this.insertBatch(insertList, 100);
		}

	}

	@Override
	public void setDeleteBatchForAdmin(SysUserEntity user, List<Long> messageIds) {
		if (user == null || user.getUserId() == null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		List<MessageUserEntity> muserList = this.baseMapper.findMessageUser(messageIds, user.getUserId());
		if (muserList != null && muserList.size() > 0) {
			for (MessageUserEntity muser : muserList) {
				muser.setDeleteMessage(true);
				if (messageIds.contains(muser.getMessageId())) {
					messageIds.remove(muser.getMessageId());
				}
			}
			this.updateBatchById(muserList, 100);
		}
		List<MessageUserEntity> insertList = new ArrayList<>();
		for (Long mid : messageIds) {
			MessageUserEntity muser = new MessageUserEntity();
			muser.setMessageId(mid);
			muser.setUserId(user.getUserId());
			muser.setUserAccount(user.getUsername());
			muser.setStatus(false);
			muser.setDeleteMessage(true);
			muser.setUpdateTime(new Date());
			insertList.add(muser);
		}
		if (!insertList.isEmpty()) {
			this.insertBatch(insertList, 100);
		}

	}

	@Override
	public void save(MessageUserEntity entity) {
		if (entity.getId() != null) {
			MessageUserEntity newEntity = entity.selectById();
			if (newEntity != null && newEntity.getStatus() == false) {
				newEntity.setStatus(true);
				this.updateById(newEntity);
			}

		} else {
			entity.setUpdateTime(new Date());
			this.insert(entity);
		}
	}

	@Override
	public MessageUserEntity queryByMessageId(Long messageId, Long userId) {
		return baseMapper.queryByMessageId(messageId, userId);
	}

	@Override
	public Page<Map<String, Object>> findMessagePage(PageParam pageParam, MessageRequestVo requestVo) {
		Page<Map<String, Object>> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数
		return page.setRecords(this.baseMapper. findMessageByUserId(page, requestVo));
	}

}
