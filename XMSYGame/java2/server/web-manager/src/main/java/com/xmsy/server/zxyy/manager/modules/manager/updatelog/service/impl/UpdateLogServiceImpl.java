package com.xmsy.server.zxyy.manager.modules.manager.updatelog.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import com.xmsy.server.zxyy.manager.modules.manager.updatelog.dao.UpdateLogDao;
import com.xmsy.server.zxyy.manager.modules.manager.updatelog.entity.UpdateLogEntity;
import com.xmsy.server.zxyy.manager.modules.manager.updatelog.service.UpdateLogService;


@Service("updateLogService")
public class UpdateLogServiceImpl extends ServiceImpl<UpdateLogDao, UpdateLogEntity> implements UpdateLogService {


}
