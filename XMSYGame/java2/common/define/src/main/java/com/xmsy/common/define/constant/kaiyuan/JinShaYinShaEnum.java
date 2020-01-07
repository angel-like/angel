package com.xmsy.common.define.constant.kaiyuan;

public enum JinShaYinShaEnum {
    YANZI("01","燕子"),GEZI("02","鸽子"),KONGQUE("03","孔雀"),LAOYING("04","老鹰"), TUZI("05","兔子"),
    HOUZI("06","猴子"), XIONGMAO("07","熊猫"),SHIZI("08","狮子"),YINSHA("09","银鲨"),JINSHA("10","金鲨");

    private final String cardTypeId;
    private final String cardTypeName;

    private JinShaYinShaEnum(String cardTypeId, String cardTypeName) {
        this.cardTypeId = cardTypeId;
        this.cardTypeName = cardTypeName;
    }

    public static String getCardTypeName(String cardTypeId) {
        JinShaYinShaEnum[] cardTypeEnums = values();
        for (JinShaYinShaEnum cardTypeEnum : cardTypeEnums) {
            if (cardTypeId.equals(cardTypeEnum.cardTypeId())) {
                return cardTypeEnum.cardTypeName();
            }
        }
        return null;
    }

    public String cardTypeId(){
        return this.cardTypeId;
    }

    public String cardTypeName(){
        return this.cardTypeName;
    }
}
