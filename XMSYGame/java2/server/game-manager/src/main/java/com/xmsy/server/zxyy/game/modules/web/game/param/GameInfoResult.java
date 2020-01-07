package com.xmsy.server.zxyy.game.modules.web.game.param;

import java.io.Serializable;
import java.util.Set;

import com.google.common.collect.Sets;

import lombok.Data;

/**
 * 
 * 游戏信息对象
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-31 14:51:26
 */
@Data
@SuppressWarnings("unused")
public class GameInfoResult implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	/**
	 * 游戏id
	 */
	private Long id;
	/**
	 * 房间id
	 */
	private Long roomId;
	
	/**
	 * 游戏名字
	 */
	private String name;

	/**
	 * 是否显示
	 */
	private Boolean display;
	/**
	 * 是否启用
	 */
	private Boolean enable;

	private Long provider;


	/**
	 * 是否维护中
	 */
	private Boolean maintenance;
	/**
	 * 游戏场次集合
	 */
	Set<GameGradeResult> grades = Sets.newHashSet();

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (obj instanceof GameInfoResult) {
			GameInfoResult o = (GameInfoResult) obj;
			return this.id == o.id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id.intValue();
	}

	@Override
	public GameInfoResult clone() throws CloneNotSupportedException {
		GameInfoResult obj = null;
		// 调用Object类的clone方法——浅拷贝
		try {
			obj = (GameInfoResult) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		Set<GameGradeResult> grades = Sets.newHashSet();
		for (GameGradeResult grade : obj.getGrades()) {
			grades.add(grade.clone());
		}
		obj.setGrades(grades);
		return obj;
	}

	// 全部场次不显示则游戏不显示
	public Boolean getDisplay() {
		int size = 0;
		for (GameGradeResult grade : grades) {
			if (!grade.getDisplay()) {
				size++;
			}
		}
		if (size == grades.size()) {
			return false;
		}
		return true;
	}

	// 全部场次被禁用则游戏被禁用
	public Boolean getEnable() {
		int size = 0;
		for (GameGradeResult grade : grades) {
			if (!grade.getEnable()) {
				size++;
			}
		}
		if (size == grades.size()) {
			return false;
		}
		return true;
	}

	// 全部场次维护中则游戏维护中
	public Boolean getMaintenance() {
		int size = 0;
		for (GameGradeResult grade : grades) {
			if (!grade.getMaintenance()) {
				size++;
			}
		}
		if (size == grades.size()) {
			return false;
		}
		return true;
	}

}
