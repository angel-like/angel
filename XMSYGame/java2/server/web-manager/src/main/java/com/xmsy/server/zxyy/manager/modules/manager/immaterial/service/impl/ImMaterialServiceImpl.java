package com.xmsy.server.zxyy.manager.modules.manager.immaterial.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.manager.immaterial.dao.ImMaterialDao;
import com.xmsy.server.zxyy.manager.modules.manager.immaterial.entity.ImMaterialEntity;
import com.xmsy.server.zxyy.manager.modules.manager.immaterial.service.ImMaterialService;

@Service("imMaterialService")
public class ImMaterialServiceImpl extends ServiceImpl<ImMaterialDao, ImMaterialEntity> implements ImMaterialService {

}
