package com.xmsy.server.zxyy.game.modules.manager.game.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.GameInfoEntity;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.param.GameParam;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.result.GameResult;
import com.xmsy.server.zxyy.game.modules.web.game.param.GameInfoResult;

/**
 *
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-31 14:51:26
 */
public interface GameInfoService extends IService<GameInfoEntity> {


	void deployRobotConfig();
	/**
	 * 获取游戏实体Map集
	 *
	 * @return
	 */
	Map<Long, GameResult> selectGameInfoList();

	/**
	 * 获取游戏
	 */
	Map<Long, GameInfoEntity> GameInfoList();

	/**
	 * 获取游戏封装对象
	 *
	 * @return
	 */
	Map<Long, GameInfoResult> selectAll();

	//根据游戏id精确的获取对应场次的游戏
	Map<Long, GameInfoResult> selectGameByIds(List<String> ids);
	/**
	 * 获取游戏列表--新版页面
	 * @param gameParam
	 * @param pageParam
	 * @return
	 */
	Page<GameResult> findGameInfosNew(GameParam gameParam, PageParam pageParam);
	/**
	 * 获取游戏列表
	 * @param gameParam
	 * @param pageParam
	 * @return
	 */
	Page<GameResult> findGameInfos(GameParam gameParam, PageParam pageParam);
	/**
	 * 获取游戏列表
	 * @param gameParam
	 * @return
	 */
	List<GameResult> findGameInfos(GameParam gameParam);
	List<GameResult> findGameInfosNotConfig();

	/**
	 * 删除游戏
	 * @param id
	 */
	void deleteGameInfo(Long id);

	/**
	 * 查询场次是否被调用
	 */
	List<GameInfoEntity> queryconfig(Long gradeId);

	/**
	 * 获取游戏信息
	 * @param gameParam
	 * @return
	 */
	GameResult getGameInfos(GameParam gameParam);
	
	/**
	 * 是否完成该游戏（状态改变）
	 * @param id
	 * @param finished
	 */
	void updateStatus(Long id,Boolean finished);
	
	/**
	 * 是否维护该游戏（状态改变）
	 * @param id
	 * @param finished
	 */
	void updateMaintenance(Long id,Boolean maintenance);

}
