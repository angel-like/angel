package com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.AppFangkaGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.GameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.result.AppGameParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.result.AppGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.playertasks.entity.PlayerTasksEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;

/**
 * 
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-22 11:12:08
 */
public interface GameRecordService extends IService<GameRecordEntity> {

	/**
	 * .获取游戏记录
	 * 
	 * @param record
	 * @param pageParam
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Page<GameRecordEntity> getGameRecords(GameRecordEntity record, PageParam pageParam, String startTime,
			String endTime);

	/**
	 * 统计总盈亏
	 * 
	 * @param record
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	BigDecimal sumPrizeCoinsByParam(GameRecordEntity record, String startTime, String endTime);

	/**
	 * 保存app游戏记录
	 * 
	 * @param gameRecordParams
	 */
	void appSaveGameRecord(GameRecordParams gameRecordParams);

	/**
	 * 通过房间ID获取用户玩过的游戏
	 * 
	 * @param roomId,userId
	 */
	List<AppGameParams> userGameForRoomId(Long roomId, Long userId);

	/**
	 * 通过游戏ID获取用户游戏记录
	 * 
	 * @param gameId,userId
	 */
	List<AppGameRecordParams> userGameRecordForGameId(Long gameId, Long userId);
	
	List<AppGameRecordParams> userGameRecordForFangka( Long userId ,Long gameId);
	
	Page<AppGameRecordParams> getGameRecordsByRoomNoForFangka(PageParam pageParam, AppFangkaGameRecordParams param);

	/**
	 * 查询用户对应的场次
	 * 
	 * @param userAccount
	 * @return
	 */
	Page<GameRecordEntity> queryField(PageParam pageParam, Date countDay, Long userId, Long gradeId, Long gameId,
			String userAccount);

	/**
	 * 查询同个局号的用户id和输赢金币
	 * 
	 * @return
	 */
	List<GameRecordEntity> queryByGameRoundNo(String gameRoundNo, Integer round);
	/**
	 * 富豪任务（打码任务）
	 * @param user
	 * @param playerTask
	 * @param cycle
	 * @return
	 */
	PlayerTasksEntity validBetTask(UserEntity user, PlayerTasksEntity playerTask, int cycle);

}
