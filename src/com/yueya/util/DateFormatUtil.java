package com.yueya.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期相关操作
 * @author liuruichao
 *
 */
public final class DateFormatUtil {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 时间转换为字符串  yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String dateToStr(Date date) {
		return format.format(date);
	}
	/**
	 * 时间转换为字符串 yyyy-MM-dd
	 * @return
	 */
	public static String dateToDicname(Date date) {
		return format2.format(date);
	}
	
	/**
	 * 将字符串转为时间 yyyy-MM-dd HH:mm:ss
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date strToDate(String str) throws ParseException {
		return format.parse(str);
	}
	
	/**
	 * 将字符串转为时间 yyyy-MM-dd
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date strToDateDic(String str) throws ParseException {
		return format2.parse(str);
	}
}
