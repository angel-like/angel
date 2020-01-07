package com.xmsy.server.zxyy.manager.modules.manager.sharerecord.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 分享记录
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-22 12:14:28
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("share_record")
public class ShareRecordEntity extends BaseEntity<ShareRecordEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 客户端类型
	 */
    private String clientType;
			/**
	 * 分享到
	 */
    private String shareTo;
			/**
	 * 用户id
	 */
    private Long userId;
			/**
	 * 用户账号
	 */
    private String userAccount;
	}
