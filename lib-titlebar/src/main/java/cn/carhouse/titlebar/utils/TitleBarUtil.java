package cn.carhouse.titlebar.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


/**
 * 状态栏颜色控制的Util类
 */

public class TitleBarUtil {


    /**
     * 设置6.0状态栏字体颜色
     *
     * @param activity
     * @param isDark   true 黑色 false 白色
     */
    public static void setMStateBarFontColor(Activity activity, boolean isDark) {
        if (activity == null || activity.getWindow() == null || activity.getWindow().getDecorView() == null) {
            return;
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isDark) {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                // 默认的
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_VISIBLE);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    public static void setStatusTranslucent(Activity activity, int color) {
        setStatusTranslucent(activity, color, false);
    }

    /**
     * 设置状态栏的颜色
     *
     * @param activity
     * @param color
     * @param isResource 是不是资源
     */
    public static void setStatusTranslucent(final Activity activity,final int color,final boolean isResource) {
        //4.4以下
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        setStatusBar(activity);
        // 设置为透明的状态栏
        // 5.0以上
       final Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (isResource) {
                // android系统级的资源id得这么拿,不然拿不到
                activity.getWindow().getDecorView().post(new Runnable() {
                    @Override
                    public void run() {
                        int identifier = activity.getResources().getIdentifier("statusBarBackground",
                                "id", "android");
                        View statusBarView = window.findViewById(identifier);
                        if (statusBarView != null) {
                            statusBarView.setBackgroundResource(color);
                        }
                    }
                });
            } else {
                window.setStatusBarColor(color);
            }
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_VISIBLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4- 5.0之间的
            // 创建一个View
            View view = new View(activity);
            if (isResource) {
                view.setBackgroundResource(color);
            } else {
                view.setBackgroundColor(color);
            }
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity)));
            // 获取到decorView
            ViewGroup decorView = (ViewGroup) window.getDecorView();
            decorView.addView(view);
        }
    }

    /**
     * 设置状态栏为透明
     */
    public static void setStatusTranslucent(Activity activity) {
        setStatusTranslucent(activity, Color.TRANSPARENT, false);
    }

    /**
     * 浸入式状态栏实现同时取消5.0以上的阴影
     */
    public static void setStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Window window = activity.getWindow();
        // 设置虚拟键盘跟着屏幕自动
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    }


    /**
     * 设置TitleBar的高度
     */
    public static void setTitlePadding(View titleView) {
        setTitlePadding(titleView, false);
    }

    /**
     * 设置TitleBar的高度
     */
    public static void setTitlePadding(View titleView, boolean isNeedOld) {
        if (titleView == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int padding = getStatusBarHeight(titleView.getContext());
            if (isNeedOld) {
                padding += titleView.getPaddingTop();
            }
            titleView.setPadding(titleView.getPaddingLeft(), padding, titleView.getPaddingRight(), titleView.getPaddingBottom());
        } else {
            titleView.setPadding(0, 0, 0, 0);
        }
    }

    /**
     * 设置TitleBar的高度
     */
    public static void setTitleHeight(Activity activity, View titleView) {
        setTitleHeight(activity, titleView, true);
    }

    /**
     * 设置TitleBar的高度
     */
    public static void setTitleHeight(Activity activity, View titleView, boolean isPadding) {
        if (titleView == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup.LayoutParams lp = titleView.getLayoutParams();
            lp.height = lp.height + getStatusBarHeight(activity);
            titleView.setLayoutParams(lp);
            if (isPadding) {
                titleView.setPadding(0, getStatusBarHeight(activity), 0, 0);
            }
        }
    }


    /**
     * 获取状态栏的高度
     */
    public static int getStatusBarHeight(Context context) {
        if (context == null) {
            return 0;
        }
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelOffset(identifier);
    }



}
