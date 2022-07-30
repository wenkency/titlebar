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

import androidx.annotation.NonNull;


/**
 * 状态栏颜色控制的Util类
 */

public class TitleBarUtil {

    // UI显示默认效果
    private static int FLAGS = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_VISIBLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

    /**
     * 设置6.0状态栏字体颜色
     *
     * @param activity
     * @param isDark   true 黑色 false 白色
     */
    public static void stateBarFontColor(Activity activity, boolean isDark) {
        if (activity == null || activity.getWindow() == null || activity.getWindow().getDecorView() == null) {
            return;
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isDark) {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        FLAGS | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                // 默认的
                activity.getWindow().getDecorView().setSystemUiVisibility(FLAGS);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }


    /**
     * 设置状态栏的颜色
     *
     * @param activity
     * @param color
     */
    public static void statusBarTrans(final Activity activity,
                                      final int color,
                                      final boolean isDark) {
        //4.4以下
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT || activity == null) {
            return;
        }
        final Window window = activity.getWindow();
        // 适配刘海屏
        fitsNotchScreen(window);
        // 清除透明信息
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        // 获取到decorView
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int flags = FLAGS;
            if (isDark) {
                flags = FLAGS | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            // 设置布局的Flags
            decorView.setSystemUiVisibility(flags);
            // 需要设置这个才能设置状态栏和导航栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // 设置状态栏颜色
            window.setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4- 5.0之间的
            // 创建一个View
            View view = new View(activity);
            view.setBackgroundColor(color);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity)));
            decorView.addView(view);
        }
    }

    /**
     * 设置状态栏为透明
     */
    public static void statusBarTrans(Activity activity) {
        statusBarTrans(activity, Color.TRANSPARENT, false);
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

    // 下面是新增方法，复制于：https://github.com/gyf-dev/ImmersionBar.git


    /**
     * 适配刘海屏
     * Fits notch screen.
     */
    public static void fitsNotchScreen(@NonNull Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                window.setAttributes(lp);
            } catch (Exception e) {
            }
        }
    }

    /**
     * 设置虚拟键盘颜色
     */
    public static void navigationBarColor(Window window, int color) {
        if (window == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 不加这个，会有灰色背景
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                window.setNavigationBarContrastEnforced(false);
            }
            window.setNavigationBarColor(color);
        }
    }

    /**
     * 设置虚拟键盘颜色
     */
    public static void navigationBarTrans(Window window) {
        navigationBarColor(window, Color.TRANSPARENT);
    }

    public static void navigationWhite(Window window) {
        navigationWhite(window, true);
    }

    public static void navigationWhite(Window window, boolean isDark) {
        int flags = FLAGS | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
        if (isDark) {
            // 状态栏
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        window.getDecorView().setSystemUiVisibility(flags);
        navigationBarColor(window, Color.WHITE);
    }


    public static void hideBar(Window window) {
        hideBar(window, false);
    }

    public static void hideBar(Window window, boolean isDark) {
        hideBarOrNav(window, true, false, isDark);
    }

    public static void hideNav(Window window) {
        hideNav(window, false);
    }

    public static void hideNav(Window window, boolean isDark) {
        hideBarOrNav(window, false, true, isDark);
    }

    public static void hideBarOrNav(Window window, boolean hideBar, boolean hideNav) {
        hideBarOrNav(window, hideBar, hideNav, false);
    }

    /**
     * Hide bar.
     * 隐藏或显示状态栏和导航栏。
     */
    public static void hideBarOrNav(Window window, boolean hideBar, boolean hideNav, boolean isDark) {
        if (window == null) {
            return;
        }
        int uiFlags = FLAGS;
        if (hideBar && hideNav) {
            uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.INVISIBLE;
        } else if (hideBar) {
            uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.INVISIBLE;
        } else if (hideNav) {
            uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        if (!hideBar && isDark) {
            uiFlags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        window.getDecorView().setSystemUiVisibility(uiFlags);
    }

}
