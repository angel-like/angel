package com.xmsy.common.define.constant.kaiyuan;

public enum MjCardEnum {

    YIWAN("01","一万"),ERWAN("02","二万"),SANWAN("03","三万"),SIWAN("04","四万"),
    WUWAN("05","五万"),LIUWAN("06","六万"),QIWAN("07","七万"),BAWAN("08","八万"),
    JIUWAN("09","九万"),DONGFENG("11","东风"),NANFENG("12","南风"),XIFENG("13","西风"),
    BEIFENG("14","北风"),ZHONG("15","中"),FA("16","發"),BAI("17","白"),
    CHUN("21","春"),XIA("22","夏"),QIU("23","秋"),DONG("24","冬"),
    MEI("25","梅"),LAN("26","兰"),ZHU("27","竹"),JV("28","菊");
    ;

    private final String card;

    private final String cardValue;

    private MjCardEnum(final String card, String cardValue){
        this.card = card;
        this.cardValue = cardValue;
    }

    public static String getCardValue(String c) {
        MjCardEnum[] cardEnums = values();
        for (MjCardEnum cardEnum : cardEnums) {
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