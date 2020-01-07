package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForDdz {
    public static  String readCard(String cardValues){
        String[] split = cardValues.split("");
        StringBuffer sb = new StringBuffer();
        int length = 0;
        for (int i = 0; i < 3; i++) {
            sb.append(i + 1 + "号玩家的牌:");
            for (int j = 0; j < 17; j++) {
                length++;
                if ("4".equals(split[i * 34 + j * 2])) {
                    if ("2".equals(split[i * 34 + j * 2 + 1])) {
                        String cardcolor = "小";
                        String card = "王";
                        sb.append(cardcolor + card);
                    } else if ("3".equals(split[i * 34 + j * 2 + 1])) {
                        String cardcolor = "大";
                        String card = "王";
                        sb.append(cardcolor + card);
                    }
                } else {
                    // 获取每位玩家的牌
                    String cardcolor = DouDiZhuEnum.CardTypeEnum.getCardValue(split[i * 34 + j * 2]);
                    String card = DouDiZhuEnum.CardValueEnum.getCardValue(split[i * 34 + j * 2 + 1]);
                    sb.append(cardcolor + card);
                }
            }
            sb.append(" ");
        }
        // 庄家牌
        sb.append("庄家牌:");
        for (int i = 0; i < 3; i++) {
            String cardcolor = DouDiZhuEnum.CardTypeEnum.getCardValue(split[length * 2 + 2 * i]);
            String card = DouDiZhuEnum.CardValueEnum.getCardValue(split[length * 2 + 2 * i + 1]);
            sb.append(cardcolor + card);
        }
        sb.append(" " + split[split.length - 1] + "号玩家为地主");
        return sb.toString();
    }
}
