package com.xmsy.common.define.constant.kaiyuan;

public class DouDiZhuEnum {
    // 花色 42代表小王 43代表大王
    public enum CardTypeEnum{
        FANGPIAN("0","方片"),MEIHUA("1","梅花"),HONGTAO("2","紅桃"),HEITAO("3","黑桃"),
        WANG("4","王");

        private final String card;

        private final String cardValue;

        CardTypeEnum(String card, String cardValue) {
            this.card = card;
            this.cardValue = cardValue;
        }

        public static String getCardValue(String c) {
            DouDiZhuEnum.CardTypeEnum[] cardEnums = values();
            for (DouDiZhuEnum.CardTypeEnum cardEnum : cardEnums) {
                if (cardEnum.card().equals(c)) {
                    return cardEnum.cardValue();
                }
            }
            return null;
        }

        public String  card(){
            return this.card;
        }

        public String cardValue(){
            return this.cardValue;
        }
    }
    // 牌值
    public enum CardValueEnum{
        YI("1","A"),ER("2","2"),SAN("3","3"),SI("4","4"),WU("5","5"),LIU("6","6"),
        QI("7","7"),BA("8","8"),JIU("9","9"),SHI("a","10"),J("b","J"),Q("c","Q"),
        K("d","K");

        private final String card;

        private final String cardValue;

        CardValueEnum(String card, String cardValue) {
            this.card = card;
            this.cardValue = cardValue;
        }

        public static String getCardValue(String c) {
            DouDiZhuEnum.CardValueEnum[] cardEnums = values();
            for (DouDiZhuEnum.CardValueEnum cardEnum : cardEnums) {
                if (cardEnum.card().equals(c)) {
                    return cardEnum.cardValue();
                }
            }
            return null;
        }

        public String  card(){
            return this.card;
        }

        public String cardValue(){
            return this.cardValue;
        }
    }
}
