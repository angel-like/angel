package com.xmsy.common.define.constant.kaiyuan;

public class ReadCardValuesForBaiRenNiuNiu {

    public  static String readCard(String cardValues){
    	StringBuffer sb = new StringBuffer();
    	char[] chs = cardValues.toCharArray();
    	for(int i=0;i<5;i++) {
    		analysisOfTablets(sb, chs[i*11]);
    		for(int j=0;j<5;j++) {
    			cardTypeAnalysis(sb, chs[i*11+j*2+1], chs[i*11+j*2+2]);
    		}
    		sb.append("；");
    	}
    	sb.append("赢牌位置：");
    	if(chs.length<56) {
    		return sb.append("无").toString();
    	}
    	for(int i=0;i<(chs.length-55)/2;i++) {
    		switch (chs[56+i*2]) {
			case '1':
				sb.append("天,");
				break;
			case '2':
				sb.append("地,");
				break;
			case '3':
				sb.append("玄,");
				break;
			case '4':
				sb.append("黄,");
				break;
			}
    	}
    	return sb.deleteCharAt(sb.length()-1).toString();
    }
    private static StringBuffer analysisOfTablets(StringBuffer sb,char c){
    	switch (c) {
		case '1':
			return sb.append("天号位手牌为：");
		case '2':
			return sb.append("地号位手牌为：");
		case '3':
			return sb.append("玄号位手牌为：");
		case '4':
			return sb.append("黄号位手牌为：");
		case '5':
			return sb.append("庄家手牌为：");
		default:
			return sb;
		}
    }
    private static StringBuffer cardTypeAnalysis(StringBuffer sb,char c1,char c2) {
    	return sb.append(DzhpkEnum.CardTypeEnum.getCardTypeName(String.valueOf(c1))).append(DzhpkEnum.CardValueEnum.getCardValueName(String.valueOf(c2)));
    }
    
}
