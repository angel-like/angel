package com.xmsy.server.zxyy.game.modules.manager.game.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.GameInfoEntity;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.param.GameParam;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.result.GameResult;
import com.xmsy.server.zxyy.game.modules.web.game.param.GameInfoSelectForRobotResult;
import com.xmsy.server.zxyy.game.modules.web.game.param.GameInfoSelectResult;
import com.xmsy.server.zxyy.game.modules.web.game.param.RoomInfoMenuResult;

/**
 *
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-31 14:51:26
 */
@Mapper
public interface GameInfoDao extends BaseMapper<GameInfoEntity> {
	/**
	 * 获取游戏列表分页---新版页面
	 * @param page
	 * @param gameParam
	 * @return
	 */
	List<GameResult> findGameInfosNew(Pagination page, @Param("gameParam") GameParam gameParam);
	/**
	 * 获取游戏列表分页
	 * @param page
	 * @param gameParam
	 * @return
	 */
	List<GameResult> findGameInfos(Pagination page, @Param("gameParam") GameParam gameParam);
	/**
	 * 获取游戏信息
	 * @param gameParam
	 * @return
	 */
	GameResult getGameInfos(@Param("gameParam") GameParam gameParam);
	/**
	 * 获取游戏列表
	 * @param gameParam
	 * @return
	 */
	List<GameResult> findGameInfos(@Param("gameParam") GameParam gameParam);
/**
	 * 获取游戏下拉
	 * 官网后台使用
	 */
	List<GameInfoSelectResult> gameSelect(@Param("roomId") Long roomId);
	/**
	 * 获取游戏下拉   任务使用
	 *
	 */
	List<GameInfoSelectResult> gameSelectForTask(@Param("roomId") Long roomId);
	/**
	 * 通过游戏ID获取该游戏下不同厅次    任务列表
	 *
	 */
	List<GameInfoSelectResult> gameSelectGradeIdForTask(@Param("gameId") Long gameId);

	/**
	 * 获取游戏下拉
	 * 机器人后台使用
	 */
	List<GameInfoSelectForRobotResult> gameSelectForRobot(@Param("roomId") Long roomId);


	/**
	 * 查询场次是否被调用
	 */
	List<GameInfoEntity>  queryconfig(@Param("gradeId") Long gradeId);

	List<RoomInfoMenuResult> gameMenu();
	
	/**
	 * 是否完成该游戏（状态改变）
	 * @param id
	 * @param finished
	 */
	void updateStatus(@Param("id") Long id,@Param("finished") Boolean finished);
	
	/**
	 * 是否维护该游戏（状态改变）
	 * @param id
	 * @param finished
	 */
	void updateMaintenance(@Param("id") Long id,@Param("maintenance") Boolean maintenance);
	
	
}
