/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.xmsy.server.zxyy.payment.service.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 日期处理
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年12月21日 下午12:53:33
 */
public class DateUtils {
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 日期格式化 日期格式为：yyyy-MM-dd
	 * 
	 * @param date
	 *            日期
	 * @return 返回yyyy-MM-dd格式日期
	 */
	public static String format(Date date) {
		return format(date, DATE_PATTERN);
	}

	/**
	 * 日期格式化 日期格式为：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            日期
	 * @return 返回yyyy-MM-dd HH:mm:ss格式日期
	 */
	public static String formatTime(Date date) {
		return format(date, DATE_TIME_PATTERN);
	}

	/**
	 * 日期格式化 日期格式为：yyyy-MM-dd
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            格式，如：DateUtils.DATE_TIME_PATTERN
	 * @return 返回yyyy-MM-dd格式日期
	 */
	public static String format(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param strDate
	 *            日期字符串
	 * @param pattern
	 *            日期的格式，如：DateUtils.DATE_TIME_PATTERN
	 */
	public static Date stringToDate(String strDate, String pattern) {
		if (StringUtils.isBlank(strDate)) {
			return null;
		}

		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
		return fmt.parseLocalDateTime(strDate).toDate();
	}

	/**
	 * 根据周数，获取开始日期、结束日期
	 * 
	 * @param week
	 *            周期 0本周，-1上周，-2上上周，1下周，2下下周
	 * @return 返回date[0]开始日期、date[1]结束日期
	 */
	public static Date[] getWeekStartAndEnd(int week) {
		DateTime dateTime = new DateTime();
		LocalDate date = new LocalDate(dateTime.plusWeeks(week));

		date = date.dayOfWeek().withMinimumValue();
		Date beginDate = date.toDate();
		Date endDate = date.plusDays(6).toDate();
		return new Date[] { beginDate, endDate };
	}
	
	/**
	 * 根据周数，获取开始日期
	 * 
	 * @param week
	 *            周期 0本周，-1上周，-2上上周，1下周，2下下周
	 * @return 返回date
	 */
	public static Date getWeekStart(int week) {
		DateTime dateTime = new DateTime();
		LocalDate date = new LocalDate(dateTime.plusWeeks(week));

		date = date.dayOfWeek().withMinimumValue();
		Date beginDate = date.toDate();
		return beginDate;
	}
	/**
	 * 根据周数，获取结束日期
	 * 
	 * @param week
	 *            周期 0本周，-1上周，-2上上周，1下周，2下下周
	 * @return 返回date
	 */
	public static Date getWeekEnd(int week) {
		DateTime dateTime = new DateTime();
		LocalDate date = new LocalDate(dateTime.plusWeeks(week));
		date = date.dayOfWeek().withMinimumValue();
		Date endDate = date.plusDays(6).toDate();
		return endDate;
	}

	/**
	 * 对日期的【秒】进行加/减
	 *
	 * @param date
	 *            日期
	 * @param seconds
	 *            秒数，负数为减
	 * @return 加/减几秒后的日期
	 */
	public static Date addDateSeconds(Date date, int seconds) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plusSeconds(seconds).toDate();
	}

	/**
	 * 对日期的【分钟】进行加/减
	 *
	 * @param date
	 *            日期
	 * @param minutes
	 *            分钟数，负数为减
	 * @return 加/减几分钟后的日期
	 */
	public static Date addDateMinutes(Date date, int minutes) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plusMinutes(minutes).toDate();
	}

	/**
	 * 对日期的【小时】进行加/减
	 *
	 * @param date
	 *            日期
	 * @param hours
	 *            小时数，负数为减
	 * @return 加/减几小时后的日期
	 */
	public static Date addDateHours(Date date, int hours) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plusHours(hours).toDate();
	}

	/**
	 * 对日期的【天】进行加/减
	 *
	 * @param date
	 *            日期
	 * @param days
	 *            天数，负数为减
	 * @return 加/减几天后的日期
	 */
	public static Date addDateDays(Date date, int days) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plusDays(days).toDate();
	}

	/**
	 * 对日期的【周】进行加/减
	 *
	 * @param date
	 *            日期
	 * @param weeks
	 *            周数，负数为减
	 * @return 加/减几周后的日期
	 */
	public static Date addDateWeeks(Date date, int weeks) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plusWeeks(weeks).toDate();
	}

	/**
	 * 对日期的【月】进行加/减
	 *
	 * @param date
	 *            日期
	 * @param months
	 *            月数，负数为减
	 * @return 加/减几月后的日期
	 */
	public static Date addDateMonths(Date date, int months) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plusMonths(months).toDate();
	}

	/**
	 * 对日期的【年】进行加/减
	 *
	 * @param date
	 *            日期
	 * @param years
	 *            年数，负数为减
	 * @return 加/减几年后的日期
	 */
	public static Date addDateYears(Date date, int years) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plusYears(years).toDate();
	}

	/**
	 * 星期几
	 * 
	 * @param date
	 * @return int
	 */
	public static int dayOfWeek(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.dayOfWeek().get();
	}

	/**
	 * 取得日期属于一年中的哪一周 返回周数
	 * 
	 * @param date
	 * @return int
	 */
	public static int getDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 取得日期属于一年中的哪一周 返回 年份并上周数
	 * 
	 * @param date
	 * @return int
	 */
	public static int getWeekOfYear(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.getYear() * 100 + dateTime.weekOfWeekyear().get();
	}

	/**
	 * 取得昨日
	 * 
	 * @return
	 */
	public static Date getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}
	/**
	 * 取得今日当前时间
	 * 
	 * @return Date
	 */
	public static Date getToday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 0);
		return cal.getTime();
	}
	/**
	 * 取得今日之前几天,或之后几天
	 * 负数是前几天
	 * 正数是后几天
	 * @return
	 */
	public static Date getDay(int n) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, n);
		return cal.getTime();
	}
	/**
	 * 取得昨日零点时间
	 * 
	 * @return
	 */
	public static Date getStartOfYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 取得昨日最后一秒
	 * 
	 * @return
	 */
	public static Date getEndOfYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 根据具体日期获取的周一和周末
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static String[] getWeekStartAndEnd(Date date) {
		String dateArr[] = new String[2];
		DateTime dateTime = new DateTime(date);
		dateArr[0] = dateTime.dayOfWeek().withMinimumValue().toString("yyyy-MM-dd");
		dateArr[1] = dateTime.dayOfWeek().withMaximumValue().toString("yyyy-MM-dd");
		return dateArr;
	}

	/**
	 * 根据具体年份周数获取日期范围
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date[] getWeekDays(int year, int week) {
		Date date[] = new Date[2];
		Calendar cal = Calendar.getInstance();
		// 设置每周的开始日期
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.WEEK_OF_YEAR, week);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		date[0] = cal.getTime();
		cal.add(Calendar.DAY_OF_WEEK, 6);
		date[1] = cal.getTime();
		return date;
	}

	/**
	 * 根据具体年份周数获取第一天日期
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getFirstDateForWeekDays(int year, int week) {
		Calendar cal = Calendar.getInstance();
		// 设置每周的开始日期
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.WEEK_OF_YEAR, week);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		return cal.getTime();
	}

	/**
	 * 根据具体年份周数获取第一天日期
	 * 
	 * @param yearAndWweek
	 *            201901
	 * @return
	 */
	public static Date getFirstDateForWeekDays(Integer yearAndWweek) {
		if (yearAndWweek == null || yearAndWweek == 0) {
			return null;
		}
		int year = yearAndWweek / 100;
		int week = yearAndWweek % 100;
		if (year == 0 || week == 0) {
			return null;
		}
		return getFirstDateForWeekDays(year, week);
	}

	/**
	 * 获取未来 第 past 天的日期
	 * 
	 * @param past
	 * @return
	 */
	public static String getFetureDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_PATTERN);
		String result = format.format(today);
		return result;
	}

	/**
	 * 获取过去第几天的日期
	 * 
	 * @param past
	 * @return
	 */
	public static String getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_PATTERN);
		String result = format.format(today);
		return result;
	}

	// public static void main(String[] args) {
	// System.out.println(getFetureDate(7));
	// }
}
