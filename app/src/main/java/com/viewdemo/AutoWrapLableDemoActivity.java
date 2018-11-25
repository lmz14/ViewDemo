package com.viewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.viewdemo.view.AutoWrapLableLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AutoWrapLableDemoActivity extends AppCompatActivity {

    @BindView(R.id.autoWrapAbleLayout)
    AutoWrapLableLayout autoWrapAbleLayout;

    private List<String> lables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_wrap_lable_demo_activity);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        lables = new ArrayList<>();
        lables.add("经济");
        lables.add( "娱乐");
        lables.add("八卦");
        lables.add("小道消息");
        lables.add("政治中心");
        lables.add("彩票");
        lables.add("情感");
    }

    private void initView() {
        //设置标签
        autoWrapAbleLayout.setLables(lables, true);
    }
}
