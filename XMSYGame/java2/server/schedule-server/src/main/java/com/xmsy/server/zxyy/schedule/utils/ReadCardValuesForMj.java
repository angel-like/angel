package com.xmsy.server.zxyy.schedule.utils;

public class ReadCardValuesForMj {

    public  static String readCard(String cardValues){
        StringBuffer sb= new StringBuffer();
        char[] ar = cardValues.toCharArray();
        int cardNum = ar.length / 2;
        for (int i = 0; i < cardNum; i++) {
            int j =i;
            String card = String.valueOf(ar[j*2]) + String.valueOf(ar[j*2 +1]);
            String cardValue = MjCardEnum.getCardValue(card);
            sb.append(cardValue);
        }
        return  sb.toString();
    }
}
