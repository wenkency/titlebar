package cn.carhouse.titlebar;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.carhouse.titlebar.utils.DensityUtils;
import cn.carhouse.titlebar.utils.TitleBarUtil;

/**
 * 通用的标题栏
 * 1. apply方法
 * 2. applyParams方法
 */
public class DefTitleBar {

    private Activity mActivity;
    private View mChild;
    private TitleViewHelper mViewHelper;
    private int mTitleHeight;

    /**
     * 这个是标题布局，子类可以复写
     */
    protected int getBarLayoutId() {
        return R.layout.base_layout_title_bar;
    }


    /**
     * 通用设置
     */
    public void apply(DefTitleBuilder params) {
        // 1. 非空校验
        if (params == null || params.mActivity == null) {
            return;
        }
        mActivity = params.mActivity;
        mTitleHeight = params.mHeight;
        // 2. 找父布局
        ViewGroup parent = getParent(params);
        if (parent == null) {
            return;
        }
        // 3. 加载Title
        mViewHelper = new TitleViewHelper(params.mActivity, parent, getBarLayoutId());
        parent.addView(mViewHelper.getContentView(), 0);
        parent.setBackgroundColor(Color.TRANSPARENT);
        // 4. 设置默认Title参数配置
        applyParams(params);
    }

    /**
     * 这是标题参数配置，子类可以复写
     *
     * @param params
     */
    protected void applyParams(final DefTitleBuilder params) {
        if (mViewHelper == null || params == null) {
            return;
        }
        // 1. 根背景颜色
        if (params.mRootBackgroundColor != 0) {
            mViewHelper.setBackgroundColor(R.id.ll_title_root, params.mRootBackgroundColor);
        }
        // 2. 标题背景颜色
        if (params.mTitleBackgroundColor != 0) {
            mViewHelper.setBackgroundColor(R.id.cl_title_content, params.mTitleBackgroundColor);
        }
        // 3. 标题高度
        if (params.mHeight != 0) {
            View clTitleView = mViewHelper.findViewById(R.id.cl_title_content);
            ViewGroup.LayoutParams lp = clTitleView.getLayoutParams();
            lp.height = params.mHeight;
            clTitleView.setLayoutParams(lp);
        }

        // 4. 左边返回按钮图片
        if (params.mLeftIvResId != 0) {
            ImageView ivBack = mViewHelper.findViewById(R.id.iv_title_left);
            ivBack.setImageResource(params.mLeftIvResId);
            if (params.mLeftIvFilterColor != 0) {
                ivBack.setColorFilter(params.mLeftIvFilterColor);
            }
        }
        // 5. 左边图片返回默认点击事件
        setOnClickListener(R.id.iv_title_left, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (params.mLeftClickListener != null) {
            setOnClickListener(R.id.iv_title_left, params.mLeftClickListener);
        }
        // 替换左边的View自定义的那种
        setLeftView(params.mLeftLayoutId);
        setLeftView(params.mLeftView);
        // 替换右边的View自定义的那种
        setRightView(params.mRightLayoutId);
        setRightView(params.mRightView);
        // 根据资源设置右边的图片和点击事件
        setRightIcons(params.mRightResIcons, params.mRightResClicks);
        // 设置Title的文本
        setText(R.id.tv_title_center, params.mTitle);
        // 设置右边的文本
        setText(R.id.tv_title_right, params.mRightText);
        if (params.mRightTextClickListener != null) {
            setOnClickListener(R.id.tv_title_right, params.mRightTextClickListener);
        }

    }

    public void finish() {
        if (mActivity != null) {
            mActivity.finish();
        }
    }

    /**
     * 找父类
     */
    private ViewGroup getParent(DefTitleBuilder params) {
        // 1. 有父类直接返回
        if (params.mParent != null) {
            mChild = params.mParent;
            return params.mParent;
        }
        if (params.mActivity != null) {
            // 如果用户不写，默认添加到根布局DecorView布局（FrameLayout-->LinearLayout）
            ViewGroup decorView = (ViewGroup) params.mActivity.getWindow().getDecorView();
            // 去掉背景色
            decorView.setBackgroundColor(Color.TRANSPARENT);
            // 这个是ContentView
            FrameLayout contentView = decorView.findViewById(android.R.id.content);
            if (contentView != null) {
                // 去掉阴影
                contentView.setForeground(new ColorDrawable(Color.TRANSPARENT));
                View child = contentView.getChildAt(0);
                if (child != null) {
                    contentView.removeView(child);
                }
                LinearLayout wrapParent = new LinearLayout(params.mActivity);
                wrapParent.setOrientation(LinearLayout.VERTICAL);
                if (child != null) {
                    wrapParent.setLayoutParams(child.getLayoutParams());
                    wrapParent.addView(child);
                }
                mChild = wrapParent;
                contentView.addView(wrapParent);
                return wrapParent;
            }
        }
        return null;
    }


    public <T extends View> T getView(int viewId) {
        return mViewHelper.findViewById(viewId);
    }

    public void setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        mViewHelper.setOnClickListener(viewId, onClickListener);
    }

    public void setText(int viewId, CharSequence text) {
        mViewHelper.setText(viewId, text);
    }

    public void setTextColor(int viewId, int color) {
        mViewHelper.setTextColor(viewId, color);
    }

    /**
     * 替换左边的View
     */
    public DefTitleBar setLeftView(int leftResId) {
        if (leftResId != 0) {
            LinearLayout leftLayout = getView(R.id.ll_title_left);
            leftLayout.removeAllViews();
            View leftView = LayoutInflater.from(leftLayout.getContext())
                    .inflate(leftResId, leftLayout, false);
            leftLayout.addView(leftView);
        }
        return this;
    }

    /**
     * 替换左边的View
     */
    public DefTitleBar setLeftView(View leftView) {
        if (leftView != null) {
            LinearLayout leftLayout = getView(R.id.ll_title_left);
            if (leftLayout != null) {
                leftLayout.removeAllViews();
                leftLayout.addView(leftView);
            }
        }
        return this;

    }

    /**
     * 清除返回按钮
     */
    public DefTitleBar clearBackImage() {
        LinearLayout leftLayout = getView(R.id.ll_title_left);
        if (leftLayout != null) {
            leftLayout.removeAllViews();
        }
        return this;
    }

    public DefTitleBar setRightText(CharSequence text, View.OnClickListener onClickListener) {
        TextView tv = getView(R.id.tv_title_right);
        if (tv != null) {
            tv.setText(text);
            tv.setOnClickListener(onClickListener);
        }
        return this;
    }

    public DefTitleBar setRightText(CharSequence text) {
        return setRightText(text, null);
    }

    public DefTitleBar setRightTextClick(View.OnClickListener onClickListener) {
        TextView tv = getView(R.id.tv_title_right);
        if (tv != null) {
            tv.setOnClickListener(onClickListener);
        }
        return this;
    }

    public DefTitleBar setRightTextColor(int color) {
        TextView tv = getView(R.id.tv_title_right);
        if (tv != null) {
            tv.setTextColor(color);
        }
        return this;
    }

    public TextView getRightText() {
        TextView tv = getView(R.id.tv_title_right);
        return tv;
    }

    /**
     * 替换右边的View
     */
    public DefTitleBar setRightView(int rightResId) {
        if (rightResId != 0) {
            LinearLayout rightLayout = getView(R.id.ll_title_right);
            rightLayout.removeAllViews();
            View rightView = LayoutInflater.from(rightLayout.getContext())
                    .inflate(rightResId, rightLayout, false);
            rightLayout.addView(rightView);
        }
        return this;
    }

    /**
     * 替换右边的View
     */
    public DefTitleBar setRightView(View rightView) {
        if (rightView != null) {
            LinearLayout rightLayout = getView(R.id.ll_title_right);
            rightLayout.removeAllViews();
            rightLayout.addView(rightView);
        }
        return this;
    }

    /**
     * 替换右边的View
     */
    public DefTitleBar setRightIcon(int rightResIcon, View.OnClickListener rightResClick) {
        int[] icons = {rightResIcon};
        View.OnClickListener[] clickListeners = {rightResClick};
        return setRightIcons(icons, clickListeners);
    }

    /**
     * 替换右边的View
     */
    public DefTitleBar setRightIcons(int[] rightResIcons, View.OnClickListener[] rightResClicks) {
        if (rightResIcons != null && rightResIcons.length > 0) {
            LinearLayout rightLayout = getView(R.id.ll_title_right);
            rightLayout.removeAllViews();
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ImageView imageView;
            int padding = DensityUtils.dp2px(5);
            for (int i = 0; i < rightResIcons.length; i++) {
                imageView = new ImageView(mActivity);
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                imageView.setImageResource(rightResIcons[i]);
                imageView.setPadding(padding, padding, padding, padding);
                if (i == rightResIcons.length - 1) {
                    imageView.setPadding(padding, padding, DensityUtils.dp2px(10), padding);
                }
                // 设置点击事件
                if (rightResClicks != null && rightResClicks.length >= i) {
                    imageView.setOnClickListener(rightResClicks[i]);
                }
                rightLayout.addView(imageView, lp);
            }
            imageView = null;
        }
        return this;
    }

    /**
     * 设置根布局背景颜色
     */
    public DefTitleBar setRootBackgroundResource(int resId) {
        getView(R.id.ll_title_root).setBackgroundResource(resId);
        return this;
    }

    /**
     * 设置标题内容背景颜色
     */
    public DefTitleBar setTitleBackgroundResource(int resId) {
        getView(R.id.cl_title_content).setBackgroundResource(resId);
        return this;
    }

    /**
     * 设置根布局背景颜色
     */
    public DefTitleBar setRootBackgroundColor(int color) {
        getView(R.id.ll_title_root).setBackgroundColor(color);
        return this;
    }

    /**
     * 设置标题内容背景颜色
     */
    public void setTitleBackgroundColor(int color) {
        getView(R.id.cl_title_content).setBackgroundColor(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mActivity.getWindow().setStatusBarColor(color);
        }
    }

    /**
     * 设置标题返回图片过滤色
     */
    public DefTitleBar setBackImageColorFilter(int color) {
        ImageView ivBack = getView(R.id.iv_title_left);
        if (ivBack != null) {
            ivBack.setColorFilter(color);
        }
        return this;
    }

    /**
     * 设置标题返回图片
     */
    public DefTitleBar setBackImageRes(int res) {
        ImageView ivBack = getView(R.id.iv_title_left);
        if (ivBack != null) {
            ivBack.setImageResource(res);
        }
        return this;
    }

    private View getContentView() {
        return mViewHelper.getContentView();
    }

    private void fitsSystem(boolean fitSystemWindows) {
        if (mChild == null) {
            return;
        }
        if (!fitSystemWindows) {
            // 重新设置标题栏
            View titleView = getContentView();
            int statusBarHeight = TitleBarUtil.getStatusBarHeight(mActivity);
            int height = DensityUtils.dp2px(48);
            if (mTitleHeight > 0) {
                height = mTitleHeight;
            }
            ViewGroup.LayoutParams lp = titleView.getLayoutParams();
            lp.height = statusBarHeight + height;
            titleView.setLayoutParams(lp);
            titleView.setPadding(0, statusBarHeight, 0, 0);
        }
        mChild.setFitsSystemWindows(fitSystemWindows);
    }


    /**
     * 标题样式
     *
     * @param titleColor  标题的颜色
     * @param windowColor 背景颜色
     * @param isDark      状态栏字体是不是黑色
     * @param isTrans     除非渐变，不然建议设置为false
     */
    public DefTitleBar colorStyle(int titleColor, int windowColor, boolean isDark, boolean isTrans) {
        if (mActivity == null) {
            return this;
        }
        // 1. 标题颜色
        setRootBackgroundColor(titleColor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 如果要透明渐变
            if (isTrans) {
                // 2. 适配布局
                fitsSystem(false);
            } else {
                fitsSystem(true);
            }
            // 3. 设置标题和颜色
            TitleBarUtil.statusBarTrans(mActivity, titleColor, isDark);
        } else {
            fitsSystem(true);
        }
        // 最后设置背景
        mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(windowColor));

        return this;
    }

    public DefTitleBar colorStyle(int titleColor, int windowColor) {
        return colorStyle(titleColor, windowColor, false, false);
    }


    /**
     * 设置标题
     */
    public DefTitleBar setTitle(CharSequence title) {
        setText(R.id.tv_title_center, title);
        return this;
    }

    /**
     * 设置标题颜色
     */
    public DefTitleBar setTitleColor(int color) {
        setTextColor(R.id.tv_title_center, color);
        return this;
    }

    /**
     * 设置下划线是否显示
     *
     * @return
     */
    public DefTitleBar setLineVisible(int visibility) {
        getView(R.id.v_title_line).setVisibility(visibility);
        return this;
    }

    /**
     * 获取标题控件
     */
    public TextView getTitle() {
        return getView(R.id.tv_title_center);
    }

    public DefTitleBar navigationWhite() {
        return navigationWhite(true);
    }

    public DefTitleBar navigationWhite(boolean isDark) {
        if (mActivity != null) {
            TitleBarUtil.navigationWhite(mActivity.getWindow(), isDark);
        }
        return this;
    }

    /**
     * 设置虚拟键盘颜色
     */
    public DefTitleBar navigationBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mActivity != null) {
                Window window = mActivity.getWindow();
                // 不加这个，会有灰色背景
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    window.setNavigationBarContrastEnforced(false);
                }
                window.setNavigationBarColor(color);
            }
        }
        return this;
    }

    /**
     * 设置虚拟键盘透明
     */
    public DefTitleBar navigationBarTrans() {
        return navigationBarColor(Color.TRANSPARENT);
    }


    public DefTitleBar hideBar() {
        return hideBar(false);
    }

    public DefTitleBar hideBar(boolean isDark) {
        hideBarOrNav(true, false, isDark);
        return this;
    }

    public DefTitleBar hideNav() {
        return hideNav(false);
    }

    public DefTitleBar hideNav(boolean isDark) {
        return hideBarOrNav(false, true, isDark);
    }

    public DefTitleBar hideBarOrNav(boolean hideBar, boolean hideNav) {
        return hideBarOrNav(hideBar, hideNav, false);
    }

    /**
     * Hide bar.
     * 隐藏或显示状态栏和导航栏。
     */
    public DefTitleBar hideBarOrNav(boolean hideBar, boolean hideNav, boolean isDark) {
        if (mActivity != null) {
            TitleBarUtil.hideBarOrNav(mActivity.getWindow(), hideBar, hideNav, isDark);
        }
        return this;
    }


}
