package com.xmsy.server.zxyy.game.modules.manager.gamestock.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.game.modules.manager.gamestock.dao.GameStockDao;
import com.xmsy.server.zxyy.game.modules.manager.gamestock.entity.GameStockEntity;
import com.xmsy.server.zxyy.game.modules.manager.gamestock.service.GameStockService;

import java.util.List;

import org.springframework.stereotype.Service;


@Service("gameStockService")
public class GameStockServiceImpl extends ServiceImpl<GameStockDao, GameStockEntity> implements GameStockService {

	@Override
	public GameStockEntity getGameStock(String roomId) {
		return this.baseMapper.getGameStock(roomId);
	}

	@Override
	public void updateStock(GameStockEntity entity) {
		this.baseMapper.updateStock(entity);
	}

	@Override
	public List<GameStockEntity> getInfo() {
		return this.baseMapper.getInfo();
	}


}
