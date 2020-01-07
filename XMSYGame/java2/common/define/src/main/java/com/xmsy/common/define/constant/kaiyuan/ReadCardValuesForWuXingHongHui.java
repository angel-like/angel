package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForWuXingHongHui {
	
    public  static String readCard(String cardValues){
    	if(cardValues == null || "".equals(cardValues)) {
    		return "";
    	}
    	char[] chs = cardValues.toCharArray();
    	return "牌值为："+DzhpkEnum.CardTypeEnum.getCardTypeName(String.valueOf(chs[0]))+DzhpkEnum.CardValueEnum.getCardValueName(String.valueOf(chs[1]))+"，赢得位置为："+winPosition(cardValues.substring(2));
    }
    
    private static String winPosition(String winPosition) {
    	switch (winPosition) {
		case "01":
			return "黑桃";
		case "02":
			return "红桃";
		case "03":
			return "梅花";
		case "04":
			return "方块";
		default:
			return "皇冠";
		}
    }
}
