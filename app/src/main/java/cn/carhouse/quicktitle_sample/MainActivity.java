package cn.carhouse.quicktitle_sample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import cn.carhouse.titlebar.DefTitleBar;
import cn.carhouse.titlebar.DefTitleBuilder;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DefTitleBar titleBar = new DefTitleBuilder(this)
                // 返回按钮
                .setLeftRes(R.drawable.ic_title_back)
                .build();
        // 标题文字
        titleBar.setTitle("我是颜色标题栏");

        int color = getResources().getColor(R.color.colorAccent);

        // 标题的颜色、背景颜色、6.0+状态栏字体是不是黑色
        titleBar.colorStyle(color, Color.WHITE);
        // titleBar.resourceStyle(R.drawable.bg_good_list_gradient,Color.WHITE);
        // 标题颜色
        titleBar.setTitleColor(Color.WHITE);

        // titleBar.whiteStyle();

        // 右边图片及点击事件
        titleBar.setRightIcon(R.mipmap.ic_launcher, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSecondActivity(v);
            }
        });


    }

    public void toSecondActivity(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }

    public void toMain(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }

}
