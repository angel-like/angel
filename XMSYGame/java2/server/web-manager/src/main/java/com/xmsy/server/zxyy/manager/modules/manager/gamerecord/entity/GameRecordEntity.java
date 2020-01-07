package com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-22 11:12:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("game_record")
public class GameRecordEntity extends BaseEntity<GameRecordEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	* 
	*/
	private String userAccount;
	/**
	 * 游戏id
	 */
	private Long gameId;
	/**
	 * 游戏名
	 */
	private String gameName;
	/**
	 * 场次id
	 */
	private Long gradeId;
	/**
	 * 场次名
	 */
	private String gradeName;
	
	/**
	 * 场次编号
	 */
	private Integer gradeNumber;
	
	/**
	 * 房间id
	 */
	private Long roomId;
	/**
	 * 房间名
	 */
	private String roomName;
	/**
	 * 下注总金币
	 */
	private Long betCoins;
	/**
	 * 中奖金币
	 */
	private Long prizeCoins;
	/**
	 * 用户类型
	 */
	private Integer userType;
	/**
	 * 是否小游戏
	 */
	private Boolean mini;
	/**
	 * 是否机器人
	 */
	private Boolean robot;
	/**
	 * 是否GM会员
	 */
	private Boolean gmUser;
	/**
	 * 代理商盈利金币
	 */
	private Long profitCoins;
	/**
	 * 抽水金额
	 */
	private BigDecimal waterProfit;
	/**
	 * 抽水比例
	 */
	private BigDecimal waterRate;
	/**
	 * 房卡转金币的汇率
	 */
	private BigDecimal exchangeRate;
	
	/**
	 * 用户余额
	 */
	@TableField(exist = false)
	private BigDecimal money;
	
	/**
	 * 金币余额
	 */
	@TableField(exist = false)
	private Long coin;
	
	/**
	 * 局数
	 */
	private Integer round;
	
	/**
	 * 游戏局号
	 */
	private String gameRoundNo;

	/**
	 * 有效投注
	 */
	private Long validBet;
	
	/**
	 * 牌型
	 */
	@TableField(exist = false)
	private String cardsStr;
	/**
	 * 下注区域
	 */
	@TableField(exist = false)
	private String betAreaStr;
	
	/**
	 * 玩家牌型
	 */
	@TableField(exist = false)
	private String idleCardStr;
	
	/**
	 * 庄家牌型
	 */
	@TableField(exist = false)
	private String bankerCardStr;
	
	/**
	 * 丢弃的牌
	 */
	@TableField(exist = false)
	private String openCardStr;
	
	/**
	 * 龙牌
	 */
	@TableField(exist = false)
	private String dragonCardStr;
	
	/**
	 * 虎牌
	 */
	@TableField(exist = false)
	private String tigerCardStr;
	
	/**
	 * 红方牌型
	 */
	@TableField(exist = false)
	private String redCardStr;
	
	/**
	 * 黑方牌型
	 */
	@TableField(exist = false)
	private String blackCardStr;
	
	/**
	 * 开奖牌型
	 */
	@TableField(exist = false)
	private String lotteryCardStr;
	
	/**
	 * 玩家牌型
	 */
	@TableField(exist = false)
	private String playerCardStr;
	
	/**
	 * 结算牌型
	 */
	@TableField(exist = false)
	private String settlementCardStr;
	
	/**
	 * 玩家牌型
	 */
	@TableField(exist = false)
	private String playCardsStr;
	
	/**
	 * 公共牌型
	 */
	@TableField(exist = false)
	private String publicCardsStr;
	
	/**
	 * 结算牌型
	 */
	@TableField(exist = false)
	private String settlementCard;
	
		/**
	 * 结算日期
	 */
	@JsonFormat(pattern="yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@TableField(exist = false)
	private Date countDay;
	
		/**
	 * 开始时间
	 */
	@TableField(exist = false)
	private String startTime;
	
	/**
	 * 结束时间
	 */
	@TableField(exist = false)
	private String endTime;
	
	/**
	 * 相同局号不同玩家的userId和PrizeCoins
	 */
	@TableField(exist = false)
	private String userIdPrizeCoins;
	
	
}
