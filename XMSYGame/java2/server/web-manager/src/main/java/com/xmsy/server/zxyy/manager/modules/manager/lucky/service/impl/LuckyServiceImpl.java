package com.xmsy.server.zxyy.manager.modules.manager.lucky.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.manager.modules.manager.luckyconfig.dao.LuckyConfigDao;
import com.xmsy.server.zxyy.manager.modules.manager.luckyconfig.entity.LuckyConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import com.xmsy.server.zxyy.manager.modules.manager.lucky.dao.LuckyDao;
import com.xmsy.server.zxyy.manager.modules.manager.lucky.entity.LuckyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.lucky.service.LuckyService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("luckyService")
public class LuckyServiceImpl extends ServiceImpl<LuckyDao, LuckyEntity> implements LuckyService {
    @Autowired
    LuckyConfigDao luckyConfigDao;



    @Override
    public  List<Map<String, Object>> getLuckySettings() {
        List<LuckyEntity> luckyEntities = this.baseMapper.selectList(new EntityWrapper<>(new LuckyEntity().setEnable(true)));
        List<Map<String, Object>> configList = new ArrayList<>();
        for (LuckyEntity luckyEntity : luckyEntities) {
            List<LuckyConfigEntity> configs = luckyConfigDao.getSettings(luckyEntity.getId());
            Map<String, Object> map = new HashMap<>();
            map.put("id",luckyEntity.getId());
            map.put("name",luckyEntity.getName());
            map.put("integral",luckyEntity.getIntegral());
            map.put("settings",configs);
            configList.add(map);
        }
        return configList;
    }
}
