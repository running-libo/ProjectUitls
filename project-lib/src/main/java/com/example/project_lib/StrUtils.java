package com.example.project_lib;

import android.text.TextUtils;
import android.text.format.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by libo on 2016/11/24.
 *
 * 字符串、数字操作工具类
 */
public class StrUtils {

	private static String patternCoder = "(?<!\\d)\\d{6}(?!\\d)";
	private static String phonePatter = "^1\\d{10}$";

	/** 获得当前时间 */
	public static CharSequence currentTime(CharSequence inFormat) {
		return DateFormat.format(inFormat, System.currentTimeMillis());
	}

	/**
	 * 时间戳转 yyyy年MM月dd日 HH:mm
	 * @param longTime
	 * @return
	 */
	public static String getDateTime(String longTime){
		long time = Long.valueOf(longTime)*1000;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		return  sdf.format(new Date(time));
	}

	/**
	 * 时间戳转 yyy年MM月dd日 HH:mm:ss
	 * @param longTime
	 * @return
	 */
	public static String getDateSec(String longTime){
		long time = Long.valueOf(longTime)*1000;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		return  sdf.format(new Date(time));
	}

	/**
	 * 时间戳转 MM月dd日 HH:mm
	 * @param longTime
	 * @return
	 */
	public static String getDateMinite(String longTime){
		long time = Long.valueOf(longTime)*1000;
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
		return  sdf.format(new Date(time));
	}

	/**
	 * 时间戳转 yyyy-MM-dd HH:mm
	 * @param longTime
	 * @return
	 */
	public static String getTime(String longTime){
		long time = Long.valueOf(longTime)*1000;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return  sdf.format(new Date(time));
	}

	/**
	 * 时间戳转 yyyy-MM-dd
	 * @param longTime
	 * @return
	 */
	public static String getdate(String longTime){
		long time = Long.valueOf(longTime)*1000;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return  sdf.format(new Date(time));
	}

	/**
	 * 日期转时间戳
	 * @return
	 */
	public static String getTimeStamp(String dateTime, String format){
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			return String.valueOf(simpleDateFormat.parse(dateTime).getTime()/1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 从短信中截取验证码
	 * @param patternContent
	 * @return
	 */
	public static String patternCode(String patternContent) {
		if (TextUtils.isEmpty(patternContent)) {
			return null;
		}
		Pattern p = Pattern.compile(patternCoder);
		Matcher matcher = p.matcher(patternContent);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

	/**
	 * 检测手机号码
	 * @param patternContent
	 * @return
	 */
	public static boolean checkPhone(String patternContent){
		Pattern pattern = Pattern.compile(phonePatter);
		Matcher matcher =  pattern.matcher(patternContent);
		return matcher.matches();
	}

	/**
	 * 保留指定小数点位数,format传 "0.0" "0.00"形式分别保存一位，两位小数
	 * @param num
	 * @param format
	 * @return
	 */
	public static String doubleRound(double num, String format){
		DecimalFormat df = new DecimalFormat(format);
		return df.format(num);
	}

	/**
	 * 判断单个字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isStr(String str){
		if(null != str && str.length() != 0) return true;
		return false;
	}

	/**
	 * 判断多个字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isArrStr(String... str){
		if(null == str) return false;
		for(String s : str){
			if(!isStr(s)) return false;
		}
		return true;
	}
	
}
