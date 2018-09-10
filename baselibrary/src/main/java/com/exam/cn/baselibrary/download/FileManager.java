package com.exam.cn.baselibrary.download;

import android.content.Context;

import java.io.File;

class FileManager {

    private static volatile FileManager mManager;

    private File mRootDir;
    private Context mContext;

    public static FileManager getManager() {
        if (mManager == null) {
            synchronized (FileManager.class) {
                if (mManager == null)
                    mManager = new FileManager();
            }
        }
        return mManager;
    }

    public void init(Context context){
        this.mContext = context.getApplicationContext();
    }

    /**
     * 根据下载链接获取本地文件路径
     *
     * @param url
     * @return
     */
    public File getFile(String url) {
        String fileName = Utils.md5Url(url);
        if(mRootDir == null){
            mRootDir = mContext.getCacheDir();
        }
        File file = new File(mRootDir,fileName);
        return file;
    }
}
