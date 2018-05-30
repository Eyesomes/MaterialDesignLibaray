package com.exam.cn.baselibrary.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

/**
 * Created by admin on 2017/8/8.
 */

public class DaoSupportFactory {

    private static DaoSupportFactory mFactory;

    private SQLiteDatabase mSqLiteDatabase;

    private DaoSupportFactory(){

        //把数据库放在内存卡里面  判断有无内存卡  动态申请权限
        File dbRoot = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                +File.separator+"Construction"+File.separator+"database");

        if (!dbRoot.exists()){
            dbRoot.mkdirs();
        }
        File dbFile = new File(dbRoot,"CM.db");

        mSqLiteDatabase=SQLiteDatabase.openOrCreateDatabase(dbFile,null);
    }

    public static DaoSupportFactory getFactory() {
        if (mFactory == null) {
            synchronized (DaoSupportFactory.class) {
                if (mFactory == null){
                    mFactory = new DaoSupportFactory();
                }
            }
        }
        return mFactory;
    }

    public <T>IDaoSupport getDao(Class<T> clazz){
        IDaoSupport daoSoupport = new DaoSupport();
        daoSoupport.init(mSqLiteDatabase,clazz);
        return daoSoupport;
    }
}
