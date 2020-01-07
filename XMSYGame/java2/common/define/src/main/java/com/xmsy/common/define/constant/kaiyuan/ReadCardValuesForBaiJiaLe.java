package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForBaiJiaLe {
    public  static String readCard(String cardValues){
        StringBuffer sb = new StringBuffer();
        char[] chs = cardValues.toCharArray();
        for(int i=0;i<2;i++) {
        	switch (i) {
			case 0:
				sb.append("闲牌：");
				break;
			default:
				sb.append("，庄牌：");
				break;
			}
        	String cardType1 = String.valueOf(chs[i*6]);
            String card1 =  String.valueOf(chs[i*6 + 1]);
            analyzeCard(sb, cardType1, card1);
            String cardType2 = String.valueOf(chs[i*6+2]);
            String card2 =  String.valueOf(chs[i*6 + 3]);
            analyzeCard(sb, cardType2, card2);
            if(chs[i*6+5]=='0') {
            	continue;
            }
            analyzeCard(sb, String.valueOf(chs[i*6+4]), String.valueOf(chs[i*6 + 5]));
        }
        sb.append("，胜利下注点为：");
        switch (chs[chs.length-1]) {
		case '1':
			sb.append("闲");
			break;
		case '2':
			sb.append("庄");
			break;
		case '3':
			sb.append("和");
			break;
		case '6':
			sb.append("庄对");
			break;
		case '7':
			sb.append("闲对");
			break;
		case '8':
			sb.append("大");
			break;
		default:
			sb.append("小");
			break;
		}
        return sb.toString();
    }
    /**
     * 解析牌值
     */
    public static StringBuffer analyzeCard(StringBuffer sb,String cardTypeId,String cardId) {
    	return sb.append(DzhpkEnum.CardTypeEnum.getCardTypeName(cardTypeId)).append(DzhpkEnum.CardValueEnum.getCardValueName(cardId));
    }
}
