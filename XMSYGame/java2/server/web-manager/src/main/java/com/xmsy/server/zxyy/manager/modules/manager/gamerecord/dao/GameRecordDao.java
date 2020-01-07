package com.xmsy.server.zxyy.manager.modules.manager.gamerecord.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
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
@Mapper
public interface GameRecordDao extends BaseMapper<GameRecordEntity> {
	/**
	 * 计算总有效下注
	 *
	 * @return
	 */
	BigDecimal sumValidBet(@Param("record") GameRecordEntity record,@Param("startTime") String startTime, @Param("endTime") String endTime);
	/**
	 * .获取游戏记录
	 *
	 * @param page
	 * @param record
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<GameRecordEntity> getGameRecords(Pagination page, @Param("record") GameRecordEntity record,
			@Param("startTime") String startTime, @Param("endTime") String endTime);
	/**
	 * .为下载CSV获取游戏记录
	 *
	 * @param page
	 * @param record
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<Map<String, Object>> getGameRecordsCSV(@Param("record") GameRecordEntity record,
			@Param("startTime") String startTime, @Param("endTime") String endTime);

	/**
	 * 计算总盈亏
	 *
	 * @param record
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	BigDecimal sumPrizeCoinsByParam(@Param("record") GameRecordEntity record, @Param("startTime") String startTime,
			@Param("endTime") String endTime);

	/**
	 * .根据时间获取用户有效下注
	 *
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Long getUserValidBet(@Param("userId") Long userId, @Param("startTime") String startTime,
			@Param("endTime") String endTime);

	/**
	 * .根据时间获取游戏有效下注
	 *
	 * @param gameId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Long getGameValidBet(@Param("gameId") Long gameId, @Param("startTime") String startTime,
			@Param("endTime") String endTime);

	/**
	 * 通过房间ID获取用户玩过的游戏
	 *
	 * @param roomId,userId
	 */
	List<AppGameParams> userGameForRoomId(@Param("roomId") Long roomId, @Param("userId") Long userId);

	/**
	 * 通过游戏ID获取用户游戏记录
	 *
	 * @param roomId,userId
	 */
	List<AppGameRecordParams> userGameRecordForGameId(@Param("gameId") Long gameId, @Param("userId") Long userId);

	/**
	 * 查询用户对应的场次
	 *
	 * @return
	 */
	List<GameRecordEntity> queryField(Pagination page, @Param("countDay") Date countDay,@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("userId") Long userId,
			@Param("gradeId") Long gradeId, @Param("gameId") Long gameId, @Param("userAccount") String userAccount);

	/**
	 * 查询同个局号的用户id和输赢金币
	 *
	 * @return
	 */
	List<GameRecordEntity> queryByGameRoundNo(@Param("gameRoundNo") String gameRoundNo, @Param("round") Integer round);

	/**
	 * 统计游戏输赢场次
	 *
	 * @return
	 */
	Map<String, Object> queryGameStatistics(@Param("list") List<Long> gameIds, @Param("userId") Long userId);
	Map<String, Object> queryGameStatisticsByRoomAndUserId(@Param("roomId") Long roomId, @Param("userId") Long userId);

	/**
	 * 查询单个会员下注和输赢金额
	 * @param user
	 * @return
	 */
	List<UserCoinSumParam> selectCoinSum(@Param("user") UserParamFour user);
	/**
	 * 根据会员id统计当前会员   总投入统计 --下注金币 （房卡房间过滤掉）
	 * @param userId
	 * @return
	 */
	BigDecimal totalPutInto(@Param("userId") Long userId,@Param("startTime") String startTime,@Param("endTime") String endTime);
	/**
	 * 根据会员id统计当前会员   总产出统计 --中奖金币 （房卡房间过滤掉）
	 * @param userId
	 * @return
	 */
	BigDecimal totalPutOut(@Param("userId") Long userId,@Param("startTime") String startTime,@Param("endTime") String endTime);

	List<GameRecordEntity> getRecords( @Param("record") GameRecordEntity record);
}
