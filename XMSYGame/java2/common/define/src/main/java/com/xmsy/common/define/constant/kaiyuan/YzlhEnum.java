package com.xmsy.common.define.constant.kaiyuan;

public class YzlhEnum {
	
	// 花色
	public enum CardTypeEnum {
		
		FK(0,"♦"),MH(1,"♣"),HT(2,"♥"),HEIT(3,"♠");
		
		private final int cardTypeId;
		private final String cardTypeName;
		
		private CardTypeEnum(final int cardTypeId, String cardTypeName) {
			this.cardTypeId = cardTypeId;
			this.cardTypeName = cardTypeName;
		}
		
		public static String getCardTypeName(int cardTypeId) {
			CardTypeEnum[] cardTypeEnums = values();
			for (CardTypeEnum cardTypeEnum : cardTypeEnums) {
				if (cardTypeEnum.cardTypeId()==cardTypeId) {
					return cardTypeEnum.cardTypeName();
				}
			}
			return null;
		}
		
		public int cardTypeId() {
			return this.cardTypeId;
		}
		
		public String cardTypeName() {
			return this.cardTypeName;
		}
	}
	
	// 数值
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
		
		public static String getCardValue(String cardId) {
			CardValueEnum[] cardValueEnums = values();
			for(CardValueEnum cardValueEnum : cardValueEnums) {
				if (cardId.equals(cardValueEnum.cardId())) {
					return cardValueEnum.cardValue();
				}
			}
			return null;
		}
		
		public String cardId() {
			return this.cardId;
		}
		
		public String cardValue() {
			return this.cardValue;
		}
	}
	
	// 开奖区域
	
	public enum PrizeAreaEnum {
		
		LONG("01","龙"),HU("02","虎"),HE("03","和"),LONGHEITAO("04","龙黑"),
		LONGHONGTAO("05","龙红"),LONGMEIHUA("06","龙梅"),LONGFANGKUAI("07","龙方"),
		HUHEITAO("08","虎黑"),HUHONGTAO("09","虎红"),HUMEIHUA("10","虎梅"),
		HUFANGKUAI("11","虎方");
		
		private final String areaId;
		private final String areaName;
		
		private PrizeAreaEnum(final String areaId, String areaName) {
			this.areaId = areaId;
			this.areaName = areaName;
		}
		
		public static String getPrizeArea(String areaId) {
			PrizeAreaEnum[] prizeAreaEnums = values();
			for(PrizeAreaEnum prizeAreaEnum: prizeAreaEnums) {
				if (prizeAreaEnum.areaId.equals(areaId)) {
					return prizeAreaEnum.areaName();
				}
			}
			return null;
		}
		public String areaId() {
			return this.areaId;
		}
		
		public String areaName() {
			return this.areaName;
		}
	}
}
