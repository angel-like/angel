package com.xmsy.server.zxyy.webhome.modules.manager.gamerecorddaily.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 每日会员游戏总下注记录
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-04-11 20:30:48
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("game_record_daily")
public class GameRecordDailyEntity extends BaseEntity<GameRecordDailyEntity> {
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
	 * 有效下注
	 */
    private Long validBet;
			/**
	 * 中奖金币
	 */
    private Long prizeCoins;
			/**
	 * 抽水金额
	 */
    private BigDecimal waterProfit;
			/**
	 * 代理商盈利金币
	 */
    private Long profitCoins;
			/**
	 * 结算日期
	 */
    @JSONField(format="yyyy-MM-dd")
    private Date countDay;
			/**
	 * 会员返水
	 */
    private BigDecimal userWaterProfit;
			/**
	 * 会员返水比例
	 */
    private BigDecimal userWaterRate;
			/**
	 * 是否计算返水
	 */
    private Boolean userReturn;
			/**
	 * 代理商是否返佣
	 */
    private Boolean agentReturn;
			/**
	 * 用户返水是否异常
	 */
    private Boolean abnormal;
			/**
	 * 用户返水异常内容
	 */
    private String abnormalContent;
			/**
	 * 代理商返佣是否异常
	 */
    private Boolean agentAbnormal;
			/**
	 * 代理商返佣异常内容
	 */
    private String agentAbnormalContent;
					}
