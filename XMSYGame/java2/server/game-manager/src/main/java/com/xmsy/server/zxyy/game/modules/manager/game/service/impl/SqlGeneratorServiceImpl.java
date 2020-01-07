package com.xmsy.server.zxyy.game.modules.manager.game.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.game.common.utils.SqlGenUtils;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.GameInfoEntity;
import com.xmsy.server.zxyy.game.modules.manager.game.service.GameInfoService;
import com.xmsy.server.zxyy.game.modules.manager.game.service.SqlGeneratorService;
import com.xmsy.server.zxyy.game.modules.manager.gameconfig.entity.GameConfigEntity;
import com.xmsy.server.zxyy.game.modules.manager.gameconfig.service.GameConfigService;
import com.xmsy.server.zxyy.game.modules.manager.room.entity.RoomEntity;
import com.xmsy.server.zxyy.game.modules.manager.room.service.RoomService;

/**
 * sql代码生成器
 * 
 * @author axiong
 * @email xxxxxx
 * @date 2019年11月11日 下午3:33:38
 */
@Service("sqlGeneratorService")
public class SqlGeneratorServiceImpl implements SqlGeneratorService {
	
	@Autowired
	private GameInfoService gameInfoService;
	@Autowired
	private GameConfigService gameConfigService;
	@Autowired
	private RoomService roomService;


	@Override
	public String generatorCode(Long[] ids) {
		String s="";
		for(Long id:ids) {
			// 1.通过id获取到游戏信息
			GameInfoEntity gameInfo = gameInfoService.selectById(id);
			RoomEntity room = roomService.selectById(gameInfo.getRoomId());
			// 2.通过id 作为游戏配置的gameID 获取到对应的游戏配置
			List<GameConfigEntity> GameConfigList = gameConfigService
					.selectList(new EntityWrapper<GameConfigEntity>(new GameConfigEntity().setGameId(id)));
			//3.生成代码
			String a = SqlGenUtils.generatorCode(gameInfo,room.getName(), GameConfigList);
			s+=a;
		}
		return s;

	}

}
