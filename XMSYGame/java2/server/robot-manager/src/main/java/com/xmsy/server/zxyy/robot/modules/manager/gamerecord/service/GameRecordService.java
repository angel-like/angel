package com.xmsy.server.zxyy.robot.modules.manager.gamerecord.service;

import java.util.List;

import javax.validation.Valid;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.robot.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.robot.modules.manager.gamerecord.entity.GameRecordParams;


/**
 * 游戏记录
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-04-02 11:32:49
 */
public interface GameRecordService extends IService<GameRecordEntity> {

	void appSaveGameRecord(@Valid GameRecordParams gameRecordParams);

	Long sumCountProfit(GameRecordEntity gamerecord);

	//删除指定时间之前数据,真删除
	void deleteGameRecord(String date);
	void deleteGameRecord(int days);

	Long sumProfitForGame(List<Long> gameIds,String date);

}

