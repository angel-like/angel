package com.xmsy.server.zxyy.manager.modules.manager.batchrechargeprop.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 批量充值会员道具
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-27 11:41:20
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("batch_recharge_prop")
public class BatchRechargePropEntity extends BaseEntity<BatchRechargePropEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 订单号
	 */
    private String orderNo;
			/**
	 * 操作人用户名
	 */
    private String sysUserAccount;
			/**
	 * 操作人id
	 */
    private Long sysUserId;
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
	 * Vip名称
	 */
    @TableField(exist=false)
    private String vipName;
    
    /**
	 * 层级名称
	 */
    @TableField(exist=false)
    private String hierarchyName;
    
    /**
   	 * 层级名称
   	 */
    @TableField(exist=false)
    private String propName;
	}
