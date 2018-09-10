package com.exam.cn.baselibrary.download;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class OkHttpManager {

    private static volatile OkHttpManager mManager;

    private OkHttpClient mOkHttpClient;

    private OkHttpManager() {
        mOkHttpClient = new OkHttpClient();
    }

    public static OkHttpManager getManager() {
        if (mManager == null) {
            synchronized (OkHttpManager.class) {
                if (mManager == null)
                    mManager = new OkHttpManager();
            }
        }
        return mManager;
    }

    public Call asyncCall(String url) {
        Request request = new Request.Builder().url(url).build();
        return mOkHttpClient.newCall(request);
    }

    public Response syncResponce(String url, long start, long end) throws IOException {
        Request request = new Request
                .Builder()
                .addHeader("Range", "bytes=" + start + "-" + end)
                .url(url)
                .build();
        return mOkHttpClient.newCall(request).execute();
    }

}
