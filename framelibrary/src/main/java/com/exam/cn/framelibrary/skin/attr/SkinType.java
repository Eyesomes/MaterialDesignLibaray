package com.exam.cn.framelibrary.skin.attr;

import android.view.View;

/**
 * Created by 杰 on 2017/11/3.
 */

public enum SkinType {
    TEXT_COLOR("textColor") {
        @Override
        public void skin(View view, String resName) {

        }
    }, BACKGROUND("background") {
        @Override
        public void skin(View view, String resName) {

        }
    }, SRC("src") {
        @Override
        public void skin(View view, String resName) {

        }
    };


    private String mName;

    SkinType(String resName) {
        this.mName = resName;
    }

    public abstract void skin(View view, String resName);

    public String getName() {
        return mName;
    }
}
