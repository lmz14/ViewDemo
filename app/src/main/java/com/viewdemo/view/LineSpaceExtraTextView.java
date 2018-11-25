package com.viewdemo.view;

import android.content.Context;
import android.graphics.Rect;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;

public class LineSpaceExtraTextView extends android.support.v7.widget.AppCompatTextView{

    private final String TAG = LineSpaceExtraTextView.class.getSimpleName();
    private Rect mLastLineShowRect;
    private Rect mLastLineActualIndexRect;

    public LineSpaceExtraTextView(Context context) {
        super(context);
        init();
    }

    public LineSpaceExtraTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LineSpaceExtraTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mLastLineShowRect = new Rect();
        mLastLineActualIndexRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //去除显示的最后一行的行间距
        //设置行间距 && 设置MaxLines && 实际行数大于MaxLines时，显示的最后一行会增加行间距
        //Redmi 3(android 5.1.1)
        //HuaWei nova youth(EMUI 5.1 andorid 7.0)
        //oppo R7(ColorOs v2.1 android 4.4.4)，只要设置了间距，默认最后一行都会增加间距
        setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight() - calculateExtraSpace());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private int calculateExtraSpace(){
        int lastRowSpace = 0;
        if(getLineCount()>0){
            //实际最后一行
            int actualLastRowIndex = getLineCount() - 1;
            //显示的最后一行
            int lastRowIndex = Math.min(getMaxLines(),getLineCount()) - 1;
            if(lastRowIndex >= 0){
                Layout layout = getLayout();
                //显示的最后一行文字基线坐标
                int baseline = getLineBounds(lastRowIndex, mLastLineShowRect);
                getLineBounds(actualLastRowIndex, mLastLineActualIndexRect);
                //测量显示的高度（measureHeight）等于TextView实际高度（layout.getHeight()）或者等于实际高度减去不可见部分的高度（mLastLineActualIndexRect.bottom - mLastLineShowRect.bottom）
                if (getMeasuredHeight() == layout.getHeight() - (mLastLineActualIndexRect.bottom - mLastLineShowRect.bottom)) {
                    lastRowSpace = mLastLineShowRect.bottom - (baseline + layout.getPaint().getFontMetricsInt().descent);
                }
//                Log.e(TAG,"baseline:"+baseline);
//                Log.e(TAG,"descent:"+layout.getPaint().getFontMetricsInt().descent);
//                Log.e(TAG,"lastRowIndex:"+lastRowIndex+",actualLastRowIndex:"+actualLastRowIndex+",lineCount:"+getLineCount());
//                Log.e(TAG,"getMeasuredHeight():"+getMeasuredHeight());
//                Log.e(TAG,"layout.getHeight():"+layout.getHeight());
//                Log.e(TAG,"mLastLineActualIndexRect.bottom:"+mLastLineActualIndexRect.bottom);
//                Log.e(TAG,"mLastLineShowRect.bottom:"+mLastLineShowRect.bottom);
//                Log.e(TAG,"-----------------------------------");
            }
        }
        return lastRowSpace;
    }
}
