package cn.carhouse.quicktitle_sample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import cn.carhouse.titlebar.DefTitleBar;
import cn.carhouse.titlebar.DefTitleBuilder;
import cn.carhouse.titlebar.utils.SensorUtils;
import cn.carhouse.titlebar.utils.TitleBarUtil;

public class ColorStyleActivity extends BaseActivity {
    private DefTitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        titleBar = new DefTitleBuilder(this, findViewById(R.id.fl_title))
                // 返回按钮
                .setBackImageRes(R.drawable.ic_title_back)
                .setBackImageFilterColor(Color.WHITE)
                .build();
        // 标题文字
        int color = Color.parseColor("#88cdabcd");
        // 配置标题
        titleBar.setTitle("我是颜色标题栏")
                // 标题的颜色、背景颜色、6.0+状态栏字体是不是黑色
                .colorStyle(color, Color.TRANSPARENT, false, true)
                // 设置虚拟键盘透明
                .navigationBarTrans()
                // 标题文本颜色
                .setTitleColor(Color.WHITE);
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

    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        super.setRequestedOrientation(requestedOrientation);
        Log.e("TAG", "setRequestedOrientation " + requestedOrientation);
    }


}
