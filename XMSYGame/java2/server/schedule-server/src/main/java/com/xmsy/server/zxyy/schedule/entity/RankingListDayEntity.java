package com.xmsy.server.zxyy.schedule.entity;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 日排行榜
 * 
 * @author lpp
 * @email xxxxx
 * @date 2019-04-04
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class RankingListDayEntity {
	
	 /**
	 * id
	 */
    private Long id;
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
    private String rankingListName;
	/**
	 * 排行值
	 */
    private BigDecimal amount;
	/**
	 * 名次
	 */
    private Integer position;
	}
