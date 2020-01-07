package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForJinShaYinSha {
    public  static String readCard(String cardValues){
        if (cardValues=="" | cardValues.length()==0){
            return cardValues;
        }
        StringBuffer sb = new StringBuffer();
        String cardTypeName = JinShaYinShaEnum.getCardTypeName(cardValues.substring(0, 2));
        sb.append(cardTypeName);
        sb.append(" "+cardValues.substring(2, 4)+"倍");
        return sb.toString();
    }
}
