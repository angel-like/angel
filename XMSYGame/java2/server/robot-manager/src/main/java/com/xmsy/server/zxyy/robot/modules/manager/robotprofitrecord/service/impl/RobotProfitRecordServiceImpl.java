package com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecord.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.robot.common.exception.RRException;
import com.xmsy.server.zxyy.robot.common.utils.ErrorCode;
import com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecord.dao.RobotProfitRecordDao;
import com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecord.entity.RobotProfitRecordEntity;
import com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecord.service.RobotProfitRecordService;
import com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecordresult.entity.RobotProfitRecordResultEntity;
import com.xmsy.server.zxyy.robot.modules.manager.robotprofitrecordresult.service.RobotProfitRecordResultService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("robotProfitRecordService")
public class RobotProfitRecordServiceImpl extends ServiceImpl<RobotProfitRecordDao, RobotProfitRecordEntity> implements RobotProfitRecordService {
	@Autowired
	private RobotProfitRecordResultService robotProfitRecordResultService;
	@Override
	public Boolean sumYesterdayProfit() {
		List<RobotProfitRecordEntity> list=baseMapper.sumYesterdayProfit();
		//先删除掉所有昨日的记录，再新增
		baseMapper.deleteByDate();
		log.info("[sumYesterdayProfit] 要统计的数据 {}", list);
		if(CollectionUtils.isEmpty(list)) {
			throw new RRException(ErrorCode.RobotCode.ROBOT_GAME_RECORD_ERRO.getErrMsg(),
					ErrorCode.RobotCode.ROBOT_GAME_RECORD_ERRO.getCode());
		}
		Boolean result=false;
		int i=0;
		
		while (true) {
			i++;
//			log.info("[sumYesterdayProfit] 开始循环【循环次数】 {}", i);
			if(i>=3) {
				break;
			}
			result=insertBatch(list);
			if(result) {
				break;
			}
			
		}
		if(result) {
			robotProfitRecordResultService.insert(new RobotProfitRecordResultEntity().setResultEnable(result));
		}
		return result;
		
	}

	@Override
	public Long countProfitCoin(RobotProfitRecordEntity entity) {
		Long num = baseMapper.countProfitCoin(entity);
		return num;
	}
}
