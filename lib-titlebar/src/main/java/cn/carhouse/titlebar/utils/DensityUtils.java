package cn.carhouse.titlebar.utils;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * 分辨率工具类
 * 1. dp转px  px转dp
 * 2. 获取屏幕宽高
 */
public class DensityUtils {
    /**
     * 获取屏幕宽
     */
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高
     */
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param value 虚拟像素
     * @return 像素
     */
    public static int dp2px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                Resources.getSystem().getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param value 像素
     * @return 虚拟像素
     */
    public static int px2dp(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, value,
                Resources.getSystem().getDisplayMetrics());
    }
}
