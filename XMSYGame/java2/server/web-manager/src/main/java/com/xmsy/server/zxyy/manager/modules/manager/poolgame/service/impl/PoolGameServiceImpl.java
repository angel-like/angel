package com.xmsy.server.zxyy.manager.modules.manager.poolgame.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.Query;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.dao.GameRecordDao;
import com.xmsy.server.zxyy.manager.modules.manager.poolgame.dao.PoolGameDao;
import com.xmsy.server.zxyy.manager.modules.manager.poolgame.entity.PoolGameEntity;
import com.xmsy.server.zxyy.manager.modules.manager.poolgame.service.PoolGameService;

@Transactional
@Service("poolGameService")
public class PoolGameServiceImpl extends ServiceImpl<PoolGameDao, PoolGameEntity> implements PoolGameService {
	
	@Autowired
	private GameRecordDao gameRecordDao;
	
	@Autowired
    private GameInfoComponent gameInfo;
    
	@Override
    public PageUtils queryPage(Map<String, Object> params) {
        PoolGameEntity entity=new PoolGameEntity();
        Long gameId=null;
    	if(params !=null) {
    		if(params.get("gameId")!=null && !"".equals(params.get("gameId").toString().trim())) {
    			gameId=Long.parseLong(params.get("gameId").toString().trim());
    			entity.setGameId(gameId);
    		}
    	}
    	Wrapper<PoolGameEntity> wrapper = new EntityWrapper<PoolGameEntity>(entity);
    	String sort=params.get("sort")!=null?params.get("sort").toString():"";
    	if (!StringUtils.isEmpty(sort)) {
    		String direction=params.get("direction")!=null?params.get("direction").toString():"";
    		if ("desc".equalsIgnoreCase(direction)) {
    			wrapper.orderDesc(Arrays.asList(sort.split(",")));
    		} else {
    			wrapper.orderAsc(Arrays.asList(sort.split(",")));
    		}
		}
    	Page<PoolGameEntity> page = this.selectPage(
                new Query<PoolGameEntity>(params).getPage(),
                wrapper
        );
    	if(page.getTotal()>0) {
    		List<PoolGameEntity> poolGameList= page.getRecords();
//    		Map<Long, String> gameMap=new HashMap<>();
//    		if(gameId!=null) {
//    			gameMap.put(gameId, gameInfoDao.selectById(gameId).getName());
//    		}else {
//    			Set<Long> idList=new HashSet<>();
//    			for(PoolGameEntity poolGame:poolGameList) {
//    				idList.add(poolGame.getGameId());
//    			}
//    			List<GameInfoEntity> gameList=gameInfoDao.findListByIds(idList);
//    			if(gameList!=null && !gameList.isEmpty()) {
//    				for(GameInfoEntity game:gameList) {
//        				gameMap.put(game.getId(), game.getName());
//        			}
//    			}
//    		}
    		Map<Long, String> gameMap = gameInfo.getGameMap();//SysConstant.gameMap
    		for(PoolGameEntity poolGame:poolGameList) {
    			poolGame.setGameName(gameMap.get(poolGame.getGameId()));
			}
    		
    	}
        return new PageUtils(page);
    }

	@Override
	public List<Map<String, Object>> findPoolList() {
		return this.baseMapper.findPoolList();
	}

	@Override
	public BigDecimal sumAllPool() {
		return this.baseMapper.sumAllPool();
	}

	@Override
	public void updatePoolByGameIdForStatistics(PoolGameEntity poolGame, String startDate, String endDate) {
		Long validBet = gameRecordDao.getGameValidBet(poolGame.getGameId(), startDate, endDate);
		if(validBet!=null && validBet>0) {
			this.baseMapper.updatePool(poolGame.getId(),
					new BigDecimal(validBet).multiply(poolGame.getBetRate())
					.setScale(2,BigDecimal.ROUND_HALF_UP));
		}
	}

}
