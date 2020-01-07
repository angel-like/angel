package com.xmsy.server.zxyy.webhome.modules.statistics.game.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 官网头部管理
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-02-21 15:45:36
 */
@Mapper
public interface GameRebateDao {

	// 获取昨日所有用户游戏有效投注
	@Select("SELECT a.user_id as userId," + " a.user_account as userAccount," + " a.game_id AS gameId,"
			+ " SUM(a.valid_bet) AS validBet"
			+ " FROM game_record a WHERE TO_DAYS( date_format(now(),'%Y-%m-%d') ) - TO_DAYS(date_format(a.create_time,'%Y-%m-%d')) = 1 "
			+ " GROUP BY a.game_id,a.user_id,a.user_account")
	List<Map<Object, Object>> selectValidBetForNow();

	// 获取游戏返利比例
	@Select("SELECT a.game_id AS gameId,a.game_name AS gameName,a.rate AS rebateRate from game a  GROUP BY a.game_id,a.game_name,a.rate")
	List<Map<Object, Object>> selectGameList();


	// 新增返利记录
	@Insert("INSERT INTO game_rebate_record" + "  (`create_time`,`game_id`,`user_id`,`rebate_coin`,`rebate_time`)"
			+ "	VALUES" + "	(NOW(),#{gameId},#{userId},#{rebateCoin},NOW())")
	void insertRebateRecord(@Param("gameId") Long gameId, @Param("userId") Long userId,
			@Param("rebateCoin") Long rebateCoin);

	// 获取今日所有用户游戏返利总额
	@Select("select SUM(a.rebate_coin) AS rebateCoin,a.user_id as userId from game_rebate_record a where a.rebate_time=date_format(now(),'%Y-%m-%d') GROUP BY a.user_id")
	List<Map<Object, Object>> selectRebateNum();
	
	// 根据用户ID为用户新增金币
	@Update("update `user` a SET a.coin=a.coin+#{rebateAmount} where a.id=#{userId}")
	void updateUserCoin(@Param("rebateAmount") Long rebateAmount, @Param("userId") Long userId);
	
	// 根据用户ID获取用户新增金币后的余额
	@Select("select a.id, a.account,a.coin,a.commission,a.money from `user` a where a.id=#{userId}")
	Map<Object, Object> selectUserForUserId( @Param("userId") Long userId);
	
	// 根据为用户新增交易记录
	@Insert("INSERT INTO user_transaction_record" + 
			" (`create_time`,`update_time`,`user_id`,`user_account`,`type`,`order_no`,`amount`,`money`,`coin`,`commission`,`detail_type`)" + 
			"		VALUES" + 
			"		(NOW(),NOW(),#{userId},#{userAccount},#{type},#{orderNo},#{amount},#{money},#{coin},#{commission},#{detailType})")
	void insertUserTransactionRecord(@Param("userId") Long userId,
			@Param("userAccount") String userAccount,
			@Param("type") int type, 
			@Param("orderNo") String orderNo, 
			@Param("amount") BigDecimal amount,
			@Param("money") BigDecimal money,
			@Param("coin") Long coin, 
			@Param("commission") BigDecimal commission,
			@Param("detailType") int detailType);
}
