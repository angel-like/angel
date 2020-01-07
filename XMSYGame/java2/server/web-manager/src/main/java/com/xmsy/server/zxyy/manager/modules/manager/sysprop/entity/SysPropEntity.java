package com.xmsy.server.zxyy.manager.modules.manager.sysprop.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 系统道具
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-23 10:18:28
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("sys_prop")
public class SysPropEntity extends BaseEntity<SysPropEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 道具名称
	 */
    private String name;
	}
