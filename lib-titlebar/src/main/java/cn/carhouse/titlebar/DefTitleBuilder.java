package cn.carhouse.titlebar;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

/**
 * 构建通用标题栏
 */
public class DefTitleBuilder {

    private ITitleParams mParams;

    public DefTitleBuilder(Activity activity, ViewGroup parent) {
        mParams = new ITitleParams(activity, parent);
    }

    public DefTitleBuilder(Activity activity) {
        this(activity, null);
    }

    public DefTitleBar build() {
        DefTitleBar titleBar = new DefTitleBar();
        titleBar.apply(mParams);
        return titleBar;
    }

    /**
     * 构建参数
     */
    static class ITitleParams {
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


        public ITitleParams(Activity activity, ViewGroup parent) {
            this.mActivity = activity;
            this.mParent = parent;
        }
    }

    /**
     * 设置Title
     */
    public DefTitleBuilder setTitle(CharSequence title) {
        mParams.mTitle = title;
        return this;
    }

    /**
     * 设置Title根背景颜色
     */
    public DefTitleBuilder setRootBackgroundColor(int color) {
        mParams.mRootBackgroundColor = color;
        return this;
    }

    /**
     * 设置Title颜色
     */
    public DefTitleBuilder setTitleBackgroundColor(int color) {
        mParams.mTitleBackgroundColor = color;
        return this;
    }

    public DefTitleBuilder setRightText(CharSequence text) {
        mParams.mRightText = text;
        return this;
    }

    public DefTitleBuilder setRightTextClick(View.OnClickListener listener) {
        mParams.mRightTextClickListener = listener;
        return this;
    }

    public DefTitleBuilder setLeftClick(View.OnClickListener listener) {
        mParams.mLeftClickListener = listener;
        return this;
    }

    /**
     * 添加左边的View
     */
    public DefTitleBuilder setLeftView(int leftLayoutId) {
        mParams.mLeftLayoutId = leftLayoutId;
        return this;
    }

    /**
     * 添加左边的View
     */
    public DefTitleBuilder setLeftView(View leftView) {
        mParams.mLeftView = leftView;
        return this;
    }

    /**
     * 添加右边的View
     */
    public DefTitleBuilder setRightView(int rightLayoutId) {
        mParams.mRightLayoutId = rightLayoutId;
        return this;
    }

    /**
     * 添加右边的View
     */
    public DefTitleBuilder setRightIcons(int[] rightResIcons, View.OnClickListener... rightResClicks) {
        mParams.mRightResIcons = rightResIcons;
        mParams.mRightResClicks = rightResClicks;
        return this;
    }

    /**
     * 添加右边的View
     */
    public DefTitleBuilder setRightView(View rightView) {
        mParams.mRightView = rightView;
        return this;
    }

    /**
     * 左边返回按钮
     */
    public DefTitleBuilder setBackImageRes(int resId) {
        mParams.mLeftIvResId = resId;
        return this;
    }

    /**
     * 左边返回按钮图片过滤色，控制返回按钮的颜色
     */
    public DefTitleBuilder setBackImageFilterColor(int color) {
        mParams.mLeftIvFilterColor = color;
        return this;
    }

    /**
     * 设置标题默认高度
     */
    public DefTitleBuilder setHeight(int height) {
        mParams.mHeight = dip2px(height);
        return this;
    }

    public int dip2px(int dip) {
        if (mParams == null || mParams.mActivity == null) {
            return 0;
        }
        // 缩放比例(密度)
        float density = mParams.mActivity.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }
}
