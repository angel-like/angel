package com.xmsy.server.zxyy.webhome.modules.manager.imdownloadmanage.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.utils.CreateQRCode;
import com.xmsy.server.zxyy.webhome.modules.manager.imdownloadmanage.dao.ImDownloadManageDao;
import com.xmsy.server.zxyy.webhome.modules.manager.imdownloadmanage.entity.ImDownloadManageEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.imdownloadmanage.service.ImDownloadManageService;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImDownloadManageResult;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImDownloadResult;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("imDownloadManageService")
public class ImDownloadManageServiceImpl extends ServiceImpl<ImDownloadManageDao, ImDownloadManageEntity>
        implements ImDownloadManageService {

    @Override
    public List<ImDownloadManageResult> selectListForType(String type) {
        List<ImDownloadManageResult> list = baseMapper.selectListForType(type);
        if (type == "PC") {
            return list;
        }
        if (CollectionUtils.isEmpty(list)) {
            return list;
        }
        for (ImDownloadManageResult entity : list) {
            if (!StringUtils.isEmpty(entity.getDownloadUrl())) {
                String qrcode = CreateQRCode.create(entity.getDownloadUrl());
                entity.setQrCode(qrcode);
            }
        }
        return list;
    }

    @Override
    public List<ImDownloadResult> selectImDownloadList(String type) {
        return baseMapper.selectImDownloadList(type);
    }

}
