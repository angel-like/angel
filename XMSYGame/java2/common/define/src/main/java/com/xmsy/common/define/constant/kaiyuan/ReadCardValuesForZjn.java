package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForZjn {

	public static String readCard(String cardValues) {
		StringBuffer sb = new StringBuffer();
		char[] ch = cardValues.toCharArray();
		for (int i = 0; i < 5; i++) {
			String cardColor1 = String.valueOf(ch[i*10]);
			String card1 = String.valueOf(ch[i*10+1]);
			String cardColor2 = String.valueOf(ch[i*10+2]);
			String card2 = String.valueOf(ch[i*10+3]);
			String cardColor3 = String.valueOf(ch[i*10+4]);
			String card3 = String.valueOf(ch[i*10+5]);
			String cardColor4 = String.valueOf(ch[i*10+6]);
			String card4 = String.valueOf(ch[i*10+7]);
			String cardColor5 = String.valueOf(ch[i*10+8]);
			String card5 = String.valueOf(ch[i*10+9]);
			int seatId = i + 1;
			if ("0".equals(card1) || "0".equals(card2) || "0".equals(card3)
					|| "0".equals(card4) || "0".equals(card5)) {
				sb.append(seatId+"号位无玩家");
			} else {
				sb.append(seatId + "号玩家手牌：");
				sb.append(ZjnEnum.CardTypeEnum.getcardTypeName(Integer.parseInt(cardColor1)));
				sb.append(ZjnEnum.CardValueEnum.getcardValue(card1));
				sb.append(ZjnEnum.CardTypeEnum.getcardTypeName(Integer.parseInt(cardColor2)));
				sb.append(ZjnEnum.CardValueEnum.getcardValue(card2));
				sb.append(ZjnEnum.CardTypeEnum.getcardTypeName(Integer.parseInt(cardColor3)));
				sb.append(ZjnEnum.CardValueEnum.getcardValue(card3));
				sb.append(ZjnEnum.CardTypeEnum.getcardTypeName(Integer.parseInt(cardColor4)));
				sb.append(ZjnEnum.CardValueEnum.getcardValue(card4));
				sb.append(ZjnEnum.CardTypeEnum.getcardTypeName(Integer.parseInt(cardColor5)));
				sb.append(ZjnEnum.CardValueEnum.getcardValue(card5));
			}
			sb.append(";");
		}
		
		return sb.toString();
	}
}
