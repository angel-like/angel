package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForBaiRenTouBao {

    public  static String readCard(String cardValues){
    	StringBuffer sb = new StringBuffer(cardValues);
    	sb.insert(1, ",");
    	sb.insert(3, ",");
    	sb.insert(5, ";");
    	String str1 = "";
    	switch (sb.substring(6, 7)) {
		case "1":
			str1 = "大 ";
			break;
		case "2":
			str1 = "小 ";
			break;
		default:
			return sb.replace(6, 11, "18点 豹子").toString();
		}
    	String str2 = "";
    	switch (sb.substring(7, 8)) {
		case "1":
			str2 = "单";
			break;
		default:
			str2 = "双";
			break;
		}
    	return sb.delete(6, 9).append("点").append(str1).append(str2).toString();
    }
    
}
