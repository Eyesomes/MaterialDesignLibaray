package com.exam.cn.baselibrary.fixBug;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/8/1.
 */

public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler {

    private static ExceptionCrashHandler mInstance;

    //用于获取应用的相关信息 比如：版本 收集信息型号
    private Context mContext;

    //系统默认的
    private Thread.UncaughtExceptionHandler mDefautExceptionHandler;

    public static ExceptionCrashHandler getInstance() {

        if (mInstance == null) {
            synchronized (ExceptionCrashHandler.class) {
                mInstance = new ExceptionCrashHandler();
            }
        }

        return mInstance;
    }

    public void init(Context context) {
        this.mContext = context;
        //设置全局的异常类为本类
        Thread.currentThread().setUncaughtExceptionHandler(this);
        mDefautExceptionHandler = Thread.currentThread().getDefaultUncaughtExceptionHandler();
    }


    public File getCrashFile() {
        String crashFileName = mContext.getSharedPreferences("crash", Context.MODE_PRIVATE).getString("CRASH_FILE_NAME", "");
        return new File(crashFileName);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

        //全局异常

        //写入到本地文件

        //崩溃的详细信息

        //应用信息 包名 版本号

        //手机信息

        //保存当前文件，等应用再次启动再上传

        String crashFileName = saveInfoToSD(e);

        Log.i("crashFileName", crashFileName);
        //缓存崩溃日志文件
        cacheCrashFile(crashFileName);

        //让系统默认处理
        mDefautExceptionHandler.uncaughtException(t, e);
    }

    /**
     * 缓存崩溃日志文件名
     *
     * @param crashFileName
     */
    private void cacheCrashFile(String crashFileName) {
        SharedPreferences sp = mContext.getSharedPreferences("crash", Context.MODE_PRIVATE);
        sp.edit().putString("CRASH_FILE_NAME", crashFileName).commit();
    }

    private String saveInfoToSD(Throwable e) {
        String fileName = null;
        StringBuffer sb = new StringBuffer();

        //1.手机信息 应用信息
        for (Map.Entry<String, String> entry : obtainSimpleInfo(mContext).entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append(" = ").append(value).append("\n");
        }

        //2.崩溃的详细信息
        sb.append(obtainExceptionInfo(e));

        //保存文件  6.0以上需要动态申请权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(mContext.getFilesDir() + File.separator + "crash" + File.separator);

            //删除之前的异常信息
            if (dir.exists()) {
                deleteDir(dir);
            }

            if (!dir.exists()) {
                dir.mkdir();
            }

            try {
                fileName = dir.toString() + File.separator + getSysTime("yy_MM_dd_HH_mm") + ".txt";
                FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                fileOutputStream.write(sb.toString().getBytes());
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return fileName;
    }

    /**
     * 获取当前时间
     *
     * @param s
     * @return
     */
    private String getSysTime(String s) {
        SimpleDateFormat formatter = new SimpleDateFormat(s);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);

        return str;
    }

    /**
     * 删除目录所有子文件
     *
     * @param dir
     */
    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (File child : children) {
                child.delete();
            }
        }
        return true;
    }


    /**
     * 获取系统未捕捉的错误信息
     *
     * @param e
     * @return
     */
    private String obtainExceptionInfo(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }

    /**
     * 获取一些简单的信息，软件版本，手机版本，型号存放在hashmap里面
     *
     * @param context
     */
    private HashMap<String, String> obtainSimpleInfo(Context context) {
        HashMap<String, String> map = new HashMap<>();
        PackageManager mPackageManager = context.getPackageManager();
        PackageInfo mPackageInfo = null;
        try {
            mPackageInfo = mPackageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        map.put("versionName", mPackageInfo.versionName);
        map.put("versionCode", "" + mPackageInfo.versionCode);
        map.put("MODEL", Build.MODEL);//版本
        map.put("SDK_INT", Build.VERSION.SDK_INT + "");
        map.put("PRODUCT", Build.PRODUCT);//厂商
        map.put("MOBILE_INFO", getMobileInfo());

        return map;
    }

    /**
     * 手机信息
     *
     * @return
     */
    private String getMobileInfo() {
        StringBuffer sb = new StringBuffer();

        try {
            //用反射获取Build的所有属性
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();//因为是静态的所有传null
                sb.append(name + "=" + value);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
