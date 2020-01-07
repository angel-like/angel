package com.xmsy.server.zxyy.webhome.constant;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 系统类型
 * 
 * @author Administrator
 *
 */
public class SysType {

	// 大厅类型
	public enum HallType {

		ALL(0, "所有大厅"), MAIN(1, "主大厅"), ROOM_CARD(2, "房卡大厅");

		private Integer id;
		private String name;
		private static final Map<Integer, String> hallTypeMap = Maps.newConcurrentMap();

		HallType(Integer id, String name) {
			this.id = id;
			this.name = name;
		}

		static {
			for (HallType hallType : HallType.values()) {
				hallTypeMap.put(hallType.getId(), hallType.getName());
			}
		}

		public Integer getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public static Map<Integer, String> getHalltypemap() {
			return hallTypeMap;
		}
	}
}
