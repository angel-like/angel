package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForSang {
    public  static String readCard(String cardValues){
        StringBuffer sb= new StringBuffer();
        char[] ar = cardValues.toCharArray();

        for(int i = 0; i < 5; i++){
            sb.append(i+1+"号位玩家");
            for(int j =0; j < 3; j++){
                if(ar[i*6+j*2]==48 && (ar[i*6+j*2+1]==48)) {
                    sb.append("为空");
                    sb.append(" ");
                    break;
                }else {
                    sb.append(DzhpkEnum.CardTypeEnum.getCardTypeName(String.valueOf(ar[i * 6 + j * 2])));
                    sb.append(DzhpkEnum.CardValueEnum.getCardValueName(String.valueOf(ar[i * 6 + j * 2 + 1])));
                }
            }
            sb.append(" ");
        }
        sb.append("庄家是5号位");
        return  sb.toString();
    }
}
