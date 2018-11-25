package com.viewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author linmeizhen
 * @date 2018/8/29
 * @description
 */
public class ThreadPoollExecutorActivity extends AppCompatActivity {

    @BindView(R.id.linParent)
    LinearLayout linParent;

    private static String TAG = "ThreadPoollExecutorActivity";
    private ThreadPoolExecutor threadPoolExecutor;
    private int corePoolSize;
    private int maximumPoolSize;
    private long keepAliveTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pooll_executor);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
//        int childCount = linParent.getChildCount();
//        for(int i=0;i<childCount;i++){
//            Log.e(TAG,"childView:"+linParent.getChildAt(i));
//        }
//        Log.e(TAG,"add view index=0");
//        ImageView imageView = new ImageView(this);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(120,120);
//        imageView.setLayoutParams(params);
//        imageView.setBackgroundResource(R.color.colorPrimary);
//        linParent.addView(imageView,0);
//        childCount = linParent.getChildCount();
//        for(int j=0;j<childCount;j++){
//            Log.e(TAG,"index:"+j+",childView:"+linParent.getChildAt(j));
//        }
    }

    private void initData() {
        corePoolSize = 2;
        maximumPoolSize = 3;
        keepAliveTime = 10;
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,
                TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.DiscardOldestPolicy());

    }

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                Log.e("ThreadPoolExecutor","run task:"+System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    @OnClick({R.id.btnExecutor})
    public void onClickView(View view){
        switch (view.getId()){
            case R.id.btnExecutor:
                threadPoolExecutor.execute(task);
                break;
            default:
                break;
        }
    }
}
