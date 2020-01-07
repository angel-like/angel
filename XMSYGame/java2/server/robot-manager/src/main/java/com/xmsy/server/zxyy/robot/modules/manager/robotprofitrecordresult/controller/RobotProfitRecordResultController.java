package com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecordresult.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.robot.common.utils.PageUtils;
import com.xmsy.server.zxyy.robot.common.utils.R;
import com.xmsy.server.zxyy.robot.constant.SysConstant;
import com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecord.service.RobotProfitRecordService;
import com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecordresult.entity.RobotProfitRecordResultEntity;
import com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecordresult.service.RobotProfitRecordResultService;

/**
 * 每日统计结果表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-04-18 16:29:12
 */
//@Slf4j
@RestController
@RequestMapping("robotprofitrecordresult/robotprofitrecordresult")
public class RobotProfitRecordResultController {
	@Autowired
	private RobotProfitRecordResultService robotProfitRecordResultService;
	@Autowired
	private RobotProfitRecordService robotProfitRecordService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	//@RequiresPermissions("robotprofitrecordresult:robotprofitrecordresult:list")
	public R list(RobotProfitRecordResultEntity robotprofitrecordresult, PageParam pageParam) {
		Page<RobotProfitRecordResultEntity> result = new Page<RobotProfitRecordResultEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<RobotProfitRecordResultEntity> entityWrapper = new EntityWrapper<RobotProfitRecordResultEntity>(
				robotprofitrecordresult).ge(!StringUtils.isEmpty(robotprofitrecordresult.getQueryTime()), "create_time", // 存款时间查询
						robotprofitrecordresult.getQueryTime() + SysConstant.START_TIME)
						.le(!StringUtils.isEmpty(robotprofitrecordresult.getQueryTime()), "create_time",
								robotprofitrecordresult.getQueryTime() + SysConstant.END_TIME)
						.orderBy("create_time", false);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		robotprofitrecordresult.selectPage(result, entityWrapper);
		int modifyRobotNum=SysConstant.modifyRobotMap.size();
		int idleRobotNum=SysConstant.idleRobotMap.size();
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages())).put("modifyRobotNum",modifyRobotNum).put("idleRobotNum",idleRobotNum);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("robotprofitrecordresult:robotprofitrecordresult:info")
	public R info(@PathVariable("id") Long id) {
		RobotProfitRecordResultEntity robotProfitRecordResult = robotProfitRecordResultService.selectById(id);
		return R.ok().put("robotprofitrecordresult", robotProfitRecordResult);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("robotprofitrecordresult:robotprofitrecordresult:save")
	public R save(@RequestBody RobotProfitRecordResultEntity robotprofitrecordresult) {
		robotProfitRecordResultService.insert(robotprofitrecordresult);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("robotprofitrecordresult:robotprofitrecordresult:update")
	public R update(@RequestBody RobotProfitRecordResultEntity robotprofitrecordresult) {
		robotProfitRecordResultService.updateById(robotprofitrecordresult);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("robotprofitrecordresult:robotprofitrecordresult:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			robotProfitRecordResultService.deleteById(id);
		}
		return R.ok();
	}

	/**
	 * 重新统计
	 */
	@RequestMapping("/sumrobotRecord")
	//@RequiresPermissions("robotprofitrecordresult:robotprofitrecordresult:sumrobotRecord")
	public R sumrobotRecord() {
		Boolean result = false;
		try {
			result = robotProfitRecordService.sumYesterdayProfit();
		} catch (Exception e) {
//			log.info("[sumYesterdayProfit] 是否统计成功 {}", result);
			if (!result) {
				robotProfitRecordResultService.insert(new RobotProfitRecordResultEntity().setResultEnable(result));
			}
			throw e;
		}
		return R.ok();
	}

}
