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
        titleBar.colorStyle(Color.WHITE, Color.TRANSPARENT, true, false)
                .setBackImageColorFilter(Color.BLACK)
                .setTitle("我是白色标题栏")
                // 设置虚拟键盘为白色
                .navigationWhite()
                // 标题文本颜色
                .setTitleColor(Color.BLACK)
                .setLineVisible(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void toMain(View view) {
        Intent intent = new Intent(SplashWhiteStyleActivity.this, ColorStyleActivity.class);
        startActivity(intent);
    }

}
