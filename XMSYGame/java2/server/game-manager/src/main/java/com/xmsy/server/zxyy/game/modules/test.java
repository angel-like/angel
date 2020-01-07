package com.xmsy.server.zxyy.game.modules;

import com.xmsy.server.zxyy.game.common.utils.Constant;

public class test {
	
 
		
public static void main(String[] args) throws Exception {
		
		
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//		Date d1=sdf.parse("2018-12-27 15:50:33");
//		Date d3=new Date();
//		
////		System.out.println("d1在当前时间之后？"+d1.after(new Date()));
////		System.out.println("d1在当前时间之前？"+d1.before(new Date()));
////		System.out.println("d1等于当前时间？"+d1.equals(new Date()));
//	Double ass=0.1;
//	Double b=0.06;
//	BigDecimal a=new BigDecimal(ass.toString());
		System.out.println(Constant.RechargeType.BANK.getValue());
////		System.out.println(new BigDecimal(0.0332222).floatValue());
//		IpUtil.getAddress();
//		//System.out.println(IpUtil.getAddress());
		
//		String cardNum="a3456";
//		String regex="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$";
//	    Matcher m=Pattern.compile(regex).matcher(cardNum);
//	    System.out.println(m.matches());
	    //System.out.println(Dictionary.BANK.getValue());
		String content = "用户打码返佣:来自会员[%s]金币 :%s, 请查收";
		System.out.println(String.format(content,"sdfdsf", 100));
	}
}
