package com.sallai.music.utils;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Description: 字符编码转换工具类
 * <p>
 * Author:
 * Date: 2023/8/15
 */
public class CharEncodeUtil {
    /**
     * unicode转utf8
     */
    public static String unicodeToUtf8(String str) {
        if(null == str) return "";
        return StringEscapeUtils.unescapeJava(str);
    }
}
