package com.exam.cn.baselibrary.recyclerView.decortation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 杰 on 2017/9/5.
 */

public class LinearLayoutItemDecoration extends RecyclerView.ItemDecoration{

    private Drawable mDivider;

    public LinearLayoutItemDecoration(Context context,int dawableResourceId){
        mDivider = ContextCompat.getDrawable(context,dawableResourceId);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int childCount = parent.getChildCount();
//
        Rect rect = new Rect();
        rect.left = parent.getPaddingLeft();
        rect.right =parent.getWidth() - parent.getPaddingRight();
        for (int i = 1; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            rect.bottom = childView.getTop();
            rect.top = rect.bottom -  mDivider.getIntrinsicHeight();
            mDivider.setBounds(rect);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildLayoutPosition(view);

        if (position != 0) {
            outRect.top = mDivider.getIntrinsicHeight();
        }
    }
}
