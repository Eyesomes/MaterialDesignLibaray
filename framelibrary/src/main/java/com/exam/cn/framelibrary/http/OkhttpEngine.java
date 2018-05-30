package com.exam.cn.framelibrary.http;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.exam.cn.baselibrary.http.HttpCallback;
import com.exam.cn.baselibrary.http.HttpUtils;
import com.exam.cn.baselibrary.http.IHttpEngine;
import com.exam.cn.baselibrary.http.OnDownloadListener;
import com.exam.cn.framelibrary.http.cache.CacheUtil;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * okhttp 引擎
 * Created by 杰 on 2017/10/16.
 */

public class OkhttpEngine implements IHttpEngine {

    private OkHttpClient mOkHttpClient = new OkHttpClient();

    private static Handler mHandler = new Handler();

    @Override
    public void get(final boolean isCache, Context context, String url, Map<String, Object> params, final HttpCallback callback) {

        final String finalUrl = HttpUtils.jointParams(url, params);
        Log.e("OkhttpEngine -->get 请求", finalUrl);

        //判断是否需要缓存
        if (isCache) {
            String resultJson = CacheUtil.getCacheResultJson(finalUrl);
            if (!TextUtils.isEmpty(resultJson)) {
                Log.i("OkhttpEngine", "读到缓存----->" + resultJson);
                //需要缓存且有缓存
                callback.onSuccess(resultJson);
            }
        }

        Request.Builder requestBuilder = new Request.Builder().url(finalUrl);
        //可以省略，默认是GET请求
        requestBuilder.method("GET", null);
        Request request = requestBuilder.build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resultJson = response.body().string();

                if (isCache) {
                    String oldResultJson = CacheUtil.getCacheResultJson(finalUrl);
                    if (!TextUtils.isEmpty(oldResultJson)) {
                        if (oldResultJson.equals(resultJson)) {
                            Log.i("OkhttpEngine", "缓存和还回数据一致----->" + resultJson);
                            return;
                        }
                    }
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(resultJson);
                    }
                });
                Log.i("OkhttpEngine", " get 还回新的数据----->" + resultJson);

                // 更新数据库里面的数据
                if (isCache) {
                    CacheUtil.cacheData(finalUrl, resultJson);
                }

            }
        });

    }

    @Override
    public void post(boolean isCache, Context context, String type, String url, Map<String, Object> params, final HttpCallback callback) {

        String finalUrl = HttpUtils.jointParams(url, params);
        Log.e("OkhttpEngine -->post 请求", finalUrl);
        RequestBody requestBody = null;

        if (type.equals("json")) {
            requestBody = appendJsonBody(params);
        } else {
            requestBody = appendBody(params);
        }

        Request request = new Request.Builder()
                .url(url)
                .tag(context)
                .post(requestBody)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(result);
                    }
                });
            }
        });
    }

    private RequestBody appendJsonBody(Map<String, Object> params) {
        Gson gson = new Gson();
        String s = gson.toJson(params);
        Log.i("appendJsonBody", s);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
    }

    /**
     * 组装post请求参数body
     */
    protected RequestBody appendBody(Map<String, Object> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        addParams(builder, params);
        return builder.build();
    }

    // 添加参数
    private void addParams(MultipartBody.Builder builder, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
//                builder.addFormDataPart(key, params.get(key) + "");
                Object value = params.get(key);
                if (value instanceof File) {
                    // 处理文件 --> Object File
                    File file = (File) value;
                    builder.addFormDataPart(key, file.getName(), RequestBody
                            .create(MediaType.parse(guessMimeType(file
                                    .getAbsolutePath())), file));
                } else if (value instanceof List) {
                    // 代表提交的是 List集合
                    try {
                        List<File> listFiles = (List<File>) value;
                        for (int i = 0; i < listFiles.size(); i++) {
                            // 获取文件
                            File file = listFiles.get(i);
                            builder.addFormDataPart(key + i, file.getName(), RequestBody
                                    .create(MediaType.parse(guessMimeType(file
                                            .getAbsolutePath())), file));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    builder.addFormDataPart(key, value + "");
                }
            }
        }
    }

    /**
     * 猜测文件类型
     */
    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /**
     * @param url      下载连接
     * @param saveDir  储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    public void downLoad(Context context, final String url, final String saveDir, final OnDownloadListener listener) {
        Log.e("OkhttpEngine -->下载", url);

        Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                // 下载失败
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onDownloadFailed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                String savePath = isExistDir(saveDir);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    final File file = new File(savePath, getNameFromUrl(url));
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        final int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onDownloading(progress);
                            }
                        });
                    }
                    fos.flush();
                    // 下载完成
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onDownloadSuccess(file.getAbsolutePath());
                        }
                    });
                } catch (final Exception e) {
                    e.printStackTrace();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onDownloadFailed(e);
                        }
                    });
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    private String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    @NonNull
    private String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
