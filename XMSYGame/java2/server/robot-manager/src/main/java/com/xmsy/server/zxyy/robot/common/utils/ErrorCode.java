package com.xmsy.server.zxyy.robot.common.utils;

/**
 * 常量 错误码
 * 
 * @author xiaoliu
 * @email xxxxxx
 * @date 2016年11月15日 下午1:23:52
 */
public class ErrorCode {
	// 用户模块错误码
	public enum TokenCode {
		/**
		 * token验证失败 (拦截器)
		 */
		TOKEN_INVALID_LOSE(5100, "token已失效"),
		/**
		 * token为空 (拦截器)
		 */
		TOKEN_IS_NULL(5101, "token为空");
		
	
		private Integer code;
		private String errMsg;
	
		TokenCode(int code, String errMsg) {
			this.code = code;
			this.errMsg = errMsg;
		}
	
		public Integer getCode() {
			return code;
		}
	
		public String getErrMsg() {
			return errMsg;
		}

	}
	// 用户模块错误码
	public enum RobotCode {
		/**
		 * 该场次没有机器人
		 */
		ROBOT_IS_NULL(21100, "该场次没有机器人"),
		/**
		 * 登录取得大厅ip失败
		 * 
		 */
		USER_LOGIN_GET_HALL_IP_ERRO(5029, "登录取得大厅ip失败"),
		/**
		 * 未找到该机器人
		 */
		ROBOT_NULL_ERRO(21101, "未找到该机器人"),
		/**
		 * 身份信息验证失败
		 */
		ROBOT_TOKEN_ERRO(21102, "身份信息验证失败"),
		/**
		 * 机器人已被禁用
		 */
		ROBOT_ENABLE_ERRO(21103, "机器人已被禁用"),
		/**
		 * 机器人数量要大于0
		 */
		ROBOT_NUM_NULL(21104, "机器人数量要大于0"),
		/**
		 * 循环次数已达上限，强行停止该进程
		 */
		GET_ROBOT_ERRO(21105, "循环次数已达上限，强行停止该进程"),
		/**
		 * 强行取回机器人失败
		 */
		ROBOT_UPDATE_ERRO(21106, "强行取回机器人失败"),
		/**
		 * 机器人验证有误
		 */
		ROBOT_CHECK_ERRO(21107, "机器人验证有误"),
		/**
		 * 机器人修改失败
		 */
		ROBOT_TAKE_ERRO(21108, "机器人修改失败"),
		/**
		 * 游戏记录获取失败
		 */
		ROBOT_GAME_RECORD_ERRO(21109, "无机器人游戏记录");
	
		private Integer code;
		private String errMsg;
	
		RobotCode(int code, String errMsg) {
			this.code = code;
			this.errMsg = errMsg;
		}
	
		public Integer getCode() {
			return code;
		}
	
		public String getErrMsg() {
			return errMsg;
		}
	
	}
			
			

	

}
