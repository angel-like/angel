package com.xmsy.server.zxyy.manager.modules.manager.reportplayergamedaily.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 每日玩家游戏投入产出报表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-11 09:26:16
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("report_player_game_daily")
public class ReportPlayerGameDailyEntity extends BaseEntity<ReportPlayerGameDailyEntity> {
	private static final long serialVersionUID = 1L;
			/**
	 * 用户id
	 */
    private Long userId;
			/**
	 * 账号名称
	 */
    private String userAccount;
			/**
	 * 游戏id
	 */
    private Long gameId;
			/**
	 * 游戏名称
	 */
    private String gameName;
			/**
	 * 场次id
	 */
    private Long gradeId;
			/**
	 * 场次名称
	 */
    private String gradeName;
			/**
	 * 房间id
	 */
    private Long roomId;
			/**
	 * 房间名称
	 */
    private String roomName;
			/**
	 * 总投入
	 */
    private BigDecimal investmentTotal;
			/**
	 * 总产出
	 */
    private BigDecimal outputTotal;
			/**
	 * 总输赢
	 */
    private BigDecimal winTotal;
	/**
	 * 总投入
	 */
    @TableField(exist = false)
	private BigDecimal investmentTotalSum;
			/**
	 * 总产出
	 */
    @TableField(exist = false)
	private BigDecimal outputTotalSum;
			/**
	 * 总输赢
	 */
    @TableField(exist = false)
	private BigDecimal winTotalSum;
			/**
	 * 结算日期
	 */
    @JsonFormat(pattern="yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date countDay;
					}
