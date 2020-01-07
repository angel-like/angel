package com.xmsy.server.zxyy.game.modules.web.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.xmsy.server.zxyy.game.common.utils.R;
import com.xmsy.server.zxyy.game.modules.manager.game.dao.GameInfoDao;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.GameInfoEntity;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.param.GameParam;
import com.xmsy.server.zxyy.game.modules.manager.game.service.GameInfoService;
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
@RestController
@RequestMapping("web/gameInfo")
public class GameInfoHandler {

	@Autowired
	private GameInfoService infoService;
	@Autowired
	private GameInfoDao gameInfoDao;
	
	/**
	 * 列表
	 */
	@GetMapping("/list")
	public R list(GameInfoEntity info) {
		if (null != info.getId()) {
			return R.ok().putData("game", Lists.newArrayList(infoService.selectAll().get(info.getId())));
		}
		return R.ok().putData("game", infoService.selectAll().values());
	}
	
	/**
	 * 列表
	 */
	@GetMapping("/gameinfo")
	public R getGameUk(GameParam info) {
		if( info!=null) {
			if( info.getGameId()==null) {
				info.setGameId(0l);
			}
			if( info.getGradeId()==null) {
				info.setGradeId(0l);
			}
			if( info.getRoomId()==null) {
				info.setRoomId(0l);
			}
		}
		return R.ok().putData("game", infoService.findGameInfos(info));
	}
	
	/**
	 * 信息
	 */
	@GetMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		GameInfoEntity info = infoService.selectById(id);
		return R.ok().put("info", info);
	}
	
	/**
	 * 信息
	 */
	@GetMapping("/infoAll")
	public R infoAll() {
		return R.ok().put("gameinfo", infoService.findGameInfos(new GameParam()));
	}
	
	/**
	 * 获取场次信息列表
	 */
	@GetMapping("/grades")
	public R grade() {
		Map<Long, String> result = new HashMap<Long, String>();
		result.put(1L, "初级场");
		result.put(2L, "中级场");
		result.put(3L, "高级场");
		return R.ok().put("grades", result);
	}

	
	
	/**
	 * 游戏下拉
	 * 官网后台为App配置游戏
	 * 机器人游戏下拉
	 */
	@GetMapping("/gameSelect")
	public R gameSelect(@RequestParam(value="roomId",required=false)Long roomId) {
		List<GameInfoSelectResult> list=gameInfoDao.gameSelect(roomId);
		return R.ok().put("data", list);
	}
	/**
	 * 游戏下拉  任务列表
	 *
	 */
	@GetMapping("/gameSelectForTask")
	public R gameSelectForTask(@RequestParam(value="roomId",required=false)Long roomId) {
		List<GameInfoSelectResult> list=gameInfoDao.gameSelectForTask(roomId);
		return R.ok().put("data", list);
	}
	/**
	 * 通过游戏ID获取该游戏下不同厅次    任务列表
	 *
	 */
	@GetMapping("/gameSelectGradeIdForTask")
	public R gameSelectGradeIdForTask(@RequestParam(value="gameId",required=false)Long gameId) {
		List<GameInfoSelectResult> list=gameInfoDao.gameSelectGradeIdForTask(gameId);
		return R.ok().put("data", list);
	}
	/**
	 * 游戏下拉
	 * 机器人配置
	 */
	@GetMapping("/gameSelectForRobot")
	public R gameSelectForRobot(@RequestParam(value="roomId",required=false)Long roomId) {
		List<GameInfoSelectForRobotResult> list=gameInfoDao.gameSelectForRobot(roomId);
		return R.ok().put("data", list);
	}
	
	/**
	 * 游戏菜单
	 * 官网后台游戏菜单
	 */
	@GetMapping("/gameMenu")
	public R gameMenu() {
		List<RoomInfoMenuResult> list=gameInfoDao.gameMenu();
		return R.ok().put("data", list);
	}


}
