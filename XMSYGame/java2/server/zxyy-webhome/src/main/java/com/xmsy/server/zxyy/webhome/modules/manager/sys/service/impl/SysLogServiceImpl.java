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

package com.xmsy.server.zxyy.webhome.modules.manager.sys.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.Query;
import com.xmsy.server.zxyy.webhome.modules.manager.sys.dao.SysLogDao;
import com.xmsy.server.zxyy.webhome.modules.manager.sys.entity.SysLogEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sys.service.SysLogService;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String username = (String)params.get("username");
        String operation = (String)params.get("operation");
        Page<SysLogEntity> page = this.selectPage(
            new Query<SysLogEntity>(params).getPage(),
            new EntityWrapper<SysLogEntity>()
//            .like(StringUtils.isNotBlank(key),"username", key)
            .eq(StringUtils.isNotBlank(username),"username", username)
            .eq(StringUtils.isNotBlank(operation),"operation", operation)
            .orderBy("create_date",false)
        );

        return new PageUtils(page);
    }
}
