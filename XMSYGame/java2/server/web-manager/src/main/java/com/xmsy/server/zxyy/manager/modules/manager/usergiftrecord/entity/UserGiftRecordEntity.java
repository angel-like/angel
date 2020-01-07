package com.xmsy.server.zxyy.manager.modules.manager.usergiftrecord.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户道具发放记录
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-24 09:34:18
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_gift_record")
public class UserGiftRecordEntity extends BaseEntity<UserGiftRecordEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 活动类型 1：奖励金
	 */
    private Integer type;
			/**
	 * 具体类型 
	 */
    private Integer detailType;
			/**
	 * 数量
	 */
    private Integer num;
			/**
	 * 道具id
	 */
    private Long propId;
			/**
	 * 发送模式 0：后台发放 1：客户领取
	 */
    private Integer sendType;
			/**
	 * 用户id
	 */
    private Long userId;
			/**
	 * 用户账号
	 */
    private String userAccount;
			/**
	 * 是否领取
	 */
    private Boolean receive;
    
    /**
     * 具体奖励类型名称
     */
    @TableField(exist=false)
    private String detailTypeName;
    
    /**
     * 道具名称
     */
    @TableField(exist=false)
    private String propName;
	}
