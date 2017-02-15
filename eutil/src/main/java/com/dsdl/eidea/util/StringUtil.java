/******************************************************************************
 * 版权所有 刘大磊 2013-07-01												   *
 *	作者：刘大磊								                               *
 * 电话：13336390671                                                           *
 * email:ldlqdsd@126.com						                               *
 *****************************************************************************/

package com.dsdl.eidea.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 刘大磊 2014年12月20日 下午9:24:37
 */
public class StringUtil {
	/**
	 * 判断字符串是否为空
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}
	/**
	 * 非空判断
	 * @param value
	 * @return
	 */
	public static boolean isNotEmpty(String value)
	{
		return !isEmpty(value);
	}
	
	/** 
	 * @Title:        fullYhStr 
	 * @Description:  单引号变为双引号
	 * @param:        @param value
	 * @param:        @return    
	 * @return:       String    
	 * @throws 
	 * @author        Charles Luo
	 * @Date          2015年6月1日 上午11:49:59 
	 */
	public static String fullYhStr(String value)
	{
		return value.replaceAll("'", "''");
	}
	
	
	public static boolean isInteger(String value)
	{
		 Pattern pattern = Pattern.compile("[0-9]*"); 
		 Matcher isNum = pattern.matcher(value);
        return isNum.matches();
    }
	
	
	public static String convertToSqlParam(String value)
	{
		value=value.replaceAll("'", "''");
		value=value.replaceAll(",", "','");
		value="'"+value+"'";
		
		return value;
		
	}
	
	
    public static boolean isEmail(String email){  
        if (null==email || "".equals(email)) return false;    
//      Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配  
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配  
        Matcher m = p.matcher(email);  
        return m.matches();  
       }

	/**
	 * 对象属性转换为字段  例如：userName to user_name
	 * @param property 字段名
	 * @return
	 */
	public static String propertyToField(String property) {
		if (null == property) {
			return "";
		}
		char[] chars = property.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char c : chars) {
			if (Character.isUpperCase(c)) {
				sb.append("_").append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 字段转换成对象属性 例如：user_name to userName
	 * @param field
	 * @return
	 */
	public static String fieldToProperty(String field) {
		if (null == field) {
			return "";
		}
		char[] chars = field.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == '_') {
				int j = i + 1;
				if (j < chars.length) {

					sb.append(String.valueOf(chars[j]).toUpperCase());
					i++;
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	public static String lowerFirstChar(String name) {
		name = name.substring(0, 1).toLowerCase() + name.substring(1);
		return  name;

	}
	public static String upperFirstChar(String name) {
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		return  name;

	}
}
