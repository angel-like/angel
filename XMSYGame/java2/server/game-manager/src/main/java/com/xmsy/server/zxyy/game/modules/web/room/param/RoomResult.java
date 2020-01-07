package com.xmsy.server.zxyy.game.modules.web.room.param;

import java.io.Serializable;
import java.util.Set;

import com.google.common.collect.Sets;
import com.xmsy.server.zxyy.game.modules.web.game.param.GameInfoResult;

import lombok.Data;

/**
 * 游戏房间（百人,匹配等）
 * 
 * @author Administrator
 *
 */
@Data
public class RoomResult implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	/**
	 * 房间id
	 */
	private Long id;

	/**
	 * 房间名字
	 */
	private String name;

	/**
	 * 游戏集合
	 */
	Set<GameInfoResult> games = Sets.newHashSet();

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (obj instanceof RoomResult) {
			RoomResult o = (RoomResult) obj;
			return this.id == o.id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id.intValue();
	}

	@Override
	public RoomResult clone() throws CloneNotSupportedException {
		RoomResult obj = null;
		// 调用Object类的clone方法——浅拷贝
		try {
			obj = (RoomResult) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		Set<GameInfoResult> games = Sets.newHashSet();
		for (GameInfoResult game : obj.getGames()) {
			games.add(game.clone());
		}
		obj.setGames(games);
		return obj;
	}

}
