package cn.carhouse.quicktitle_sample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import cn.carhouse.titlebar.DefTitleBar;
import cn.carhouse.titlebar.DefTitleBuilder;

public class SplashWhiteStyleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DefTitleBar titleBar = new DefTitleBuilder(this)
                .setBackImageRes(R.drawable.ic_title_back)
                .build();
        titleBar.whiteStyle();
        titleBar.clearBackImage();
        titleBar.setTitle("我是白色标题栏");
        // 标题文本颜色
        titleBar.setTitleColor(Color.BLACK);


    }

    public void toMain(View view) {
        Intent intent = new Intent(SplashWhiteStyleActivity.this, ColorStyleActivity.class);
        startActivity(intent);
    }
}
