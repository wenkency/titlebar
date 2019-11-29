package cn.carhouse.titlebar;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


/**
 * 默认标题
 */
public abstract class TitleBar<B extends TitleBuilder> {


    protected TitleViewHelper mViewHelper;
    protected Activity mActivity;
    protected LinearLayout mWrapParent;

    /**
     * Title布局ID
     */
    protected abstract int getBarLayoutId();

    /**
     * 应用子类的参数
     */
    protected abstract void applyParams(B titleBuild);


    public void apply(TitleBuilder.TitleParams params) {
        // 1. 标题添加到哪个父布局
        mActivity = params.mActivity;
        ViewGroup parent = getParent(params);
        if (parent == null) {
            return;
        }
        mViewHelper = new TitleViewHelper(params.mActivity, parent, getBarLayoutId());
        parent.addView(mViewHelper.getContentView(), 0);
        parent.setBackgroundColor(Color.TRANSPARENT);
        // 2.设置文本
        SparseArray<CharSequence> mTextArray = params.mTextArray;
        int textSize = mTextArray.size();
        for (int i = 0; i < textSize; i++) {
            mViewHelper.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
        }
        // 3.设置点击
        SparseArray<View.OnClickListener> mClickArray = params.mClickArray;
        int clickSize = mClickArray.size();
        for (int i = 0; i < clickSize; i++) {
            mViewHelper.setOnClickListener(mClickArray.keyAt(i), mClickArray.valueAt(i));
        }
        // 应用子类构建参数
        applyParams((B) params.mTitleBuild);
    }

    /**
     * 找父类
     */
    private ViewGroup getParent(TitleBuilder.TitleParams params) {
        // 1. 有父类直接返回
        if (params.mParent != null) {
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
                mWrapParent = new LinearLayout(params.mActivity);
                mWrapParent.setLayoutParams(child.getLayoutParams());
                mWrapParent.setOrientation(LinearLayout.VERTICAL);
                if (child != null) {
                    mWrapParent.addView(child);
                }
                contentView.addView(mWrapParent);
                return mWrapParent;
            }
        }
        return null;
    }


    public <T extends View> T findViewById(int viewId) {
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

    public int dip2px(int dip) {
        if (mActivity == null) {
            return 0;
        }
        // 缩放比例(密度)
        float density = mActivity.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }


}
