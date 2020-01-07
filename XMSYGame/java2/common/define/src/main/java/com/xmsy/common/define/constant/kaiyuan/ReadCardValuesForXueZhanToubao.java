package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForXueZhanToubao {

    public  static String readCard(String cardValues){
    	if(!cardValues.matches("\\d{30,31}")) {
    		return "";
    	}
    	String[] str  = new String[6];
    	String cards = "";
    	for(int i=0;i<6;i++) {
    		str[i] = cardValues.substring(5*i, 5*i+5);
    		cards += i + 1 + "号玩家手牌为:" + analyticCardType(str[i]) + ";";
    	}
    	return cards.substring(0, cards.length()-1);
    }
    private static String analyticCardType(String card) {
    	if(card.equals("00000")) {
    		return "无人,为空";
    	}
    	String type = "";
    	char[] chs = card.toCharArray();
    	int[] a = new int[6];
    	for(int i=0;i<chs.length;i++) {
    		switch (chs[i]) {
			case '1':
				a[0]++;
				break;
			case '2':
				a[1]++;
				break;
			case '3':
				a[2]++;
				break;
			case '4':
				a[3]++;
				break;
			case '5':
				a[4]++;
				break;
			case '6':
				a[5]++;
				break;
    		}
    	}
    	int b = 0;
    	int c = 0;
    	for(int i=0;i<a.length;i++) {
    		switch (a[i]) {
			case 5:
				type = "豹子";
				return card+",牌型为"+type;
			case 4:
				type = "炸弹";
				return card+",牌型为"+type;
			case 3:
				b++;
				break;
			case 2:
				c++;
				break;
			default:
				break;
			}
    	}
    	if(b==1) {
    		if(c==1) {
    			type = "葫芦";
    		}else {
    			type = "三条";
    		}
    	}else {
    		switch (c) {
			case 2:
				type = "两对";
				break;
			case 1:
				type = "对子";
				break;
			default:
				type = "散点";
				break;
			}
    	}
    	return card+",牌型为"+type;
    }
}
