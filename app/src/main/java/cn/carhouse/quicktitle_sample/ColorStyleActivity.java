package cn.carhouse.quicktitle_sample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import cn.carhouse.titlebar.DefTitleBar;
import cn.carhouse.titlebar.DefTitleBuilder;
import cn.carhouse.titlebar.utils.TitleBarUtil;

public class ColorStyleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        DefTitleBar titleBar = new DefTitleBuilder(this, findViewById(R.id.fl_title))
                // 返回按钮
                .setBackImageRes(R.drawable.ic_title_back)
                .setBackImageFilterColor(Color.WHITE)
                .build();
        // 标题文字
        titleBar.setTitle("我是颜色标题栏");
        int color = Color.parseColor("#55cdabcd");
        // 标题的颜色、背景颜色、6.0+状态栏字体是不是黑色
        titleBar.colorStyle(Color.TRANSPARENT, Color.TRANSPARENT, false, true, false);
        // 标题文本颜色
        titleBar.setTitleColor(Color.WHITE);
        // 右边图片及点击事件
        titleBar.setRightIcon(R.mipmap.ic_launcher, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSecondActivity(v);
            }
        });
        TitleBarUtil.setNavigationBarTrans(getWindow());

        titleBar.setTitleBackgroundColor(color);
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
