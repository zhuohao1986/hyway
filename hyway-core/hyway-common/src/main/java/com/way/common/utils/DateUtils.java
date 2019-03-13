package com.way.common.utils;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类
 * 
 * @author
 */
public class DateUtils extends DateFormatUtils {
	private static String DATE_TIME_FORMAT_19 = "yyyy-MM-dd HH:mm:ss";
	private static String DATE_TIME_FORMAT_14 = "yyyyMMddHHmmss";
	private static String DATE_FORMAT_10 = "yyyy-MM-dd";
	private static String DATE_FORMAT_8 = "yyyyMMdd";
	private static String DATE_FORMAT_6 = "yyyyMM";
	private final static SimpleDateFormat FM_YYYY = new SimpleDateFormat("yyyy");
	private final static SimpleDateFormat FM_MM = new SimpleDateFormat("MM");
	private final static SimpleDateFormat FM_DD = new SimpleDateFormat("dd");
	private final static SimpleDateFormat FM_HH = new SimpleDateFormat("HH");
	private final static SimpleDateFormat FM_MI = new SimpleDateFormat("mm");

	private final static SimpleDateFormat FM_HHMISS = new SimpleDateFormat("HHmmss");

	private final static SimpleDateFormat FM_YYYY_MM_DD_HHMISS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat FM_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat FM_MMM_YYY = new SimpleDateFormat("MMM yyyy");
	private final static SimpleDateFormat FM_HHMI = new SimpleDateFormat("HH:mm"); // 24// Hours
	private final static SimpleDateFormat FM_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
	private final static SimpleDateFormat FM_YYYYMM = new SimpleDateFormat("yyyyMM");
	private final static SimpleDateFormat FM_YYYYMMDD_HHMISS = new SimpleDateFormat("yyyyMMddHHmmss");
	private final static SimpleDateFormat FM_YYYYMMDD_HMMSS = new SimpleDateFormat("yyyyMMddHmmss");
	private final static SimpleDateFormat FM_YYYYMMDD_MISS = new SimpleDateFormat("yyyyMMddmmss");
	private final static SimpleDateFormat FM_YYYYMMDDHHMMSSSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	/**
	 * 获取当前日期 add by:jackyshang
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * 获取当前日期的23位格式 格式：yyyyMMddHHmmssSSS add by:jackyshang
	 * 
	 * @return
	 */
	public static String getCurDateTimeStr23() {
		Date date = new Date();
		return FM_YYYYMMDDHHMMSSSSS.format(date);
	}

	/**
	 * 获取当前日期的时分秒 格式：HHmmss add
	 * 
	 * @return
	 */
	public static String getCurDateTimeHHMISS() {
		Date date = new Date();
		return FM_HHMISS.format(date);
	}

	/**
	 * 获取当前日期的19位格式 格式：yyyy-MM-dd HH:mm:ss add by:jackyshang
	 * 
	 * @return
	 */
	public static String getCurDateTimeStr19() {
		Date date = new Date();
		return FM_YYYY_MM_DD_HHMISS.format(date);
	}

	/**
	 * 获取当前年份 add by:jackyshang
	 * 
	 * @return
	 */
	public static Integer getCurrentYear() {
		return Integer.valueOf(FM_YYYY.format(getCurrentDate()));
	}

	/**
	 * 获取当前月份 add by:jackyshang
	 * 
	 * @return
	 */
	public static Integer getCurrentMonth() {
		return Integer.valueOf(FM_MM.format(getCurrentDate()));
	}

	/**
	 * 获取当前天 add by:jackyshang
	 * 
	 * @return
	 */
	public static Integer getCurrentDay() {
		return Integer.valueOf(FM_DD.format(getCurrentDate()));
	}

	/**
	 * 获取前一月日期
	 * 
	 * @return
	 */
	public static Date getPreMonthDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 获取前一月月份
	 * 
	 * @return
	 */
	public static String getPreMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		return String.valueOf(FM_MMM_YYY.format(cal.getTime()));
	}

	/**
	 * 将19位格式的字符串转为日期类型 格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateTimeStr19
	 * @return
	 */
	public static Date putFMStr19ToDate(String dateTimeStr19) {
		Date date = null;
		try {
			date = FM_YYYY_MM_DD_HHMISS.parse(dateTimeStr19);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将时间格式字符串转为日期类型 格式： yyyyMMdd yyyy-MM-dd yyyyMMddmmss yyyyMMddHmmss
	 * yyyyMMddHHmmss
	 * 
	 * @param
	 * @return
	 */
	public static Date putFMStrToDate(String dateTimeStr) {
		Date date = null;
		try {
			switch (dateTimeStr.length()) {
			case 8:
				date = FM_YYYYMMDD.parse(dateTimeStr);
				break;
			case 10:
				date = FM_YYYY_MM_DD.parse(dateTimeStr);
				break;
			case 12:
				date = FM_YYYYMMDD_MISS.parse(dateTimeStr);
				break;
			case 13:
				;
				date = FM_YYYYMMDD_HMMSS.parse(dateTimeStr);
				break;
			case 14:
				;
				date = FM_YYYYMMDD_HHMISS.parse(dateTimeStr);
				break;
			case 19:
				;
				date = FM_YYYY_MM_DD_HHMISS.parse(dateTimeStr);
				break;
			default:
				break;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 格式化日期为19位的字符串 格式：yyyy-MM-dd HH:mm:ss create by:jackyshang
	 * 
	 * @param date
	 * @return
	 */
	public static String putDateToTimeStr19(Date date) {
		return FM_YYYY_MM_DD_HHMISS.format(date);
	}

	/**
	 * 格式化日期为时分 格式：HH:mm create by:jackyshang
	 * 
	 * @param date
	 * @return
	 */
	public static String putDateToTimeHhMm(Date date) {
		return FM_HHMI.format(date);
	}

	/**
	 * 格式化日期为时分 格式：yyyy-MM-dd create by:jackyshang
	 * 
	 * @param date
	 * @return
	 */
	public static String putDateToTimeStr10(Date date) {
		return FM_YYYY_MM_DD.format(date);
	}

	/**
	 * 格式化日期为时分 格式：yyyy-MM-dd create by:jackyshang 返回Data
	 * 
	 * @param date
	 * @return date
	 * @throws ParseException
	 */
	public static Date putDateToTimeStr11(Date date) throws ParseException {
		String time = FM_YYYY_MM_DD.format(date);
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		return format1.parse(time);
	}

	/**
	 * 格式化日期为时分 格式：yyyyMM create by:jackyshang
	 * 
	 * @param date
	 * @return
	 */
	public static String putDateToYYYYMMStr(Date date) {
		String dateStr = null;
		if (date != null)
			dateStr = FM_YYYYMM.format(date);
		return dateStr;
	}

	/**
	 * 格式化日期为时分 格式：yyyyMM create by:jackyshang
	 * 
	 * @param date
	 * @return
	 */
	public static String putDateToDDStr(Date date) {

		String dateStr = null;
		if (date != null)
			dateStr = FM_DD.format(date);
		return dateStr;
	}

	/**
	 * 格式化日期为时分 格式：HH create by:jackyshang
	 * 
	 * @param date
	 * @return
	 */
	public static String putDateToHHStr(Date date) {

		String dateStr = null;
		if (date != null)
			dateStr = FM_HH.format(date);
		return dateStr;
	}

	/**
	 * 格式化日期为时分 格式：mi create by:jackyshang
	 * 
	 * @param date
	 * @return
	 */
	public static String putDateToMIStr(Date date) {

		String dateStr = null;
		if (date != null)
			dateStr = FM_MI.format(date);
		return dateStr;
	}

	/**
	 * 格式化日期为时分 格式：yyyyMMdd create by:jackyshang
	 * 
	 * @param date
	 * @return
	 */
	public static String putDateToYYYYMMDDStr(Date date) {
		String dateStr = null;
		if (date != null)
			dateStr = FM_YYYYMMDD.format(date);
		return dateStr;
	}

	/**
	 * 获取当前日期+时间字符串
	 * 
	 * @return 格式：yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowDateTime19String() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_19);
		Date now = new Date();
		return dateFormat.format(now);
	}

	/**
	 * 获取当前日期+时间字符串
	 * 
	 * @return 格式：yyyyMMddHHmmss
	 */
	public static String getNowDateTime14String() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_14);
		Date now = new Date();
		return dateFormat.format(now);
	}

	/**
	 * 获取指定日期+时间字符串
	 * 
	 * @return 格式：yyyyMMddHHmmss
	 */
	public static String getDateTO14String(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_14);
		return dateFormat.format(date);
	}

	/**
	 * 获取当前日期字符串
	 * 
	 * @return 格式：yyyy-MM-dd
	 */
	public static String getNowDate10String() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_10);
		Date now = new Date();
		return dateFormat.format(now);
	}

	/**
	 * 获取当前日期字符串
	 * 
	 * @return 格式：yyyyMMdd
	 */
	public static String getNowDate8String() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_8);
		Date now = new Date();
		return dateFormat.format(now);
	}

	public static String getNowDate6String() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_6);
		Date now = new Date();
		return dateFormat.format(now);
	}

	/**
	 * 
	 * <p>
	 * 将当期日期转为当月第一天
	 * </p>
	 * 
	 * <pre>
	 *  例如：当前日期为：2014-08-22 15:23:40 转换后为 2014-08-01 15:23:40
	 * </pre>
	 * 
	 * @param @param dateTime @param @return @return <code>Date</code> @throws
	 */
	public static Date convertDateToFirstDay(Date dateTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 
	 * <p>
	 * 将当期日期转为最后一天
	 * </p>
	 * 
	 * <pre>
	 *  例如：当前日期为：2014-08-22 15:23:40 转换后为 2014-08-31 15:23:40
	 * </pre>
	 * 
	 * @param @param dateTime @param @return @return <code>Date</code> @throws
	 */
	public static Date convertDateToLastDay(Date dateTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * 
	 * <p>
	 * 用于获得日期的年月的字符串
	 * </p>
	 * 
	 * <pre>
	 *  例如：2014-8-22 15:23:40 返回的值为2014-08
	 * </pre>
	 * 
	 * @param @param dateTime @param @return @return <code>Date</code> @throws
	 */
	public static String convertFormatDate(Date dateTime, String... format) {
		String _format = "yyyy/MM/dd";
		if (format != null && format.length > 0) {
			String s = StringUtils.trim(format[0]);
			if (s != null && !"".equals(s)) {
				_format = s;
			}
		}

		SimpleDateFormat DATE_MONTH_FORMAT = new SimpleDateFormat(_format);
		return DATE_MONTH_FORMAT.format(dateTime);
	}

	/**
	 * 
	 * <p>
	 * 用于将时间取整到小时 或者到分
	 * </p>
	 * 
	 * <pre>
	 *  例如：2014-8-22 15:23:40 取整后为 2014-8-22 15:00:00
	 * </pre>
	 * 
	 * @param @param dateTime @param @return @return <code>Date</code> @throws
	 */
	public static Date convertFormatDateStr(String dateTime, String... format) {
		String _format = "yyyy/MM/dd";
		if (format != null && format.length > 0) {
			String s = StringUtils.trim(format[0]);
			if (s != null && !"".equals(s)) {
				_format = s;
			}
		}

		SimpleDateFormat DATE_MONTH_FORMAT = new SimpleDateFormat(_format);
		try {
			return DATE_MONTH_FORMAT.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * 比较开始时间和结束时间
	 * </p>
	 * 
	 * <pre>
	 *  1.beginDateTime < endDateTime，返回1 2.beginDateTime >
	 * endDateTime，返回-1 3.beginDateTime = endDateTime，返回0
	 * </pre>
	 * 
	 * @param @param beginDateTime @param @param endDateTime @param @return @return
	 *        <code>int</code> @throws
	 */
	public static int compareDate(Date beginDateTime, Date endDateTime) {
		long bt = beginDateTime.getTime();
		long et = endDateTime.getTime();
		long mt = et - bt;
		if (mt == 0) {
			return 0;
		}
		return mt > 0 ? 1 : -1;
	}

	/**
	 * 
	 * <p>
	 * 获得日期的年份
	 * </p>
	 * 
	 * <pre></pre>
	 * 
	 * @param @param dateTime @param @return @return <code>int</code> @throws
	 */
	public static int getYear(Date dateTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 
	 * <p>
	 * 获得日期的月份
	 * </p>
	 * 
	 * <pre></pre>
	 * 
	 * @param @param dateTime @param @return @return <code>int</code> @throws
	 */
	public static int getMonth(Date dateTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * 
	 * <p>
	 * 获得日期的天
	 * </p>
	 * 
	 * <pre></pre>
	 * 
	 * @param @param dateTime @param @return @return <code>int</code> @throws
	 */
	public static int getDay(Date dateTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 
	 * <p>
	 * 获得日期的小时
	 * </p>
	 * 
	 * <pre></pre>
	 * 
	 * @param @param dateTime @param @return @return <code>int</code> @throws
	 */
	public static int getHour(Date dateTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 
	 * <p>
	 * 是否为当前年月
	 * </p>
	 * 
	 * <pre></pre>
	 * 
	 * @param @param beginDateTime @param @param endDateTime @param @return @return
	 *        <code>boolean</code> @throws
	 */
	public static boolean isCurrentYearMonth(Date beginDateTime, Date endDateTime) {
		int b_year = getMonth(beginDateTime);
		int b_month = getYear(beginDateTime);
		int e_year = getMonth(endDateTime);
		int e_month = getYear(endDateTime);

		if (b_year == e_year && b_month == e_month) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * 是否为当天
	 * </p>
	 * 
	 * <pre></pre>
	 * 
	 * @param @param beginDateTime @param @param endDateTime @param @return @return
	 *        <code>boolean</code> @throws
	 */
	public static boolean isCurrentDay(Date beginDateTime, Date endDateTime) {
		int b_year = getMonth(beginDateTime);
		int b_month = getYear(beginDateTime);
		int b_day = getDay(beginDateTime);
		int e_year = getMonth(endDateTime);
		int e_month = getYear(endDateTime);
		int e_day = getDay(endDateTime);

		if (b_year == e_year && b_month == e_month && b_day == e_day) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * 是否为当天
	 * </p>
	 * 
	 * <pre></pre>
	 * 
	 * @param @param beginDateTime @param @param endDateTime @param @return @return
	 *        <code>boolean</code> @throws
	 */
	public static boolean isInner24Hour(Date beginDateTime, Date endDateTime) {
		long b_millis = beginDateTime.getTime();
		long e_millis = endDateTime.getTime();
		long m_millis = e_millis - b_millis;
		long d_millis = 1000 * 60 * 60 * 24;

		if (m_millis <= d_millis) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * 获得昨天23:59:59的时间
	 * </p>
	 * 
	 * <pre></pre>
	 * 
	 * @param @param date @param @return @return <code>Date</code> @throws
	 */
	public static Date getYesterday(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, -1);
		c.set(Calendar.HOUR_OF_DAY, c.getMaximum(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE, c.getMaximum(Calendar.MINUTE));
		c.set(Calendar.SECOND, c.getMaximum(Calendar.SECOND));
		return c.getTime();
	}

	/**
	 * 
	 * <p>
	 * 取这个时间段内每个月的最后一天
	 * </p>
	 * 
	 * <pre></pre>
	 * 
	 * @param @param beginDateTime @param @param endDateTime @param @return @return
	 *        <code>HashSet<Date></code> @throws
	 */
	public static HashSet<Date> getEveryLastDay(Date beginDateTime, Date endDateTime) {
		int b_year = getYear(beginDateTime);
		int e_year = getYear(endDateTime);
		int b_month = getMonth(beginDateTime);
		int e_month = getMonth(endDateTime);

		int m = (e_year - b_year) * 12 + (e_month - b_month);
		HashSet<Date> reSet = new HashSet<Date>();
		for (int i = 0; i <= m; i++) {
			Calendar c = Calendar.getInstance();
			c.setTime(beginDateTime);
			c.add(Calendar.MONTH, i);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			reSet.add(c.getTime());
		}
		return reSet;
	}

	/**
	 * 
	 * <p>
	 * 取这个时间段内每个月的最后一天
	 * </p>
	 * 
	 * <pre></pre>
	 * 
	 * @param @param beginDateTime @param @param endDateTime @param @return @return
	 *        <code>HashSet<Date></code> @throws
	 */
	public static List<Date> getEveryLastDayList(Date beginDateTime, Date endDateTime) {
		int b_year = getYear(beginDateTime);
		int e_year = getYear(endDateTime);
		int b_month = getMonth(beginDateTime);
		int e_month = getMonth(endDateTime);

		int m = (e_year - b_year) * 12 + (e_month - b_month);
		List<Date> list = new ArrayList<Date>();
		for (int i = 0; i <= m; i++) {
			Calendar c = Calendar.getInstance();
			c.setTime(beginDateTime);
			c.add(Calendar.MONTH, i);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			list.add(c.getTime());
		}
		return list;
	}

	/**
	 * <p>
	 * 将这段时间内按日划分
	 * </p>
	 * 
	 * <pre></pre>
	 * 
	 * @param @param beginDateTime @param @param endDateTime @param @return @return
	 *        <code>HashSet<Date></code> @throws
	 */
	public static List<Date> getEveryDayList(Date beginDateTime, Date endDateTime) {
		long b_millis = beginDateTime.getTime();
		long e_millis = endDateTime.getTime();
		long m_millis = e_millis - b_millis;
		long d_millis = 1000 * 60 * 60 * 24;
		long m_days = m_millis / d_millis;

		if (m_millis <= 0) {
			return null;
		}

		List<Date> list = new ArrayList<Date>();
		list.add(beginDateTime);

		for (int i = 0; i < m_days; i++) {
			b_millis = b_millis + d_millis;
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(b_millis);
			list.add(c.getTime());
		}

		m_millis = e_millis - b_millis;
		if (m_millis > 0) {
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(e_millis);
			list.add(c.getTime());
		}
		return list;
	}

	/**
	 * 
	 * <p>
	 * 将这段时间内按月划分
	 * </p>
	 * 
	 * <pre></pre>
	 * 
	 * @param @param beginDateTime @param @param endDateTime @param @param
	 *        format @param @return @return <code>HashSet<String></code> @throws
	 */
	public static List<Date> getEveryMonthList(Date beginDateTime, Date endDateTime) {
		long b_millis = beginDateTime.getTime();
		long e_millis = endDateTime.getTime();
		long m_millis = e_millis - b_millis;

		if (m_millis <= 0) {
			return null;
		}

		int b_year = getYear(beginDateTime);
		int e_year = getYear(endDateTime);
		int b_month = getMonth(beginDateTime);
		int e_month = getMonth(endDateTime);
		int m = (e_year - b_year) * 12 + (e_month - b_month);

		List<Date> list = new ArrayList<Date>();
		list.add(beginDateTime);

		for (int i = 0; i <= m; i++) {
			Calendar c = Calendar.getInstance();
			c.setTime(beginDateTime);
			c.add(Calendar.MONTH, 1);

			beginDateTime = c.getTime();
			b_millis = beginDateTime.getTime();

			if (b_millis > e_millis) {
				list.add(endDateTime);
				break;
			} else {
				list.add(beginDateTime);
			}
		}
		return list;
	}

	/**
	 * 
	 * <p>
	 * 取这个时间段内每个月的月份
	 * </p>
	 * 
	 * <pre></pre>
	 * 
	 * @param @param beginDateTime @param @param endDateTime @param @param
	 *        format @param @return @return <code>HashSet<String></code> @throws
	 */
	public static HashSet<String> getEveryYearMonth(Date beginDateTime, Date endDateTime, String... format) {
		int b_year = getYear(beginDateTime);
		int e_year = getYear(endDateTime);
		int b_month = getMonth(beginDateTime);
		int e_month = getMonth(endDateTime);

		int m = (e_year - b_year) * 12 + (e_month - b_month);
		HashSet<String> reSet = new HashSet<String>();
		for (int i = 0; i <= m; i++) {
			Calendar c = Calendar.getInstance();
			c.setTime(beginDateTime);
			c.add(Calendar.MONTH, i);
			reSet.add(convertFormatDate(c.getTime(), format));
		}
		return reSet;
	}

	public static List<String> getEveryYearMonthList(Date beginDateTime, Date endDateTime, String... format) {
		int b_year = getYear(beginDateTime);
		int e_year = getYear(endDateTime);
		int b_month = getMonth(beginDateTime);
		int e_month = getMonth(endDateTime);

		int m = (e_year - b_year) * 12 + (e_month - b_month);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i <= m; i++) {
			Calendar c = Calendar.getInstance();
			c.setTime(beginDateTime);
			c.add(Calendar.MONTH, i);
			list.add(convertFormatDate(c.getTime(), format));
		}
		return list;
	}

	public static Date getBeforeDate(Date d) {
		Date date = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 获取当前时间的前一天 yyyMMdd
	 * 
	 * @return str
	 */
	public static String getBeforeDate() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_8);
		return dateFormat.format(date);
	}

	/**
	 * 获取当前时间的前一天 yyyMM
	 * 
	 * @return str
	 */
	public static String getBeforeDate6String() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_6);
		return dateFormat.format(date);
	}

	/**
	 * 当前时间加上指定秒数
	 * 
	 * @return str
	 */
	public static Date addDate(Date d, long second) throws ParseException {
		long time = d.getTime();
		second = second * 60 * 1000;
		time += second;
		return new Date(time);

	}

	public static Date addSecond(Date d, long day) throws ParseException {
		long time = d.getTime();
		day = day * 24 * 60 * 60 * 1000;
		time += day;
		return new Date(time);

	}

	/**
	 * 在指定日期的基础上,添加monthNum个月
	 * 
	 * @param date
	 * @param monthNum
	 * @return
	 * @throws ParseException
	 */
	public static Date addMonth(Date dt, int monthNum) throws ParseException {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.MONTH, monthNum);
		return rightNow.getTime();
	}

	/**
	 * @throws ParseException
	 * 
	 * @Title: getSubMonthNum
	 * @Description: 获取两个日期的时间差
	 * @author qijian
	 * @param startDate
	 * @param endDate
	 * @return @throws
	 */
	public static int getSubMonthNum(Date startDate, Date endDate) throws ParseException {
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		endCalendar.setTime(endDate);
		if (endCalendar.compareTo(startCalendar) < 0) {
			System.out.println("后一时次的日期小于前一时次的日期，请重新输入。");
			return -1;
		}

		int day = endCalendar.get(Calendar.DAY_OF_MONTH) - startCalendar.get(Calendar.DAY_OF_MONTH);
		int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);

		if (day < 0) {
			month--;
		}

		if (month < 0) {
			month += 12;
			year--;
		}
		// System.out.println("两者相差年月为：" + year + "年" + month + "个月");
		// System.out.println("两者相差月为：" + (year*12 + month) + "个月");
		return year * 12 + month;
	}

	/**
	 * 
	 * @param day
	 * @return
	 */
	public static Date getAddOrSubtractDayDate(Integer day) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}

	/**
	 * 获取当前日期 add by:jackyshang之后的一年
	 * 
	 * @return
	 */
	public static Date getDateCalc() {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(System.currentTimeMillis());
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, 1);
		date = calendar.getTime();
		return date;
	}

	public static int getIntervalDays(Date fDate, Date oDate) {
		if (null == fDate || null == oDate) {
			return -1;
		}
		long intervalMilli = oDate.getTime() - fDate.getTime();
		return (int) (intervalMilli / (24 * 60 * 60 * 1000)) + 1;

	}

	public static void main(String[] args) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse("2008-07-04");
			Date oDate = sdf.parse("2008-07-10");
//				 Date date2 = sdf.parse( "2008-07-10 19:20:01" );
//				 long a = date.getTime();
//				  long b = date2.getTime();
//				  int c = (int)((a - b) / 1000);
			int aa = DateUtils.getIntervalDays(date, oDate);
			System.out.println(aa);
		} catch (ParseException e) {
		}
	}

	/**
	 * 获得指定日期的后一天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static Date getSpecifiedDayAfter(Date specifiedDay) {
		Calendar c = Calendar.getInstance();
		c.setTime(specifiedDay);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);
//			String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
		return c.getTime();
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static Date getSpecifiedDayFront(Date specifiedDay) {
		Calendar c = Calendar.getInstance();
		c.setTime(specifiedDay);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
//			String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
		return c.getTime();
	}

	/**
	 * 两个时间相差距离多少秒
	 * 
	 * @param startDate 时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2      时间参数 2 格式：2009-01-01 12:00:00
	 * @return endDate 返回值为：xx秒
	 */
	public static int calLastedTime(Date startDate, Date endDate) {
		long a = endDate.getTime();
		long b = startDate.getTime();
		int c = (int) ((a - b) / 1000);
		return c;
	}
}
