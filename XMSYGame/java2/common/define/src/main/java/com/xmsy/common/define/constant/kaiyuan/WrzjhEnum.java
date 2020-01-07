package com.xmsy.common.define.constant.kaiyuan;

public class WrzjhEnum {
	
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
	
	//下注区域
	public enum AreaNameEnum{
		
		TIAN(1,"天"),DI(2,"地"),XUAN(3,"玄"),HUANG(4,"黄"),
		ZHUANG(5,"庄");
		
		private final int areaId;
		private final String areaName;
		
		private AreaNameEnum(final int areaId, String areaName) {
			this.areaId = areaId;
			this.areaName = areaName;
		}
		
		public static String getAreaName(int areaId) {
			AreaNameEnum[] areaNameEnums = values();
			for(AreaNameEnum areaNameEnum:areaNameEnums) {
				if (areaNameEnum.areaId()==areaId) {
					return areaNameEnum.areaName();
				}
			}
			return null;
		}
		public int areaId() {
			return this.areaId;
		}
		
		public String areaName() {
			return this.areaName;
		}
	}
}
