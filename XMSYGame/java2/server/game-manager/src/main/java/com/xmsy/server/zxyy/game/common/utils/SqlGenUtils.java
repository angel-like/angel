package com.xmsy.server.zxyy.game.common.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.xmsy.server.zxyy.game.modules.manager.game.entity.GameInfoEntity;
import com.xmsy.server.zxyy.game.modules.manager.gameconfig.entity.GameConfigEntity;

/**
 * 代码生成器 工具类
 *
 * @author axiong
 * @email xxxxxx
 * @date 2019年11月1日 下午4:40:24
 */
public class SqlGenUtils {

	public static List<String> getTemplates() {
		List<String> templates = new ArrayList<String>();
		templates.add("template/game.sql.vm");
		return templates;
	}

	/**
	 * 升成sql代码  模板数据
	 * @param gameInfo  游戏信息
	 * @param roomName  房间名称
	 * @param gameConfigList  游戏对应配置信息
	 * @param zip   压缩包
	 */
	public static String generatorCode(GameInfoEntity gameInfo,String roomName, List<GameConfigEntity> gameConfigList) {
		// 设置velocity资源加载器
		Properties prop = new Properties();
		prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init(prop);
		// 封装模板数据
		Map<String, Object> map = new HashMap<>();
		map.put("gameId", gameInfo.getGameId());
		map.put("gameName", gameInfo.getGameName());
		map.put("roomId", gameInfo.getRoomId());
		map.put("gradeId", gameInfo.getGradeId());
		map.put("enable", gameInfo.getEnable());
		map.put("display", gameInfo.getDisplay());
		map.put("sence", gameInfo.getSence());
		map.put("version", 0);
		map.put("rate", gameInfo.getRate());
		map.put("bscore", gameInfo.getBscore());
		map.put("restrict", gameInfo.getRestrict());
		map.put("maxTimes", gameInfo.getMaxTimes());
		map.put("maxlines", gameInfo.getMaxLines());
		map.put("deleteStatus", 0);
		map.put("maintenance", gameInfo.getMaintenance());
		map.put("limitHigh", gameInfo.getLimitHigh());
		map.put("limitLower", gameInfo.getLimitLower());
		map.put("onlineMin", gameInfo.getOnlineMin());
		map.put("onlineMax", gameInfo.getOnlineMax());
		map.put("finished", gameInfo.getFinished());
		map.put("provider", gameInfo.getProvider());
		//通过roomId去寻找房间名称
		map.put("roomName", roomName);
		//循环的gameConfigList列表的数据
		map.put("gameConfigList", gameConfigList);
		VelocityContext context = new VelocityContext(map);
		// 获取模板列表
		List<String> templates = getTemplates();
		for (String template : templates) {
			// 渲染模板
			StringWriter sw = new StringWriter();
			Template tpl = Velocity.getTemplate(template, "UTF-8");
			tpl.merge(context, sw);
			return sw.toString();
		}
		return null;
	}


	/**
	 * 获取文件名
	 */
	public static String getFileName(String template, String className, String packageName, String moduleName) {
		if (template.contains("game.sql.vm")) {
			return className.toLowerCase() + ".sql";
		}
		return null;
	}
}
