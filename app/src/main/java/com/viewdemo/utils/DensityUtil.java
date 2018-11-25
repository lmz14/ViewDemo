package com.viewdemo.utils;

import android.content.Context;

public class DensityUtil {
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context
     *            上下文，一般为Activity
     * @param pxValue
     *            px像素值
     * @return dp数据值
     */
    public static int px2dip(Context context, float pxValue) {
        // 密度因子
        final float scale = context.getResources()
                .getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context
     *            上下文，一般为Activity
     * @param dpValue
     *            dp数据值
     * @return px像素值
     */
    public static int dip2px(Context context, float dpValue) {
        // 密度因子
        final float scale = context.getResources()
                .getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
