package com.qipilang.lvyouplatform.util;

import org.apache.commons.lang.StringUtils;

/**************************************************************************
 * 
 * DESCRIPTION:	对字符串管理
 * 
 * @author 		张建国
 *
 * @since 		2016.3.4
 * 
 * @version 	1.0
 *
 *************************************************************************/
public class StringUtil {
    public static boolean isEmpty(String str){
        if(str != null){
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
    public static boolean checkLength(int defaultLength, String str){
    	int strLength = str.length();
    	return defaultLength > strLength;
    }
    public static boolean isEqual(String str1, String str2){
    	return str1.equals(str2);
    }
}
