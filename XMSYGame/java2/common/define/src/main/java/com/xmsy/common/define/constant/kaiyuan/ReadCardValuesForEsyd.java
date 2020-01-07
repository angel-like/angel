package com.xmsy.common.define.constant.kaiyuan;


public class ReadCardValuesForEsyd {
 /* public static void main(String[] args) {
        //String str = "02d1317,13d062a,2032703-233b,323253d-233b|41c29|5393b";
        String str = "02d1317,13d062a,2032703,323253d-233b|41c29|5393b";
        System.out.println(readCard(str));
    }*/
    public static String readCard(String cardValues){
        if(cardValues == "" || cardValues.length() == 0){
            return "牌型错误";
        }
        StringBuffer sb = new StringBuffer();
        String[] str = cardValues.split(",");
        for (int i = 0; i < str.length ; i++) {
            String s = str[i];
            String[] split = s.split("");
            if (s.contains("|") || s.contains("-")) {
                String[] str1 = str[i].split("\\|");
                for (int k = 0; k < str1.length; k++) {
                    if(str1[k].contains("-")){
                        String[] split2 = str1[k].split("-");
                        for (int m = 0; m < split2.length; m++) {
                            String[] split1 = split2[m].split("");
                            sb.append("第"+split[0] + "位玩家的牌为:");
                            sb.append("第"+(m+1)+"墩的牌为:");
                            if(split2[m].length()>6){
                                String s1 = funcFor(split1);
                                sb.append(s1);
                            }else{
                                for (int j = 0; j < split1.length / 2; j++) { // 每个玩家的牌
                                    String cardColors = split1[j * 2 ];
                                    String card = split1[j * 1 + 2  ];
                                    sb.append(DzhpkEnum.CardTypeEnum.getCardTypeName(cardColors));
                                    sb.append(DzhpkEnum.CardValueEnum.getCardValueName(card));
                                }
                            }
                            sb.append(" ");
                        }
                    }else{
                        String[] split3 = str1[k].split("");
                        sb.append("第"+split3[0] + "位玩家的牌为:");
                        String s1 = funcFor(split3);
                        sb.append(s1);
                    }
                }
            } else {
                if(split[0].equals("0") ){
                    sb.append("庄家的牌型为:");
                    String s1 = funcFor(split);
                    sb.append(s1);
                }else{
                    sb.append("第"+split[0] + "位玩家的牌为:");
                    String s1 = funcFor(split);
                    sb.append(s1);
                }
            }
        }
        return sb.toString();
    }

    public static String funcFor(String[] string){
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < string.length / 2; j++) { // 每个玩家的牌
            String cardColors = string[j * 2 + 1];
            String card = string[j * 2 + 2];
            sb.append(DzhpkEnum.CardTypeEnum.getCardTypeName(cardColors));
            sb.append(DzhpkEnum.CardValueEnum.getCardValueName(card));
        }
        sb.append(" ");
        return sb.toString();
    }
}


