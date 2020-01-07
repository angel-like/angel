package com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.dao.FortuneActiviConfigDao;
import com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.entity.FortuneActiviConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.service.FortuneActiviConfigService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("fortuneActiviConfigService")
public class FortuneActiviConfigServiceImpl extends ServiceImpl<FortuneActiviConfigDao, FortuneActiviConfigEntity> implements FortuneActiviConfigService {
    @Override
    public Map<String,String> getMap() {
        List<Map<String, Object>> list = this.baseMapper.getMap();
        Map<String, String> hashMap = new HashMap<>();
        for (Map<String, Object> map : list) {
            String id = map.get("id").toString();
            String activityName = map.get("activityName").toString();
            hashMap.put(id,activityName);
        }
        return hashMap;
    }



}
