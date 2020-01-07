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

package com.xmsy.server.zxyy.manager.modules.manager.sys.dao;

import java.util.List;

import com.xmsy.server.zxyy.manager.base.BaseDao;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysColumnEntity;

/**
 * 菜单管理
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年9月18日 上午9:33:01
 */
public interface SysColumnDao extends BaseDao<SysColumnEntity> {
	
	/**
	 * 根据父栏目，查询子栏目
	 * @param parentId 父菜单ID
	 */
	List<SysColumnEntity> queryListParentId(Long parentId);
	
	/**
	 * 获取栏目列表
	 */
	List<SysColumnEntity> queryNotButtonList();

}
