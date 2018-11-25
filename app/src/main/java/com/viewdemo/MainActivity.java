package com.viewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * @author linmeizhen
 * @date 2018/8/29
 * @description
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnAutoWrapAbleDemo,R.id.btnThreadPoolExecutor,R.id.btnLetterIndexSlideBar,R.id.btnWebView,R.id.btnTextView})
    public void onClickView(View v){
        switch (v.getId()){
            case R.id.btnAutoWrapAbleDemo:
                Intent intent = new Intent(MainActivity.this,AutoWrapLableDemoActivity.class);
                startActivity(intent);
                break;
            case R.id.btnThreadPoolExecutor:
                intent = new Intent(MainActivity.this,ThreadPoollExecutorActivity.class);
                startActivity(intent);
                break;
            case R.id.btnLetterIndexSlideBar:
                intent = new Intent(MainActivity.this,LetterIndexSlideBarActivity.class);
                startActivity(intent);
                break;
            case R.id.btnWebView:
                intent = new Intent(MainActivity.this,WebViewDemoActivity.class);
                startActivity(intent);
                break;
            case R.id.btnTextView:
                intent = new Intent(MainActivity.this,LineSpaceExtraTextViewActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
