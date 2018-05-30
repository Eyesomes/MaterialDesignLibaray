package com.exam.cn.baselibrary.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * alercontroller view工具类
 * Created by admin on 2017/8/4.
 */

class DialogViewHelper {

    private View mContentView;
    //缓存view，避免重复findviewbyid
    private SparseArray<WeakReference<View>> mViews;

    public DialogViewHelper(Context context, int layoutResId) {
        mContentView = LayoutInflater.from(context).inflate(layoutResId, null);
        mViews = new SparseArray<>();
    }

    public DialogViewHelper(View view) {
        mContentView = view;
        mViews = new SparseArray<>();

    }

    /**
     * 获取 ContentView
     *
     * @return
     */
    public View getContentView() {
        return mContentView;
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {
        TextView tv = (TextView) getView(viewId);
        if (tv != null) {
            tv.setText(text);
        }
    }

    /**
     * 设置 点击监听
     *
     * @param viewId
     * @param listener
     */
    public void setOnclickListener(int viewId, View.OnClickListener listener) {
        View view = mContentView.findViewById(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }

    /**
     * 获取view 避免重复 findviewbyid
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {

        WeakReference<View> viewWeakReference = mViews.get(viewId);
        View view = null;
        if (viewWeakReference!=null) {
            view = mViews.get(viewId).get();
        }
        if (view == null) {
            view = mContentView.findViewById(viewId);
            mViews.put(viewId, new WeakReference<>(view));
        }
        return (T) view;
    }
}
