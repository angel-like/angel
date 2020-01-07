package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordkaiyuan.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordkaiyuan.entity.GameRecordKaiyuanEntity;


/**
 * 开源游戏
 *
 * @author sanqian
 * @email xxxxx
 * @date 2019-12-02 14:25:01
 */
public interface GameRecordKaiyuanService extends IService<GameRecordKaiyuanEntity> {

    public boolean isExistGameRecord(String  gameId);


}

