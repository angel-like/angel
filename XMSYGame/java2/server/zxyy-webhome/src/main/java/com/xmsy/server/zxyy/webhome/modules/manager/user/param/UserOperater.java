package com.xmsy.server.zxyy.webhome.modules.manager.user.param;

import lombok.Data;

/**
 * 操作内容
 * 
 * @author lpp
 * @email xxxxx
 * @date 2019-01-04 16:04:27
 */
@Data
public class UserOperater  {
	/**
	 * 备注
	 */
    private String remark;
    /**
	 * 
	 */
    private Long memberId;
	/**
	 * 用户账号
	 */
    private String memberAccount;
	}
