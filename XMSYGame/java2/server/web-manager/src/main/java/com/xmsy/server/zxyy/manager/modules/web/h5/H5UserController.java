package com.xmsy.server.zxyy.manager.modules.web.h5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.app.login.result.UserDetail;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.utils.JwtUtil;

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
	/**
	 * h5获取用户信息及大厅地址
	 */
	@GetMapping("/getUserInfoForH5")
	public R getUserInfoForH5(@RequestHeader("token") String token,@RequestParam Long hallId) {
		System.out.println(hallId);
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserDetail result=userService.getUserInfoForH5(userId,hallId);
		result.setToken("");
		return R.ok().put("data", result);
	}
}
