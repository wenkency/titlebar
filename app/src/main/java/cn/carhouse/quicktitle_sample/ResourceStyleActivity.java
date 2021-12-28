package cn.carhouse.quicktitle_sample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import cn.carhouse.titlebar.DefTitleBar;
import cn.carhouse.titlebar.DefTitleBuilder;

public class ResourceStyleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DefTitleBar titleBar = new DefTitleBuilder(this)
                // 返回按钮
                .setBackImageRes(R.drawable.ic_title_back)
                .setBackImageFilterColor(Color.WHITE)
                .setHeight(65)
                .build();
        // 标题文字
        titleBar.setTitle("我是资源渐变标题栏");
        // 标题的颜色、背景颜色、6.0+状态栏字体是不是黑色
        titleBar.resourceStyle(R.drawable.bg_good_list_gradient, Color.WHITE);
        // 标题文本颜色
        titleBar.setTitleColor(Color.WHITE);
        // 右边图片及点击事件
        titleBar.setRightIcon(R.mipmap.ic_launcher, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResourceStyleActivity.this, ColorStyleActivity.class);
                startActivity(intent);
            }
        });
    }

    public void toMain(View view) {
        Intent intent = new Intent(this, ColorStyleActivity.class);
        startActivity(intent);
    }
}
