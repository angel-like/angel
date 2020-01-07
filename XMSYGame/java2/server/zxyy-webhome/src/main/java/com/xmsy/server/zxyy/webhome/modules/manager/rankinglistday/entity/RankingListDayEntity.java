package com.xmsy.server.zxyy.webhome.modules.manager.rankinglistday.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 日排行榜
 * 
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("ranking_list_day")
public class RankingListDayEntity extends BaseEntity<RankingListDayEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 用户id
	 */
    private Long userId;
			/**
	 * 用户账号
	 */
    private String userAccount;
	/**
	 * 统计日期
	 */
    private Date thatDay;
	/**
	 * 排行榜id
	 */
    private Long rankingListId;
    /**
     * 排行榜名称
     */
    @TableField(exist=false)
    private String rankingListName;
			/**
	 * 排行值
	 */
    private BigDecimal amount;
			/**
	 * 名次
	 */
    private Integer position;
    /**
     * 0:未返佣  1：管理员新增（不返佣） 2：已返佣 3：返佣异常
     */
    private Integer type;
    
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
	
	}
