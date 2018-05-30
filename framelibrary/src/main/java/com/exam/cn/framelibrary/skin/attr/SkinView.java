package com.exam.cn.framelibrary.skin.attr;

import android.view.View;

import java.util.List;

/**
 * Created by 杰 on 2017/11/3.
 */

public class SkinView {

    private View mView;

    private List<SkinAttr> mSkinAttrs;

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        this.mView = view;
        this.mSkinAttrs = skinAttrs;
    }

    public void skin(){
        for (SkinAttr attr : mSkinAttrs) {
            attr.skin(mView);
        }
    }
}
