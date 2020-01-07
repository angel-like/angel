package com.xmsy.server.payment;

import java.util.HashSet;
import java.util.Set;

/**
 * Unit test for simple App.
 */
public class AppTest {

	/**
	 * 泛型方法，类型安全
	 */
	public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
		Set<E> result = new HashSet<E>(s1);
		result.addAll(s2);
		return result;
	}

	public static void main(String[] args) {
		Set<Long> s1 = new HashSet<>(1);
		s1.add(1L);
		Set<Long> s2 = new HashSet<>(2);
		s2.add(2L);
		s2.add(3L);
		Set<Long> s3 = union(s1, s2);
		System.out.println(s3);

	}
}
