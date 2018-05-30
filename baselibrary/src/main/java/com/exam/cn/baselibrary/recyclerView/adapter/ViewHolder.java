package com.exam.cn.baselibrary.recyclerView.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 杰 on 2017/10/13.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    //用于缓存 View ,避免重复的findViewById
    private SparseArray<View> mViews;

    public ViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    /**
     * 使用缓存的方式 , 避免重复的findViewById
     * 用泛型避免强转
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {

        View view = mViews.get(viewId);

        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    // 通用的功能封装  设置文本 设置图片 点击事件

    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, CharSequence text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    /**
     * 设置图片资源
     *
     * @param viewId
     * @param resourceId
     * @return
     */
    public ViewHolder setImageResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }

    /**
     * 设置是否显示
     *
     * @param viewId
     * @param visibility
     * @return
     */
    public ViewHolder setVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    /**
     * 点击事件
     * @param viewId
     * @param listener
     * @return
     */
    public ViewHolder setClickListener(int viewId, View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    /**
     * 加载网络图片  用 ImageLoaderHolder 优化使用
     * @param viewId
     * @param imageLoaderHolder
     * @return
     */
    public ViewHolder setImagePath(int viewId , ImageLoaderHolder imageLoaderHolder){
        ImageView imageView = getView(viewId);

        imageLoaderHolder.loadImage(imageView,imageLoaderHolder.getPath());
        return this;
    }
}
