package com.xmsy.server.zxyy.webhome.modules.app.login;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.ImmutableMap;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.app.login.param.BindingPhone;
import com.xmsy.server.zxyy.webhome.modules.app.login.param.OAuth2ParamsToBind;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.oauth2.OAuth2;
import com.xmsy.server.zxyy.webhome.oauth2.OAuth2Utils;
import com.xmsy.server.zxyy.webhome.utils.Base64Util;
import com.xmsy.server.zxyy.webhome.utils.JwtUtil;
import com.xmsy.server.zxyy.webhome.utils.VerificationUitl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("V1.0/App")
public class AppBindController {
	@Autowired
	private UserService userService;
	@Autowired
	private OAuth2Utils oauth2Utils;
	
	/**
     * APP绑定手机号
     */
    @PostMapping("/bindingPhone")
    public R bindingPhone(@RequestBody @Valid BindingPhone bindingPhone, @RequestHeader("token") String token,
                          HttpServletRequest httpServletRequest) {
        Long id = Long.valueOf(JwtUtil.getUserId(token));
        log.info("[APP绑定手机号] registerParams {}", bindingPhone);
        userService.bindingPhone(bindingPhone, id);
        return R.ok().put("data", ImmutableMap.of(
				"phoneShow",VerificationUitl.phoneNoShow(bindingPhone.getPhone())));
    }

	/**
	 * 绑定第三方平台
	 */
	@PostMapping(value = "/bindThird")
	public R bindThird(@RequestBody @Valid OAuth2ParamsToBind oauth2Param, HttpServletRequest httpServletRequest)
			throws Exception {
		String token = httpServletRequest.getHeader("token");
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = this.userService.selectById(userId);
		if (user == null || user.getId() <= 0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		if (!StringUtils.isEmpty(user.getOpenId())) {
			throw new RRException(ErrorCode.UserErrorCode.USER_BING_THIRD_ERROR.getErrMsg(),
					ErrorCode.UserErrorCode.USER_BING_THIRD_ERROR.getCode());
		}
		//log.info("oauth2Param {}", oauth2Param);
		OAuth2 oauth2 = oauth2Utils.getOAuth2(oauth2Param.getType());
		if (null == oauth2) {
			throw new RRException(ErrorCode.UserErrorCode.USER_REGISTER_TYPE_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_REGISTER_TYPE_ERRO.getCode());
		}
		String respone = oauth2.getAccessToken(oauth2Param.getCode());
		log.info("respone {}", respone);
		String access_token = JSON.parseObject(respone).getString("access_token");
		String openId = JSON.parseObject(respone).getString("openid");
		if (StringUtils.isEmpty(openId)) {
			throw new RRException(ErrorCode.UserErrorCode.USER_OAUTH_CODE_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_OAUTH_CODE_ERRO.getCode());
		}
		UserEntity userParam = new UserEntity();
		userParam.setOpenId(openId);
		if(userService.selectCount(new EntityWrapper<UserEntity>(userParam))>0) {
			throw new RRException(ErrorCode.UserErrorCode.OTHER_USER_BING_THIRD_ERROR.getErrMsg(),
					ErrorCode.UserErrorCode.OTHER_USER_BING_THIRD_ERROR.getCode());
		}
		user.setOpenId(openId);
		//log.info("[bindThird] entity{}", user);
//			// 该第三方未登陆过，需要注册
		user.setUnionType(oauth2Param.getType());
		String userinfo = oauth2.getUser(access_token, openId);
		JSONObject userResult = JSON.parseObject(userinfo);
		log.info("[bindThird] userinfo obj {}", userResult);
		if (userResult.getInteger("errcode") != null) {
			throw new RRException("第三方登陆出错");
		}
		String unionId = userResult.getString("unionid");
		int sex = userResult.getIntValue("sex");
		user.setUnionId(unionId);
		if (sex == 0) {
			user.setSex(false);
		} else if (sex == 1) {
			user.setSex(true);
		}
		String nickName = userResult.getString("nickname");
		user.setNickName(Base64Util.base64Encoder(nickName));
		userService.updateById(user);
		return R.ok().put("data", ImmutableMap.of("nickName", nickName));
	}

}
