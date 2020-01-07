package com.xmsy.server.zxyy.webhome.modules.manager.rankinglistweek.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 周排行榜
 * 
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("ranking_list_week")
public class RankingListWeekEntity extends BaseEntity<RankingListWeekEntity> {
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
	 * 排行榜id
	 */
    private Long rankingListId;
    /**
     * 排行榜名称
     */
    @TableField(exist=false)
    private String rankingListName;
    /**
     * 周一的日期
     */
    @TableField(exist=false)
    private Date firstOfWeek;
			/**
	 * 排行值
	 */
    private BigDecimal amount;
			/**
	 * 名次
	 */
    private Integer position;
	/**
	 * 第几周
	 */
    private Integer weekOfYear;
	}
