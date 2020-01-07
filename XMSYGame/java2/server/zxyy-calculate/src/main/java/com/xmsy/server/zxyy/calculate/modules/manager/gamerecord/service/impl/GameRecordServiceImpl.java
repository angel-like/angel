package com.xmsy.server.zxyy.calculate.modules.manager.gamerecord.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.calculate.modules.manager.gamerecord.dao.GameRecordDao;
import com.xmsy.server.zxyy.calculate.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.gamerecord.service.GameRecordService;

/**
 * .游戏记录实现类
 * 
 * @author Administrator
 *
 */
@Service("gameRecordService")
public class GameRecordServiceImpl extends ServiceImpl<GameRecordDao, GameRecordEntity> implements GameRecordService {
}
