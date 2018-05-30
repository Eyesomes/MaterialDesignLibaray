package com.exam.cn.baselibrary.widge.banner;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by 杰 on 2017/11/12.
 */

public class BannerViewPager extends ViewPager {

    private BannerAdapter mAdapter;

    private final int SCROLL_MSG = 0x0011;

    private int mSwitchTime = 60000;

    private BannerScroller mScroller;

    private Map<Integer, View> mConvertViews;

    private Activity mActivity;

    // activity 的生命周期监听
    private Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new SimpleActivityLifecycleCallbacks() {
        @Override
        public void onActivityResumed(Activity activity) {
            if (activity == mActivity)
                // 开始轮播
                mHandler.sendEmptyMessageDelayed(SCROLL_MSG, mSwitchTime);
        }

        @Override
        public void onActivityPaused(Activity activity) {
            if (activity == mActivity)
                //停止轮播
                mHandler.removeMessages(SCROLL_MSG);
        }
    };

    // 轮播
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SCROLL_MSG:

                    setCurrentItem(getCurrentItem() + 1);
                    startScroll();
                    break;
                default:
                    break;
            }
        }
    };

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        mActivity = (Activity) context;

        switchScroller(context);

        mConvertViews = new ArrayMap();
    }

//----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 设置banneradapter
     *
     * @param adapter
     */
    public void setAdapter(BannerAdapter adapter) {
        this.mAdapter = adapter;

        setAdapter(new MyAdapter());

        // 注册 mActivityLifecycleCallbacks
        mActivity.getApplication().registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }

//----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 替换换scroller 用于i修改切换速度
     *
     * @param context
     */
    private void switchScroller(Context context) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            mScroller = new BannerScroller(context);
            field.setAccessible(true);
            field.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置页面的切换速度
     *
     * @param duration
     */
    public void setScrollerDuration(int duration) {
        mScroller.setScrollerDuration(duration);
    }

    /**
     * 轮播
     */
    public void startScroll() {
        // 清除消息
        mHandler.removeMessages(SCROLL_MSG);

        mHandler.sendEmptyMessageDelayed(SCROLL_MSG, mSwitchTime);
    }

    /**
     * 销毁handler 防止内存泄漏
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(SCROLL_MSG);
        mHandler = null;

        mActivity.getApplication().unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }

    /**
     * 获取复用的view 不够就再加
     *
     * @param i
     * @return
     */
    public View getConvertView(int i) {
        View view = mConvertViews.get(i);
        if (view != null && view.getParent()==null){
            return view;
        }
        return null ;
    }


    //----------------------------------------------------------------------------------------------------------------------------------------------
    private class MyAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 创建条目
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mAdapter.getItemView(container, position % mAdapter.getCount(), getConvertView(position % mAdapter.getCount()));
            container.addView(itemView);
            return itemView;
        }

        /**
         * 销毁条目
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mAdapter.destroyItem(container,position, object);
            container.removeView((View) object);
            View v = mConvertViews.get(position %mAdapter.getCount());
            if (v == null) {
                mConvertViews.put(position %mAdapter.getCount(),  (View) object);
            }
        }
    }

}
