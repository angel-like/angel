package com.xmsy.server.zxyy.webhome.modules.manager.messageuser.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.network.redis.utils.RedisLockUtil;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.Query;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.dao.MessageManagementDao;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.dao.MessageUserDao;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.entity.MessageUserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.param.MessageRequestVo;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.service.MessageUserService;
import com.xmsy.server.zxyy.webhome.modules.manager.sys.entity.SysUserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysmessageprop.entity.SysMessagePropEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.mq.MqClient;
import com.xmsy.server.zxyy.webhome.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Transactional
@Service("messageUserService")
public class MessageUserServiceImpl extends ServiceImpl<MessageUserDao, MessageUserEntity>
		implements MessageUserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private MessageManagementDao messageManagementDao;
	@Resource
	private PushService pushService;
	@Autowired
	private MqClient mqClient;
	@Autowired
	private RedisLockUtil redisLockUtil;


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
//		entity.setUserId(userId);
//		List<MessageUserEntity> dataList = this.baseMapper.selectList(new EntityWrapper<MessageUserEntity>(entity));

//		if (!CollectionUtils.isEmpty(dataList)) {
//			if (dataList.get(0).getStatus() == false) {
//				dataList.get(0).setStatus(true);
//				this.updateById(dataList.get(0));
//				readNum++;
//			}
//		}
		MessageUserEntity messageUserEntity = this.baseMapper.selectById(entity);
		if(messageUserEntity!=null){
			if(messageUserEntity.getStatus()==false) {
				messageUserEntity.setStatus(true);
				this.updateById(messageUserEntity);
				readNum++;
			}
		}
		else {
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
	public List<Long> setReadBatch(String token) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = userDao.selectById(userId);
		if (user == null || user.getId() == null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		List<Long> messageIdResultList=new  ArrayList<>();//(1)返回所有读取的消息id列表
		// 1.根据用户信息查询出所有的消息和用户的关系表没有记录的消息id
//		MessageRequestVo requestVo = new MessageRequestVo();
//		requestVo.setContentType(1);
//		String hierachyid = user.getHierarchyId() == null ? "" : user.getHierarchyId().toString().trim();
//		requestVo.setHierarchyIds(hierachyid.split(","));
//		requestVo.setUserId(user.getId());
//		requestVo.setUserAccount(user.getAccount());
//		List<Long> messageIdList = messageManagementDao.findMessageIDByUser(requestVo);

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
				messageIdResultList.add(messageUserEntity.getMessageId());//(2)状态更新为已读的消息id  添加进去
			}
			this.updateBatchById(list);
		}
//		// 4.消息和用户的关系表没有记录的消息id放入新增集合做新增
//		List<MessageUserEntity> insertList = new ArrayList<>();
//		if (!CollectionUtils.isEmpty(messageIdList)) {
//			MessageUserEntity messageUserEntity = new MessageUserEntity();
//			for (Long messageId : messageIdList) {
//				messageIdResultList.add(messageId);//(3)消息和用户的关系表没有记录的消息id   添加进去
//				messageUserEntity.setDeleteMessage(false);
//				messageUserEntity.setMessageId(messageId);
//				messageUserEntity.setUserId(user.getId());
//				messageUserEntity.setUserAccount(user.getAccount());
//				messageUserEntity.setStatus(true);
//				insertList.add(messageUserEntity);
//				messageUserEntity = new MessageUserEntity();
//			}
//			this.insertBatch(insertList);
//		}
		//5.推送 消息和用户的关系表   未读变已读的个数  +新增进去的个数
		UserEntity pushMessage = new UserEntity();
		pushMessage.setId(userId);
		pushMessage.setUnreadNum(messageIdResultList.size()*-1);//
		UserInfoMessage message = new UserInfoMessage(pushMessage, null);
		pushService.pushExchange(message);

		return messageIdResultList;//(4)返回所有读取的消息id列表

	}
	@Override
	public List<Long> setDeleteBatch(String token) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = userDao.selectById(userId);
		if (user == null || user.getId() == null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		List<Long> messageIdResultList=new  ArrayList<>();//(1)返回所有删除的消息id列表
		// 2.查询已存在的消息和用户的关系的状态为已读、已接收、未删除集合
		MessageUserEntity entity = new MessageUserEntity();
		entity.setUserId(userId);
		entity.setDeleteMessage(false);
	entity.setStatus(true);
	entity.setReceive(true);
		List<MessageUserEntity> list = this.baseMapper.selectList(new EntityWrapper<MessageUserEntity>(entity));

		// 3，状态是未读的把状态更新为已读，
		if (!CollectionUtils.isEmpty(list)) {
			for (MessageUserEntity messageUserEntity : list) {
				messageIdResultList.add(messageUserEntity.getMessageId());//(2)把状态更新为已读并 删除状态改为已删除的  添加进
			messageUserEntity.setStatus(true);
			messageUserEntity.setDeleteMessage(true);
//				this.deleteById(messageUserEntity.getId());
			}
			this.updateBatchById(list);

		}
		return messageIdResultList;//(3)返回所有删除的消息id列表

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
				messageIds.remove(muser.getMessageId());
			}
			this.updateBatchById(muserList, 100);
		}
		List<MessageUserEntity> insertList = new ArrayList<>();
		if(messageIds.isEmpty()) {
			return;
		}
		//查询消息id是否存在
		List<Long> mids=messageManagementDao.getMessageIds(messageIds);
		for (Long mid : mids) {
			messageIds.remove(mid);
		}
		if(!messageIds.isEmpty()) {
			throw new RRException(ErrorCode.MessagemanagementErrorCode.MESSAGE_ID_ERROR.getErrMsg(),
					ErrorCode.MessagemanagementErrorCode.MESSAGE_ID_ERROR.getCode());
		}
		for (Long mid : mids) {
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

	/**
	 * 通过用户一键领取全部消息里的道具
	 * @param token
	 * @return messageIdList  消息id集合
	 */
	@Override
	public Map<String,Object> receiveAllMessageProp(String token) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = userDao.selectById(userId);
		if (user == null || user.getId() == null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}

		// 设置锁如果失败，返回没有可领取的邮件
		if(!redisLockUtil.setLock(userId.toString()+"MessageProp",userId.toString())) {
			throw new RRException(ErrorCode.MessagemanagementErrorCode.RECEIVE_MESSAGE_IS_NULL.getErrMsg(),
					ErrorCode.MessagemanagementErrorCode.RECEIVE_MESSAGE_IS_NULL.getCode());
		}

		//1.1 查询当前用户的 所有未领取 的消息 道具列表
		MessageRequestVo requestVo = new MessageRequestVo();
		requestVo.setUserId(user.getId());
		requestVo.setUserAccount(user.getAccount());
		List<SysMessagePropEntity> messagePropList = this.baseMapper.findMessageProp(requestVo);
		if(null==messagePropList||messagePropList.size()==0) {//空的话就抛异常提示
			throw new RRException(ErrorCode.MessagemanagementErrorCode.RECEIVE_MESSAGE_IS_NULL.getErrMsg(),
					ErrorCode.MessagemanagementErrorCode.RECEIVE_MESSAGE_IS_NULL.getCode());
		}
//		redisUtil.set(userId.toString()+"MessageProp2",userId.toString());
		//1.2查询  当前用户 站内信用户关系表
		List<MessageUserEntity> MessageUserList = this.baseMapper.findMessageUserByUserId(requestVo);
		//2.把MessageUserEntity 通过  消息队列异步处理 加到用户上   并标记为已领取
		asynProcessMessagePush(MessageUserList);

		//3.获取道具数量   以及消息id（Set 不能相同）
		Set<Long> messageIdResultSet=new  HashSet<>();//(1)返回所有领取的消息id列表
		Long coin=0L;//金币
		Long roomCard=0L;//房卡
		for(SysMessagePropEntity sysMessageProp:messagePropList) {
			messageIdResultSet.add(sysMessageProp.getMessageId());//(2)所有领取的消息id列表  添加进集合
			//查询道具名称  1就是金币
			if(SysConstant.COIN_PROPID.equals(sysMessageProp.getPropId())) {
				coin+=sysMessageProp.getPropNum();
			}
			//查询道具名称  2就是房卡
			if (SysConstant.ROOMCARD_PROPID.equals(sysMessageProp.getPropId())) {
				roomCard+=sysMessageProp.getPropNum();
			}
		}


		UserInfoMessage userInfoMessage=null;
		try {
			userInfoMessage=new UserInfoMessage();
			userInfoMessage.setUid(user.getId());
			userInfoMessage.setAccount(user.getAccount());
			userInfoMessage.setUnReadNum(MessageUserList.size()*-1);//邮件未读个数
			userInfoMessage.setCoin(coin);
			userInfoMessage.setRoomCard(roomCard);
			log.debug("[用户一键领取邮件内道具-推送消息] message {}", userInfoMessage);
			pushService.pushUserInfo(userInfoMessage);
		} catch (Exception e) {
			log.error("[用户一键领取邮件内道具-推送消息] message {}", userInfoMessage);
		}
		Map<String,Object> map=new HashMap<>();
		map.put("messageIdResultSet", messageIdResultSet);
		map.put("coinNum", coin);
		map.put("roomCardNum", roomCard);
		return map;//(3).返回的消息id
	}

	@Override
	public Page<Map<String, Object>> findMessagePage(PageParam pageParam, MessageRequestVo requestVo) {
		Page<Map<String, Object>> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数
		return page.setRecords(this.baseMapper.findMessageByUserId(page, requestVo));
	}

	@Async  //去zxyy-Calculate里面 设置监听机制  有放进去就能监听到 就可以取出来给用户
	void asynProcessMessagePush(List<MessageUserEntity> MessageUserList){
		for(MessageUserEntity messageUserEntity:MessageUserList) {
			mqClient.messagePush(messageUserEntity);//发送给消息队列里
		}

	}


}
