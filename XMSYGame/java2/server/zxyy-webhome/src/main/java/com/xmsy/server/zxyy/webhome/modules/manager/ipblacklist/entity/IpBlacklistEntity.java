package com.xmsy.server.zxyy.webhome.modules.manager.ipblacklist.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * ip黑名单
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-02-17 15:58:11
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("ip_blacklist")
public class IpBlacklistEntity extends BaseEntity<IpBlacklistEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * ip地址
	 */
    private String ip;
	}
