package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordkaiyuan.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordkaiyuan.dao.GameRecordKaiyuanDao;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordkaiyuan.entity.GameRecordKaiyuanEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordkaiyuan.service.GameRecordKaiyuanService;

import java.util.List;


@Service("gameRecordKaiyuanService")
public class GameRecordKaiyuanServiceImpl extends ServiceImpl<GameRecordKaiyuanDao, GameRecordKaiyuanEntity> implements GameRecordKaiyuanService {


    @Override
    public boolean isExistGameRecord(String gameId) {

        List<GameRecordKaiyuanEntity> list = this.baseMapper.findRecordListByGameId(gameId);
        if (list != null && list.size() >0){
            return  true;
        }
        return false;
    }
}
