package com.xmsy.server.zxyy.manager.modules.manager.reportdatadaily.entity;

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
 * 每日平台数据报表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-11 09:26:16
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("report_data_daily")
public class ReportDataDailyEntity extends BaseEntity<ReportDataDailyEntity> {
	private static final long serialVersionUID = 1L;
			/**
	 * 充值总额
	 */
    private BigDecimal rechargeTotal;
			/**
	 * 充值人数
	 */
    private Integer rechargeNum;
			/**
	 * 注册用户数
	 */
    private Integer registereNum;
			/**
	 * 活跃人数
	 */
    private Integer activeNum;
			/**
	 * 赢钱人数
	 */
    private Integer winNum;
			/**
	 * 输钱人数
	 */
    private Integer loseNum;
			/**
	 * 总投入
	 */
    private Long investmentTotal;
			/**
	 * 总产出
	 */
    private Long outputTotal;
			/**
	 * 总输赢
	 */
    private Long winTotal;
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
    
    /**
     * 兑换总额
     */
    @TableField(exist=false)
    private BigDecimal totalExchangeAmount;
    
    /**
     * 兑换人数
     */
    @TableField(exist=false)
    private Integer exchangeNum;
    
}
