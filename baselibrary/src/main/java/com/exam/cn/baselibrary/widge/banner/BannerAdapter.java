package com.exam.cn.baselibrary.widge.banner;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 杰 on 2017/11/13.
 */

public abstract class BannerAdapter {

    // itemview
    public abstract View getItemView(ViewGroup container, int position , View convertview);

    //条数
    public abstract int getCount() ;

    // 文字描述
    public String getDescription(int position){
        return null;
    };

    public void destroyItem(ViewGroup container, int position, Object object){

    };
}
