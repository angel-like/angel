package com.xmsy.server.zxyy.calculate.common.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MathUtil {
	
	public static BigDecimal getBigDecimal(Object obj) {
		try {
			return new BigDecimal(obj.toString());
		} catch (Exception e) {
		}
		return BigDecimal.ZERO;
	}
	
	public static List<Integer> getRandomInt(int max,int len) {
		
		 Random rand = new Random();
		 Set<Integer> rSet=new HashSet<>();
		 while(true){
			 rSet.add(rand.nextInt(max));
	  			if(rSet.size()==len)
	  				break;
 		}
 		return new ArrayList<Integer>(rSet);
	}

	public static void main(String[] args) {
//		MathUtil.getBigDecimal(0.1).add(MathUtil.getBigDecimal(0.06));
		System.out.println(MathUtil.getRandomInt(3, 3));

	}

}
