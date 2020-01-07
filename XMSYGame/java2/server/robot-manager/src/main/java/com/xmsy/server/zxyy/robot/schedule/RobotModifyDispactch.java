package com.xmsy.server.zxyy.robot.schedule;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.robot.cache.LocalContentCache;
import com.xmsy.server.zxyy.robot.constant.SysConstant;
import com.xmsy.server.zxyy.robot.modules.manager.robot.entity.RobotEntity;
import com.xmsy.server.zxyy.robot.modules.manager.robot.service.RobotService;
import com.xmsy.server.zxyy.robot.queue.ConcurrentLinked;
import com.xmsy.server.zxyy.robot.utils.JwtUtil;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 更新机器人到数据库
 * 
 * @author xiaoliu
 *
 */
@Slf4j
@Component
public class RobotModifyDispactch {
	@Autowired
	private RobotService robotService;
	@Autowired
	private LocalContentCache cache;

	@Resource(name = "sysTaskExecutor")
	private TaskExecutor sysTaskExecutor;

	private boolean dispactchStatus = true;

	@PostConstruct
	void init() {
		List<RobotEntity> list = robotService.selectList(new EntityWrapper<RobotEntity>(
				new RobotEntity().setStatus(SysConstant.ROBOT_STATUS_FALSE).setEnable(SysConstant.ROBOT_ENABLE_TRUE)));
		log.info("[机器人更新线程启动] 空闲机器人数量 {}", list.size());
		if (!CollectionUtil.isEmpty(list)) {
			for (RobotEntity entity : list) {
				ConcurrentLinked.putIdle(entity);
			}
		}
		List<RobotEntity> whileList = robotService
				.selectList(new EntityWrapper<RobotEntity>(new RobotEntity().setStatus(SysConstant.ROBOT_STATUS_TRUE)));
		log.info("[机器人更新线程启动] 游戏中机器人数量 {}", whileList.size());
		if (!CollectionUtil.isEmpty(whileList)) {
			for (RobotEntity entity : whileList) {
				entity.setToken(JwtUtil.createJWT(entity.getId()));
				cache.put(SysConstant.ROBOT + entity.getId(), entity);
			}
		}
		log.info("[机器人更新线程启动] sysTaskExecutor {}", sysTaskExecutor);
		sysTaskExecutor.execute(new ModifyRobot());
//		sysTaskExecutor.execute(new ModifyRobot());
//		sysTaskExecutor.execute(new ModifyRobot());
		log.info("[机器人更新线程成功] ====================================");

	}

	class ModifyRobot implements Runnable {

		@Override
		public void run() {
			RobotEntity robot = null;
			while (dispactchStatus) {
				try {
					while (!SysConstant.modifyRobotMap.isEmpty()) {
						robot = SysConstant.modifyRobotMap.poll();
//						log.info("[修改机器人] robot {}", robot);
						if (null != robot) {
							if(!robotService.updateRobot(robot)) {
								log.error("[修改机器人] 失败 robotId {}", robot.getId());
								log.error("[修改机器人] 失败 robot {}", robot);
							}
//							log.info("[修改机器人] 是否成功 {}", result);
						}
					}
				} catch (Exception e) {
					log.error("[ModifyRobot] updateById", e);
				}
				try {
					Thread.sleep(500L);
				} catch (InterruptedException e) {
					log.error("[ModifyRobot] InterruptedException", e);
				}

			}
		}

	}

	public boolean isDispactchStatus() {
		return dispactchStatus;
	}

	public void stop() {
		this.dispactchStatus = false;
	}

}
