package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForYaBaoQiangZhuangNiuNiu {

    public  static String readCard(String cardValues){
    	StringBuffer sb= new StringBuffer();
        char[] ar = cardValues.toCharArray();
        for(int i=0;i<4;i++) {
        	int j=i+1;
        	String cardTypeId1 = String.valueOf(ar[i*10]);
        	String cardId1 = String.valueOf(ar[i*10+1]);
        	String cardTypeId2 = String.valueOf(ar[i*10+2]);
        	String cardId2 = String.valueOf(ar[i*10+3]);
        	String cardTypeId3 = String.valueOf(ar[i*10+4]);
        	String cardId3 = String.valueOf(ar[i*10+5]);
        	String cardTypeId4 = String.valueOf(ar[i*10+6]);
        	String cardId4 = String.valueOf(ar[i*10+7]);
        	String cardTypeId5 = String.valueOf(ar[i*10+8]);
        	String cardId5 = String.valueOf(ar[i*10+9]);
        	if("0".equals(cardId1) || "0".equals(cardId2) || "0".equals(cardId3) || "0".equals(cardId4) || "0".equals(cardId5)) {
        		sb.append(j+"号玩家手牌：为空");
        	}else {
        		sb.append(j+"号玩家手牌：");
        		add(sb, cardTypeId1, cardId1);
        		sb.append("、");
        		add(sb, cardTypeId2, cardId2);
        		sb.append("、");
        		add(sb, cardTypeId3, cardId3);
        		sb.append("、");
        		add(sb, cardTypeId4, cardId4);
        		sb.append("、");
        		add(sb, cardTypeId5, cardId5);
        	}
        	sb.append("；");
        }
        return  sb.append("庄家为："+ar[ar.length-1]+"号位玩家").toString();
    }
    
    private static StringBuffer add(StringBuffer sb,String cardTypeId,String cardId) {
    	return sb.append(DzhpkEnum.CardTypeEnum.getCardTypeName(cardTypeId))
    			.append(DzhpkEnum.CardValueEnum.getCardValueName(cardId));
    }
}
