package com.example.asus.xyd_order.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	public static boolean isEmpty(String text){
		if(text == null || "".equals(text) || "null".equals(text)){
			return true;
		}
		return false;
	}
	
	public static boolean isEmail(String strEmail)
	{
		String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail);
		return m.matches();
	}
	
	public static boolean isUserName(String strUserName) {
		String strPattern = "^[_0-9a-z]{5,20}$";

		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strUserName);
		return m.matches();
	}

	public static boolean isPassword(String strPassword) {
		/**
		 * 8-15位包含数字和字母
		 */
		String strPattern = "^(?![^a-zA-Z]+$)(?!\\D+$).{8,15}$";

		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strPassword);
		return m.matches();
	}

	public static boolean isPhone(String phoneNumber) {
		String pattern = "^((13[0-9])|(15[^4,\\D])|(18[0,2-9]))\\d{8}$";
		
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(phoneNumber);
		return m.matches();
	}
	
	public static boolean isNumber(String phoneNumber) {
		String pattern = "^[0-9]*$";
		
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(phoneNumber);
		return m.matches();
	}
	
	public static String formatMoney(double money) {
		String moneyStr = "";
		if(money >= 10000 && money < 100000000) {
			moneyStr = (money / 10000) + "万元";
		} else if(money > 0 && money < 10000) {
			moneyStr = money + "元";
		}
		return moneyStr;
	}
	
	/** 文字局部变色 */
	public static void setTextColorOfPart(Context context, TextView tv, int colorRes, int start, int end) {
		SpannableStringBuilder spannable = new SpannableStringBuilder(
				tv.getText().toString());// 用于可变字符串
		ForegroundColorSpan span = new ForegroundColorSpan(context.getResources()
				.getColor(colorRes));
		spannable.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv.setText(spannable);
	}

	/**
	 * 获取当前时间
	 *
	 * @return
	 */
	public static String getTime() {
		Date date = new Date();// 创建一个时间对象，获取到当前的时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");// 设置时间显示格式

		return sdf.format(date);
	}

	/**
	 * 时间戳转换成时间
	 *
	 * @return
	 */
	public static String getTime(String str, String format) {

		Date date = new Date(Long.valueOf(str)*1000L);// 创建一个时间对象，获取到当前的时间
		//"yyyy年MM月dd HH:mm:ss"
		SimpleDateFormat sdf = new SimpleDateFormat(format);// 设置时间显示格式

		return sdf.format(date);
	}
	/**
	 * 流转换成字符串
	 */
	public static String streamToString(InputStream stream){
		BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(stream));
		StringBuffer stringBuffer=new StringBuffer();
		String tempBuffer=new String();
		try {
			while ((tempBuffer=bufferedReader.readLine())!=null){
				stringBuffer.append(tempBuffer);
			}
            return stringBuffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 字符串转换成流
	 */
	public static InputStream stringToStream(String string){
		if (!TextUtils.isEmpty(string)&&!TextUtils.isEmpty(string.trim())){
			ByteArrayInputStream bis=new ByteArrayInputStream(string.getBytes());
			return bis;
		}


		return null;
	}
}
