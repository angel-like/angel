package com.xmsy.server.zxyy.manager.modules.manager.batchrechargeprop.param;

import com.baomidou.mybatisplus.annotations.TableField;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserOperater;

import lombok.Data;

@Data
public class BatchRechargePropParam {
	
		/**
	 * 道具id
	 */
	private Long propId;
			/**
	 * 道具数量
	 */
	private Long propNum;
			/**
	 * 支付人账号
	 */
	private String account;
			/**
	 * 层级id
	 */
	private Long hierarchyId;
			/**
	 * vip_id
	 */
	private Long vipId;
	/**
	 * 充值对象
	 */
	private Integer targetObject;
		/**
	 * 是否发送站内信
	 */
	private Boolean sendMessage;
			/**
	 * 站内信标题
	 */
	private String messageTitle;
			/**
	 * 站内信内容
	 */
	private String messageContent;
			/**
	 * 站内信有效期限
	 */
	private Integer effectiveDate;
	/**
	 * 操作内容
	 */
	@TableField(exist = false)
	private UserOperater userOperater;
	

}
