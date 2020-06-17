package cn.carhouse.titlebar;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

/**
 * 构建通用标题栏
 */
public class DefTitleBuilder {

    // 所有标题配置信息
    Activity mActivity;
    ViewGroup mParent;
    // 标题文字
    CharSequence mTitle;
    // 标题高度
    int mHeight;
    // 左边imageView的资源ID
    int mLeftIvResId;
    // 左边imageView的过滤色
    int mLeftIvFilterColor;
    // 替换左边布局的资源ID
    int mLeftLayoutId;
    // 替换左边View
    View mLeftView;
    // 替换右边边布局的资源ID
    int mRightLayoutId;
    // 替换右边View
    View mRightView;
    // 根布局背景颜色
    int mRootBackgroundColor;
    // 标题布局背景颜色
    int mTitleBackgroundColor;
    // 右边文字
    CharSequence mRightText;
    // 右边点击事件
    View.OnClickListener mRightTextClickListener;
    // 左边点击事件
    View.OnClickListener mLeftClickListener;
    // 右边图片资源集合
    int[] mRightResIcons;
    // 右边图片资源集合点击事件
    View.OnClickListener[] mRightResClicks;

    public DefTitleBuilder(@NonNull Activity activity, ViewGroup parent) {
        this.mActivity = activity;
        this.mParent = parent;
    }

    public DefTitleBuilder(Activity activity) {
        this(activity, null);
    }

    public DefTitleBar build() {
        DefTitleBar titleBar = new DefTitleBar();
        titleBar.apply(this);
        return titleBar;
    }


    /**
     * 设置Title
     */
    public DefTitleBuilder setTitle(CharSequence title) {
        this.mTitle = title;
        return this;
    }

    /**
     * 设置Title根背景颜色
     */
    public DefTitleBuilder setRootBackgroundColor(int color) {
        this.mRootBackgroundColor = color;
        return this;
    }

    /**
     * 设置Title颜色
     */
    public DefTitleBuilder setTitleBackgroundColor(int color) {
        this.mTitleBackgroundColor = color;
        return this;
    }

    public DefTitleBuilder setRightText(CharSequence text) {
        this.mRightText = text;
        return this;
    }

    public DefTitleBuilder setRightTextClick(View.OnClickListener listener) {
        this.mRightTextClickListener = listener;
        return this;
    }

    public DefTitleBuilder setLeftClick(View.OnClickListener listener) {
        this.mLeftClickListener = listener;
        return this;
    }

    /**
     * 添加左边的View
     */
    public DefTitleBuilder setLeftView(int leftLayoutId) {
        this.mLeftLayoutId = leftLayoutId;
        return this;
    }

    /**
     * 添加左边的View
     */
    public DefTitleBuilder setLeftView(View leftView) {
        this.mLeftView = leftView;
        return this;
    }

    /**
     * 添加右边的View
     */
    public DefTitleBuilder setRightView(int rightLayoutId) {
        this.mRightLayoutId = rightLayoutId;
        return this;
    }

    /**
     * 添加右边的View
     */
    public DefTitleBuilder setRightIcons(int[] rightResIcons, View.OnClickListener... rightResClicks) {
        this.mRightResIcons = rightResIcons;
        this.mRightResClicks = rightResClicks;
        return this;
    }

    /**
     * 添加右边的View
     */
    public DefTitleBuilder setRightView(View rightView) {
        this.mRightView = rightView;
        return this;
    }

    /**
     * 左边返回按钮
     */
    public DefTitleBuilder setBackImageRes(int resId) {
        this.mLeftIvResId = resId;
        return this;
    }

    /**
     * 左边返回按钮图片过滤色，控制返回按钮的颜色
     */
    public DefTitleBuilder setBackImageFilterColor(int color) {
        this.mLeftIvFilterColor = color;
        return this;
    }

    /**
     * 设置标题默认高度
     */
    public DefTitleBuilder setHeight(int height) {
        this.mHeight = dip2px(height);
        return this;
    }

    public int dip2px(int dip) {
        if (this == null || this.mActivity == null) {
            return 0;
        }
        // 缩放比例(密度)
        float density = this.mActivity.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }
}
