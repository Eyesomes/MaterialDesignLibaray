package com.exam.cn.baselibrary.download;

import java.io.File;
import java.io.IOException;

public interface DownloadCallback {
    void onSucceed(File file);

    void onFailure(boolean stopped, IOException e);
}
