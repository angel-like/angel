package com.xmsy.common.define.constant.kaiyuan;

public enum MjErBaCardEnum {

    YITONG("1","一筒"),ERTONG("2","二筒"),SANTONG("3","三筒"),SITONG("4","四筒"),WUTONG("5","五筒"),
    LIUTONG("6","六筒"),QITONG("7","七筒"),BATONG("8","八筒"),JIUTONG("9","九筒"),BAIBAN("a","白板");

    private final String card;

    private final String cardValue;

    MjErBaCardEnum(String card, String cardValue) {
        this.card = card;
        this.cardValue = cardValue;
    }

    public static String getCardValue(String c) {
        MjErBaCardEnum[] cardEnums = values();
        for (MjErBaCardEnum cardEnum : cardEnums) {
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
