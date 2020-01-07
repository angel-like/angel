package com.xmsy.server.zxyy.schedule.jdbc;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class ResultSetTool {

	/**
	 * 将resultSet转化为实体类（实体字段全为String类型）
	 * 
	 * @param rs
	 * @param dto
	 * @return
	 * @throws Exception
	 */

	public static <T> T bindDataToDTO(ResultSet rs, T dto) throws Exception {
		// 取得Method方法
		Method[] methods = dto.getClass().getMethods();
		// 取得ResultSet的列名
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsCount = rsmd.getColumnCount();
		String[] columnNames = new String[columnsCount];
		for (int i = 0; i < columnsCount; i++) {
			columnNames[i] = rsmd.getColumnLabel(i + 1);
		}
		// 遍历ResultSet
		while (rs.next()) {
			// 反射, 从ResultSet绑定到JavaBean
			for (int i = 0; i < columnNames.length; i++) {
				// 取得Set方法
				String setMethodName = "set" + columnNames[i];
				// 遍历Method
				for (int j = 0; j < methods.length; j++) {
					if (!methods[j].getName().equalsIgnoreCase(setMethodName)) {
						continue;
					}
					setMethodName = methods[j].getName();
					Object value = rs.getObject(columnNames[i]);
					if (value == null) {
						continue;
					}
					// 实行Set方法
					try {
						// JavaBean内部属性和ResultSet中一致时候
						Method setMethod = dto.getClass().getMethod(setMethodName, value.getClass());
						setMethod.invoke(dto, value);
					} catch (Exception e) {
						// JavaBean内部属性和ResultSet中不一致时候，使用String来输入值。
						Method setMethod = dto.getClass().getMethod(setMethodName, String.class);
						setMethod.invoke(dto, value.toString());

					}

				}

			}

		}
		return dto;

	}

	/**
	 * 将resultSet转化为JSON数组
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @throws JSONException
	 */

	public static JSONArray resultSetToJsonArry(ResultSet rs) throws SQLException, JSONException {
		// json数组
		JSONArray array = new JSONArray();
		// 获取列数
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		// 遍历ResultSet中的每条数据
		while (rs.next()) {
			JSONObject jsonObj = new JSONObject();
			// 遍历每一列
			for (int i = 1; i <= columnCount; i++) {
				String columnName = metaData.getColumnLabel(i);
				String value = rs.getString(columnName);
				jsonObj.put(columnName, value);

			}
			array.add(jsonObj);
		}
		return array;

	}

	/**
	 * 将resultSet转化为JSONObject
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @throws JSONException
	 */
	public static JSONObject resultSetToJsonObject(ResultSet rs) throws SQLException, JSONException {
		// json对象
		JSONObject jsonObj = new JSONObject();
		// 获取列数
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		// 遍历ResultSet中的每条数据
		if (rs.next()) {
			// 遍历每一列
			for (int i = 1; i <= columnCount; i++) {
				String columnName = metaData.getColumnLabel(i);
				String value = rs.getString(columnName);
				jsonObj.put(columnName, value);
			}

		}
		return jsonObj;

	}

}