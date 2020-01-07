package com.xmsy.common.define.constant.kaiyuan;

public class ZjnEnum {
	
	//花色
	public enum CardTypeEnum{
		
		FK(0,"♦"),MH(1,"♣"),HT(2,"♥"),HEIT(3,"♠");
		
		private final int cardTypeId;
		private final String cardTypeName;
		
		private CardTypeEnum(final int cardTypeId, String cardTypeName) {
			this.cardTypeId = cardTypeId;
			this.cardTypeName = cardTypeName;
		}
		
		public static String getcardTypeName(int cardTypeId) {
			CardTypeEnum[] cardTypeEnums = values();
			for(CardTypeEnum cardTypeEnum : cardTypeEnums) {
				if(cardTypeEnum.cardTypeId()==cardTypeId) {
					return cardTypeEnum.cardTypeName();
				}
			}
			return null;
		}
		public int  cardTypeId(){
            return this.cardTypeId;
        }

        public String cardTypeName(){
            return this.cardTypeName;
        }
	}
	//牌值
	public enum CardValueEnum{
		
		YI("1","1"),ER("2","2"),SAN("3","3"),SI("4","4"),WU("5","5"),
		LIU("6","6"),QI("7","7"),BA("8","8"),JIU("9","9"),SHI("a","10"),
		J("b","J"),Q("c","Q"),K("d","K");
		
		private final String cardId; 
		private final String cardValue;
		
		private CardValueEnum(final String cardId, String cardValue) {
			this.cardId = cardId;
			this.cardValue = cardValue;
		}
		
		public static String getcardValue(String cardId) {
			CardValueEnum[] cardValueEnums = values();
			for(CardValueEnum cardValueEnum : cardValueEnums) {
				if (cardId.equals(cardValueEnum.cardId())) {
					return cardValueEnum.cardValue();
				}
			}
			return null;
		}
		
		public String cardId(){
            return this.cardId;
        }

        public String cardValue(){
            return this.cardValue;
        }
	}
}
