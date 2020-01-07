package com.xmsy.server.zxyy.webhome.modules.app.headframe;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.app.headframe.param.HeadframParam;
import com.xmsy.server.zxyy.webhome.modules.app.headframe.service.AppHeadFrameService;
import com.xmsy.server.zxyy.webhome.modules.manager.headframe.entity.HeadframeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.headframe.service.HeadframeService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userheadframe.entity.UserHeadframeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userheadframe.service.UserHeadframeService;

/**
 * 
 * 用户头像框接口
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-06-19 11:00:25
 */
@RestController
@RequestMapping("V1.0/App")
public class AppHeadFrameController {
	@Autowired
	private HeadframeService headframeService;
	@Autowired
	private AppHeadFrameService appHeadFrameService;	
	@Autowired
	private UserHeadframeService userHeadframeService;
	@Autowired
	private UserService userService;
	/**
	 * 获取头像框列表
	 * @return
	 */
	@GetMapping("/getHeadFrame")
	public R getHeadFrame(@RequestHeader("token") String token) {
		UserEntity user=userService.getUser(token);
		//获取头像框信息
		List<HeadframeEntity> listheadframe=headframeService.selectList(
				new EntityWrapper<HeadframeEntity>(new HeadframeEntity().setEnable(true))
				.orderBy("type,type_id"));
		//获取当前用户头像框关系
		List<UserHeadframeEntity> listUserHeadframe=userHeadframeService.selectList(
				new EntityWrapper<UserHeadframeEntity>(new UserHeadframeEntity().setUserId(user.getId())));	
		List<Map<String,Object>> resultList=appHeadFrameService.getListHeadFrameData(listheadframe,listUserHeadframe);
		return R.ok().put("data", resultList);
	}
	/**
	 * 修改头像框信息
	 * @return
	 */
	@PostMapping("/updateUserHeadFrame")
	public R updateUserHeadFrame(@RequestHeader("token") String token,@RequestBody @Valid HeadframParam param){
		UserEntity user=userService.getUser(token);
		//修改用户头像
		Boolean flag=appHeadFrameService.updateUserHeadframe(user,param.getHeadframId());
		if(flag) {
			return R.ok();
		}
		return R.error();
	}
}
