package com.xmsy.network.sms;

import java.util.Random;

public enum EnumNumber {
	
	RANDOM_SIX {

		@Override
		public Integer getRandomNumber() {
			return new Random().nextInt(899999)+100000;
		}
		
	};
	public abstract Integer getRandomNumber();
}
