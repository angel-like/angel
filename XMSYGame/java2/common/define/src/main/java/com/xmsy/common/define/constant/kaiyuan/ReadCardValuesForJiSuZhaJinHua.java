package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForJiSuZhaJinHua {

    public  static String readCard(String cardValues){
        StringBuffer sb= new StringBuffer();
        char[] ar = cardValues.toCharArray();
        for (int i = 0; i < 9; i++) {
            int j =i+1;
            String cardType1 = String.valueOf(ar[i*6]);
            String card1 =  String.valueOf(ar[i*6 + 1]);
            String cardType2 = String.valueOf(ar[i*6+2]);
            String card2 =  String.valueOf(ar[i*6 + 3]);
            String cardType3 = String.valueOf(ar[i*6+4]);
            String card3 =  String.valueOf(ar[i*6 + 5]);
            if("0".equals(card1) || "0".equals(card2) || "0".equals(card3)){
            	sb.append(j+"号位没有玩家；");
                continue;
            }else{
                sb.append(j+"号位玩家手牌：");
                analyzeCard(sb, cardType1, card1);
                analyzeCard(sb, cardType2, card2);
                analyzeCard(sb, cardType3, card3);
                sb.append("；");
            }
        }
        sb.append("赢家是："+ar[ar.length-1]+"号位");
        return  sb.toString();
    }
    /**
     * 解析牌值
     */
    public static StringBuffer analyzeCard(StringBuffer sb,String cardTypeId,String cardId) {
    	return sb.append(DzhpkEnum.CardTypeEnum.getCardTypeName(cardTypeId)).append(DzhpkEnum.CardValueEnum.getCardValueName(cardId));
    }
}
