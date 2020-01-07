package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForXlch {

    public  static String readString(String str) {
        StringBuffer sb = new StringBuffer();
        String[] split = str.split(",");
        sb.append("1号位玩家牌为:");
        sb.append(readCard(split[0]));
        sb.append(",");
        sb.append("2号位玩家牌为:");
        sb.append(readCard(split[1]));
        sb.append(",");
        sb.append("3号位玩家牌为:");
        sb.append(readCard(split[2]));
        sb.append(",");
        sb.append("4号位玩家牌为:");
        sb.append(readCard(split[3]));
        sb.append(",");
        String s = split[4];
        sb.append(s+"号玩家为庄家");
        return  sb.toString();
    }

    public  static String readCard(String cardValues) {
        StringBuffer sb = new StringBuffer();
        char[] ar = cardValues.toCharArray();
        int cardNum = ar.length / 2;
        for (int i = 0; i < cardNum; i++) {
            int j = i;
            String card = String.valueOf(ar[j * 2]) + String.valueOf(ar[j * 2 + 1]);
            String cardValue = XlchCardEnum.getCardValue(card);
            sb.append(cardValue);
        }
        return sb.toString();
    }

public static void main(String[] agrs) {
    int i = 0;
   String v ="11111313161731313335353539,2121222424252627283829292929,14151617181919323334363636,21212323232424262626272727,1";
    String string = readString(v);
    System.out.println(string);
}


}
