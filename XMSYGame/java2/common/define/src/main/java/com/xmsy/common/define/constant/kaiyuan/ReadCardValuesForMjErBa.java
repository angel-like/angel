package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForMjErBa {

    public static String readCard(String cardValues) {
        // 值:5326814a3 四位玩家，每位玩家两张牌，最后一位数字是庄家号
        String str[] = cardValues.split("");
        StringBuffer sb = new StringBuffer();
        // 获取每一位玩家的牌
        int length = str.length / 2;
        for (int i = 0; i < length; i++) {
            sb.append("玩家"+(i+1)+":");
            String cardValue = MjErBaCardEnum.getCardValue(String.valueOf(str[2 * i]));
            sb.append(cardValue != null ? cardValue : ""); // 获取玩家1的牌型

            cardValue = MjErBaCardEnum.getCardValue(String.valueOf(str[2 * i + 1]));
            sb.append(cardValue != null ? cardValue : ""); // 获取玩家1的牌型
            sb.append(",");
        }
        sb.append("庄家:" + str[str.length - 1]);

        return sb.toString();
    }
}
