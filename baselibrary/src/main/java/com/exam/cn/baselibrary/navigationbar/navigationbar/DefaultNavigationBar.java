package com.exam.cn.baselibrary.navigationbar.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.exam.cn.baselibrary.R;

public class DefaultNavigationBar extends AbsNavigationBar {

    public static final int ID_NAVIGATIONBAR = R.layout.navigationbar;
    public static final int ID_CONTAINER = R.id.navigationbar_container;
    public static final int ID_TITLE_TEXT = R.id.navigationbar_title;
    public static final int ID_LEFT_TEXT = R.id.navigationbar_left_text;
    public static final int ID_RIGHT_TEXT = R.id.navigationbar_right_text;
    public static final int ID_LEFT_ICON = R.id.navigationbar_left_icon;
    public static final int ID_RIGHT_ICON1 = R.id.navigationbar_right_icon;
    public static final int ID_RIGHT_ICON2 = R.id.navigationbar_right_icon2;

    protected DefaultNavigationBar(Builder builder) {
        super(builder);
    }

    public static class Builder extends AbsNavigationBar.Builder<Builder> {

        public Builder(Context context) {
            this(context, null);
        }

        public Builder(Context context, ViewGroup parent) {
            super(context, ID_NAVIGATIONBAR, parent);
        }

        @Override
        public DefaultNavigationBar create() {
            return new DefaultNavigationBar(this);
        }

        /**
         * 设置title
         *
         * @param title
         * @return
         */
        public DefaultNavigationBar.Builder title(String title) {
            setText(ID_TITLE_TEXT, title);
            return this;
        }

        /**
         * 设置右边的text
         *
         * @param rightText
         * @return
         */
        public DefaultNavigationBar.Builder rightText(String rightText) {
            setText(ID_RIGHT_TEXT, rightText);
            return this;
        }

        /**
         * 设置右边的图片
         *
         * @param rightRes
         * @return
         */
        public DefaultNavigationBar.Builder rightIcon1(int rightRes) {
            setImageRes(ID_RIGHT_ICON1, rightRes);
            return this;
        }

        /**
         * 设置右边的图片
         *
         * @param rightRes
         * @return
         */
        public DefaultNavigationBar.Builder rightIcon2(int rightRes) {
            setImageRes(ID_RIGHT_ICON2, rightRes);
            return this;
        }

        /**
         * 设置左边的text
         *
         * @param leftText
         * @return
         */
        public DefaultNavigationBar.Builder leftText(String leftText) {
            setText(ID_LEFT_TEXT, leftText);
            return this;
        }

        /**
         * 设置左边的图片
         *
         * @param leftRes
         * @return
         */
        public DefaultNavigationBar.Builder leftIcon(int leftRes) {
            setImageRes(ID_LEFT_ICON, leftRes);
            return this;
        }

        /**
         * 设置左边默认的后退按钮
         *
         * @return
         */
        public DefaultNavigationBar.Builder defautBack() {
            setImageRes(ID_LEFT_ICON, R.mipmap.back);
            setOnclickListener(ID_LEFT_ICON, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) mContext).finish();
                }
            });
            return this;
        }

        /**
         * 设置右边的text ClickListener
         *
         * @param listener
         * @return
         */
        public DefaultNavigationBar.Builder rightTextClickListener(View.OnClickListener listener) {
            setOnclickListener(ID_RIGHT_TEXT, listener);
            return this;
        }

        /**
         * 设置右边的icon ClickListener
         *
         * @param listener
         * @return
         */
        public DefaultNavigationBar.Builder rightIcon1ClickListener(View.OnClickListener listener) {
            setOnclickListener(ID_RIGHT_ICON1, listener);
            return this;
        }

        /**
         * 设置右边的icon2 ClickListener
         *
         * @param listener
         * @return
         */
        public DefaultNavigationBar.Builder rightIco2ClickListener(View.OnClickListener listener) {
            setOnclickListener(ID_RIGHT_ICON2, listener);
            return this;
        }

        /**
         * 设置右边的text ClickListener
         *
         * @param listener
         * @return
         */
        public DefaultNavigationBar.Builder leftTextClickListener(View.OnClickListener listener) {
            setOnclickListener(ID_LEFT_TEXT, listener);
            return this;
        }

        /**
         * 设置左边的icon ClickListener
         *
         * @param listener
         * @return
         */
        public DefaultNavigationBar.Builder leftIconClickListener(View.OnClickListener listener) {
            setOnclickListener(ID_LEFT_ICON, listener);
            return this;
        }

        /**
         * 设置背景色
         *
         * @param bgColor
         * @return
         */
        public DefaultNavigationBar.Builder mainBgColor(int bgColor) {
            setbgColor(ID_CONTAINER, bgColor);
            return this;
        }
    }
}
