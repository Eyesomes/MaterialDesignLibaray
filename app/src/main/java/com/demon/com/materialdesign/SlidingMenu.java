package com.demon.com.materialdesign;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

public class SlidingMenu extends HorizontalScrollView {

    private Context mContext;
    private int mMenuWidth;

    private GestureDetector mGestureDetector;

    private View mMenuView;
    private View mContentView;
    private View mMaskView;


    private float x;
    private float y;

    private boolean mMenuIsOpen = false;

    private GestureDetector.OnGestureListener mGustureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (mMenuIsOpen) {
                if (velocityX < 0 && Math.abs(velocityX) > Math.abs(velocityY)) {
                    closeMenu();
                    return true;
                }
            } else {
                if (velocityX > 0 && Math.abs(velocityX) > Math.abs(velocityY) * 3) {
                    openMenu();
                    return true;
                }
            }
            return false;
        }
    };

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        mMenuWidth = getScreenWidth(context) - dipToPx(40);
        mGestureDetector = new GestureDetector(context, mGustureListener);
    }

    private void init(Context context) {
        ViewGroup container = (ViewGroup) getChildAt(0);
        mMenuView = container.getChildAt(0);
        mContentView = container.getChildAt(1);

        ViewGroup.LayoutParams menuLayoutParams = mMenuView.getLayoutParams();
        menuLayoutParams.width = mMenuWidth;
        mMenuView.setLayoutParams(menuLayoutParams);

        ViewGroup.LayoutParams contentLayoutParams = mContentView.getLayoutParams();
        contentLayoutParams.width = getScreenWidth(context);
        mContentView.setLayoutParams(contentLayoutParams);

        container.removeView(mContentView);
        mMaskView = new View(context);
        mMaskView.setBackgroundColor(Color.parseColor("#65000000"));
        RelativeLayout contentContainer = new RelativeLayout(context);
        contentContainer.addView(mContentView);
        contentContainer.addView(mMaskView);
        contentContainer.setLayoutParams(contentLayoutParams);
        container.addView(contentContainer);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init(mContext);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = 1f * l / mMenuWidth;
        ViewCompat.setAlpha(mMenuView, 0.7f + (1 - scale) * 0.3f);
        ViewCompat.setTranslationX(mMenuView, 0.7f * l);

        mMaskView.setAlpha(1 - scale);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mMenuIsOpen) {
            if (ev.getX() > mMenuWidth) {
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        scrollTo(mMenuWidth, 0);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mGestureDetector.onTouchEvent(ev)) {
            return true;
        }

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            x = ev.getRawX();
            y = ev.getRawY();
            return super.onTouchEvent(ev);
        }
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (Math.abs(ev.getRawX() - x) > Math.abs(ev.getRawY() - y) * 3) {
                return super.onTouchEvent(ev);
            } else {
                return false;
            }
        }

        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (mMenuIsOpen && ev.getX() > mMenuWidth) {
                closeMenu();
                return true;
            }
            int scrollX = getScrollX();
            if (scrollX > mMenuWidth / 2) {
                //关闭
                closeMenu();
            } else {
                //打开
                openMenu();
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    private void closeMenu() {
        mMenuIsOpen = false;
        smoothScrollTo(mMenuWidth, 0);
    }

    private void openMenu() {
        mMenuIsOpen = true;
        smoothScrollTo(0, 0);
    }

    private int dipToPx(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    private int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetris = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetris);
        return outMetris.widthPixels;
    }
}
