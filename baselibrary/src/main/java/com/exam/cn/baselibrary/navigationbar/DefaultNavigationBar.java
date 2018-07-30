package com.exam.cn.baselibrary.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.exam.cn.baselibrary.R;


/**
 * Created by admin on 2017/8/7.
 */

public class DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder.DefaultNavigationParams> {
    public DefaultNavigationBar(DefaultNavigationBar.Builder.DefaultNavigationParams params) {
        super(params);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.navigationbar;
    }

    @Override
    public void applyView() {
        // 绑定效果
        if (getParams().mBgColor!=0){
            RelativeLayout relativeLayout = findViewById(R.id.navigationbar_container);
            relativeLayout.setBackgroundColor(getParams().mBgColor);
        }
        if (getParams().mTitle!=null) {
            setText(R.id.navigationbar_title, getParams().mTitle);
        }
        if (getParams().mRightText!=null) {
            setText(R.id.navigationbar_right_text, getParams().mRightText);
        }
        if (getParams().mLeftText!=null){
            setText(R.id.navigationbar_left_text, getParams().mLeftText);
        }
        if (getParams().mRightIcon!=0){
            setIcon(R.id.navigationbar_right_icon,getParams().mRightIcon);
        }
        if (getParams().mRightIcon2 !=0){
            setIcon(R.id.navigationbar_right_icon2,getParams().mRightIcon2);
        }
        if (getParams().mLeftIcon!=0){
            setIcon(R.id.navigationbar_left_icon,getParams().mLeftIcon);
        }else {//左边默认一个后退icon
            setIcon(R.id.navigationbar_left_icon, R.mipmap.back);
        }

        //左边一个默认的 finish()
        if (getParams().mLeftIconClickListener ==null){
            setOnclickListener(R.id.navigationbar_left_icon, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = (Activity) getParams().mContext;
                    activity.finish();
                }
            });
        }else {
            setOnclickListener(R.id.navigationbar_left_icon, getParams().mLeftIconClickListener);
        }

        if (getParams().mLeftClickListener!=null) {
            setOnclickListener(R.id.navigationbar_left_text, getParams().mLeftClickListener);
        }

        if (getParams().mRightClickListener!=null) {
            setOnclickListener(R.id.navigationbar_right_text, getParams().mRightClickListener);
        }

        if (getParams().mRightIconClickListener!=null) {
            setOnclickListener(R.id.navigationbar_right_icon, getParams().mRightIconClickListener);
        }

        if (getParams().mRightIcon2ClickListener!=null) {
            setOnclickListener(R.id.navigationbar_right_icon2, getParams().mRightIcon2ClickListener);
        }
    }



    public static class Builder extends AbsNavigationBar.Builder {

        DefaultNavigationParams P;

        public Builder(Context context) {
            super(context, null);
            P = new DefaultNavigationParams(context, null);
        }

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            P = new DefaultNavigationParams(context, parent);
        }

        @Override
        public DefaultNavigationBar builder() {
            DefaultNavigationBar navigationBar = new DefaultNavigationBar(P);

            return navigationBar;
        }

        // 设置所有效果

        /**
         * 设置title
         * @param title
         * @return
         */
        public DefaultNavigationBar.Builder setTitle(String title) {
            P.mTitle = title;
            return this;
        }

        /**
         * 设置右边的text
         *
         * @param rightText
         * @return
         */
        public DefaultNavigationBar.Builder setRightText(String rightText) {
            P.mRightText = rightText;
            return this;
        }

        /**
         * 设置右边的图片
         *
         * @param rightRes
         * @return
         */
        public DefaultNavigationBar.Builder setRightIcon(int rightRes) {
            P.mRightIcon = rightRes;
            return this;
        }

        /**
         * 设置右边的text图片
         *
         * @param rightRes
         * @return
         */
        public DefaultNavigationBar.Builder setRightIcon2(int rightRes) {
            P.mRightIcon2 = rightRes;
            return this;
        }

        /**
         * 设置左边的text
         *
         * @param leftText
         * @return
         */
        public DefaultNavigationBar.Builder setLeftText(String leftText) {
            P.mLeftText = leftText;
            return this;
        }

        /**
         * 设置左边的图片
         *
         * @param leftRes
         * @return
         */
        public DefaultNavigationBar.Builder setLeftIcon(int leftRes) {
            P.mLeftIcon = leftRes;
            return this;
        }

        /**
         * 设置右边的text ClickListener
         * @param listener
         * @return
         */
        public DefaultNavigationBar.Builder setRightClickListener(View.OnClickListener listener) {
            P.mRightClickListener = listener;
            return this;
        }

        /**
         * 设置右边的icon ClickListener
         * @param listener
         * @return
         */
        public DefaultNavigationBar.Builder setRightIconClickListener(View.OnClickListener listener) {
            P.mRightIconClickListener = listener;
            return this;
        }

        /**
         * 设置右边的icon2 ClickListener
         * @param listener
         * @return
         */
        public DefaultNavigationBar.Builder setRightIconClickListener2(View.OnClickListener listener) {
            P.mRightIcon2ClickListener = listener;
            return this;
        }

        /**
         * 设置右边的text ClickListener
         * @param listener
         * @return
         */
        public DefaultNavigationBar.Builder setLeftClickListener(View.OnClickListener listener) {
            P.mLeftClickListener = listener;
            return this;
        }

        /**
         * 设置左边的icon ClickListener
         * @param listener
         * @return
         */
        public DefaultNavigationBar.Builder setLeftIconClickListener(View.OnClickListener listener) {
            P.mLeftIconClickListener = listener;
            return this;
        }

        /**
         * 设置背景色
         * @param bgColor
         * @return
         */
        public DefaultNavigationBar.Builder setBgColor(int bgColor) {
            P.mBgColor = bgColor;
            return this;
        }

        public static class DefaultNavigationParams extends AbsNavigationParams {

            // 放置所有效果
            public String mTitle;
            public String mRightText;
            public String mLeftText;
            public int mRightIcon = 0;
            public int mLeftIcon = 0;
            public int mBgColor = 0;
            public int mRightIcon2;

            public View.OnClickListener mRightClickListener;
            public View.OnClickListener mLeftIconClickListener;
            public View.OnClickListener mRightIconClickListener;
            public View.OnClickListener mLeftClickListener;
            public View.OnClickListener mRightIcon2ClickListener;


            public DefaultNavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }
    }
}
