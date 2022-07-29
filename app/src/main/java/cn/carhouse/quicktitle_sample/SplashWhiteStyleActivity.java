package cn.carhouse.quicktitle_sample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import cn.carhouse.titlebar.DefTitleBar;
import cn.carhouse.titlebar.DefTitleBuilder;
import cn.carhouse.titlebar.utils.TitleBarUtil;

public class SplashWhiteStyleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DefTitleBar titleBar = new DefTitleBuilder(this)
                .setBackImageRes(R.drawable.ic_title_back)
                .build();
        // titleBar.whiteStyle(true);
        titleBar.colorStyle(Color.WHITE, Color.TRANSPARENT, true, true, false);
        titleBar.clearBackImage();
        titleBar.setTitle("我是白色标题栏");
        // 标题文本颜色
        titleBar.setTitleColor(Color.BLACK);
        titleBar.findViewById(R.id.v_title_line).setVisibility(View.VISIBLE);
        // 设置虚拟键盘为透明
        //TitleBarUtil.setNavigationBarTrans(getWindow());
        // 隐藏状态栏、虚拟键盘
        TitleBarUtil.hideBarOrNav(getWindow(), false, true,true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TitleBarUtil.hideBarOrNav(getWindow(), false, true,true);
    }

    public void toMain(View view) {
        Intent intent = new Intent(SplashWhiteStyleActivity.this, ColorStyleActivity.class);
        startActivity(intent);
    }

}
