package com.xmsy.server.zxyy.manager.modules.app.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.ImmutableMap;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.app.event.PushService;
import com.xmsy.server.zxyy.manager.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.manager.modules.manager.messagemanagement.entity.MessageManagementEntity;
import com.xmsy.server.zxyy.manager.modules.manager.messagemanagement.service.MessageManagementService;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.entity.MessageUserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.param.MessageInfoRequestVo;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.param.MessageRequestVo;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.service.MessageUserService;
import com.xmsy.server.zxyy.manager.modules.manager.sysmessageprop.service.SysMessagePropService;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.utils.JwtUtil;

/**
 * 站内信
 *
 * @author adu
 * @email xxxxx
 * @date 2019-02-18 11:30:25
 */
@RestController
@RequestMapping("V1.0/App")
public class AppMessageController {
	@Resource
	private MessageManagementService messageManagementService;
	@Resource
	private MessageUserService messageUserService;
	@Resource
	private SysMessagePropService sysMessagePropService;
	@Autowired
	private PushService pushService;
	@Resource
	private UserService userService;

	/**
	 * 站内信列表
	 */
	@GetMapping("/MailList")
	public R mailList(HttpServletRequest httpServletRequest, PageParam pageParam) {

		String token = httpServletRequest.getHeader("token");
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = this.userService.selectById(userId);
		if (user == null || user.getId() <= 0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		pageParam.setLimit(100);
		MessageRequestVo requestVo = new MessageRequestVo();
		requestVo.setContentType(1);
//		String hierachyid = user.getHierarchyId() == null ? "" : user.getHierarchyId().toString().trim();
//		requestVo.setHierarchyIds(hierachyid.split(","));
		requestVo.setUserId(user.getId());
//		requestVo.setUserAccount(user.getAccount());
		requestVo.setRegisterDate(user.getCreateTime());
//		Page<Map<String, Object>> result = messageManagementService.findMessagePage(pageParam, requestVo);
		Page<Map<String, Object>> result = messageUserService.findMessagePage(pageParam, requestVo);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 站内信未读数量
	 */
	@GetMapping("/unreadnum")
	public R getNum(HttpServletRequest httpServletRequest) {
		String token = httpServletRequest.getHeader("token");
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = this.userService.selectById(userId);
		if (user == null || user.getId() <= 0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		MessageUserEntity mesageUser = new MessageUserEntity();
		mesageUser.setUserId(userId);
		mesageUser.setStatus(false);
		Wrapper<MessageUserEntity> entityWrapper = new EntityWrapper<MessageUserEntity>(mesageUser);
		int size = messageUserService.selectCount(entityWrapper);

//		MessageRequestVo requestVo = new MessageRequestVo();
//		requestVo.setContentType(1);
//		String hierachyid = user.getHierarchyId() == null ? "" : user.getHierarchyId().toString().trim();
//		requestVo.setHierarchyIds(hierachyid.split(","));
//		requestVo.setUserId(user.getId());
//		requestVo.setUserAccount(user.getAccount());
//		requestVo.setRegisterDate(user.getCreateTime());

		return R.ok().put("data", ImmutableMap.of("unreadNum", size));
	}

	/**
	 * 获取站内信息
	 */
	@GetMapping("/MailInfo")
	public R getUserInfo(HttpServletRequest httpServletRequest, MessageInfoRequestVo vo) {
		String token = httpServletRequest.getHeader("token");
		MessageManagementEntity mailEntity = messageManagementService.selectById(vo.getMessageId());
		Map<String, Object> mail = new HashMap<>();
		mail.put("messageId", mailEntity.getId());
		mail.put("title", mailEntity.getTitle());
		mail.put("content", mailEntity.getContent());
		mail.put("effectTime", mailEntity.getEffectTime());
		mail.put("failureTime", mailEntity.getFailureTime());
		mail.put("readOnly", mailEntity.getReadonly());
		// 去更新已读
		MessageUserEntity messageUserEntity = new MessageUserEntity();
		messageUserEntity.setMessageId(mailEntity.getId());
		int unreadNum = messageUserService.save(token, messageUserEntity);
		if (unreadNum == 1) {
			Long userId = Long.valueOf(JwtUtil.getUserId(token));
			UserEntity pushMessage = new UserEntity();
			pushMessage.setId(userId);
			pushMessage.setUnreadNum(-1);
			UserInfoMessage message = new UserInfoMessage(pushMessage, null);
			pushService.pushExchange(message);
		}
		List<Map<String, Object>> messagePropList = sysMessagePropService.findMessagePropListByMessageId(mailEntity.getId());
		if(messagePropList == null) {
			messagePropList=new ArrayList<>();
		}
		mail.put("propList", messagePropList);
		return R.ok().put("data", mail);
	}

	/**
	 * 设置已读
	 * 
	 */
	@PutMapping("/setRead")
	public R setReadBatch(HttpServletRequest httpServletRequest) {
		String token = httpServletRequest.getHeader("token");
		messageUserService.setReadBatch(token);
		return R.ok();
	}

	/**
	 * 设置删除
	 * 
	 */
	@DeleteMapping("/setDelete")
	public R setDeleteBatch(@RequestBody List<Long> messageIds, HttpServletRequest httpServletRequest) {
		String token = httpServletRequest.getHeader("token");
		messageUserService.setDeleteBatch(token, messageIds);
		return R.ok();
	}

}
