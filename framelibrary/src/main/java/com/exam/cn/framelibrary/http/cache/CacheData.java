package com.exam.cn.framelibrary.http.cache;

/**
 * 缓存的实体类
 * Created by 杰 on 2017/10/27.
 */

public class CacheData {
    // 请求链接
    private String mUrlKey;
    // 还回的结果Json
    private String mResultJson;

    public CacheData() {

    }

    public CacheData(String mUrlKey, String mResultJson) {
        this.mUrlKey = mUrlKey;
        this.mResultJson = mResultJson;
    }

    public String getmUrlKey() {
        return mUrlKey;
    }

    public void setmUrlKey(String mUrlKey) {
        this.mUrlKey = mUrlKey;
    }

    public String getmResultJson() {
        return mResultJson;
    }

    public void setmResultJson(String mResultJson) {
        this.mResultJson = mResultJson;
    }
}
