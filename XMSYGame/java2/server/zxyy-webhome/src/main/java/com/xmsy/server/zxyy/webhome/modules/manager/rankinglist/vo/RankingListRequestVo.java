package com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.vo;


import java.util.Date;

import lombok.Data;

@Data
public class RankingListRequestVo {
	/**
	 * 昨日
	 */
	private Date yesterday;
	/**
	 * startDate
	 */
	private Date startDate;
	/**
	 * endDate
	 */
	private Date endDate;
	/**
	 * 第几周
	 */
	private Integer weekOfYear;
	/**
	 * top
	 */
	private Integer limit;
	/**
	 * 排行榜id
	 */
	private Long rankingListId;
}
