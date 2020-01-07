package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForTbnn {
    public static String readCard(String cardValues){
        if(cardValues == "" || cardValues.length() == 0){
            return "牌型错误";
        }
        StringBuffer sb = new StringBuffer();
        String[] split = cardValues.split("");
        for (int i = 1; i <= split.length / 10; i++) {
            sb.append(i+"号位：");
            String cards = cardValues.substring(i*10 - 10 , i * 10);
            if(cards.equals("0000000000")){
                sb.append("没有玩家"+" ");
            }else{
                String[] Card = cards.split("");
                for (int j = 0; j < cards.length() / 2 ; j++) {
                    String cardColor = Card[2 * j];
                    String card = Card[2 * j + 1 ];
                    sb.append(DzhpkEnum.CardTypeEnum.getCardTypeName(cardColor));
                    sb.append(DzhpkEnum.CardValueEnum.getCardValueName(card));
                }
                sb.append(" ");
            }
        }
        sb.append("庄家是"+split[split.length-1]+"号位的玩家");
        return sb.toString();
    }
}
