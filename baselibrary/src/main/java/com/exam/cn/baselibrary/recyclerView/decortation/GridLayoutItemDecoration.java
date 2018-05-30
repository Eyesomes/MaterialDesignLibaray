package com.exam.cn.baselibrary.recyclerView.decortation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 杰 on 2017/9/5.
 */

public class GridLayoutItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    public GridLayoutItemDecoration(Context context, int dawableResourceId) {
        mDivider = ContextCompat.getDrawable(context, dawableResourceId);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        drawHorizontal(c, parent);
        drawVerticle(c, parent);
    }

    private void drawVerticle(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();

            int left = childView.getRight() + params.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();
            int top = childView.getTop() - params.topMargin;
            int bottom = childView.getBottom() + params.bottomMargin;

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();

            int left = childView.getLeft() - params.leftMargin;
            int right = childView.getRight() + params.rightMargin + mDivider.getIntrinsicWidth();
            int top = childView.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int right = mDivider.getIntrinsicWidth();
        int bottom = mDivider.getIntrinsicHeight();

        if (isLastCloumn(view, parent)) {
            right = 0;
        }

        if (isLastRow(view, parent)) {
            bottom = 0;
        }

        outRect.bottom = bottom;
        outRect.right = right;
    }

    /**
     * 最后一行
     *
     * @param view
     * @param parent
     * @return
     */
    private boolean isLastRow(View view, RecyclerView parent) {
        //列
        int spanCount = getSpanCount(parent);
        //行
        int rowNumber = parent.getAdapter().getItemCount() % spanCount == 0 ?
                parent.getAdapter().getItemCount() / spanCount :
                (parent.getAdapter().getItemCount() / spanCount + 1);

        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();

        //当前位置>（行数-1 )*列数
        return (position + 1) > (position - 1) * spanCount;
    }

    /**
     * 最后一列
     *
     * @param view
     * @param parent
     * @return
     */
    private boolean isLastCloumn(View view, RecyclerView parent) {

        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int spanCount = getSpanCount(parent);

        return (position + 1) % spanCount == 0;
    }

    /**
     * 获取行数
     *
     * @param parent
     * @return
     */
    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int spanCount = gridLayoutManager.getSpanCount();

            return spanCount;
        }
        return 1;
    }
}
