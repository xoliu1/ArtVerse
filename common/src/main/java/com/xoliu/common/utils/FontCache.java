package com.xoliu.common.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局字体缓存工具类
 * 从 assets/fonts/ 目录加载字体并全局缓存，避免重复加载导致 OOM。
 *
 * 用法：
 *   FontCache.apply(textView, context, FontCache.FONT8);
 *   // 或
 *   Typeface tf = FontCache.get(context, FontCache.FONT4);
 *   textView.setTypeface(tf);
 */
public class FontCache {

    // 字体名常量
    public static final String FONT4 = "font4.ttf";
    public static final String FONT8 = "font8.ttf";
    public static final String FONT11 = "font11.ttf";
    public static final String POMO = "pomo.ttf";

    private static final Map<String, Typeface> cache = new HashMap<>();

    /**
     * 获取缓存的 Typeface，不存在则从 assets 加载
     */
    public static Typeface get(Context context, String fontFileName) {
        Typeface cached = cache.get(fontFileName);
        if (cached != null) {
            return cached;
        }
        try {
            Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontFileName);
            cache.put(fontFileName, tf);
            return tf;
        } catch (Exception e) {
            // 字体加载失败时返回默认字体，不崩溃
            return Typeface.DEFAULT;
        }
    }

    /**
     * 快捷方法：直接给 TextView 设置字体
     */
    public static void apply(TextView textView, Context context, String fontFileName) {
        if (textView != null && context != null) {
            textView.setTypeface(get(context, fontFileName));
        }
    }
}
