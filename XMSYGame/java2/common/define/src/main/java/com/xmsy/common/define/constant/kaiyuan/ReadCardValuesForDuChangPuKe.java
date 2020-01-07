package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForDuChangPuKe {

    public  static String readCard(String cardValues){
    	StringBuffer sb= new StringBuffer();
        char[] ar = cardValues.toCharArray();
        sb.append("公共牌：");
        for(int i=0;i<5;i++) {
        	add(sb, String.valueOf(ar[i*2]), String.valueOf(ar[i*2+1]));
        	sb.append("、");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("；庄家手牌：");
        for(int i=0;i<2;i++) {
        	add(sb, String.valueOf(ar[i*2+10]), String.valueOf(ar[i*2+11]));
        	sb.append("、");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("；");
        for(int i=0;i<5;i++) {
        	int j=i+1;
        	String cardTypeId1 = String.valueOf(ar[i*4+14]);
        	String cardId1 = String.valueOf(ar[i*4+15]);
        	String cardTypeId2 = String.valueOf(ar[i*4+16]);
        	String cardId2 = String.valueOf(ar[i*4+17]);
        	if("0".equals(cardId1) || "0".equals(cardId2)) {
        		sb.append(j+"号玩家手牌：为空");
        	}else {
        		sb.append(j+"号玩家手牌：");
        		add(sb, cardTypeId1, cardId1);
        		sb.append("、");
        		add(sb, cardTypeId2, cardId2);
        	}
        	sb.append("；");
        }
        return  sb.deleteCharAt(sb.length()-1).toString();
    }
    
    private static StringBuffer add(StringBuffer sb,String cardTypeId,String cardId) {
    	return sb.append(DzhpkEnum.CardTypeEnum.getCardTypeName(cardTypeId)).append(DzhpkEnum.CardValueEnum.getCardValueName(cardId));
    }
    
}
