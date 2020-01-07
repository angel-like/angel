package com.xmsy.server.zxyy.webhome.modules.manager.hierarchypaychannelconfigrelationship.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 层级支付限额关系表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-03 11:02:14
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("hierarchy_paychannelconfig_relationship")
public class HierarchyPaychannelconfigRelationshipEntity extends BaseEntity<HierarchyPaychannelconfigRelationshipEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 层级id
	 */
    private Long hierarchyId;
			/**
	 * 支付限额id
	 */
    private Long paychannelconfigId;
	}
