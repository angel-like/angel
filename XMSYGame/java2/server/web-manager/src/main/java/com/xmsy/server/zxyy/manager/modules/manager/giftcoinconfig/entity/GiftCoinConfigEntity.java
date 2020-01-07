package com.xmsy.server.zxyy.manager.modules.manager.giftcoinconfig.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 金币奖励配置
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-21 11:00:51
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("gift_coin_config")
public class GiftCoinConfigEntity extends BaseEntity<GiftCoinConfigEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 奖励类型
	 */
    private Integer type;
			/**
	 * 次数|人数
	 */
    private Integer num;
		/**
	 * 当天最多次数
	 */
	private Integer maxNum;
			/**
	 * 奖励的金币值
	 */
    private Long coin;
			/**
	 * 状态（1.启用，0：禁用）
	 */
    private Boolean enable;
    
	/**
	 * 发送模式 0：后台发放 1：客户领取
	 */
	private Integer sendType;
	
	/**
	 * 周期单位（天）
	 */
	private Integer cycle;
	
	/**
	 * 模板id
	 */
	private Long templateId;
	
	/**
	 * vip等級
	 */
	private Long vipId;
	
	/**
     * 奖励类型名称
     */
    @TableField(exist = false)
    private String typeName;
    
    /**
     * 模板名称
     */
    @TableField(exist = false)
    private String templateName;
    
    /**
     * vip名称
     */
    @TableField(exist = false)
    private String vipName;
    
	}
