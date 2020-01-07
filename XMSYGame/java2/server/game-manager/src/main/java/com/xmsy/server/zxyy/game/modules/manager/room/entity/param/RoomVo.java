package com.xmsy.server.zxyy.game.modules.manager.room.entity.param;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.xmsy.server.zxyy.game.common.utils.SpringUtil;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.result.GameResult;
import com.xmsy.server.zxyy.game.modules.manager.game.service.GameInfoService;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RoomVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Long id;
	/**
	 * 房间名称
	 */
	private String name;
	/**
	 * 是否可用
	 */
	private Boolean enable;
	/**
	 * 是否显示
	 */
	private Boolean display;
	/**
	 * 游戏id数组
	 */
	private String gameIds;
	/**
	 * 游戏集合
	 */
	@TableField(exist = false)
	private String gameArray;
	@TableField(exist = false)
	private List<List<String>> gameArrays;

	public String getGameArray() {
		String gameIds = this.gameIds;
		this.gameArrays=new ArrayList<>();
		if (StringUtils.isEmpty(gameIds)) {
			return gameIds;
		}
		
		String[] gameArray = gameIds.split(",");
		List<GameResult>  gameInfoMap = SpringUtil.getApplicationContext().getBean(GameInfoService.class).findGameInfosNotConfig();
		StringBuffer sb = new StringBuffer();
		List<String> gameStr=new ArrayList<>();
		Long oldGameId=null;
		for(GameResult gameResult : gameInfoMap){
			if(Arrays.asList(gameArray).contains(gameResult.getId().toString())){
				sb.append(gameResult.getGameName() + "-"+ gameResult.getGradeName());
				sb.append(",");
				if(oldGameId!=null && !oldGameId.equals(gameResult.getGameId())) {
					this.gameArrays.add(gameStr);
					gameStr=new ArrayList<>();
				}
				oldGameId=gameResult.getGameId();
				gameStr.add(gameResult.getGameName() + "-"+ gameResult.getGradeName());
			}
		}
		if(!gameStr.isEmpty()) {
			this.gameArrays.add(gameStr);
		}
//		Map<Long, GameResult> gameInfoMap = SpringUtil.getApplicationContext().getBean(GameInfoService.class)
//				.selectGameInfoList();
//		for (int i = 0; i < gameArray.length; i++) {
//			gameId = gameArray[i];
//			if (null == gameInfoMap.get(Long.valueOf(gameId))) {
//				continue;
//			}
//			GameResult gameResult = gameInfoMap.get(Long.valueOf(gameId));
//			gameArray[i] = gameResult.getGameName() + "-"
//					+ gameResult.getGradeName();
//			gameResult.getGameId()
//		}
//		return Joiner.on(",").join(gameArray);
		return sb.toString();
	}
	
//	public List getGameArrays() {
//		String gameIds = this.gameIds;
//		if (StringUtils.isEmpty(gameIds)) {
//			return gameIds;
//		}
//		String[] gameArray = gameIds.split(",");
//		String gameId = "";
//		Map<Long, GameResult> gameInfoMap = SpringUtil.getApplicationContext().getBean(GameInfoService.class)
//				.selectGameInfoList();
//		for (int i = 0; i < gameArray.length; i++) {
//			gameId = gameArray[i];
//			if (null == gameInfoMap.get(Long.valueOf(gameId))) {
//				continue;
//			}
//			GameResult gameResult = gameInfoMap.get(Long.valueOf(gameId));
//			gameArray[i] = gameResult.getGameName() + "-"
//					+ gameResult.getGradeName();
//		}
//		return Joiner.on(",").join(gameArray);
//	}

}
