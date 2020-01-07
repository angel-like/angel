package com.xmsy.server.zxyy.manager.modules.app.useractivity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.app.useractivity.param.UserPropReceiveParam;
import com.xmsy.server.zxyy.manager.modules.manager.messagemanagement.entity.MessageManagementEntity;
import com.xmsy.server.zxyy.manager.modules.manager.messagemanagement.service.MessageManagementService;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.usergiftrecord.service.UserGiftRecordService;
import com.xmsy.server.zxyy.manager.utils.JwtUtil;


@RestController
@RequestMapping("V1.0/App/userActivity")
public class UserPropReceivedController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserGiftRecordService userGiftRecordService;
	
	@Autowired
	private MessageManagementService messageManagementService;
	
	/**
	 * 会员道具领取
	 */
	@PutMapping("/UserPropReceived")
	public R UserPropReceived(@RequestHeader("token") String token,@RequestBody UserPropReceiveParam userPropReceiveParam) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = userService.selectById(userId);
		if (user == null || user.getId() <= 0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		MessageManagementEntity messageUserEntity = messageManagementService.selectById(userPropReceiveParam.getMessageId());
		if(messageUserEntity == null || messageUserEntity.getId()<=0) {
			throw new RRException(ErrorCode.UserActivityCode.MESSAGE_ID_ERRO.getErrMsg(),
					ErrorCode.UserActivityCode.MESSAGE_ID_ERRO.getCode());
		}
		userGiftRecordService.UserPropReceived(userPropReceiveParam.getMessageId(), userId);
		return R.ok();
	}

}
