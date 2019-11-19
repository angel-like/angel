package com.zy.shiro.helloworld;

import java.util.Calendar;
import java.util.Date;

/*
 * Calendar：日历，提供了一些操作年月日时的方法
 * 
 * 获取
 * 修改
 * 添加
 * 
 * 
 */
public class ObjectDemo6Calendar {
	public static void main(String[] args) {
		//static Calendar getInstance() 
		Calendar c=Calendar.getInstance();
		
		//void set(int field, int value) ：把指定的字段修改成指定的值
		//c.set(Calendar.DAY_OF_MONTH, 30);
		
		//int get(int field) // 返回给定日历字段的值
		//public static final int YEAR 1 
		System.out.println(Calendar.YEAR);//得到日历的当前字段，并输出字段
		int year1 =c.get(Calendar.YEAR);//得到当前年
		System.out.println(year1);
		System.out.println("-------");
		
		
		//void add(int field, int amount): 在指定的字段上加上指定的值
	//	c.add(Calendar.DAY_OF_MONTH, -5);
		
		
		//int year2=c.get(1);System.out.println(year2+"------");
		int year=c.get(Calendar.YEAR);//获取计算机当前的年份   (返回给定日历字段的值)
		int month=c.get( Calendar.MONTH)+1;//获取计算机当前的月份 少了一个月 （0~11） 所以+1
		int day=c.get(Calendar.DAY_OF_MONTH-1);//获取计算机当前的日
		System.out.println(year);
		System.out.println(month);
		System.out.println(day);
		System.out.println(year + "年" + month + "月" + day + "日");
		System.out.println("------->>>>>>>>>>>>>>>>>>>");
		Date d=new Date();
		System.out.println(d);
		Calendar c2=Calendar.getInstance();
		c2.setTime(d);
		int year2=c2.get(Calendar.YEAR);//获取计算机当前的年份   (返回给定日历字段的值)
		int month2=c2.get( Calendar.MONTH)+1;//获取计算机当前的月份 少了一个月 （0~11） 所以+1
		int day2=c2.get(Calendar.DAY_OF_MONTH);//获取计算机当前的日
		c2.add(Calendar.DAY_OF_MONTH, -5);//这个添加进去，日期减去5
		System.out.println(year2 + "年" + month2 + "月" + day2 + "日");
		System.out.println(c2.getTime());//这个返回的Date类型
	}
}
