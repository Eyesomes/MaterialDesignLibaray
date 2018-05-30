package com.exam.cn.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * 网络引擎的规范
 */
public interface IHttpEngine {

    // get
    void get(boolean isCache, Context context, String url, Map<String, Object> params, HttpCallback callback);

    // post
    void post(boolean isCache, Context context, String type, String url, Map<String, Object> params, HttpCallback callback);

    //下载文件
    void downLoad(Context context, String url,String saveDir, OnDownloadListener callback);
    //上传文件

    // https 添加证书
}
