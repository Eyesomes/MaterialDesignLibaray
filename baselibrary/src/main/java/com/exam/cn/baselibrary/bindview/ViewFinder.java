package com.exam.cn.baselibrary.bindview;

import android.app.Activity;
import android.view.View;

/**
 * Created by admin on 2017/7/22.
 * description  view的findviewbyid的辅助类
 */

public class ViewFinder {

    private Activity mActivity;
    private View mView;

    public ViewFinder(Activity activity){
        this.mActivity=activity;
    }

    public ViewFinder(View view){
        this.mView=view;
    }

    public View findViewById(int viewId){
        return mActivity!=null?mActivity.findViewById(viewId):mView.findViewById(viewId);
    }
}
