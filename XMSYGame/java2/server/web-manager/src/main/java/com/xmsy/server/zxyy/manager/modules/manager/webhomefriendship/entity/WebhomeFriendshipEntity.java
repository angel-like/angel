package com.xmsy.server.zxyy.manager.modules.manager.webhomefriendship.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 友情链接管理
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-07 11:02:39
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("webhome_friendship")
public class WebhomeFriendshipEntity extends BaseEntity<WebhomeFriendshipEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 名称
	 */
    private String name;
			/**
	 * 链接
	 */
    private String url;
    	/**
	 * 排序
	 */
    private String orderNum;
    /**
	 * 颜色
	 */
    private String color;
    /**
	 * 跳转类型
	 */
    private Integer type;
    /**
	 * 图标
	 */
    private Long icon;
    
    /**
   	 * 图标路径
   	 */
    @TableField(exist=false)
    private String iconUrl;
    
    
	}
