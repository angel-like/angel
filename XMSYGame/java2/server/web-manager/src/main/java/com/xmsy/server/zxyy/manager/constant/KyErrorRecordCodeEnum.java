package com.xmsy.server.zxyy.manager.constant;

public enum KyErrorRecordCodeEnum {
	
	CG(0,"成功"),
	TOKEN_DS(1,"TOKEN 丢失（重新调用登录接口获取）"),
	QDBCZ(2,"渠道不存在（请检查渠道 ID 是否正确）"),
	YZSJCS(3,"验证时间超时（请检查 timestamp 是否正确）"),
	YZCW(4,"验证错误"),
	QDBMDCW(5,"渠道白名单错误（请联系客服添加服务器白名单）"),
	YZZDDS(6,"验证字段丢失（请检查参数完整性）"),
	BCZDQQ(8,"不存在的请求（请检查子操作类型是否正确）"),
	QDYZCW(15,"渠道验证错误（1.MD5key 值是否正确；2.生成 key 值中的 timestamp 与\r\n" + 
			"参数中的是否一致；3. 生成 key 值中的 timestamp 与代理编号以字符串形式拼接）"),
	SJBCZ(16,"数据不存在（当前没有注单）"),
	ZHJY(20,"账号禁用"),
	AES_JMSB(22,"AES 解密失败"),
	QDLQSJCS(24,"渠道拉取数据超过时间范围"),
	DDHBCZ(26,"订单号不存在"),
	SJKYC(27,"数据库异常"),
	IP_JY(28,"ip 禁用"),
	DDHYDDGZBF(29,"订单号与订单规则不符"),
	HQWJZXZTSB(30,"获取玩家在线状态失败"),
	GXFSXYHDY(31,"更新的分数小于或者等于 0"),
	GXWJXXSB(32,"更新玩家信息失败"),
	GXWJJBSB(33,"更新玩家金币失败"),
	DDCF(34,"订单重复"),
	HQWJXXSB(35,"获取玩家信息失败（请调用登录接口创建账号）"),
	KINDID_BCZ(36,"KindID 不存在"),
	DLSJXFSB(37,"登录瞬间禁止下分，导致下分失败"),
	YEBZDZXFSB(38,"余额不足导致下分失败"),
	JZTYZH(39,"禁止同一账号登录带分、上分、下分并发请求，后一个请求被拒"),
	DCSXFSL(40,"单次上下分数量不能超过一千万"),
	LQDJHZTJ(41,"拉取对局汇总统计时间范围有误"),
	DLBJY(42,"代理被禁用"),
	LQGYPF(43,"拉单过于频繁(两次拉单时间间隔必须大于 5 秒)"),
	DDZZCLZ(44,"订单正在处理中"),
	CSCW(45,"参数错误"),
	ZCHYZHXTYC(1001,"注册会员账号系统异常"),
	DLSJEBZ(1002,"代理商金额不足");
	

	private final int errorCodeId;
	private final String errorCodeMsg;
	private KyErrorRecordCodeEnum(final int errorCodeId, String errorCodeMsg) {
		this.errorCodeId = errorCodeId;
		this.errorCodeMsg = errorCodeMsg;
	}
	public static String getKyKyErrorRecordCodeEnum(int errorCodeId) {
		KyErrorRecordCodeEnum[] kyErrorRecordCodeEnums = values();
		for (KyErrorRecordCodeEnum kyErrorRecordCodeEnum : kyErrorRecordCodeEnums) {
			if (kyErrorRecordCodeEnum.errorCodeId() == errorCodeId) {
				return kyErrorRecordCodeEnum.errorCodeMsg();
			}
		}
		return null;
	}
	public int  errorCodeId(){
		return this.errorCodeId;
	}

	public String errorCodeMsg(){
		return this.errorCodeMsg;
	}
}
