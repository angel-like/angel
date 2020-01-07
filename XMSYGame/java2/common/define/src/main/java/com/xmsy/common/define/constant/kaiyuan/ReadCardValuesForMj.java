package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForMj {

    public  static String readCard(String cardValues){
    	String str[]=cardValues.split(";");
        StringBuffer sb= new StringBuffer();
        //2;0202030404060707080914161725,030304040505070708080909121228,0
        //座位号;第一位牌型,第二位牌型,0
        String cardValuesStr[]=str[1].split(";");
      //0202030404060707080914161725,030304040505070708080909121228,0
        //第一位牌型,第二位牌型,0
        char[] ar = cardValuesStr[0].toCharArray();
        int cardNum = ar.length / 2;
        for (int i = 0; i < cardNum; i++) {
            int j =i;
            String card = String.valueOf(ar[j*2]) + String.valueOf(ar[j*2 +1]);
            String cardValue = MjCardEnum.getCardValue(card);
            sb.append(cardValue!=null?cardValue:"");
        }
        if(!"".equals(sb.toString())) {
        	return  str[1];
        }
        
        String cardValues1=sb.toString();
        char[] ar1 = cardValuesStr[1].toCharArray();
        cardNum = ar1.length / 2;
        sb= new StringBuffer();
        for (int i = 0; i < cardNum; i++) {
            int j =i;
            String card = String.valueOf(ar1[j*2]) + String.valueOf(ar1[j*2 +1]);
            String cardValue = MjCardEnum.getCardValue(card);
            sb.append(cardValue!=null?cardValue:"");
        }
        String cardValues2=sb.toString();
        if ("1".equals(str[0])) {
			cardValues1 = "本方:" + cardValues1;
			cardValues2 = "对方:" + cardValues2;
		} else {
			cardValues2 = "本方:" + cardValues2;
			cardValues1 = "对方:" + cardValues1;
		}
        return  cardValues1 + "," + cardValues2;
    }
}
