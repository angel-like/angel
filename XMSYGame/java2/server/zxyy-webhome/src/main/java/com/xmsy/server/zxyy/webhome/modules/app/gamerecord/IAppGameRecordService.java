package com.xmsy.server.zxyy.webhome.modules.app.gamerecord;

import java.util.List;

import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;

/**
 * 游戏记录业务逻辑
 * 
 * @author Administrator
 *
 */
public interface IAppGameRecordService {

	void saveRecord(String gameRecordParam) throws Exception;
	
	List<GameRecordFindCardType> findCardType(String gameRoundNo,Integer round) throws Exception;

}
