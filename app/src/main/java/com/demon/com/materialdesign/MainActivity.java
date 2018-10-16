package com.demon.com.materialdesign;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import com.exam.cn.baselibrary.download.DownloadCallback;
import com.exam.cn.baselibrary.download.DownloadHelper;

import java.io.File;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadHelper.getInstance().init(this);

       Observable.just("1")
               .subscribeOn(Schedulers.io())
               .subscribe(new Consumer<String>() {
                   @Override
                   public void accept(String s) throws Exception {

                   }
               });

        DownloadHelper.getInstance()
                .download("http://acj3.pc6.com/pc6_soure/2018-5/com.luojilab.player_20180523.apk", new DownloadCallback() {

                    @Override
                    public void onSucceed(File file) {
                        installFile(file);
//                        File file1 = new File(Environment.getExternalStorageDirectory() + File.separator + "1.apk");
//                        file.renameTo(file1);
                    }

                    @Override
                    public void onFailure(boolean stopped, IOException e) {

                    }
                });

    }


    private void installFile(File file) {
        // 核心是下面几句代码
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);
    }
}
