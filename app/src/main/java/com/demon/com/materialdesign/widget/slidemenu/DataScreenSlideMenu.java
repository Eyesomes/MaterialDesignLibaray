package com.demon.com.materialdesign.widget.slidemenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class DataScreenSlideMenu extends LinearLayout {

    private Context mContext;

    private LinearLayout mMenuTabView;
    private FrameLayout mContentContainer;
    private FrameLayout mMenuContainer;
    private View mShadowView;

    private SlideMenuAdapter mAdapter;

    private int mMenuContainerHeight;
    private int mShadowColor = Color.parseColor("#88999999");

    private int mCurrentPosition = -1;
    private boolean mAnimatorIsExecute = false;

    public DataScreenSlideMenu(Context context) {
        this(context, null);
    }

    public DataScreenSlideMenu(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DataScreenSlideMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        initLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mMenuContainerHeight == 0) {
            int height = MeasureSpec.getSize(heightMeasureSpec);
            mMenuContainerHeight = height * 75 / 100;
            ViewGroup.LayoutParams layoutParams = mMenuContainer.getLayoutParams();
            layoutParams.height = mMenuContainerHeight;
            mMenuContainer.setLayoutParams(layoutParams);
            mMenuContainer.setTranslationY(-mMenuContainerHeight);
        }
    }

    private void initLayout() {
        setOrientation(VERTICAL);

        mMenuTabView = new LinearLayout(mContext);
        mMenuTabView.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mMenuTabView);

        mContentContainer = new FrameLayout(mContext);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        params.weight = 1;
        mContentContainer.setLayoutParams(params);
        addView(mContentContainer);

        mShadowView = new View(mContext);
        mShadowView.setBackgroundColor(mShadowColor);
        mShadowView.setAlpha(0);
        mShadowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
        mShadowView.setVisibility(GONE);
        mContentContainer.addView(mShadowView);//framlayout默认铺满

        mMenuContainer = new FrameLayout(mContext);
        mMenuContainer.setBackgroundColor(Color.WHITE);
        mContentContainer.addView(mMenuContainer);
    }

    public void setAdapter(SlideMenuAdapter adapter) {
        if (adapter == null)
            return;
        if (mAdapter != null)
            mAdapter.unRegisterObserver();
        this.mAdapter = adapter;
        mAdapter.registerObserver(new AdapterMenuObserver());

        int count = mAdapter.getCount();
        for (int i = 0; i < count; i++) {
            View tabView = mAdapter.getTabView(i, mMenuTabView);
            mMenuTabView.addView(tabView);
            LayoutParams params = (LayoutParams) tabView.getLayoutParams();
            params.weight = 1;
            tabView.setLayoutParams(params);
            setTabClick(tabView, i);

            View menuView = mAdapter.getMenuView(i, mMenuContainer);
            menuView.setVisibility(GONE);
            mMenuContainer.addView(menuView);
        }
    }

    private void setTabClick(final View tabview, final int position) {
        tabview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentPosition == -1) {
                    openMenu(position, tabview);
                } else {
                    if (mCurrentPosition == position)
                        closeMenu();
                    else
                        switchMenu(position);
                }
            }
        });
    }

    private void closeMenu() {
        if (mAnimatorIsExecute)
            return;
        if (mCurrentPosition == -1)
            return;

        ObjectAnimator menuAnimator = ObjectAnimator.ofFloat(mMenuContainer,
                "translationY", 0, 0 - mMenuContainerHeight);
        menuAnimator.setDuration(350);
        ObjectAnimator shadowAnimator = ObjectAnimator.ofFloat(mShadowView,
                "alpha", 1, 0);
        shadowAnimator.setDuration(350);
        menuAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                mAdapter.menuClose(mCurrentPosition, mMenuTabView.getChildAt(mCurrentPosition));
                mAnimatorIsExecute = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                View menuView = mMenuContainer.getChildAt(mCurrentPosition);
                menuView.setVisibility(GONE);

                mCurrentPosition = -1;
                mShadowView.setVisibility(GONE);

                mAnimatorIsExecute = false;
            }
        });

        menuAnimator.start();
        shadowAnimator.start();
    }

    private void openMenu(final int position, final View tabview) {
        if (mAnimatorIsExecute)
            return;
        if (mCurrentPosition != -1)
            return;

        mShadowView.setVisibility(VISIBLE);
        View menuView = mMenuContainer.getChildAt(position);
        menuView.setVisibility(VISIBLE);

        ObjectAnimator menuAnimator = ObjectAnimator.ofFloat(mMenuContainer,
                "translationY", 0 - mMenuContainerHeight, 0);
        menuAnimator.setDuration(350);
        ObjectAnimator shadowAnimator = ObjectAnimator.ofFloat(mShadowView,
                "alpha", 0, 1);
        shadowAnimator.setDuration(350);
        menuAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentPosition = position;
                mAnimatorIsExecute = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mAdapter.menuOpen(position, tabview);
                mAnimatorIsExecute = true;
            }
        });

        menuAnimator.start();
        shadowAnimator.start();
    }

    private void switchMenu(int position) {

        View currentTab = mMenuContainer.getChildAt(mCurrentPosition);
        currentTab.setVisibility(GONE);
        mAdapter.menuClose(mCurrentPosition, currentTab);
        mCurrentPosition = position;
        currentTab = mMenuContainer.getChildAt(mCurrentPosition);
        currentTab.setVisibility(VISIBLE);
        mAdapter.menuOpen(mCurrentPosition, currentTab);
    }

    private class AdapterMenuObserver extends MenuObserver {

        @Override
        public void closeMenu() {
            DataScreenSlideMenu.this.closeMenu();
        }
    }
}
