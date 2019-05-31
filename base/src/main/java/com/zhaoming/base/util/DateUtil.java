package com.zhaoming.base.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author huhuixin
 */
@UtilityClass
public class DateUtil {

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat fullDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	public static SimpleDateFormat SDF_MINUTE = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	/**
	 * 根据标准时间字符串获取时间long
	 * @param dateTime
	 * @return
	 * @throws ParseException 
	 */
	public static long getTime(String dateTime) throws ParseException{
		return sdf.parse(dateTime).getTime();
	}
	/**
	 * 根据标准时间字符串逆向生成Date
	 * @param dateTime
	 * @return
	 * @throws ParseException 
	 */
	public static Date getDatePart(String dateTime) throws ParseException{
		return sdf_date.parse(dateTime);
	}
	/**
	 * 根据标准时间字符串逆向生成Date
	 * @param dateTime
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(String dateTime) throws ParseException{
		return sdf.parse(dateTime);
	}
	public static Date getDate(String dateTime,SimpleDateFormat format) throws ParseException{
		return format.parse(dateTime);
	}
	/**
	 * 根据毫秒数生成时间
	 * @param time
	 * @return
	 */
	public static Date getDate(long time){
		return new Date(time);
	}
	/**
	 * 根据毫秒数获取时间字符串
	 * @param time
	 * @return
	 */
	public static String getString(long time){
		return sdf.format(getDate(time));
	}
	/**
	 * 获取标准当前时间字符串
	 * @return
	 */
	public static String getNow(){
		return sdf.format(new Date());
	}
	public static String getNow(SimpleDateFormat format){
		return format.format(new Date());
	}
	/**
	 * 将Date用字符串表示
	 * @param date
	 * @return
	 */
	public static String getString(Date date){
		return sdf.format(date);
	}

	
	public static String getFullString(Date d){
		return fullDate.format(d);
	}
	
	public static Date getDate(Date d,int day){  
		Calendar now = Calendar.getInstance();  
		now.setTime(d);  
		now.set(Calendar.DATE,now.get(Calendar.DATE)+day);  
		return now.getTime();  
	}

	/**
	 * 返回true时,说明已经过期
	 * @return
	 */
	public static boolean isOverTime(Date time) {
		if (null!=time) {
			return time.compareTo(new Date()) <= 0;
		}
		return false;
	}

	/**
	 * 目录格式获取今日日期字符串
	 * @return
	 */
	public static String getDateForFolder(String basePath) {
		return basePath+ FastDateFormat.getInstance("yyyy/MM/dd/").format(new Date());
	}
	public static Date pushMonth(Integer num){
		if (num==null){
			return null;
		}
		Date d;
		Calendar ca = Calendar.getInstance();
		// num为增加的月数，可以改变的
		ca.add(Calendar.MONTH, num);
		d = ca.getTime();
		return d;
	}
	/**
	 * 判断时间是否在时间段内(传入的年月)
	 * @param nowTime SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		if(end.before(begin)){
			end.add(Calendar.DATE, 1);
		}
		return date.after(begin) && date.before(end);
	}
}
