package com.exam.cn.baselibrary.navigationbar.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础类
 */
public class AbsNavigationBar<T extends AbsNavigationBar.Builder, A extends AbsNavigationBar>
        implements INavigationBar {

    private T mBuilder;
    private View mNavigationBar;

    private SparseArray<View> mViews;

    protected AbsNavigationBar(T builder) {
        this.mBuilder = builder;
        mViews = new SparseArray<>();

        createNavigationBar();
    }

    public Builder getBuilder() {
        return mBuilder;
    }

    @Override
    public void createNavigationBar() {
        mNavigationBar = LayoutInflater.from(mBuilder.mContext)
                .inflate(mBuilder.layoutId, mBuilder.mParent, false);

        attachParent(mNavigationBar, mBuilder.mParent);
        attachNavigationParams();
    }

    @Override
    public void attachParent(View view, ViewGroup parent) {
        if (parent == null) {
            ViewGroup activityRoot = (ViewGroup) ((Activity) (mBuilder.mContext))
                    .getWindow().getDecorView();
            parent = (ViewGroup) activityRoot.getChildAt(0);
        }
        parent.addView(view, 0);
    }

    @Override
    public void attachNavigationParams() {
        Map<Integer, String> textMap = mBuilder.mTextMap;
        for (Map.Entry<Integer, String> entry : textMap.entrySet()) {
            setText(entry.getKey(), entry.getValue());
        }

        Map<Integer, View.OnClickListener> clickListenerMap = mBuilder.mClickListenerMap;
        for (Map.Entry<Integer, View.OnClickListener> entry : clickListenerMap.entrySet()) {
            setOnclickListener(entry.getKey(), entry.getValue());
        }

        Map<Integer, Integer> resMap = mBuilder.mImageResMap;
        for (Map.Entry<Integer, Integer> entry : resMap.entrySet()) {
            setImageRes(entry.getKey(), entry.getValue());
        }

        Map<Integer, Integer> bgColorMap = mBuilder.mBgColorMap;
        for (Map.Entry<Integer, Integer> entry : bgColorMap.entrySet()) {
            setBackgroundColor(entry.getKey(), entry.getValue());
        }
    }

    public <V extends View> V getView(int viewId) {
        if (mViews.indexOfKey(viewId) != -1)
            return (V) mViews.get(viewId);
        else {
            View view = mNavigationBar.findViewById(viewId);
            mViews.put(viewId, view);
            return (V) view;
        }
    }

    public A setText(int id, String text) {
        TextView textView = getView(id);
        textView.setText(text);
        return (A) this;
    }

    public A setOnclickListener(int id, View.OnClickListener listener) {
        getView(id).setOnClickListener(listener);
        return (A) this;
    }

    public A setBackgroundColor(int id, int color) {
        getView(id).setBackgroundColor(color);
        return (A) this;
    }

    public A setImageRes(int id, int resId) {
        ImageView imageView = getView(id);
        imageView.setImageResource(resId);
        return (A) this;
    }

    /**
     * 构建类 存储参数
     */
    public static abstract class Builder<B extends Builder> {

        public Context mContext;
        public int layoutId;
        public ViewGroup mParent;

        public HashMap<Integer, String> mTextMap;
        public HashMap<Integer, Integer> mImageResMap;
        public HashMap<Integer, Integer> mBgColorMap;
        public HashMap<Integer, View.OnClickListener> mClickListenerMap;


        public Builder(Context context, int layotId, ViewGroup parent) {
            this.mContext = context;
            this.layoutId = layotId;
            this.mParent = parent;

            mTextMap = new HashMap<>();
            mImageResMap = new HashMap<>();
            mClickListenerMap = new HashMap<>();
            mBgColorMap = new HashMap<>();
        }

        public abstract AbsNavigationBar create();

        protected B setText(int id, String text) {
            mTextMap.put(id, text);
            return (B) this;
        }

        protected B setOnclickListener(int id, View.OnClickListener listener) {
            mClickListenerMap.put(id, listener);
            return (B) this;
        }

        protected B setImageRes(int id, int resId) {
            mImageResMap.put(id, resId);
            return (B) this;
        }

        protected B setbgColor(int id, int color) {
            mBgColorMap.put(id, color);
            return (B) this;
        }
    }
}
