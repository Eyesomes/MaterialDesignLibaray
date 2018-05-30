package com.demon.com.materialdesign;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FlowLayout extends ViewGroup {

    // 缓存每行的view 避免多次去判定,循环
    private ArrayList<ArrayList<View>> mChildViews = new ArrayList<>();

    private ArrayList<Integer> mLineHeights = new ArrayList<>();


    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int lineWidth = getPaddingLeft();
        int lineMaxHeight = 0;
        int height = getPaddingTop() + getPaddingBottom();

        mChildViews.clear();
        mLineHeights.clear();
        ArrayList<View> childViews = new ArrayList<>();
        mChildViews.add(childViews);

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            MarginLayoutParams childParams = (MarginLayoutParams) childView.getLayoutParams();

            int childHeight = childView.getMeasuredHeight() + childParams.topMargin + childParams.bottomMargin;
            int childWidth = childView.getMeasuredWidth() + childParams.leftMargin + childParams.rightMargin;

            lineMaxHeight = Math.max(lineMaxHeight, childHeight);

            if (lineWidth + childWidth > width) {
                childViews = new ArrayList<>();
                mChildViews.add(childViews);
                childViews.add(childView);
                mLineHeights.add(lineMaxHeight);

                height += lineMaxHeight;
                lineMaxHeight = childHeight;
                lineWidth = getPaddingLeft() + childWidth;
            } else {
                lineWidth += childWidth;
                childViews.add(childView);
            }
        }
        height += lineMaxHeight;
        mLineHeights.add(lineMaxHeight);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left, right, bottom;
        int top = getPaddingTop();
        for (int i = 0; i < mChildViews.size(); i++) {
            ArrayList<View> childViews = mChildViews.get(i);
            if (childViews.size() == 0)
                return;
            left = getPaddingLeft();
            for (View childView : childViews) {
                MarginLayoutParams childParams = (MarginLayoutParams) childView.getLayoutParams();

                left += childParams.leftMargin;
                right = left + childView.getMeasuredWidth() + childParams.rightMargin;
                int childTop = top + childParams.topMargin;
                bottom = childTop + childView.getMeasuredHeight() + childParams .bottomMargin;

                Log.e("tag", "onLayout: " + left + "-" + top + "-" + right + "-" + bottom);
                childView.layout(left, childTop, right, bottom);
                left = right;
            }
            top += mLineHeights.get(i);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
