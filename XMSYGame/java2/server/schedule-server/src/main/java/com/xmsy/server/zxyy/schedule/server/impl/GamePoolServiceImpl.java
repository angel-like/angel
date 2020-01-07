package com.xmsy.server.zxyy.schedule.server.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.jdbc.WebHomeJdbcUtil;
import com.xmsy.server.zxyy.schedule.server.GamePoolService;

@Service("gamePoolService")
public class GamePoolServiceImpl implements GamePoolService {
	@Autowired
	private WebHomeJdbcUtil jdbcUtil;

	@Override
	public JSONArray findGamePoolList() throws Exception {
		StringBuilder sqlSB=new StringBuilder();
		sqlSB.append(" SELECT id,game_id gameId,bet_rate betRrate,pool from pool_game");
		sqlSB.append(" where delete_status=0 and `enable`=1 and (count_time IS NULL OR  count_time<curdate()) ");
		return jdbcUtil.selectByParamReturnJsonArray(sqlSB.toString());
	}


	@Override
	public void updatePoolByGameIdForStatistics(JSONObject poolGame, String startDate, String endDate) throws Exception {
		if(poolGame==null || poolGame.get("id")==null ) {
			return ;
		}
		StringBuilder gameBetSql=new StringBuilder();
		gameBetSql.append(" SELECT IFNULL(sum(valid_bet), 0) validBet ");
		gameBetSql.append(" FROM game_record");
		gameBetSql.append(" where robot=0 and gm_user=0 and game_id=?  ");
		gameBetSql.append(" and create_time >=?  ");
		gameBetSql.append(" and create_time <=?  ");
		JSONObject gameBet = jdbcUtil.selectByParamReturnJsonObject
				(gameBetSql.toString(), poolGame.getLong("gameId"),startDate,endDate);
		if(gameBet!=null && gameBet.getBigDecimal("validBet")!=null) {
			StringBuilder updatePoolSql=new StringBuilder();
			updatePoolSql.append(" update pool_game set pool=pool+ ?,");
			updatePoolSql.append(" update_time=now(),count_time=now()");
			updatePoolSql.append(" where id=?");
			BigDecimal pool = gameBet.getBigDecimal("validBet").
					multiply(poolGame.getBigDecimal("betRrate"))
					.setScale(2,BigDecimal.ROUND_HALF_UP);
			jdbcUtil.update(updatePoolSql.toString(),
					pool,poolGame.getLong("id"));
		}
		
	}

}
