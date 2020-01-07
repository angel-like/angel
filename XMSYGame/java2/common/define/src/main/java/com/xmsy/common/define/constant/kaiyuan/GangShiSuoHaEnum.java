package com.xmsy.common.define.constant.kaiyuan;

public class GangShiSuoHaEnum {
    public enum huaSe{
        FANGPIAN("0","方片"),MEIHUA("1","梅花"),HONGTAO("2","紅桃"),HEITAO("3","黑桃");

        private final String card;

        private final String cardValue;

        huaSe(String card, String cardValue) {
            this.card = card;
            this.cardValue = cardValue;
        }

        public static String getCardValue(String c) {
            huaSe[] cardEnums = values();
            for (huaSe cardEnum : cardEnums) {
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
    public enum paiZhi{
        YI("1","A"),ER("2","2"),SAN("3","3"),SI("4","4"),WU("5","5"),LIU("6","6"),
        QI("7","7"),BA("8","8"),JIU("9","9"),SHI("a","10"),J("b","J"),Q("c","Q"),
        K("d","K");

        private final String card;

        private final String cardValue;

        paiZhi(String card, String cardValue) {
            this.card = card;
            this.cardValue = cardValue;
        }

        public static String getCardValue(String c) {
            paiZhi[] cardEnums = values();
            for (paiZhi cardEnum : cardEnums) {
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
