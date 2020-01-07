package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForDzpk {

    public  static String readCard(String cardValues){
        StringBuffer sb= new StringBuffer();
        char[] ar = cardValues.toCharArray();

        for (int i = 0; i < 9; i++) {
            int j =i+1;
            String cardcolor1 = String.valueOf(ar[i*4]);
            String card1 =  String.valueOf(ar[i*4 + 1]);
            String cardcolor2 = String.valueOf(ar[i*4+2]);
            String card2 =  String.valueOf(ar[i*4 + 3]);
            if("0".equals(card1) || "0".equals(card2)){
                continue;
            }else{
                sb.append(j+"号位:");
                sb.append(DzhpkEnum.CardTypeEnum.getCardTypeName(cardcolor1));
                sb.append(DzhpkEnum.CardValueEnum.getCardValueName(card1));
                sb.append(DzhpkEnum.CardTypeEnum.getCardTypeName(cardcolor2));
                sb.append(DzhpkEnum.CardValueEnum.getCardValueName(card2));

            }
            sb.append(" ");
        }

        sb.append("公共牌：");
        for(int i =0 ;i<5 ;i++){
            String cardcolor = String.valueOf(ar[36+ i*2]);
            String card =  String.valueOf(ar[ i*2 + 37]);
            sb.append(DzhpkEnum.CardTypeEnum.getCardTypeName(cardcolor));
            sb.append(DzhpkEnum.CardValueEnum.getCardValueName(card));
        }
        return  sb.toString();
    }
}
