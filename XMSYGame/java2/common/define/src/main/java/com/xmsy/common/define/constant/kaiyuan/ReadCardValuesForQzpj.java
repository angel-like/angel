package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForQzpj {
/*    public static void main(String[] args) {
        String str = "14120000151611331";
        System.out.println(readCard(str));
    }*/
    public static String readCard(String cardValues){
        StringBuffer sb = new StringBuffer();
        if(cardValues == " " || cardValues.length() == 0){
            return "牌型错误！";
        }
        String[] split = cardValues.split("");
        for (int i = 1; i <= split.length / 4 ; i++) {
            sb.append(i+"号位:");
            String cards = cardValues.substring(i*4-4,i*4);
            if(cards.equals("0000") || cards.length() == 0){
                sb.append("没有玩家"+"  ");
            }else{
                sb.append(QzpjEnum.CardValueEnum.getCardValueName(cards.substring(0,2)));
                sb.append(QzpjEnum.CardValueEnum.getCardValueName(cards.substring(2))+"  ");
            }
        }
        sb.append(split[split.length - 1]+"号位的玩家为庄家");
        return sb.toString();
    }
}
