package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForSlwh {

    public  static String readCard(String cardValues){
        StringBuffer sb= new StringBuffer();
        //第一位 事件
        int  event = Integer.valueOf(cardValues.substring(0,1));
        char[] ar = cardValues.toCharArray();
        switch (event){
            case 1: //无事件
                sb.append(SlwhEnum.ColorEnum.getColorName(String.valueOf(ar[1])));
                sb.append(SlwhEnum.TypeEnum.getTypeName(String.valueOf(ar[2])));
                sb.append(String.valueOf(ar[3])).append(String.valueOf(ar[4])).append("倍");

                break;
            case 2: //大三元
                sb.append("大三元,");
                sb.append(SlwhEnum.ColorEnum.getColorName(String.valueOf(ar[1])));
                sb.append(SlwhEnum.TypeEnum.getTypeName(String.valueOf(ar[2])));
                sb.append(String.valueOf(ar[3])).append(String.valueOf(ar[4])).append("倍,");

                sb.append(SlwhEnum.ColorEnum.getColorName(String.valueOf(ar[5])));
                sb.append(SlwhEnum.TypeEnum.getTypeName(String.valueOf(ar[6])));
                sb.append(String.valueOf(ar[7])).append(String.valueOf(ar[8])).append("倍,");

                sb.append(SlwhEnum.ColorEnum.getColorName(String.valueOf(ar[9])));
                sb.append(SlwhEnum.TypeEnum.getTypeName(String.valueOf(ar[10])));
                sb.append(String.valueOf(ar[11])).append(String.valueOf(ar[12])).append("倍");
                break;
            case 3: //大四喜
                sb.append("大四喜,");
                sb.append(SlwhEnum.ColorEnum.getColorName(String.valueOf(ar[1])));
                sb.append(SlwhEnum.TypeEnum.getTypeName(String.valueOf(ar[2])));
                sb.append(String.valueOf(ar[3])).append(String.valueOf(ar[4])).append("倍,");

                sb.append(SlwhEnum.ColorEnum.getColorName(String.valueOf(ar[5])));
                sb.append(SlwhEnum.TypeEnum.getTypeName(String.valueOf(ar[6])));
                sb.append(String.valueOf(ar[7])).append(String.valueOf(ar[8])).append("倍,");

                sb.append(SlwhEnum.ColorEnum.getColorName(String.valueOf(ar[9])));
                sb.append(SlwhEnum.TypeEnum.getTypeName(String.valueOf(ar[10])));
                sb.append(String.valueOf(ar[11])).append(String.valueOf(ar[12])).append("倍,");

                sb.append(SlwhEnum.ColorEnum.getColorName(String.valueOf(ar[13])));
                sb.append(SlwhEnum.TypeEnum.getTypeName(String.valueOf(ar[14])));
                sb.append(String.valueOf(ar[15])).append(String.valueOf(ar[16])).append("倍");

                break;
            case 4: //霹雳闪电
                sb.append("霹雳闪电,");
                sb.append(SlwhEnum.ColorEnum.getColorName(String.valueOf(ar[1])));
                sb.append(SlwhEnum.TypeEnum.getTypeName(String.valueOf(ar[2])));
                sb.append(String.valueOf(ar[3])).append(String.valueOf(ar[4])).append("倍");
                break;
            case 5: //送灯
                sb.append("送灯,");
                sb.append(SlwhEnum.ColorEnum.getColorName(String.valueOf(ar[1])));
                sb.append(SlwhEnum.TypeEnum.getTypeName(String.valueOf(ar[2])));
                sb.append(String.valueOf(ar[3])).append(String.valueOf(ar[4])).append("倍,");

                sb.append(SlwhEnum.ColorEnum.getColorName(String.valueOf(ar[5])));
                sb.append(SlwhEnum.TypeEnum.getTypeName(String.valueOf(ar[6])));
                sb.append(String.valueOf(ar[7])).append(String.valueOf(ar[8])).append("倍");
                break;
        }

        return  sb.toString();
    }
}
