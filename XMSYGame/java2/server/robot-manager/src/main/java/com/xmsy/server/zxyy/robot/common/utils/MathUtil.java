package com.xmsy.server.zxyy.robot.common.utils;

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

	public static List<Integer> getRandomInt(int max, int len) {

		Random rand = new Random();
		Set<Integer> rSet = new HashSet<>();
		while (true) {
			rSet.add(rand.nextInt(max));
			if (rSet.size() == len)
				break;
		}
		return new ArrayList<Integer>(rSet);
	}

	public static Boolean getRandomSex() {
		int random = (int) (Math.random() * 2) + 1;
		if (random == 1) {
			return true;
		}
		return false;
	}

	public static String getRandomPortrait(boolean sex) {
		String name = "";
		if (sex) {
			name = "boy_";
		} else {
			name = "girl_";
		}
		int random = (int) (Math.random() * 4) + 1;
		return name + random;
	}
}
