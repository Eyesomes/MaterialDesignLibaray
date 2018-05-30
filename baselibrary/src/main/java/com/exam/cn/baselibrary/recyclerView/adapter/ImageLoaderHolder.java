package com.exam.cn.baselibrary.recyclerView.adapter;

/**
 * Created by 杰 on 2017/10/13.
 */

import android.widget.ImageView;

/**
 * ImageLoaderHolder 虚拟类 , 用于解决可能会不同的加载网络图片方式 , 或者有可能会对图片处理
 */
public abstract class ImageLoaderHolder {
    private String mPath;

    public ImageLoaderHolder(String path){
        this.mPath = path;
    }

    public String getPath(){
        return mPath;
    }

    public abstract void loadImage(ImageView imageView , String path);
}