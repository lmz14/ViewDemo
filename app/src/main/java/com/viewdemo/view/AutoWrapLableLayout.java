package com.viewdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.viewdemo.R;

import java.util.ArrayList;
import java.util.List;

public class AutoWrapLableLayout extends ViewGroup{

    private final String TAG = "AutoWrapLableLayout";
    private List<String> lables;

    /**
     * 标签之间左右距离
     */
    private int leftRightSpace;

    /**
     * 标签行距
     */
    private int rowSpace;

    public AutoWrapLableLayout(Context context) {
        this(context,null);
    }

    public AutoWrapLableLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AutoWrapLableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoWrapLableLayout);
        leftRightSpace = typedArray.getDimensionPixelSize(R.styleable.AutoWrapLableLayout_leftAndRightSpace,10);
        rowSpace = typedArray.getDimensionPixelSize(R.styleable.AutoWrapLableLayout_rowSpace,10);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //为所有的标签childView计算宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //获取高的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //建议的高度
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //布局的宽度采用建议宽度（match_parent或者size），如果设置wrap_content也是match_parent的效果
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int height ;
        if (heightMode == MeasureSpec.EXACTLY) {
            //如果高度模式为EXACTLY（match_perent或者size），则使用建议高度
            height = heightSize;
        } else {
            //其他情况下（AT_MOST、UNSPECIFIED）需要计算计算高度
            int childCount = getChildCount();
            if(childCount<=0){
                //没有标签时，高度为0
                height = 0;
            }else{
                // 标签行数
                int row = 1;
                // 当前行右侧剩余的宽度
                int widthSpace = width;
                for(int i = 0;i<childCount; i++){
                    View view = getChildAt(i);
                    //获取标签宽度
                    int childW = view.getMeasuredWidth();
                    Log.v(TAG , "标签宽度:"+childW +" 行数："+row+"  剩余宽度："+widthSpace);
                    if(widthSpace >= childW ){
                        //如果剩余的宽度大于此标签的宽度，那就将此标签放到本行
                        widthSpace -= childW;
                    }else{
                        row ++;    //增加一行
                        //如果剩余的宽度不能摆放此标签，那就将此标签放入一行
                        widthSpace = width-childW;
                    }
                    //减去标签左右间距
                    widthSpace -= leftRightSpace;
                }
                //由于每个标签的高度是相同的，所以直接获取第一个标签的高度即可
                int childH = getChildAt(0).getMeasuredHeight();
                //最终布局的高度=标签高度*行数+行距*(行数-1)
                height = (childH * row) + rowSpace * (row-1);

                Log.v(TAG , "总高度:"+height +" 行数："+row+"  标签高度："+childH);
            }
        }

        //设置测量宽度和测量高度
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int row = 0;
        // 标签相对于布局的右侧位置
        int right = 0;
        // 标签相对于布局的底部位置
        int botom;
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            int childW = childView.getMeasuredWidth();
            int childH = childView.getMeasuredHeight();
            //右侧位置=本行已经占有的位置+当前标签的宽度
            right += childW;
            //底部位置=已经摆放的行数*（标签高度+行距）+当前标签高度
            botom = row * (childH + rowSpace) + childH;
            // 如果右侧位置已经超出布局右边缘，跳到下一行
            // if it can't drawing on a same line , skip to next line
            if (right > (r - leftRightSpace)){
                row++;
                right = childW;
                botom = row * (childH + rowSpace) + childH;
            }
            Log.d(TAG, "left = " + (right - childW) +" top = " + (botom - childH)+
                    " right = " + right + " botom = " + botom);
            childView.layout(right - childW, botom - childH,right,botom);

            right += leftRightSpace;
        }
    }
    /**
     * 添加标签
     * @param lables 标签集合
     * @param add 是否追加
     */
    public void setLables(List<String> lables, boolean add){
        if(this.lables == null){
            this.lables = new ArrayList<>();
        }
        if(add){
            this.lables.addAll(lables);
        }else{
            this.lables.clear();
            this.lables = lables;
        }

        if(lables!=null && lables.size()>0){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            for(final String lable : lables){
                TextView tv = (TextView) inflater.inflate(R.layout.auto_warp_lable_item, null);
                tv.setText(lable);
                //给标签设置点击事件
                tv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(),lable,Toast.LENGTH_SHORT).show();
                    }
                });

                //将标签添加到容器中
                addView(tv);
            }
        }
    }
}
