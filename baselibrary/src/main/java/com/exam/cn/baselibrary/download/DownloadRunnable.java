package com.exam.cn.baselibrary.download;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Response;

class DownloadRunnable implements Runnable {
    private final int STATUS_DOWNLOADING = 1;
    private final int STATUS_STOP = 2;

    private final DownloadCallback callback;
    private final String url;
    private final int threadId;
    private final long start;
    private final long end;

    private long mProgress;
    private int mStatus = STATUS_DOWNLOADING;
    private DownloadEntity mDownloadEntity;

    public DownloadRunnable(String url, int threadId, long start, long end, long progress, DownloadEntity downloadEntity, DownloadCallback callback) {
        this.url = url;
        this.threadId = threadId;
        this.start = start;
        this.end = end;
        this.callback = callback;
        this.mProgress = progress;
        this.mDownloadEntity = downloadEntity;
    }

    @Override
    public void run() {
        // 只读写指定的部分内容
        RandomAccessFile randomAccessFile = null;
        InputStream inputStream = null;
        File file = FileManager.getManager().getFile(url);
        try {
            Response response = OkHttpManager.getManager().syncResponce(url, start + mProgress, end);
            inputStream = response.body().byteStream();

            randomAccessFile = new RandomAccessFile(file, "rwd");
            randomAccessFile.seek(start + mProgress);

            int len = 0;
            byte[] buffer = new byte[1024 * 10];

            while ((len = inputStream.read(buffer)) != -1) {
                if (mStatus == STATUS_STOP)
                    break;

                randomAccessFile.write(buffer, 0, len);
                mProgress += len;
            }

        } catch (IOException e) {
            callback.onFailure(false, e);
        } finally {
            Utils.close(randomAccessFile);
            Utils.close(inputStream);

            mDownloadEntity.setProgress(mProgress);
            DaoManagerHelper.getManager().addEntity(mDownloadEntity);

            Log.e("download", DownloadRunnable.this.toString());

            callback.onSucceed(file);
        }
    }


    @Override
    public String toString() {
        return "DownloadRunnable{" +
                ", threadId=" + threadId +
                ", start=" + start +
                ", end=" + end +
                ", mProgress=" + mProgress +
                ", mStatus=" + mStatus +
                ", url='" + url + '\'' +
                '}';
    }
}
