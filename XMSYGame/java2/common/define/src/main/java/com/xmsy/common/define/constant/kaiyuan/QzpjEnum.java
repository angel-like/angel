package com.xmsy.common.define.constant.kaiyuan;

public class QzpjEnum {
    public enum CardValueEnum{

        YI("12","丁三"),ER("24","二四"),SAN("23","杂五"),SI("14","杂五"),WU("25","杂七"),
        LIU("34","杂七"),QI("26","杂八"),BA("35","杂八"),JIU("36","杂九"),SHI("45","杂九"),
        SYI("15","零霖六"),SER("16","高脚七"),SSAN("46","红头十"),SSI("56","斧头"),SWU("22","板凳"),
        SLIU("33","长三"),SQI("55","梅牌"),SBA("13","鹅牌"),SJIU("44","人牌"),ESHI("11","地牌"),
        EYI("66","天牌");

        private final String cardId;

        private final String cardValueName;

        private CardValueEnum (final String cardId,String cardValueName){
            this.cardId = cardId;
            this.cardValueName = cardValueName;
        }

        public static String getCardValueName(String cardId){
            CardValueEnum[] cardValueEnums = values();
            for (CardValueEnum cardValueEnum : cardValueEnums) {
                if(cardValueEnum.cardId.equals(cardId)){
                    return cardValueEnum.cardValueName;
                }
            }
            return null;
        }

        public String cardId(){
            return this.cardId;
        }

        public String CardValueName(){
            return this.cardValueName;
        }
    }
}
