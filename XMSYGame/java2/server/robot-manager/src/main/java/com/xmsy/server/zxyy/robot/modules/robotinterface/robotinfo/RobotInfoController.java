package com.xmsy.server.zxyy.robot.modules.robotinterface.robotinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.robot.cache.LocalContentCache;
import com.xmsy.server.zxyy.robot.common.exception.RRException;
import com.xmsy.server.zxyy.robot.common.utils.ErrorCode;
import com.xmsy.server.zxyy.robot.common.utils.R;
import com.xmsy.server.zxyy.robot.constant.SysConstant;
import com.xmsy.server.zxyy.robot.modules.manager.robot.entity.RobotEntity;
import com.xmsy.server.zxyy.robot.modules.robotinterface.robotinfo.param.RobotInfoParam;
import com.xmsy.server.zxyy.robot.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 返还机器人
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-18 14:58:45
 */
@Slf4j
@RestController
@RequestMapping("V1.0/robot")
public class RobotInfoController {
	@Autowired
	private LocalContentCache cache;
	/**
	 * 通过token获取机器人信息
	 */
	@GetMapping("/robotInfo")
	public R robotInfo(@RequestParam("token") String token) {
//		log.info("[robotInfo===获取机器人信息] token {}", token);
		Long id = Long.valueOf(JwtUtil.getUserId(token));
		RobotEntity entity=(RobotEntity) cache.get(SysConstant.ROBOT+id);
//		log.info("[robotInfo===获取机器人信息] 缓存获取到的机器人信息 {}", entity);
		if(null==entity) {
			log.error("[robotInfo===获取机器人信息] token {}", token);
			throw new RRException(ErrorCode.RobotCode.ROBOT_NULL_ERRO.getErrMsg(),
					ErrorCode.RobotCode.ROBOT_NULL_ERRO.getCode());
		}
		RobotInfoParam robotInfo = new RobotInfoParam();
		robotInfo.setId(entity.getId());
		robotInfo.setCoin(entity.getCoin());
		robotInfo.setName(entity.getName());
		robotInfo.setSex(entity.getSex());
		robotInfo.setPortrait(entity.getPortrait());
		robotInfo.setToken(token);
//		log.info("[robotInfo===获取机器人信息] 返回的机器人信息 {}", robotInfo);
		return R.ok().put("data", robotInfo);
	}
	

}
