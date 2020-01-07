package com.xmsy.server.zxyy.manager.modules.manager.gamerecorddezhoupuke.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecorddezhoupuke.entity.GameRecordDezhoupukeEntity;


/**
 * 游戏记录-德州扑克
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-11-22 17:21:28
 */
public interface GameRecordDezhoupukeService extends IService<GameRecordDezhoupukeEntity> {
	
	/**
	 * 查找同个局号所有玩家以及公共的牌型
	 * @param gameRoundNo
	 * @return
	 */
	List<GameRecordDezhoupukeEntity> findAllCardType(String gameRoundNo);

}

