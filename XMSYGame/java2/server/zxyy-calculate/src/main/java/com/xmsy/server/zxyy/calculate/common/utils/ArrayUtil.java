package com.xmsy.server.zxyy.calculate.common.utils;

import java.util.Arrays;

public class ArrayUtil {

	public static void main(String[] args) {
		String arr[]=new String[] {"A","B","c"};
		System.out.println(useArraysBinarySearch(arr,"A"));
	}
	
	/**
	 * 查找数组中是否包含某个值
	 * 使用循环判断
	 * @param arr
	 * @param targetValue
	 * @return
	 */
	public static boolean useLoopSearch(String[] arr,String targetValue){
	    for(String s:arr){
	        if(s.equals(targetValue)) {
	        	return true;
	        }
	    }  
        return false;
    }
	
	/**
	 *   查找有序数组中是否包含某个值
	 * @param arr
	 * @param targetValue
	 * @return
	 */
	
	public static boolean useArraysBinarySearch(String[] arr,String targetValue){
	    return Arrays.binarySearch(arr, targetValue)>=0;
	}

}
