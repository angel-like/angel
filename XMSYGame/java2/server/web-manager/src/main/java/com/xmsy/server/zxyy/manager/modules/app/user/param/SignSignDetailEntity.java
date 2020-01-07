package com.xmsy.server.zxyy.manager.modules.app.user.param;

import lombok.Data;


/**
 * 用户本周签到详情
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-05-13 15:50:02
 */
@Data
public class SignSignDetailEntity {
		/**
	* id
	*/
	private Long id;				
	/**
	 * 签到名称
	 */
    private String dayName;
			/**
	 * 奖励
	 */
    private Long reward;
    /**
	 * 状态
	 */
    private Integer status;
	}
