package com.xmsy.common.define.constant.kaiyuan;

public enum XlchCardEnum {

    YIWAN("11","一万"),ERWAN("12","二万"),SANWAN("13","三万"),SIWAN("14","四万"),
    WUWAN("15","五万"),LIUWAN("16","六万"),QIWAN("17","七万"),BAWAN("18","八万"),
    JIUWAN("19","九万"), YITIAO("21","一条"),ERTIAO("22","二条"),SANTIAO("23","三条"),SITIAO("24","四条"),
    WUTIAO("25","五条"),LIUTIAO("26","六条"),QITIAO("27","七条"),BATIAO("28","八条"),
    JIUTIAO("29","九条"), YITONG("31","一筒"),ERTONG("32","二筒"),SANTONG("33","三筒"),SITONG("34","四筒"),
    WUTONG("35","五筒"),LIUTONG("36","六筒"),QITONG("37","七筒"),BATONG("38","八筒"),
    JIUTONG("39","九筒")
    ;

    private final String card;

    private final String cardValue;

    private XlchCardEnum(final String card, String cardValue){
        this.card = card;
        this.cardValue = cardValue;
    }

    public static String getCardValue(String c) {
        XlchCardEnum[] cardEnums = values();
        for (XlchCardEnum cardEnum : cardEnums) {
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