package cn.carhouse.titlebar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * 默认标题
 */
public class DefTitleBar extends TitleBar<DefTitleBuilder> {
    @Override
    protected int getBarLayoutId() {
        return R.layout.base_layout_title_bar;
    }

    @Override
    protected void applyParams(final DefTitleBuilder build) {
        // 1. 根背景颜色
        if (build.mRootBackgroundColor != 0) {
            findViewById(R.id.ll_title_root)
                    .setBackgroundColor(build.mRootBackgroundColor);
        }
        // 2. 标题背景颜色
        if (build.mTitleBackgroundColor != 0) {
            findViewById(R.id.cl_title_content)
                    .setBackgroundColor(build.mTitleBackgroundColor);
        }
        // 左边返回按钮图片
        if (build.mLeftIvResId != 0) {
            ImageView ivBack = findViewById(R.id.iv_title_left);
            ivBack.setImageResource(build.mLeftIvResId);
        }
        // 3. 左边图片返回默认点击事件
        setOnClickListener(R.id.iv_title_left, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                build.finish();
            }
        });
        if (build.mLeftClickListener != null) {
            setOnClickListener(R.id.iv_title_left, build.mLeftClickListener);
        }
        // 替换左边的View自定义的那种
        setLeftView(build.mLeftLayoutId);
        setLeftView(build.mLeftView);
        // 替换右边的View自定义的那种
        setRightView(build.mRightLayoutId);
        setRightView(build.mRightView);
        // 根据资源设置右边的图片和点击事件
        setRightIcons(build.mRightResIcons, build.mRightResClicks);
        // 设置Title的文本
        setText(R.id.tv_title_center, build.mTitle);
        // 设置右边的文本
        setText(R.id.tv_title_right, build.mRightText);
        if (build.mRightTextClickListener != null) {
            setOnClickListener(R.id.tv_title_right, build.mRightTextClickListener);
        }
    }


    /**
     * 替换左边的View
     */
    public DefTitleBar setLeftView(int leftResId) {
        if (leftResId != 0) {
            LinearLayout leftLayout = findViewById(R.id.ll_title_left);
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
            LinearLayout leftLayout = findViewById(R.id.ll_title_left);
            leftLayout.removeAllViews();
            leftLayout.addView(leftView);
        }
        return this;

    }

    /**
     * 替换右边的View
     */
    public DefTitleBar setRightView(int rightResId) {
        if (rightResId != 0) {
            LinearLayout rightLayout = findViewById(R.id.ll_title_right);
            rightLayout.removeAllViews();
            View leftView = LayoutInflater.from(rightLayout.getContext())
                    .inflate(rightResId, rightLayout, false);
            rightLayout.addView(leftView);
        }
        return this;
    }

    /**
     * 替换右边的View
     */
    public DefTitleBar setRightView(View rightView) {
        if (rightView != null) {
            LinearLayout rightLayout = findViewById(R.id.ll_title_right);
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
            LinearLayout rightLayout = findViewById(R.id.ll_title_right);
            rightLayout.removeAllViews();
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ImageView imageView;
            int padding = dip2px(5);
            for (int i = 0; i < rightResIcons.length; i++) {
                imageView = new ImageView(mActivity);
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                imageView.setImageResource(rightResIcons[i]);
                imageView.setPadding(padding, padding, padding, padding);
                if (i == rightResIcons.length - 1) {
                    imageView.setPadding(padding, padding, dip2px(10), padding);
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
    public void setRootBackgroundResource(int resId) {
        findViewById(R.id.ll_title_root).setBackgroundResource(resId);
    }

    /**
     * 设置标题内容背景颜色
     */
    public void setTitleBackgroundResource(int resId) {
        findViewById(R.id.cl_title_content).setBackgroundResource(resId);
    }

    /**
     * 设置根布局背景颜色
     */
    public void setRootBackgroundColor(int color) {
        findViewById(R.id.ll_title_root).setBackgroundColor(color);
    }

    /**
     * 设置标题内容背景颜色
     */
    public void setTitleBackgroundColor(int color) {
        findViewById(R.id.cl_title_content).setBackgroundColor(color);
    }

    /**
     * 默认白色样式
     */
    public void whiteStyle() {
        whiteStyle(Color.WHITE, false);
    }

    /**
     * 白底黑字的样式
     *
     * @param windowColor 背景色
     * @param isTrans     除非渐变，不然建议设置为false
     */
    public void whiteStyle(int windowColor, boolean isTrans) {
        if (mActivity == null) {
            return;
        }
        // 1. 标题也是透明的
        setRootBackgroundColor(Color.TRANSPARENT);
        // 2. 标题颜色是透明的
        setTitleBackgroundColor(Color.TRANSPARENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 4. 设置Activity透明
            TitleBarUtil.setStatusTranslucent(mActivity, Color.WHITE);
            mViewHelper.getContentView().setFitsSystemWindows(true);
            if (isTrans) {
                // 3. 设置padding
                TitleBarUtil.setTitlePadding(mViewHelper.getContentView());
                mViewHelper.getContentView().setFitsSystemWindows(false);
            }
            // 5. 状态栏字体是黑色的
            TitleBarUtil.setMStateBarFontColor(mActivity, true);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 4. 设置Activity透明
            TitleBarUtil.setStatusTranslucent(mActivity);
            mViewHelper.getContentView().setFitsSystemWindows(true);
            if (isTrans) {
                TitleBarUtil.setTitlePadding(mViewHelper.getContentView());
                mViewHelper.getContentView().setFitsSystemWindows(false);
            }
        }
        // 最后设置背景
        mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(windowColor));
    }

    /**
     * 标题样式:适用于渐变色
     *
     * @param resId       标题的颜色
     * @param windowColor 背景颜色
     */
    public void resourceStyle(int resId, int windowColor) {
        this.resourceStyle(resId, windowColor, false);
    }

    /**
     * 标题样式
     *
     * @param resId       标题的颜色
     * @param windowColor 背景颜色
     * @param isDark      状态栏字体是不是黑色
     */
    public void resourceStyle(int resId, int windowColor, boolean isDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 1. 标题颜色
            setRootBackgroundResource(resId);
            // 2. 标题颜色是透明的
            setTitleBackgroundColor(Color.TRANSPARENT);
            // 3. 设置标题和颜色
            TitleBarUtil.setStatusTranslucent(mActivity, resId, true);
            // 4. 设置Activity透明
            TitleBarUtil.setTitlePadding(mViewHelper.getContentView());
            mViewHelper.getContentView().setFitsSystemWindows(false);
            // 5. 状态栏字体颜色
            TitleBarUtil.setMStateBarFontColor(mActivity, isDark);
        }
        // 最后设置背景
        mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(windowColor));
    }

    /**
     * 标题样式
     *
     * @param titleColor  标题的颜色
     * @param windowColor 背景颜色
     * @param isDark      状态栏字体是不是黑色
     * @param isTrans     除非渐变，不然建议设置为false
     */
    public void colorStyle(int titleColor, int windowColor, boolean isDark, boolean isTrans) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 1. 标题颜色
            setRootBackgroundColor(titleColor);
            // 2. 标题颜色是透明的
            setTitleBackgroundColor(Color.TRANSPARENT);
            // 3. 设置标题和颜色
            TitleBarUtil.setStatusTranslucent(mActivity, titleColor);
            mViewHelper.getContentView().setFitsSystemWindows(true);
            // 如果要透明渐变
            if (isTrans) {
                // 4. 设置Activity透明
                TitleBarUtil.setTitlePadding(mViewHelper.getContentView());
                mViewHelper.getContentView().setFitsSystemWindows(false);
            }
            // 5. 状态栏字体颜色
            TitleBarUtil.setMStateBarFontColor(mActivity, isDark);
        }
        // 最后设置背景
        mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(windowColor));
    }

    public void colorStyle(int titleColor, int windowColor) {
        colorStyle(titleColor, windowColor, false, false);
    }

    /**
     * 设置标题
     */
    public void setTitle(CharSequence title) {
        setText(R.id.tv_title_center, title);
    }

    /**
     * 设置标题颜色
     */
    public void setTitleColor(int color) {
        setTextColor(R.id.tv_title_center, color);
    }

    /**
     * 获取标题控件
     */
    public TextView getTitle() {
        return findViewById(R.id.tv_title_center);
    }
}
