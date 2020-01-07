package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForZjh {
	
	public static String readCard(String cardValues) {
		StringBuffer sb = new StringBuffer();
		char[] ch = cardValues.toCharArray();
		for(int i=0;i<5;i++) {
			int j=i+1;
			String cardColor1 = String.valueOf(ch[i*6]);
			String card1 = String.valueOf(ch[i*6+1]);
			String cardColor2 = String.valueOf(ch[i*6+2]);
			String card2 = String.valueOf(ch[i*6+3]);
			String cardColor3 = String.valueOf(ch[i*6+4]);
			String card3 = String.valueOf(ch[i*6+5]);
			if("0".equals(card1) || "0".equals(card2) || "0".equals(card3)){
				sb.append(j+"号位没有玩家;");
				 continue;
			}else {
				sb.append(j+"号位：");
				sb.append(ZjhEnum.CardTypeEnum.getcardTypeName(Integer.parseInt(cardColor1)));
				sb.append(ZjhEnum.CardValueEnum.getcardValue(card1));
				sb.append(ZjhEnum.CardTypeEnum.getcardTypeName(Integer.parseInt(cardColor2)));
				sb.append(ZjhEnum.CardValueEnum.getcardValue(card2));
				sb.append(ZjhEnum.CardTypeEnum.getcardTypeName(Integer.parseInt(cardColor3)));
				sb.append(ZjhEnum.CardValueEnum.getcardValue(card3));
				
			}
			sb.append(";"); 
		}
		sb.append("赢家：");
		String str = String.valueOf(ch[30]);
		sb.append(str);
		
		return sb.toString();
	}
}
