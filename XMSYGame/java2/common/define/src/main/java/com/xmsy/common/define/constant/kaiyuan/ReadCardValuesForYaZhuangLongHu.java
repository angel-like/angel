package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForYaZhuangLongHu {
	public static  String readCard(String cardValues) {
		StringBuffer sb = new StringBuffer();
		System.out.println("数值：" + cardValues);
		char[] ch = cardValues.toCharArray();
		String longCardColor = String.valueOf(ch[0]);
		String longCardValue = String.valueOf(ch[1]);
		sb.append("龙开牌：");
		sb.append(YzlhEnum.CardTypeEnum.getCardTypeName(Integer.parseInt(longCardColor)));
		sb.append(YzlhEnum.CardValueEnum.getCardValue(longCardValue));
		String huCardColor = String.valueOf(ch[2]);
		String huCardValue = String.valueOf(ch[3]);
		sb.append(" " + "虎开牌：");
		sb.append(YzlhEnum.CardTypeEnum.getCardTypeName(Integer.parseInt(huCardColor)));
		sb.append(YzlhEnum.CardValueEnum.getCardValue(huCardValue));
		sb.append(" " + "开奖区域：");
		for (int i = 2; i < ch.length/2; i++) {
			String areaValue = String.valueOf(ch[i*2])+String.valueOf(ch[i*2+1]);
			sb.append(YzlhEnum.PrizeAreaEnum.getPrizeArea(areaValue));
			if (i != ch.length/2 - 1) {
				sb.append(",");	
			}
		}
		
		return sb.toString();
	}
}
