package com.xmsy.server.zxyy.webhome.modules.web.h5;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.app.login.result.UserDetail;
import com.xmsy.server.zxyy.webhome.modules.manager.fortuneactivitymanager.entity.EnvelopeRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.fortuneactivitymanager.entity.FortuneActiviConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.fortuneactivitymanager.service.EnvelopeRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.fortuneactivitymanager.service.FortuneActiviConfigService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userlogin.entity.UserLoginEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userlogin.service.UserLoginService;
import com.xmsy.server.zxyy.webhome.utils.JwtUtil;

/**
 * 用户
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-12 20:18:25
 */
@RestController
@RequestMapping("V1.0/webhome")
public class H5UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private FortuneActiviConfigService fortuneActiviConfigService;
	@Autowired
	private EnvelopeRecordService envelopeRecordService;
	/**
	 * h5获取用户信息及大厅地址
	 */
	@GetMapping("/getUserInfoForH5")
	public R getUserInfoForH5(@RequestHeader("token") String token,@RequestParam Long hallId) {
		System.out.println(hallId);
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserDetail result=userService.getUserInfoForH5(userId,hallId);
		//获取当前时间 在 活动时间内   （只有一个活动，但有多个事件）
		Wrapper<FortuneActiviConfigEntity> entityWrapper = new EntityWrapper<FortuneActiviConfigEntity>();
		entityWrapper.le("start_time", new Date());
		entityWrapper.ge("end_time", new Date());
		FortuneActiviConfigEntity fortuneActiviConfig = fortuneActiviConfigService.selectOne(entityWrapper);
		// 查询活动期间未开启红包个数
		Integer unEnvelopeNum = 0;
		if (fortuneActiviConfig != null) {
			Wrapper<EnvelopeRecordEntity> wrapper = new EntityWrapper<EnvelopeRecordEntity>(new EnvelopeRecordEntity()
					.setUserId(result.getId()).setActivityId(fortuneActiviConfig.getId()).setStatus(0));
			wrapper.ge("create_time", fortuneActiviConfig.getStartTime());
			wrapper.le("create_time", fortuneActiviConfig.getEndTime());
			unEnvelopeNum = envelopeRecordService.selectCount(wrapper);
		}
		result.setUnEnvelopeNum(unEnvelopeNum);
		UserLoginEntity userLogin = userLoginService.selectOne(new EntityWrapper<UserLoginEntity>(new UserLoginEntity().setToken(token)
				.setLoginStatus("success")).orderBy("id", false));
		if(userLogin!=null) {
			if(userLogin.getIpAddress() != null && userLogin.getNation() != null) {
				result.setAddress(userLogin.getIpAddress().replace(userLogin.getNation(), ""));
			}
		}
		result.setToken("");
		return R.ok().put("data", result);
	}
}
