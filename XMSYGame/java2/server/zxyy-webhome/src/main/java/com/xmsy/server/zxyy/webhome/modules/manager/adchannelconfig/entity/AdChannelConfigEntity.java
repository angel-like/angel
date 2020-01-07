package com.xmsy.server.zxyy.webhome.modules.manager.adchannelconfig.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 渠道配置表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-02 17:05:09
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("ad_channel_config")
public class AdChannelConfigEntity extends BaseEntity<AdChannelConfigEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 渠道名称
	 */
    private String channelName;
			/**
	 * 渠道代码
	 */
    private String channelCode;
    /**
     * 渠道地址
     */
    private String channelAddress;
    /**
     * 发布地址
     */
    private String publishUrl;
			/**
	 * 备注
	 */
    private String remake;
	}
