package com.xmsy.server.zxyy.manager.constant;

public class DzhpkEnum {
	
	//花色
	public enum CardTypeEnum {
		
		FK(0,"方块"),MH(1,"梅花"),HT(2,"红桃"),HEIT(3,"黑桃");
        
        private final int cardTypeId;

        private final String cardTypeName;

        private CardTypeEnum(final int cardTypeId, String cardTypeName) {
			this.cardTypeId = cardTypeId;
			this.cardTypeName = cardTypeName;
		}

		public static String getCardTypeName(int cardTypeId) {
			CardTypeEnum[] cardTypeEnums = values();
            for (CardTypeEnum cardTypeEnum : cardTypeEnums) {
                if (cardTypeEnum.cardTypeId() == cardTypeId) {
                    return cardTypeEnum.cardTypeName();
                }
            }
            return null;
        }

        public int cardTypeId(){
            return this.cardTypeId;
        }

        public String cardTypeName(){
            return this.cardTypeName;
        }
    }
    
	//牌值
public enum CardValueEnum {
		
		YI("1","1"),ER("2","2"),SAN("3","3"),SI("4","4"),WU("5","5"),
		LIU("6","6"),QI("7","7"),BA("8","8"),JIU("9","9"),SHI("A","10"),
		J("B","J"),Q("C","Q"),K("D","K");
        
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
   