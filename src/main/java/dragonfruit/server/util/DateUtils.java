package dragonfruit.server.util;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Xuyh at 2016/08/05 下午 06:58.
 */
public class DateUtils {
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String DATE_HOUR_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";
	private static final String YEAR_MONTH_FORMAT = "yyyy-MM";
	private static final String YEAR_FORMAT = "yyyy";

	/**
	 * 得到当前时间
	 *
	 * @return 当前时间,格式"yyyy-MM-dd HH:mm:ss"
	 */
	public static String currentDate() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String currentTime = sdf.format(dt);
		return currentTime;
	}

	/**
	 * 得到当前时间
	 * 
	 * @return
	 */
	public static Date currentDateForDate() {
		return parseDate(currentDate());
	}

	/**
	 * 得到当前时间
	 *
	 * @return 当前时间,格式"yyyy-MM-dd HH:mm:ss"
	 */
	public static String currentDateTime() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
		String currentTime = sdf.format(dt);
		return currentTime;
	}

	/**
	 * 得到当前时间
	 * 
	 * @return
	 */
	public static Date currentDateTimeForDate() {
		return parseDateTime(currentDateTime());
	}

	/**
	 * 获取当前时间差值为amount小时的时间戳
	 * 
	 * <pre>
	 * 	amount:
	 * 	-4	当前时间四小时前的时间戳
	 *	3	当前时间三小时后的时间戳
	 *	0	当前时间的时间戳
	 * </pre>
	 * 
	 * @return
	 */
	public static Long getTimeStamp(int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR_OF_DAY, amount);
		return calendar.getTime().getTime();
	}

	/**
	 * 得到格式化时间
	 *
	 * @return 当前时间
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String dateFormate = sdf.format(date);
		return dateFormate;
	}

	/**
	 * 把20160203 转换成2016-02-03形式
	 *
	 * @param date 如20160203
	 * @return 如2016-02-03
	 */
	public static String formatDate(Integer date) {
		String str = date.toString();
		if (str.length() == 8) {
			str = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);
		} else if (str.length() == 6) {
			str = str.substring(0, 4) + "-" + str.substring(4, 6);
		}
		return str;
	}

	/**
	 * 得到格式化时间
	 *
	 * @return 当前时间
	 */
	public static String formatDateTime(Date date) {
		if (date == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
		String dateFormate = sdf.format(date);
		return dateFormate;
	}

	/**
	 * 得到格式化时间
	 *
	 * @return 当前时间
	 */
	public static String formatYear(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(YEAR_FORMAT);
		String dateFormate = sdf.format(date);
		return dateFormate;
	}

	/**
	 * 得到格式化时间
	 *
	 * @return 当前时间
	 */
	public static String formatYearMonth(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MONTH_FORMAT);
		String dateFormate = sdf.format(date);
		return dateFormate;
	}

	/**
	 * 得到格式化时间yyyy-MM-dd HH:mm
	 *
	 * @return 当前时间
	 */
	public static String formatDateHourMinute(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_HOUR_MINUTE_FORMAT);
		String dateFormate = sdf.format(date);
		return dateFormate;
	}

	/**
	 * 得到与传入日期相差天数的日期
	 *
	 * @param date 传入日期
	 * @param diff 相差天数
	 * @return
	 */
	public static Date getDateDayByDiff(Date date, Integer diff) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, diff);
		return c.getTime();
	}

	/**
	 * 得到与传入日期相差月数的日期
	 *
	 * @param date 传入日期
	 * @param diff 相差月数
	 * @return
	 */
	public static Date getDateMonthByDiff(Date date, Integer diff) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, diff);
		return c.getTime();
	}

	/**
	 * 得到某年某月的第一天
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));

		return c.getTime();
	}

	/**
	 * 得到某年某季度第一天
	 *
	 * @param year
	 * @param quarter
	 * @return
	 */
	public static Date getFirstDayOfQuarter(int year, int quarter) {
		int month = 0;
		if (quarter > 4) {
			return null;
		} else {
			month = (quarter - 1) * 3 + 1;
		}
		return getFirstDayOfMonth(year, month);
	}

	/**
	 * 得到某年某周的第一天
	 *
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.WEEK_OF_YEAR, week);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 设置周一
		c.setFirstDayOfWeek(Calendar.MONDAY);

		return c.getTime();
	}

	/**
	 * 得到某年第一天
	 *
	 * @param year
	 * @return
	 */
	public static Date getFirstDayOfYear(int year) {
		return getFirstDayOfQuarter(year, 1);
	}

	/**
	 * 得到某年某月的最后一天
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));

		return c.getTime();
	}

	/**
	 * 得到某年某季度最后一天
	 *
	 * @param year
	 * @param quarter
	 * @return
	 */
	public static Date getLastDayOfQuarter(int year, int quarter) {
		int month = 0;
		if (quarter > 4) {
			return null;
		} else {
			month = quarter * 3;
		}
		return getLastDayOfMonth(year, month);
	}

	/**
	 * 得到某年某周的最后一天
	 *
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getLastDayOfWeek(int year, int week) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.WEEK_OF_YEAR, week);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday

		return c.getTime();
	}

	/**
	 * 得到某年最后一天
	 *
	 * @param year
	 * @return
	 */
	public static Date getLastDayOfYear(int year) {
		return getLastDayOfQuarter(year, 4);
	}

	/**
	 * 得到某一年周的总数
	 *
	 * @param year
	 * @return
	 */
	public static int getMaxWeekNumOfYear(int year) {
		Calendar c = Calendar.getInstance();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

		return getWeekOfYear(c.getTime());
	}

	/**
	 * 取得当前日期是多少周
	 *
	 * @param date
	 * @return
	 */
	public static int getWeekOfYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		/**
		 * 设置一年中第一个星期所需的最少天数，例如，如果定义第一个星期包含一年第一个月的第一天，则使用值 1 调用此方法。
		 * 如果最少天数必须是一整个星期，则使用值 7 调用此方法。
		 **/
		c.setMinimalDaysInFirstWeek(1);
		c.setTime(date);

		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 返回与当前时间相差小时数的时间
	 *
	 * @param diff 相差的小时
	 * @return 时刻的小时
	 */
	public static int hoursBeforeNow(Integer diff) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.HOUR, -diff);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * string类型转成Date
	 *
	 * @param date 传入str类型
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Long类型时间戳转换成Date
	 * 
	 * @param timeStamp
	 * @return
	 */
	public static Date parseDate(Long timeStamp) {
		Format formatter = new SimpleDateFormat(DATE_FORMAT);
		return parseDate(formatter.format(timeStamp));
	}

	/**
	 * string类型转成Date
	 *
	 * @param date 传入str类型
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseDateTime(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Long类型时间戳转换成Date
	 * 
	 * @param timeStamp
	 * @return
	 */
	public static Date parseDateTime(Long timeStamp) {
		Format formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
		return parseDateTime(formatter.format(timeStamp));
	}
}
