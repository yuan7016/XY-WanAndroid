package com.xyuan.wanandroid.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import java.util.List;

/**
 * SharedPreferences工具类
 */
public class SharedPreferencesUtil {

    static SharedPreferences sharedPreferences;

    /**
     * 初始化SharedPreferences
     */
    public static void init(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 保存int键值对
     *
     * @param key
     * @param value
     */
    public static void setPreferInt(String key, int value) {
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 根据key获取int值
     *
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public static int getPreferInt(String key,int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    /**
     * 保存字符串键值对
     *
     * @param key
     * @param value
     */
    public static void setPreferString(String key, String value) {
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 根据key获取字符串value
     *
     * @param key
     * @return
     */
    public static String getPreferString(String key) {
        return sharedPreferences.getString(key, "");
    }

    /**
     * 保存对象的键值对
     *
     * @param t
     */
    public static <T> void setObj(String key, T t) {
        Editor editor = sharedPreferences.edit();
        editor.putString(key, JSON.toJSONString(t));
        editor.apply();
    }


    public static boolean getPreferBool(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public static void setPreferBool(String key) {
        sharedPreferences.edit().putBoolean(key, true).apply();
    }

    public static void setPreferBool(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static void remove(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    /**
     * 根据key获取对象
     *
     * @param key
     * @return
     */
    public static <T> T getObj(String key, Class<T> clazz) {
        T t = null;
        try {
            String e = sharedPreferences.getString(key, "");
            if (!TextUtils.isEmpty(e)) {
                t = JSON.parseObject(e, clazz);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return t;
    }

    /**
     * 根据key获取对象
     *
     * @param key
     * @return
     */
    public static <T> List<T> getList(String key, Class<T> clazz) {
        List<T> t = null;
        try {
            String e = sharedPreferences.getString(key, "");
            if (!TextUtils.isEmpty(e)) {
                t = JSON.parseArray(e, clazz);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return t;
    }

    /**
     * 清除保存在SP中的信息
     * @return
     */
    public static boolean clearAllSPData(){
        Editor editor = sharedPreferences.edit();
        editor.clear();

        return editor.commit();
    }
}
