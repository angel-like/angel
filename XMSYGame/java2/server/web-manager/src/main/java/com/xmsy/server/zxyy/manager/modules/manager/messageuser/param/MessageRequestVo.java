package com.xmsy.server.zxyy.manager.modules.manager.messageuser.param;

import java.util.Date;

import lombok.Data;

@Data
public class MessageRequestVo {
	private Long userId;
	private String userAccount;
	private String title;
	private Integer contentType;
	private String[] hierarchyIds;
	private Long hierarchyId;
	/**
	 * 注册时间
	 */
	private Date registerDate;

}
