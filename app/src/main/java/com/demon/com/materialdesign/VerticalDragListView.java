package com.demon.com.materialdesign;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class VerticalDragListView extends FrameLayout {

    private ViewDragHelper mViewDragHelper;

    private View mDragView;

    private View mMenuView;
    private int mMenuHeight;


    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mDragView;
        }

//          水平拖动
//        @Override
//        public int clampViewPositionHorizontal(View child, int left, int dx) {
//            return super.clampViewPositionHorizontal(child, left, dx);
//        }


        // 手指松开时回调
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (yvel >mMenuHeight/2){
                mViewDragHelper.settleCapturedViewAt(0,mMenuHeight);
            }else {
                mViewDragHelper.settleCapturedViewAt(0,0);
            }
            invalidate();
            setOnTouchListener();
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (top < 0) {
                top = 0;
            } else if (top > mMenuHeight) {
                top = mMenuHeight;
            }
            return top;
        }
    };

    public VerticalDragListView(@NonNull Context context) {
        this(context, null);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewDragHelper = ViewDragHelper.create(this, mCallback);
    }

    @Override
    protected void onFinishInflate() {
        int childCount = getChildCount();
        if (childCount != 2)
            throw new RuntimeException("VerticalDragListView有且只有2个子view");
        mDragView = getChildAt(1);
        mMenuView = getChildAt(0);
        super.onFinishInflate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mMenuHeight = mMenuView.getMeasuredHeight();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)){
            invalidate();
        }
        super.computeScroll();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return super.onInterceptTouchEvent(ev);
    }
}
