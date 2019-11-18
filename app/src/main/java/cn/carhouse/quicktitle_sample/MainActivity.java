package cn.carhouse.quicktitle_sample;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.carhouse.titlebar.DefTitleBar;
import cn.carhouse.titlebar.DefTitleBuilder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DefTitleBar titleBar = new DefTitleBuilder(this)
                // 返回按钮
                .setLeftRes(R.drawable.ic_title_back)
                .build();
        int color = getResources().getColor(R.color.colorPrimaryDark);
        // 标题的颜色、背景颜色、6.0+状态栏字体是不是黑色
        titleBar.colorStyle(color, Color.WHITE, false);
        // 标题文字
        titleBar.setTitle("我是主页面");
        // 标题颜色
        titleBar.setTitleColor(Color.WHITE);

        // 右边图片及点击事件
        titleBar.setRightIcon(R.mipmap.ic_launcher, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "右边点击", Toast.LENGTH_SHORT).show();
            }
        });
        // titleBar.whiteStyle();
    }
}
