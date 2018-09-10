package com.exam.cn.baselibrary.download;

import android.content.Context;

public class DownloadHelper {

    private static volatile DownloadHelper mDownloadHelper;

    private Context mContext;

    public static DownloadHelper getInstance() {
        if (mDownloadHelper == null) {
            synchronized (DownloadHelper.class) {
                if (mDownloadHelper == null)
                    mDownloadHelper = new DownloadHelper();
            }
        }
        return mDownloadHelper;
    }

    public void init(Context context) {
        this.mContext = context.getApplicationContext();

        FileManager.getManager().init(mContext);
        DaoManagerHelper.getManager().init(mContext);
    }

    public void download(String url, DownloadCallback callback) {
        DownloadDispatcher.getDispatcher().startDownLoad(url, callback);
    }
}
