package com.exam.cn.baselibrary.http;

import android.content.Context;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求自己的实现
 */

public class HttpUtils {

    private String mUrl;

    private int mType = GET_TYPE;
    private static final int POST_TYPE = 0x0011;
    private static final int POST_JSON_TYPE = 0x0033;
    private static final int GET_TYPE = 0x0022;

    private Map<String, Object> mParams;

    private boolean mIsCache = false; // 是否缓存

    private Context mContext;

    private static IHttpEngine mHttpEngine = null;

    private HttpUtils(Context context) {
        this.mContext = context;
        mParams = new HashMap<>();
    }

    public void init(IHttpEngine httpEngine) {
        this.mHttpEngine = httpEngine;
    }

    public static HttpUtils with(Context context) {
        return new HttpUtils(context);
    }

    // url
    public HttpUtils url(String url) {
        this.mUrl = url;
        return this;
    }

    //是否缓存
    public HttpUtils cache(boolean isCache) {
        this.mIsCache = isCache;
        return this;
    }

    //请求的方式
    public HttpUtils get() {
        mType = GET_TYPE;
        return this;
    }

    public HttpUtils post() {
        mType = POST_TYPE;
        return this;
    }

    public HttpUtils postJson() {
        mType = POST_JSON_TYPE;
        return this;
    }

    //请求的参数
    public HttpUtils addParam(String key, Object value) {
        mParams.put(key, value);
        return this;
    }

    public HttpUtils addParam(Map<String, Object> params) {
        mParams.putAll(params);
        return this;
    }

    // 执行
    public void execute() {
        execute(null);
    }

    public void execute(HttpCallback callback) {
        if (callback == null) {
            callback = HttpCallback.DEFAULT_CALLBACK;
        }

        callback.onPreExecute(mContext, mUrl, mParams);

        if (mType == GET_TYPE) {
            get(mIsCache, mUrl, mParams, callback);
        }
        if (mType == POST_TYPE) {
            post(mIsCache, "", mUrl, mParams, callback);
        }
        if (mType == POST_JSON_TYPE) {
            post(mIsCache, "json", mUrl, mParams, callback);
        }
    }

    // 执行
    public void execute(String saveDir , OnDownloadListener listener) {
        downLoad(mUrl,saveDir,listener);
    }

    private void get(boolean isCache, String url, Map<String, Object> params, HttpCallback callback) {
        mHttpEngine.get(isCache, mContext, url, params, callback);
    }

    private void post(boolean isCache, String type, String url, Map<String, Object> params, HttpCallback callback) {
        mHttpEngine.post(isCache, mContext, type, url, params, callback);
    }

    public void downLoad(String url, String saveDir, OnDownloadListener listener) {
        mHttpEngine.downLoad(mContext, url, saveDir, listener);
    }

    /**
     * 拼接 url 和 参数
     */
    public static String jointParams(String url, Map<String, Object> params) {
        if (params == null || params.size() <= 0) {
            return url;
        }

        StringBuffer stringBuffer = new StringBuffer(url);
        if (!url.contains("?")) {
            stringBuffer.append("?");
        } else {
            if (!url.endsWith("?")) {
                stringBuffer.append("&");
            }
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
        }

        stringBuffer.deleteCharAt(stringBuffer.length() - 1);

        return stringBuffer.toString();
    }

    /**
     * 解析一个类上面的class信息
     */
    public static Type analysisClazzInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return params[0];
    }
}
