package com.xmsy.common.define.constant.kaiyuan;

public class DtnnEnum {
    //花色
    public enum CardTypeEnum {

        FK("0","♦"),MH("1","♣"),HT("2","♥"),HEIT("3","♠");
        private final String cardTypeId;

        private final String cardTypeName;

        private CardTypeEnum(final String cardTypeId, String cardTypeName) {
            this.cardTypeId = cardTypeId;
            this.cardTypeName = cardTypeName;
        }

        public static String getCardTypeName(String cardTypeId) {
            CardTypeEnum[] cardTypeEnums = values();
            for (CardTypeEnum cardTypeEnum : cardTypeEnums) {
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

    //牌值
    public enum CardValueEnum {

        YI("1","A"),ER("2","2"),SAN("3","3"),SI("4","4"),WU("5","5"),
        LIU("6","6"),QI("7","7"),BA("8","8"),JIU("9","9"),SHI("a","10"),
        J("b","J"),Q("c","Q"),K("d","K");

        private final String cardId;

        private final String cardValueName;

        private CardValueEnum(final String cardId, String cardValueName) {
            this.cardId = cardId;
            this.cardValueName = cardValueName;
        }

        public static String getCardValueName(String cardId) {
            CardValueEnum[] cardValueEnums = values();
            for (CardValueEnum cardValueEnum : cardValueEnums) {
                if (cardValueEnum.cardId().equals(cardId)) {
                    return cardValueEnum.cardValueName();
                }
            }
            return null;
        }

        public String cardId(){
            return this.cardId;
        }

        public String cardValueName(){
            return this.cardValueName;
        }
    }
}
