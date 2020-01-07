package com.xmsy.common.define.constant.kaiyuan;

import java.util.HashMap;
import java.util.Map;

public class ReadCardValuesForShiSanShui {
	
	private static final Map<String, String> TYPE = new HashMap<String, String>();
	static {
		TYPE.put("0", "乌龙");
		TYPE.put("1", "对子");
		TYPE.put("2", "两对");
		TYPE.put("3", "三条");
		TYPE.put("4", "顺子");
		TYPE.put("5", "同花");
		TYPE.put("6", "葫芦");
		TYPE.put("7", "铁支");
		TYPE.put("8", "同花顺");
		TYPE.put("14", "三同花");
		TYPE.put("15", "三顺子");
		TYPE.put("16", "六对半");
		TYPE.put("17", "五对三条");
		TYPE.put("18", "四套三条");
		TYPE.put("19", "凑一色");
		TYPE.put("20", "全小");
		TYPE.put("21", "全大");
		TYPE.put("22", "三分天下");
		TYPE.put("23", "三同花顺");
		TYPE.put("24", "十二皇族");
		TYPE.put("25", "一条龙");
		TYPE.put("26", "至尊青龙");
	}

    public  static String readCard(String cardValues){
    	cardValues = cardValues.substring(0, cardValues.length()-2);
    	StringBuffer sb= new StringBuffer();
    	String[] strs = cardValues.split(";");
    	for (String str : strs) {
			String[] ss = str.split(",");
			if(ss.length==1) {
				//特殊牌型
		        char[] ar = ss[0].trim().toCharArray();
				sb.append(ar[ar.length-1]+"号位：");
				analyticCards(sb, ar);
				sb.append(" ");
				sb.append(TYPE.get(ss[0].substring(ss[0].length()-3, ss[0].length()-1)));
				sb.append("；");
			}else {
				sb.append(ss[3]+"号位：");
				//普通牌型
				for(int i=0;i<ss.length-1;i++) {
					char[] ar = ss[i].trim().toCharArray();
					analyticCards(sb, ar);
					sb.append(" ");
					sb.append(TYPE.get(String.valueOf(ar[ar.length-1])));
					sb.append(",");
				}
				sb.deleteCharAt(sb.length()-1);
				sb.append("；");
			}
		}
    	return sb.deleteCharAt(sb.length()-1).toString();
    }
    
    
    
    /**
     * 解析牌值
     */
    private static StringBuffer analyticCards(StringBuffer sb,char[] ar) {
    	int length = analyticLength(ar.length);
    	for(int i=0;i<length;i++) {
    		add(sb, String.valueOf(ar[i*2]), String.valueOf(ar[i*2+1]));
    	}
    	return sb;
    }
    
    /**
     * 解析长度
     */
    private static int analyticLength(int length) {
    	switch (length) {
		case 29:
			return 13;
		case 7:
			return 3;
		default:
			return 5;
    	}
    }
    
    /**
     * 添加每张牌
     */
    private static StringBuffer add(StringBuffer sb,String cardTypeId,String cardId) {
    	return sb.append(DzhpkEnum.CardTypeEnum.getCardTypeName(cardTypeId)).append(DzhpkEnum.CardValueEnum.getCardValueName(cardId));
    }
}
