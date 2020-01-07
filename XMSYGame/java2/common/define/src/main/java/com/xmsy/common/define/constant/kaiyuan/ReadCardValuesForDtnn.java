package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForDtnn {
    public static String readCard(String cardValues){
        StringBuffer sb = new StringBuffer();
        String[] cardValue = cardValues.split("");
        char[] ch = cardValues.toCharArray();
        for (int i = 0; i < cardValue.length / 10; i++) {
            sb.append((i+1)+"号位玩家手牌为:");
            sb.append(DtnnEnum.CardTypeEnum.getCardTypeName(String.valueOf(ch[i*10])));
            sb.append(DtnnEnum.CardValueEnum.getCardValueName(String.valueOf(ch[i*10+1])));
            sb.append(DtnnEnum.CardTypeEnum.getCardTypeName(String.valueOf(ch[i*10+2])));
            sb.append(DtnnEnum.CardValueEnum.getCardValueName(String.valueOf(ch[i*10+3])));
            sb.append(DtnnEnum.CardTypeEnum.getCardTypeName(String.valueOf(ch[i*10+4])));
            sb.append(DtnnEnum.CardValueEnum.getCardValueName(String.valueOf(ch[i*10+5])));
            sb.append(DtnnEnum.CardTypeEnum.getCardTypeName(String.valueOf(ch[i*10+6])));
            sb.append(DtnnEnum.CardValueEnum.getCardValueName(String.valueOf(ch[i*10+7])));
            sb.append(DtnnEnum.CardTypeEnum.getCardTypeName(String.valueOf(ch[i*10+8])));
            sb.append(DtnnEnum.CardValueEnum.getCardValueName(String.valueOf(ch[i*10+9]))+"  ");
        }
        sb.append("赢家是"+cardValue[cardValue.length - 1]+"号位的玩家");
        return sb.toString();
    }
}
