package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForGssh {
    public static String readCard(String values){
        String[] split = values.split("");
        StringBuffer sb = new StringBuffer();
        int length = split.length/10;
        for (int i = 0; i < length; i++) {
            if ("0".equals(String.valueOf(split[i*10+1])) && "0".equals(String.valueOf(split[i*10+3]))){
                sb.append(i+1+"号玩家:无人 ");
            }else {
                sb.append(i+1+"号玩家的牌:");
                for (int j = 0; j < 5; j++) {
                    // 获取每位玩家的牌
                    if (!"0".equals(split[i * 10 + j * 2 + 1])) {
                        String cardcolor = GangShiSuoHaEnum.huaSe.getCardValue(split[i*10+j*2]);
                        String card = GangShiSuoHaEnum.paiZhi.getCardValue(split[i*10+j*2+1]);
                        sb.append(cardcolor+card);
                    }
                }
                sb.append(" ");
            }
        }
        return sb.toString();
    }

}
