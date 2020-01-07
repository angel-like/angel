package com.xmsy.server.zxyy.game.modules.manager.gameconfig.entity;



import java.util.Map;

import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.game.base.BaseEntity;
import com.xmsy.server.zxyy.game.common.utils.SpringUtil;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.result.GameResult;
import com.xmsy.server.zxyy.game.modules.manager.game.service.GameInfoService;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-26 11:40:23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("game_config")
public class GameConfigEntity extends BaseEntity<GameConfigEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 游戏id
	 */
	private Long gameId;
	/**
	 * 属性名称
	 */
	@NotEmpty(message = "配置项名称不能为空")
	private String name;
	/**
	 * 开始值
	 */
	private Long startVal;
	/**
	 * 结束值
	 */
	private Long endVal;
	/**
	 * 游戏概率
	 */
	//@Min(value = 0, message = "游戏概率必须大于0")
	@NotBlank(message = "val值不能为空")
	private String val;
	/**
	 * 属性描述
	 */
	private String description;
	
	/**
	 * 获取游戏
	 */
	@TableField(exist = false)
	private String  gameArray;
	
	@TableField(exist = false)
	private Boolean  interval;
	
	@Override
	public GameConfigEntity clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (GameConfigEntity) super.clone();
	}
	
	public String getGameArray() {
//		String gameIds = this.gameId.toString();
//		if (StringUtils.isEmpty(gameIds)) {
//			return gameIds;
//		}
//		String[] gameIdArray = gameIds.split(",");
//		String gameId = "";
//		Map<Long, GameInfoEntity> roomMap = SpringUtil.getApplicationContext().getBean(GameInfoService.class).GameInfoList();
		Map<Long, GameResult> gameInfoMap = SpringUtil.getApplicationContext().getBean(GameInfoService.class).selectGameInfoList();
//		for (int i = 0; i < gameIdArray.length; i++) {
//			gameId = gameIdArray[i];
//			gameIdArray[i] = roomMap.get(Long.valueOf(gameId)).getGameName() + "-"
//					+ gameInfoMap.get(Long.valueOf(gameId)).getGradeName();
//		}
//		return Joiner.on(",").join(gameIdArray);
		String gameName="";
		if(gameInfoMap!=null && gameInfoMap.get(this.gameId)!=null) {
			gameName=gameInfoMap.get(this.gameId).getGameName() + "-"+gameInfoMap.get(this.gameId).getGradeName();
		}
		return gameName;
		
	}
}
