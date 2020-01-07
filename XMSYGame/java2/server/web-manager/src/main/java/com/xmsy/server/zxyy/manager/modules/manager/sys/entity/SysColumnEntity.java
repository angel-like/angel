/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.xmsy.server.zxyy.manager.modules.manager.sys.entity;


import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 栏目管理
 * 
 * @author ayang
 * @email xxxxxx
 * @date 2016年9月18日 上午9:26:39
 */
@Data
@TableName("sys_column")
public class SysColumnEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 栏目ID
	 */
	@TableId
	private Long menuId;

	/**
	 * 上级栏目ID，一级栏目为0
	 */
	private Long parentId;
	
	/**
	 * 父栏目名称
	 */
	@TableField(exist=false)
	private String parentName;

	/**
	 * 栏目名称
	 */
	private String name;

	/**
	 * 链接地址
	 */
	private String url;



	/**
	 * 栏目图标
	 */
	private String icon;

	/**
	 * 排序
	 */
	private Integer orderNum;


	
	/**
	 * ztree属性
	 */
	@TableField(exist=false)
	private Boolean open;

	@TableField(exist=false)
	private List<?> list;

	private  Boolean status;
}
