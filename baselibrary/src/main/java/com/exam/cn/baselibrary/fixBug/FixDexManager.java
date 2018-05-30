package com.exam.cn.baselibrary.fixBug;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.BaseDexClassLoader;

/**
 * Created by admin on 2017/8/4.
 */

public class FixDexManager {

    private static final String TAG = "FixDexManager";
    private Context mContext;

    private File mDexDir;//系统的 dex路径


    public FixDexManager(Context context) {
        this.mContext = context;
        this.mDexDir = context.getDir("odex", Context.MODE_PRIVATE);
    }


    /**
     * 修复dex包
     *
     * @param fixDexPath
     */
    public void fixDex(String fixDexPath) throws Exception {

        //2. 获取下载好的补丁的 DexElement
        //2.1 移动到体统能够访问的 dex目录下
        File srcFile = new File(fixDexPath);

        if (!srcFile.exists()) {
            throw new FileNotFoundException(fixDexPath);
        }

        File destFile = new File(mDexDir, srcFile.getName());

        if (destFile.exists()) {
            Log.d(TAG, "fixDex: " + fixDexPath + "已经加载");
            return;
        }

        copyFile(srcFile, destFile);

        //2.2 classLoader 读取dex路径    加入集合是为了启动修复可能有多个
        List<File> fixDexFiles = new ArrayList<>();
        fixDexFiles.add(destFile);

        fixDexFiles(fixDexFiles);
    }

    /**
     * 加载全部了修复包
     */
    public void loadFixDex() throws Exception {
        File[] dexFiles = mDexDir.listFiles();

        ArrayList<File> fixDexFiles = new ArrayList<>();

        for (File dexFile : dexFiles) {
            if (dexFile.getName().endsWith(".dex")) {
                fixDexFiles.add(dexFile);
            }
        }

        fixDexFiles(fixDexFiles);
    }

    /**
     * 修复dex
     *
     * @param fixDexFiles
     */
    private void fixDexFiles(List<File> fixDexFiles) throws Exception {
        //1. 先获取已经运行的 DexElement
        ClassLoader applicationClassLoader = mContext.getClassLoader();

        Object applicationDexElements = getDexElementByClassLoader(applicationClassLoader);


        File optinizeDirectory = new File(mDexDir, "odex");
        if (!optinizeDirectory.exists()) {
            optinizeDirectory.mkdirs();
        }

        //修复
        for (File fixDexFile : fixDexFiles) {
            //dexpath dex 路径
            //optimizedDirectory 解压路径
            //librarySearchPath  .so 文件位置
            //parent 父classLoader
            ClassLoader fixDexClassLoader = new BaseDexClassLoader(
                    fixDexFile.getAbsolutePath(),
                    optinizeDirectory,
                    null,
                    applicationClassLoader
            );
            Object fixDexElement = getDexElementByClassLoader(fixDexClassLoader);
            //3. 把补丁的 DexElement 插入到已经运行的 DexElement 的最前面  合并
            //dexElement 和 fixDexElement 合并
            applicationDexElements = combineArray(fixDexElement, applicationDexElements);
        }
        //4. 把最后的数组注入到原来的类中  applicationClassLoader
        injectDexElements(applicationClassLoader, applicationDexElements);
    }

    /**
     * 把 dexElements 注入到 classLoader
     *
     * @param classLoader
     * @param dexElements
     */
    private void injectDexElements(ClassLoader classLoader, Object dexElements) throws Exception {
        //先反射获取 pathlist
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);
        //获取 pathList 里面的 dexElement
        Field dexElementsField = pathList.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);

        dexElementsField.set(pathList, dexElements);
    }

    /**
     * 从 classLoader 中获取 dexElement
     *
     * @param classLoader
     * @return
     */
    private Object getDexElementByClassLoader(ClassLoader classLoader) throws Exception {
        //先反射获取 pathlist
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);
        //获取 pathList 里面的 dexElement
        Field dexElementsField = pathList.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);

        return dexElementsField.get(pathList);
    }

    /**
     * copy file
     *
     * @param src  source file
     * @param dest target file
     * @throws IOException
     */
    public static void copyFile(File src, File dest) throws IOException {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            if (!dest.exists()) {
                dest.createNewFile();
            }
            inChannel = new FileInputStream(src).getChannel();
            outChannel = new FileOutputStream(dest).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }

    /**
     * 合并2个数组
     *
     * @param arrayLhs
     * @param arrayRhs
     * @return
     */
    private static Object combineArray(Object arrayLhs, Object arrayRhs) {
        Class<?> localClass = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);
        int j = i + Array.getLength(arrayRhs);
        Object result = Array.newInstance(localClass, j);
        for (int k = 0; k < j; ++k) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i));
            }
        }

        return result;
    }

}
