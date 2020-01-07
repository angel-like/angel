package com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.AppFangkaGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.result.AppGameParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.result.AppGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordEntity;

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
	
	List<AppGameRecordParams> getGameRecordsByUserIdForFangka(@Param("userId") Long userId,
			@Param("roomId") Long roomId,@Param("gameId") Long gameId);
	
	List<AppGameRecordParams> getGameRecordsByRoomNoForFangka(Pagination page,
			@Param("param") AppFangkaGameRecordParams param);

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
	 * .根据时间获取+游戏id+房间id  获取用户在该游戏 房间 的有效下注
	 *
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Long getUserValidBetByGameId(@Param("userId") Long userId, @Param("startTime") String startTime,
			@Param("endTime") String endTime, @Param("gameId") Long gameId,@Param("gradeId") Long gradeId,@Param("roomId") Long roomId);

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
	 * 通过游戏ID获取用户（百家乐）游戏记录
	 * 
	 * @param roomId,userId
	 */
	List<AppGameRecordParams> userBjlGameRecordForGameId(@Param("gameId") Long gameId, @Param("userId") Long userId);
	

	/**
	 * 查询用户对应的场次
	 * 
	 * @return
	 */
	List<GameRecordEntity> queryField(Pagination page, @Param("countDay") Date countDay, @Param("userId") Long userId,
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

	/**
	 * 统计时间区间内  +某个游戏 +某个场次（某个厅）=》  的输赢次数次
	 * 
	 * @return
	 */
	Map<String, Object> queryGameNumByGameIdGradeId(@Param("userId") Long userId, @Param("startTime") String startTime,
			@Param("endTime") String endTime, @Param("gameId") Long gameId, @Param("gradeId") Long gradeId,
			@Param("roomId") Long roomId);
}
