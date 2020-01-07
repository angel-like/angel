package com.xmsy.server.zxyy.webhome.modules.web.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.entity.MessageManagementEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.service.MessageManagementService;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.entity.MessageUserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.param.MessageInfoRequestVo;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.param.MessageRequestVo;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.service.MessageUserService;
import com.xmsy.server.zxyy.webhome.modules.manager.sysmessageprop.service.SysMessagePropService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.utils.JwtUtil;

/**
 * 站内信、公告
 *
 * @author lpp
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@RestController
@RequestMapping("webhome/message")
public class WebHomeMessageController {
	@Resource
	private MessageManagementService messageManagementService;
	@Resource
	private MessageUserService messageUserService;
	@Resource
	private UserService userService;
	@Resource
	private SysMessagePropService sysMessagePropService;
	
	
	
	/**
	 * 站内信列表
	 */
	@GetMapping("/mailList")
	public R mailList(HttpServletRequest httpServletRequest,PageParam pageParam) {
		String token=httpServletRequest.getHeader("token");
		Long userId=Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = this.userService.selectById(userId);
		if(user==null || user.getId()<=0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		MessageRequestVo requestVo=new MessageRequestVo();
    	requestVo.setContentType(1);
    	String hierachyid=user.getHierarchyId()==null?"":user.getHierarchyId().toString().trim();
    	requestVo.setHierarchyIds(hierachyid.split(","));
    	requestVo.setUserId(user.getId());
    	requestVo.setUserAccount(user.getAccount());
    	requestVo.setRegisterDate(user.getCreateTime());
    	Page<Map<String, Object>> result = messageManagementService.findMessagePage(pageParam, requestVo);
		return R.ok().put("page", result);
	}
	
	/**
	 * 站内信未读数量
	 */
	@GetMapping("/unreadnum")
	public R getNum(HttpServletRequest httpServletRequest) {
		String token=httpServletRequest.getHeader("token");
		Long userId=Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = this.userService.selectById(userId);
		if(user==null || user.getId()<=0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		MessageRequestVo requestVo=new MessageRequestVo();
    	requestVo.setContentType(1);
    	String hierachyid=user.getHierarchyId()==null?"":user.getHierarchyId().toString().trim();
    	requestVo.setHierarchyIds(hierachyid.split(","));
    	requestVo.setUserId(user.getId());
    	requestVo.setUserAccount(user.getAccount());
    	requestVo.setRegisterDate(user.getCreateTime());
    	
		return R.ok().put("unreadNum", messageManagementService.countUnreadNumber(requestVo));
	}
	
	/**
	 * 获取站内信息
	 */
	@GetMapping("/getMailInfo")
	public R getUserInfo(HttpServletRequest httpServletRequest, MessageInfoRequestVo vo) {
		String token=httpServletRequest.getHeader("token");
		MessageManagementEntity mailEntity = messageManagementService.selectById(vo.getMessageId());
		Map<String, Object> mail=new HashMap<>();
		mail.put("messageId", mailEntity.getId());
		mail.put("title", mailEntity.getTitle());
		mail.put("content", mailEntity.getContent());
		mail.put("effectTime", mailEntity.getEffectTime());
		mail.put("failureTime", mailEntity.getFailureTime());
		mail.put("readOnly", mailEntity.getReadonly()==null?true:mailEntity.getReadonly());
		//去更新已读
		MessageUserEntity messageUserEntity=new MessageUserEntity();
		messageUserEntity.setMessageId(mailEntity.getId());
//		messageUserEntity.setStatus(1);
//		messageUserEntity.setId(vo.getStatusId());
		messageUserService.save(token, messageUserEntity);
		List<Map<String, Object>> messagePropList = sysMessagePropService.findMessagePropListByMessageId(mailEntity.getId());
		if(messagePropList == null) {
			messagePropList=new ArrayList<>();
		}
		mail.put("propList", messagePropList);
		return R.ok().put("mail", mail);
	}
	
	/**
	 * 设置已读
	 * 
	 */
	@PostMapping("/setRead")
	public R setReadBatch(HttpServletRequest httpServletRequest) {
		String token=httpServletRequest.getHeader("token");
		messageUserService.setReadBatch(token);
		return R.ok();
	}
	/**
	 * 设置删除
	 * 
	 */
	@PostMapping("/setDelete")
	public R setDeleteBatch(@RequestBody List<Long> messageIds, HttpServletRequest httpServletRequest) {
		String token=httpServletRequest.getHeader("token");
		messageUserService.setDeleteBatch(token, messageIds);
		return R.ok();
	}
	

}
