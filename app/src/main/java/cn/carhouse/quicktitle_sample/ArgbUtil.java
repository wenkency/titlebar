package cn.carhouse.quicktitle_sample;

import android.annotation.SuppressLint;
import android.view.View;

/**
 * 颜色渐变工具类
 */

public class ArgbUtil {

    private ArgbEvaluator evaluator;
    private static volatile ArgbUtil mInstance;

    @SuppressLint("RestrictedApi")
    private ArgbUtil() {
        evaluator = new ArgbEvaluator();
    }

    public static ArgbUtil getDefault() {
        if (mInstance == null) {
            synchronized (ArgbUtil.class) {
                if (mInstance == null) {
                    mInstance = new ArgbUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * @param dy
     * @param instance
     * @param targetView
     * @param startColor
     * @param endColor
     */
    @SuppressLint("RestrictedApi")
    public void evaluate(int dy, float instance, View targetView, int startColor, int endColor) {
        float alpha = dy / instance;
        int bgColor = startColor;
        if (alpha <= 0) {
            bgColor = startColor;
        } else if (alpha >= 1) {
            bgColor = endColor;
        } else {
            bgColor = (int) evaluator.evaluate(alpha, startColor, endColor);
        }
        targetView.setBackgroundColor(bgColor);
    }

    @SuppressLint("RestrictedApi")
    public int evaluate(float alpha, int startColor, int endColor) {
        int bgColor = startColor;
        if (alpha <= 0) {
            bgColor = startColor;
        } else if (alpha >= 1) {
            bgColor = endColor;
        } else {
            bgColor = (int) evaluator.evaluate(alpha, startColor, endColor);
        }
        return bgColor;
    }
}
