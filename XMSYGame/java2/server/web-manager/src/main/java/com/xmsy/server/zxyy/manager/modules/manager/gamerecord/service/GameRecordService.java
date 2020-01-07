package com.xmsy.server.zxyy.manager.modules.manager.gamerecord.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.GameRecordParams;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.result.AppGameParams;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.result.AppGameRecordParams;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserCoinSumParam;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamFour;

/**
 *
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-22 11:12:08
 */
public interface GameRecordService extends IService<GameRecordEntity> {
	/**
	 * 统计总有效下注
	 *
	 *
	 * @return
	 */
	BigDecimal sumValidBet(GameRecordEntity record,String startTime,
						   String endTime);
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
        List   <GameRecordEntity>      getRecords(GameRecordEntity record);
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

	/**
	 * 查询用户对应的场次
	 *
	 * @param userAccount
	 * @return
	 */
	Page<GameRecordEntity> queryField(PageParam pageParam, Date countDay, String startTime, String endTime, Long userId, Long gradeId, Long gameId,
			String userAccount);

	/**
	 * 查询同个局号的用户id和输赢金币
	 *
	 * @return
	 */
	List<GameRecordEntity> queryByGameRoundNo(String gameRoundNo, Integer round);

	/**
	 * 查询单个用户下注金额和输赢金额
	 * @param user
	 * @return
	 */
	List<UserCoinSumParam> selectCoinSum(UserParamFour user);
	
	/**
	 * 根据会员id统计当前会员   总投入统计 --下注金币 （房卡房间过滤掉）
	 * 
	 * @param userId
	 * @return
	 */
	BigDecimal totalPutInto(Long userId,String startTime,String endTime);
	/**
	 * 根据会员id统计当前会员   总产出统计 --中奖金币 （房卡房间过滤掉）
	 * 
	 * @param userId
	 * @return
	 */
	BigDecimal totalPutOut(Long userId,String startTime,String endTime);

}
