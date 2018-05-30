package com.exam.cn.baselibrary.db;

import android.text.TextUtils;

import java.util.Locale;

/**
 * Created by admin on 2017/8/8.
 */

public class DaoUtil {


    /**
     * 数据类型转换
     * @param type
     * @return
     */
    public static String getColumnType(String type) {
        String value = null;
        if (type.contains("String")) {
            value = " text";
        } else if (type.contains("int")) {
            value = " integer";
        } else if (type.contains("boolean")) {
            value = " boolean";
        } else if (type.contains("float")) {
            value = " float";
        } else if (type.contains("double")) {
            value = " double";
        } else if (type.contains("char")) {
            value = " varchar";
        } else if (type.contains("long")) {
            value = " long";
        }
        return value;
    }

    public static String getTableName(Class<?> clazz) {
        return clazz.getSimpleName();
    }

    /**
     * 首字母大写
     * @param string
     * @return
     */
    public static String capitalize(String string) {
        if (!TextUtils.isEmpty(string)) {
            return string.substring(0, 1).toUpperCase(Locale.US) + string.substring(1);
        }
        return string == null ? null : "";
    }
}
