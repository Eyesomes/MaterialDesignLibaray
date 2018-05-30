package com.exam.cn.framelibrary.skin.attr;

import android.view.View;

/**
 * Created by 杰 on 2017/11/3.
 */

public class SkinAttr {

    // 资源的名称
    private String mResName;

    // 资源类型
    private SkinType mSkinType;

    public SkinAttr(String resName, SkinType skinType) {
        this.mResName = resName;
        this.mSkinType = skinType;
    }

    public void skin(View mView) {
        mSkinType.skin(mView,mResName);
    }
}
