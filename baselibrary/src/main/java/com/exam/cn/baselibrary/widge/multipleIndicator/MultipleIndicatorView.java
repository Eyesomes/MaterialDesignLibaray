package com.exam.cn.baselibrary.widge.multipleIndicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.exam.cn.baselibrary.R;


/**
 * 用 adapter适应多种indicator样式
 * Created by 杰 on 2017/11/8.
 */

public class MultipleIndicatorView extends HorizontalScrollView implements ViewPager.OnPageChangeListener {

    private IndicatorAdapter mIndicatorAdapter;

    // indicator容器 因为 ScrollView 只能有一个子view
    private IndicatorContainerView mIndicatorContainer;

    // 一个屏幕可见个数
    private int mVisibleNums;
    // 每个Item的宽度
    private int mItemWidth = 0;

    // 当前的位置
    private int mCurrentPosition = 0;

    private ViewPager mViewPager;

    // 为了解决点击tab 抖动切换的问题
    private boolean mIsExecuteScroll = false;

    private boolean mIsFirst = false;


    public MultipleIndicatorView(Context context) {
        this(context, null);
    }

    public MultipleIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultipleIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mIndicatorContainer = new IndicatorContainerView(context);
        addView(mIndicatorContainer);

        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MultipleIndicatorView);

        mVisibleNums = typedArray.getInt(R.styleable.ColorTrackTextView_originColor, 0);

        typedArray.recycle();
    }
    public void setVisibleNums(int num) {
        this.mVisibleNums = num;
    }

    /**
     * 设置 adapter 并 添加view
     *
     * @param adapter
     */
    public void setAdapter(IndicatorAdapter adapter) {

        if (adapter == null) {
            throw new NullPointerException("adapter is null");
        }
        mIndicatorContainer.removeAllItemView();
        mIndicatorContainer.removeIndicator();
        this.mIndicatorAdapter = adapter;
        mIsFirst = true;

        int itemCount = mIndicatorAdapter.getCount();
        for (int i = 0; i < itemCount; i++) {
            View itemView = mIndicatorAdapter.getView(i, this);
            mIndicatorContainer.addItemView(itemView);

            // 设置与view pager的联动 ， 即点击切换
            if (mViewPager != null) {
                clickSwitchPager(itemView, i);
            }
        }

        // 指定item的宽度 需要去onLayout里面 此时的 getWidth() = 0

        // 默认高亮第一个位置
        mIndicatorAdapter.highLightIndicator(mIndicatorContainer.getItemAt(0), 0);

        // 添加底部跟踪的指示器 v需要去onLayout里面 此时的 getWidth() = 0
    }

    /**
     * 设置 adapter 并 添加view  设置viewPager
     *
     * @param adapter
     */
    public void setAdapter(IndicatorAdapter adapter, ViewPager viewPager) {

        if (viewPager == null) {
            throw new NullPointerException("viewPager is null");
        }
        this.mViewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
        mIsFirst = true;
        setAdapter(adapter);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        if (mIndicatorAdapter != null && mIsFirst) {
            // 制定item的宽度
            mItemWidth = getItemWidth();

            int itemCount = mIndicatorAdapter.getCount();
            for (int i = 0; i < itemCount; i++) {
                View view = mIndicatorContainer.getItemAt(i);
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = mItemWidth;
                view.setLayoutParams(layoutParams);
            }

            // 添加底部跟踪的指示器
            mIndicatorContainer.addBottomTrackView(mIndicatorAdapter.getBottomTrackView(), mItemWidth);
            mIsFirst = false;
        }
    }

    /**
     * 设置点击事件 与viewpager联动
     *
     * @param view
     * @param position
     */
    private void clickSwitchPager(View view, final int position) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(position);
                // 滚动到相应位置 ， 即居中
                smoothScrollCurrentIndicator(position);
                // 移动下标
                mIndicatorContainer.smoothScrollBottom(position);
            }
        });
    }

    /**
     * 获取每一个条目的宽度
     */
    public int getItemWidth() {
        int itemWidth = 0;
        // 获取当前控件的宽度
        int width = getWidth();
        if (mVisibleNums != 0) {
            // 在布局文件中指定一屏幕显示多少个
            itemWidth = width / mVisibleNums;
            return itemWidth;
        }
        // 如果没有指定获取最宽的一个作为ItemWidth
        int maxItemWidth = 0;
        int mItemCounts = mIndicatorAdapter.getCount();
        // 总的宽度
        int allWidth = 0;

        for (int i = 0; i < mItemCounts; i++) {
            View itemView = mIndicatorContainer.getItemAt(i);
            int childWidth = itemView.getLayoutParams().width;
            maxItemWidth = Math.max(maxItemWidth, childWidth);
            allWidth += childWidth;
        }

        itemWidth = maxItemWidth;
        // 如果不足一个屏那么宽度就为  width/mItemCounts
        if (allWidth < width) {
            itemWidth = width / mItemCounts;
        }
        return itemWidth + 24;
    }

    /**
     * indicator 的滚动
     *
     * @param position
     * @param positionOffset
     */
    private void scrollCurrentIndicator(int position, float positionOffset) {
        // 当前的总共的偏移位置
        int totalScroll = (int) ((position + positionOffset) * mItemWidth);
        // 中心的偏移量
        int offSetWith = (getWidth() - mItemWidth) / 2;
        // 需要滚动的距离
        int finalScroll = totalScroll - offSetWith;

        scrollTo(finalScroll, 0);
    }

    /**
     * 带动画的滚动到指定位置
     *
     * @param position
     */
    public void smoothScrollCurrentIndicator(int position) {
        // 当前的总共的偏移位置
        int totalScroll = (int) (position * mItemWidth);
        // 中心的偏移量
        int offSetWith = (getWidth() - mItemWidth) / 2;
        // 需要滚动的距离
        int finalScroll = totalScroll - offSetWith;

        smoothScrollTo(finalScroll, 0);
    }

    public void scrollTo(int position) {
            // 滚动indicator
            scrollCurrentIndicator(position, 0);
            // 滚动底部的指示器
            mIndicatorContainer.scrollBottomTrackView(position, 0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mIsExecuteScroll) {
            // 滚动indicator
            scrollCurrentIndicator(position, positionOffset);
            // 滚动底部的指示器
            mIndicatorContainer.scrollBottomTrackView(position, positionOffset);
        }
    }


    @Override
    public void onPageSelected(int position) {
        // 上一个位置重置
        mIndicatorAdapter.restoreIndicator(mIndicatorContainer.getItemAt(mCurrentPosition), mCurrentPosition);

        // 当前位置高亮
        mIndicatorAdapter.highLightIndicator(mIndicatorContainer.getItemAt(position), position);
        mCurrentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // 只有有滑动动作的时候我们才让 mIsExecuteScroll 为true ，停止就false 注意还有state = 2的情况

        if (state == 1) {
            mIsExecuteScroll = true;
        }

        if (state == 0) {
            mIsExecuteScroll = false;
        }
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
