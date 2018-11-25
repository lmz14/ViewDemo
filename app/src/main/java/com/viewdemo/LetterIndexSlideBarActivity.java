package com.viewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.viewdemo.utils.DensityUtil;
import com.viewdemo.view.LetterIndexSlideBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LetterIndexSlideBarActivity extends AppCompatActivity {

    @BindView(R.id.LetterIndexSlideBar)
    LetterIndexSlideBar letterIndexSlideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_index_slide_bar);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        List<String> letters = new ArrayList<>();
        letters.add("A");
        letters.add("B");
        letters.add("C");
        letters.add("D");
        letters.add("E");
        letters.add("F");
        letters.add("G");
        letters.add("H");
        letters.add("I");
        letters.add("J");
        letters.add("K");
        letters.add("L");
        letters.add("M");
        letters.add("O");
        letters.add("P");
        letters.add("#");
        int height = 16 * letters.size();
        letterIndexSlideBar.setBarHeight(DensityUtil.dip2px(this,height));
        letterIndexSlideBar.update(letters);
    }
}
