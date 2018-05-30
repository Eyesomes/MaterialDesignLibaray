package com.exam.cn.baselibrary.widge.multipleIndicator;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * 包含有底部指示器的indicator容器
 * Created by 杰 on 2017/11/8.
 */

public class IndicatorContainerView extends FrameLayout {

    // indicator容器 因为 ScrollView 只能有一个子view
    private LinearLayout mIndicatorGroup;

    private View mBottomTrackView;

    private LayoutParams mTrackParams;

    private int mItemWidth = 0;

    // 初始的偏移值
    private int mInitLeftMargin = 0;


    public IndicatorContainerView(@NonNull Context context) {
        this(context, null);
    }

    public IndicatorContainerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorContainerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setForegroundGravity(Gravity.CENTER_VERTICAL);
        mIndicatorGroup = new LinearLayout(context);
//        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.gravity = Gravity.CENTER_VERTICAL;
//        mIndicatorGroup.setLayoutParams(layoutParams);
        addView(mIndicatorGroup);
    }


    /**
     * 添加ItemView
     *
     * @param itemView
     */
    public void addItemView(View itemView) {
        mIndicatorGroup.addView(itemView);
    }

    /**
     * 移除所有ItemView
     */
    public void removeAllItemView() {
        mIndicatorGroup.removeAllViews();
    }

    public void removeIndicator() {
        if (mBottomTrackView != null)
            removeView(mBottomTrackView);
    }

    /**
     * 获取ItemView
     *
     * @param position
     * @return
     */
    public View getItemAt(int position) {
        return mIndicatorGroup.getChildAt(position);
    }

    /**
     * 添加底部跟踪的指示器
     *
     * @param bottomTrackView
     * @param mItemWidth
     */
    public void addBottomTrackView(View bottomTrackView, int mItemWidth) {

        if (bottomTrackView == null) {
            return;
        }

        this.mItemWidth = mItemWidth;
        this.mBottomTrackView = bottomTrackView;
        addView(mBottomTrackView);

        mTrackParams = (LayoutParams) mBottomTrackView.getLayoutParams();
        mTrackParams.gravity = Gravity.BOTTOM;

        // 宽度  如果指定了宽度 就用指定的
        int trackWidth = mTrackParams.width;

        // 过大不用
        if (trackWidth == ViewGroup.LayoutParams.MATCH_PARENT || trackWidth > mItemWidth) {
            trackWidth = mItemWidth;
        }

        mTrackParams.width = trackWidth;

        // 确保在中间
        mInitLeftMargin = (mItemWidth - trackWidth) / 2;
        mTrackParams.leftMargin = mInitLeftMargin;
    }

    /**
     * 滚动底部的指示器
     *
     * @param position
     * @param positionOffset
     */
    public void scrollBottomTrackView(int position, float positionOffset) {
        if (mBottomTrackView == null) {
            return;
        }

        int leftMargin = (int) ((position + positionOffset) * mItemWidth) + mInitLeftMargin;
        mTrackParams.leftMargin = leftMargin;
        mBottomTrackView.setLayoutParams(mTrackParams);
    }

    /**
     * 点击移动底部的指示器到指定位置
     *
     * @param position
     */
    public void smoothScrollBottom(int position) {
        if (mBottomTrackView == null) {
            return;
        }

        // 最终的位置
        int finalLeftMargin = (int) ((position) * mItemWidth) + mInitLeftMargin;
        // 当前的位置
        int currentLeftMargin = mTrackParams.leftMargin;

        int distance = finalLeftMargin - currentLeftMargin;
        // 用动画来执行
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(currentLeftMargin, finalLeftMargin).setDuration((long) (Math.abs(distance) * 0.3f));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float leftMargin = (float) animation.getAnimatedValue();
                mTrackParams.leftMargin = (int) leftMargin;
                mBottomTrackView.setLayoutParams(mTrackParams);
            }
        });

        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.start();
    }
}
