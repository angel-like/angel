package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForBenChiBaoMa {
	
    public  static String readCard(String cardValues){
    	return car(cardValues.substring(0, 2))+Integer.valueOf(cardValues.substring(2))+"倍";
    }
    private static String car(String car) {
    	switch (car) {
		case "01":
			return "法拉利";
		case "02":
			return "兰博基尼";
		case "03":
			return "玛莎拉蒂";
		case "04":
			return "保时捷";
		case "05":
			return "雷克萨斯";
		case "06":
			return "大众";
		case "07":
			return "奔驰";
		default:
			return "宝马";
		}
    }
    public static void main(String[] args) {
		System.out.println(readCard("0705"));
	}
}
