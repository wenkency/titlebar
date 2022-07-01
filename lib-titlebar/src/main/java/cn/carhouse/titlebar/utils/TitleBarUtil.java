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
    public static void setMStateBarFontColor(Activity activity, boolean isDark) {
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
     * @param isResource 是不是资源
     */
    public static void setStatusTranslucent(final Activity activity,
                                            final int color,
                                            final boolean isResource,
                                            final boolean isDark) {
        //4.4以下
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        // 设置为透明的状态栏
        setStatusBar(activity);

        final Window window = activity.getWindow();
        // 适配刘海屏
        fitsNotchScreen(window);


        // 获取到decorView
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // FLAG_TRANSLUCENT_STATUS
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            int flags = FLAGS;
            if (isDark) {
                flags = FLAGS | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            window.getDecorView().setSystemUiVisibility(flags);

            // 需要设置这个才能设置状态栏和导航栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


            if (isResource) {
                // android系统级的资源id得这么拿,不然拿不到
                decorView.post(new Runnable() {
                    @Override
                    public void run() {
                        int viewId = activity.getResources()
                                .getIdentifier("statusBarBackground",
                                        "id", "android");
                        View statusBarView = window.findViewById(viewId);
                        if (statusBarView != null) {
                            statusBarView.setBackgroundResource(color);
                        }
                    }
                });
            } else {
                window.setStatusBarColor(color);
            }

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

            decorView.addView(view);
        }
       /* if (!isResource) {
            // 设置虚拟键盘颜色
            setNavigationBarColor(window, color);
        }*/
    }

    /**
     * 设置状态栏为透明
     */
    public static void setStatusTranslucent(Activity activity) {
        setStatusTranslucent(activity, Color.TRANSPARENT, false, false);
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
    public static void setNavigationBarColor(Window window, int color) {
        if (window == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setNavigationBarColor(color);
        }
    }

    /**
     * 设置虚拟键盘颜色
     */
    public static void setNavigationBarTrans(Window window) {
        setNavigationBarColor(window, Color.TRANSPARENT);
    }


/*    // 隐藏状态栏
    public static void hideStatusBar(@NonNull Window window) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    // 显示状态栏
    public static void showStatusBar(@NonNull Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }*/
    /**
     * Hide bar.
     * 隐藏或显示状态栏和导航栏。
     */
    public static void hideBarOrNav(Window window, boolean hideBar, boolean hideNav) {
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
        window.getDecorView().setSystemUiVisibility(uiFlags);
    }

}
