package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForWrzjh {
	public static String readCard(String cardValues) {
		StringBuffer sb = new StringBuffer();
		char[] ch = cardValues.toCharArray();
		for (int i = 0; i < 5; i++) {
			String area = String.valueOf(ch[i*7]);
			String cardColor1 = String.valueOf(ch[i*7+1]);
			String card1 = String.valueOf(ch[i*7+2]);
			String cardColor2 = String.valueOf(ch[i*7+3]);
			String card2 = String.valueOf(ch[i*7+4]);
			String cardColor3 = String.valueOf(ch[i*7+5]);
			String card3 = String.valueOf(ch[i*7+6]);
			String areaName = WrzjhEnum.AreaNameEnum.getAreaName(Integer.parseInt(area));
			sb.append(areaName+"号手牌为:");
			sb.append(WrzjhEnum.CardTypeEnum.getcardTypeName(Integer.parseInt(cardColor1)));
			sb.append(WrzjhEnum.CardValueEnum.getcardValue(card1));
			sb.append(WrzjhEnum.CardTypeEnum.getcardTypeName(Integer.parseInt(cardColor2)));
			sb.append(WrzjhEnum.CardValueEnum.getcardValue(card2));
			sb.append(WrzjhEnum.CardTypeEnum.getcardTypeName(Integer.parseInt(cardColor3)));
			sb.append(WrzjhEnum.CardValueEnum.getcardValue(card3));
			sb.append(";");
		}
		sb.append("获胜区域：");
		System.out.println("数值:"+cardValues);
		for (int i = 0; i < (ch.length-34)/2; i++) {
			String winArea = WrzjhEnum.AreaNameEnum.getAreaName(Integer.parseInt(String.valueOf(ch[i*2+36])));
			sb.append(winArea);
			if(i != (ch.length-34)/2-1) {
				sb.append("、");
			}
		}
		
		return sb.toString();
	}
}
