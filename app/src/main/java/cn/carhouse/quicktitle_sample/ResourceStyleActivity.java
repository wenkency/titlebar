package cn.carhouse.quicktitle_sample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import cn.carhouse.titlebar.DefTitleBar;
import cn.carhouse.titlebar.DefTitleBuilder;

public class ResourceStyleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res);
        ViewGroup title = findViewById(R.id.fl_title_res);
        DefTitleBar titleBar = new DefTitleBuilder(this, title)
                // 返回按钮
                .setBackImageRes(R.drawable.ic_title_back)
                .setBackImageFilterColor(Color.WHITE)
                .setHeight(80)
                .build();
        // 标题文字
        titleBar.setTitle("我是资源渐变标题栏")
                // 标题的颜色、背景颜色、6.0+状态栏字体是不是黑色、透明
                .colorStyle(Color.TRANSPARENT, Color.TRANSPARENT, false, true)
                .navigationBarTrans()
                // 设置资源渐变背景
                .setRootBackgroundResource(R.drawable.bg_good_list_gradient)
                // 标题文本颜色
                .setTitleColor(Color.WHITE);

        // 右边图片及点击事件
        titleBar.setRightIcon(R.mipmap.ic_launcher, v -> {
            Intent intent = new Intent(ResourceStyleActivity.this, ColorStyleActivity.class);
            startActivity(intent);
        });

    }

    public void toMain(View view) {
        Intent intent = new Intent(this, ColorStyleActivity.class);
        startActivity(intent);
    }
}
