package cn.carhouse.quicktitle_sample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import cn.carhouse.titlebar.DefTitleBar;
import cn.carhouse.titlebar.DefTitleBuilder;

public class ColorStyleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DefTitleBar titleBar = new DefTitleBuilder(this)
                // 返回按钮
                .setBackImageRes(R.drawable.ic_title_back)
                .setBackImageFilterColor(Color.WHITE)
                .build();
        // 标题文字
        titleBar.setTitle("我是颜色标题栏");
        int color = getResources().getColor(R.color.colorAccent);
        // 标题的颜色、背景颜色、6.0+状态栏字体是不是黑色
        titleBar.colorStyle(color, Color.WHITE);
        // 标题文本颜色
        titleBar.setTitleColor(Color.WHITE);
        // 右边图片及点击事件
        titleBar.setRightIcon(R.mipmap.ic_launcher, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSecondActivity(v);
            }
        });


    }

    public void toSecondActivity(View view) {
        Intent intent = new Intent(ColorStyleActivity.this, ResourceStyleActivity.class);
        startActivity(intent);
    }

    public void toMain(View view) {
        Intent intent = new Intent(ColorStyleActivity.this, ResourceStyleActivity.class);
        startActivity(intent);
    }

}
