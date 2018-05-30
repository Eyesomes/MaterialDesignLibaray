package com.exam.cn.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * 回调
 * Created by 杰 on 2017/10/16.
 */

public interface HttpCallback {

    public void onPreExecute(Context context, String url, Map<String, Object> params);

    public void onError(Exception e);

    public void onSuccess(String result);

    //默认的
    public final HttpCallback DEFAULT_CALLBACK = new HttpCallback() {
        @Override
        public void onPreExecute(Context context, String url, Map<String, Object> params) {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {
        }
    };
}
