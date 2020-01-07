package com.xmsy.server.zxyy.manager.modules.app.hierarchy.param;


import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class HierarchyRateResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//层级id
	private Long hierarchyId;
	//胜率
	private BigDecimal rate;
}
