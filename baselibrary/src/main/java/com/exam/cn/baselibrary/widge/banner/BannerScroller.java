package com.exam.cn.baselibrary.widge.banner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 *
 * 复写Scroller改变动画的持续时间
 * Created by 杰 on 2017/11/13.
 */

public class BannerScroller extends Scroller {

    private int mScrollerDuration = 2000;

    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    public void setScrollerDuration(int duration){
        this.mScrollerDuration = duration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {

        super.startScroll(startX, startY, dx, dy, mScrollerDuration);
    }
}
