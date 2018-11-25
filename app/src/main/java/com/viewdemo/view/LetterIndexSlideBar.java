package com.viewdemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.viewdemo.R;
import com.viewdemo.utils.DensityUtil;
import com.viewdemo.utils.TextUtil;

import java.util.ArrayList;
import java.util.List;
/**
 * @author linmeizhen
 * @date 2018/9/20
 * @description
 */
public class LetterIndexSlideBar extends View{
    private List<String> mLetters;
    private RectF mSlideBarRect;
    private Paint mPaint;
    private Paint mSelectPaint;
    private TextPaint mTextPaint;

    private int mBackgroundColor;
    private int mTextSize;
    private int mTextColor;

    /**
     * slide bar 内容高度
     */
    private float mBarHeight;

    /**
     * slide bar 内容宽度
     */
    private int mBarWidth;

    /**
     * slide bar 内容上下左右padding
     */
    private int mPaddingLeft,mPaddingRight,mPaddingTop,mPaddingBottom;

    /**
     * 每个字母上或下padding
     */
    private int mContentPadding;

    /**
     * 右侧提示view
     */
    private int mLeftHintViewWidth;
    private int mLeftHintViewHeight;
    private int mLeftHintViewBgRes;
    private int mHintTextSize;
    private int mHintTextColor;
    private Bitmap mLeftHintViewBgBitmap;

    /**
     * slide bar 选中字体
     */
    private int mSelectTextColor;
    private int mSelectTextSize;
    private int mSelectCircleRadius;
    private int mSelectCircleBackgroundColor;

    private int mSelect;
    private int mPreSelect;
    private int mNewSelect;
    private int mTouchY;
    private float mAnimationRatio;
    private ValueAnimator mRatioAnimator;
    private OnLetterChangeListener mListener;

    public LetterIndexSlideBar(Context context) {
        this(context,null);
    }

    public LetterIndexSlideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LetterIndexSlideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute(attrs,defStyleAttr);
        initData();
    }

    private void initAttribute(AttributeSet attrs, int defStyleAttr){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LetterIndexSlideBar,defStyleAttr,0);
        mBackgroundColor = typedArray.getColor(R.styleable.LetterIndexSlideBar_backgroundColor, Color.parseColor("#00000000"));
        mTextColor = typedArray.getColor(R.styleable.LetterIndexSlideBar_textColor, Color.parseColor("#888888"));
        mTextSize = typedArray.getDimensionPixelOffset(R.styleable.LetterIndexSlideBar_textSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10,getResources().getDisplayMetrics()));
        mContentPadding = typedArray.getDimensionPixelOffset(R.styleable.LetterIndexSlideBar_contentPadding,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3,
                        getResources().getDisplayMetrics()));
        mPaddingLeft = typedArray.getDimensionPixelOffset(R.styleable.LetterIndexSlideBar_paddingLeft,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0,
                        getResources().getDisplayMetrics()));
        mPaddingRight = typedArray.getDimensionPixelOffset(R.styleable.LetterIndexSlideBar_paddingRight,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0,
                        getResources().getDisplayMetrics()));
        mPaddingTop = typedArray.getDimensionPixelOffset(R.styleable.LetterIndexSlideBar_paddingTop,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0,
                        getResources().getDisplayMetrics()));
        mPaddingBottom = typedArray.getDimensionPixelOffset(R.styleable.LetterIndexSlideBar_paddingBottom,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0,
                        getResources().getDisplayMetrics()));

        mBarWidth = typedArray.getDimensionPixelOffset(R.styleable.LetterIndexSlideBar_barWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
                        getResources().getDisplayMetrics()));

        mLeftHintViewWidth = typedArray.getDimensionPixelOffset(R.styleable.LetterIndexSlideBar_leftHintViewWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0,
                        getResources().getDisplayMetrics()));

        mLeftHintViewHeight = typedArray.getDimensionPixelOffset(R.styleable.LetterIndexSlideBar_leftHintViewHeight,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 49,
                        getResources().getDisplayMetrics()));

        mLeftHintViewBgRes = typedArray.getResourceId(R.styleable.LetterIndexSlideBar_leftHintViewBgRes,-1);

        mHintTextSize = typedArray.getDimensionPixelOffset(R.styleable.LetterIndexSlideBar_hintTextSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 30,
                        getResources().getDisplayMetrics()));
        mHintTextColor = typedArray.getColor(R.styleable.LetterIndexSlideBar_hintTextColor, Color.parseColor("#FFFFFF"));

        mSelectTextColor = typedArray.getColor(R.styleable.LetterIndexSlideBar_selectTextColor, Color.parseColor("#FFFFFF"));

        mSelectTextSize = typedArray.getDimensionPixelOffset(R.styleable.LetterIndexSlideBar_selectTextSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10,
                        getResources().getDisplayMetrics()));

        mSelectCircleBackgroundColor = typedArray.getColor(R.styleable.LetterIndexSlideBar_selectCircleBackgroundColor, Color.parseColor("#ff3c4c"));

        mSelectCircleRadius = typedArray.getDimensionPixelOffset(R.styleable.LetterIndexSlideBar_selectCircleRadius,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6,
                        getResources().getDisplayMetrics()));

        typedArray.recycle();

        /**
         * 长按事件必须设置为true，否则dispatchTouchEvent中的move事件无法监听到
         */
        setLongClickable(true);
        setClickable(true);
    }

    private void initData(){
        mLetters = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mSelectPaint = new Paint();
        mSelectPaint.setAntiAlias(true);
        mTextPaint = new TextPaint();
        if(mLeftHintViewBgRes!=-1){
            mLeftHintViewBgBitmap = BitmapFactory.decodeResource(getResources(),mLeftHintViewBgRes);
        }
        mSelect = -1;
    }

    public void update(List<String> letters){
        mLetters = letters;
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mSlideBarRect == null) {
            mSlideBarRect = new RectF();
        }

        float contentLeft = getMeasuredWidth() - mBarWidth - mPaddingRight;
        float contentRight = getMeasuredWidth() - mPaddingRight;
        float contentTop = mPaddingTop;
        float contentBottom = (float) (getMeasuredHeight() - mPaddingBottom);
        if(mBarHeight >0){
            contentBottom = mBarHeight + mPaddingTop;
        }
        mSlideBarRect.set(contentLeft, contentTop, contentRight, contentBottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mLetters.size()>0){
            //绘制slide bar 上字母列表
            drawLetters(canvas);
            //绘制左侧提示
            drawLeftHintView(canvas);
            //绘制选中时的提示信息(圆＋文字)
            drawSelect(canvas);
        }
        Log.e("test221","onDraw mselect:"+mSelect);
    }

    private float itemHeight;
    /**
     * 绘制slide bar 上字母列表
     * @param canvas
     */
    private void drawLetters(Canvas canvas){
        //绘制矩形
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mBackgroundColor);
        canvas.drawRect(mSlideBarRect,mPaint);
        //顺序绘制文字
        itemHeight = (mSlideBarRect.bottom - mSlideBarRect.top - mContentPadding * 2) / mLetters.size();
        for (int index = 0; index < mLetters.size(); index++){
            float baseLine = TextUtil.getTextBaseLineByCenter(
                    mSlideBarRect.top + mContentPadding + itemHeight * index + itemHeight / 2, mTextPaint, mTextSize);
            mTextPaint.setColor(mTextColor);
            mTextPaint.setTextSize(mTextSize);
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            mTextPaint.setTypeface(Typeface.create(mLetters.get(index), Typeface.BOLD));
            float pointX = mSlideBarRect.left + (mSlideBarRect.right - mSlideBarRect.left) / 2.0f;
            canvas.drawText(mLetters.get(index), pointX, baseLine, mTextPaint);
        }
    }

    /**
     * 绘制右侧提示view背景
     * @param canvas
     */
    private void drawLeftHintView(Canvas canvas){
        if(mSelect != -1){
            if(mLeftHintViewBgBitmap!=null){
                float radius = mLeftHintViewHeight/2;
                float left = getMeasuredWidth() - mPaddingRight - mPaddingLeft - mBarWidth - mLeftHintViewWidth;
                float top = mTouchY- radius;
                float minTop = mSlideBarRect.top + itemHeight/2 - radius + mContentPadding;
                float maxTop = mSlideBarRect.bottom - itemHeight/2 - radius - mContentPadding;
                if(mTouchY == 0 || top <= minTop){
                    top = minTop;
                    mCurrentSelect = mSelect = 0;
                }else if(top >= maxTop){
                    top = maxTop;
                    mCurrentSelect = mSelect = mLetters.size()-1;
                }
                //将图画到画布上，并设置图片右上角点坐标
                canvas.drawBitmap(mLeftHintViewBgBitmap,left,top,null);

                // 绘制提示字符
//                if (mAnimationRatio >= 0.9f && mSelect!=-1) {
                    float textY = TextUtil.getTextBaseLineByCenter(mTouchY, mTextPaint, mHintTextSize);
                    float maxTextY = TextUtil.getTextBaseLineByCenter(maxTop + radius, mTextPaint, mHintTextSize);
                    float minTextY = TextUtil.getTextBaseLineByCenter(minTop + radius,mTextPaint, mHintTextSize);
                    if(textY >= maxTextY){
                        textY = maxTextY;
                    }else if(textY <= minTextY){
                        textY = minTextY;
                    }
                    mTextPaint.setColor(mHintTextColor);
                    mTextPaint.setTextSize(mHintTextSize);
                    mTextPaint.setTextAlign(Paint.Align.CENTER);
                    canvas.drawText(mLetters.get(mSelect), left + radius, textY, mTextPaint);
//                }
            }
        }
    }

    /**
     * 保存当前选中位置，不重置-1，区别于mSelect
     */
    private int mCurrentSelect;

    /**
     * 绘制选中时的提示信息(圆＋文字)
     */
    private void drawSelect(Canvas canvas){
        if (mCurrentSelect != -1){
            mSelectPaint.setStyle(Paint.Style.FILL);
            mSelectPaint.setColor(mSelectCircleBackgroundColor);
            mTextPaint.setColor(mSelectTextColor);
            mTextPaint.setTextSize(mSelectTextSize);
            mTextPaint.setTextAlign(Paint.Align.CENTER);

            float pointY = mSlideBarRect.top + mContentPadding + itemHeight * mCurrentSelect + itemHeight / 2;
            float baseLine = TextUtil.getTextBaseLineByCenter(pointY, mTextPaint, mTextSize);
            float pointX = mSlideBarRect.left + (mSlideBarRect.right - mSlideBarRect.left) / 2.0f;
            //绘制圆(算出来的圆心实际显示时，文字会有点偏下，这里加上0.5dp，降低圆心高度)
            canvas.drawCircle(pointX, pointY + DensityUtil.dip2px(getContext(),0.5f), mSelectCircleRadius, mSelectPaint);
            //绘制文字
            canvas.drawText(mLetters.get(mCurrentSelect), pointX, baseLine, mTextPaint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final float y = event.getY();
        final float x = event.getX();
        mPreSelect = mSelect;
        mNewSelect = (int) ((y - mPaddingTop) / (mSlideBarRect.bottom - mSlideBarRect.top) * mLetters.size());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //保证down的时候在bar区域才响应事件
                if (x < mSlideBarRect.left || y < mSlideBarRect.top || y > mSlideBarRect.bottom) {
                    return false;
                }
                mTouchY = (int) y;
                if (mPreSelect != mNewSelect && mNewSelect >= 0 && mNewSelect < mLetters.size()) {
                    mCurrentSelect = mSelect = mNewSelect;
                    if (mListener != null) {
                        mListener.onLetterChange(mLetters.get(mNewSelect));
                    }
                }
                invalidate();
                startAnimator(1.0f);
                break;
            case MotionEvent.ACTION_MOVE:
                mTouchY = (int) y;
                if (mPreSelect != mNewSelect && mNewSelect >= 0 && mNewSelect < mLetters.size()) {
                    mCurrentSelect = mSelect = mNewSelect;
                    if (mListener != null) {
                        mListener.onLetterChange(mLetters.get(mNewSelect));
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                startAnimator(0f);
                mSelect = -1;

                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private void startAnimator(float value) {
        if (mRatioAnimator == null) {
            mRatioAnimator = new ValueAnimator();
        }
        mRatioAnimator.cancel();
        mRatioAnimator.setFloatValues(value);
        mRatioAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator value) {
                mAnimationRatio = (float) value.getAnimatedValue();
                //球弹到位的时候，并且点击的位置变了，即点击的时候显示当前选择位置
                if (mAnimationRatio == 1f && mPreSelect != mNewSelect) {
                    if (mNewSelect >= 0 && mNewSelect < mLetters.size()) {
                        mSelect = mNewSelect;
                        if (mListener != null) {
                            mListener.onLetterChange(mLetters.get(mNewSelect));
                        }
                    }
                }
                invalidate();
            }
        });
        mRatioAnimator.start();
    }

    public void setOnLetterChangeListener(OnLetterChangeListener listener) {
        this.mListener = listener;
    }

    public interface OnLetterChangeListener {
        void onLetterChange(String letter);
    }

    public void setBarHeight(float mBarHeight){
        this.mBarHeight = mBarHeight;
    }

    public void recycle(){
        if(mLeftHintViewBgBitmap != null){
            mLeftHintViewBgBitmap.recycle();
        }
        if(mRatioAnimator!=null){
            mRatioAnimator.cancel();
            clearAnimation();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        recycle();
    }
}
