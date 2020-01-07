package com.xmsy.server.zxyy.manager.modules.manager.reportproviderdaily.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;


import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xmsy.server.zxyy.manager.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 每日平台投入产出报表
 * 
 * @author ayang
 * @email xxxxx
 * @date 2020-01-03 11:16:44
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("report_provider_daily")
public class ReportProviderDailyEntity extends BaseEntity<ReportProviderDailyEntity> {
	private static final long serialVersionUID = 1L;
			/**
	 * 运营商编码
	 */
    private Long providerCode;
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
	 * 开始时间
	 */
	@TableField(exist=false)
	private String startTime;
	/**
	 * 结束时间
	 */
	@TableField(exist=false)
	private String endTime;
	/**
	 * 结算日期
	 */
	@JsonFormat(pattern="yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date countDay;
					}
