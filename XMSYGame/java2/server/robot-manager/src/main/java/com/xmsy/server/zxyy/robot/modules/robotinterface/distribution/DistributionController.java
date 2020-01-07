package com.xmsy.server.zxyy.robot.modules.robotinterface.distribution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.robot.common.utils.R;
import com.xmsy.server.zxyy.robot.modules.manager.robot.service.RobotService;
import com.xmsy.server.zxyy.robot.modules.robotinterface.distribution.param.RobotResultParam;

/**
 * 机器人分配
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-18 16:58:45
 */
@RestController
@RequestMapping("V1.0/robotinterface")
public class DistributionController {

	@Autowired
	private RobotService robotService;

	/**
	 * 根据游戏及场次分配机器人
	 */
	@GetMapping(value = "/distributionRobot", produces = "application/json;charset=utf-8")
	public R distributionRobot(@RequestParam("gameId") Long gameId, @RequestParam("gradeId") Long gradeId,
			@RequestParam("coin") Long coin, @RequestParam("hallId") Long hallId) {
		RobotResultParam result = robotService.distributionRobot(gameId, gradeId, hallId, coin);
		return R.ok().put("data", result);
	}
}
