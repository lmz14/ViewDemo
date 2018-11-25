package com.viewdemo.utils;

import android.graphics.Paint;
import android.text.TextPaint;

public class TextUtil {

    /**
     * 给定文字的center获取文字的base line
     */
    public static float getTextBaseLineByCenter(float center, TextPaint paint, int size) {
        paint.setTextSize(size);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float height = fontMetrics.bottom - fontMetrics.top;
        return center + height / 2 - fontMetrics.bottom;
    }
}
