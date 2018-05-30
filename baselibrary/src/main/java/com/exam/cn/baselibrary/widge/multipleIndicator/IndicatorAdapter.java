package com.exam.cn.baselibrary.widge.multipleIndicator;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 杰 on 2017/11/8.
 */

public abstract class IndicatorAdapter {

    // 获取条数
    public abstract int getCount();

    // vie
    public abstract View getView(int position, ViewGroup parent);

    // 当前位置的高亮效果
    public void highLightIndicator(View view, int position) {

    }

    // 重置位置的高亮效果
    public void restoreIndicator(View view, int position) {

    }

    // 添加底部跟踪的指示器
    public View getBottomTrackView() {
        return null;
    }
}
