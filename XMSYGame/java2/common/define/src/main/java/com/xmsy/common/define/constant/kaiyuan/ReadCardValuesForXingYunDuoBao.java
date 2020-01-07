package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForXingYunDuoBao {

    public  static String readCard(String cardValues){
    	if(cardValues==null || "".equals(cardValues)) {
    		return "";
    	}
    	String[] str = cardValues.split("-");
    	int num = Integer.parseInt(str[2])/100;
    	return "获奖用户："+str[0]+"，中奖号码："+str[1]+"，"+num+"游戏币场次";
    }
    
}
