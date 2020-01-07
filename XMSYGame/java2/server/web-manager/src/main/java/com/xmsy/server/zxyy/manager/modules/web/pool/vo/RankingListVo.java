package com.xmsy.server.zxyy.manager.modules.web.pool.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class RankingListVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long rankingListId;//排行榜id
	private Integer type;//1:日排行  2:周排行
	private Date quertDate;//查询日期

}
