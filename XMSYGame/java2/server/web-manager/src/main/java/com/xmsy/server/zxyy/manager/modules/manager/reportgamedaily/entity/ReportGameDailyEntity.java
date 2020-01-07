package com.xmsy.server.zxyy.manager.modules.manager.reportgamedaily.entity;

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
 * 每日游戏投入产出报表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-11 09:26:16
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("report_game_daily")
public class ReportGameDailyEntity extends BaseEntity<ReportGameDailyEntity> {
	private static final long serialVersionUID = 1L;
			/**
	 * 游戏id
	 */
    private Long gameId;
			/**
	 * 游戏名称
	 */
    private String gameName;
	
			/**
	 * 参与人数
	 */
    private Integer participateNum;
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
	 * 结算日期
	 */
    @JsonFormat(pattern="yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date countDay;
    
    @TableField(exist=false)
    private String startTime;
    
    @TableField(exist=false)
    private String endTime;
    
    
}
