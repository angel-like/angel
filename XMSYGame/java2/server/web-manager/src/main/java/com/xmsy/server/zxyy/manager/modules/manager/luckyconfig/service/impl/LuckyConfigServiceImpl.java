package com.xmsy.server.zxyy.manager.modules.manager.luckyconfig.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import com.xmsy.server.zxyy.manager.modules.manager.luckyconfig.dao.LuckyConfigDao;
import com.xmsy.server.zxyy.manager.modules.manager.luckyconfig.entity.LuckyConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.luckyconfig.service.LuckyConfigService;

import java.util.List;


@Service("luckyConfigService")
public class LuckyConfigServiceImpl extends ServiceImpl<LuckyConfigDao, LuckyConfigEntity> implements LuckyConfigService {


    @Override
    public List<LuckyConfigEntity> getAllconfig(Long luckyId) {
        return this.baseMapper.getAllconfig(luckyId);
    }

    @Override
    public List<String> getNames(Long luckyId) {
        return this.baseMapper.getNames(luckyId);
    }

    @Override
    public List<LuckyConfigEntity> getSettings(Long luckyId) {
        return this.baseMapper.getSettings(luckyId);
    }

    @Override
    public void updateConfig(List<LuckyConfigEntity> configList) {
        for (LuckyConfigEntity luckyConfigEntity : configList) {
            this.baseMapper.deleteById(luckyConfigEntity);
        }
    }

    @Override
    public void updateConfigById(LuckyConfigEntity luckyConfigEntity) {
        this.baseMapper.updateConfigById(luckyConfigEntity);
    }

}
