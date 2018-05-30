package com.exam.cn.baselibrary.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by admin on 2017/8/8.
 */

public interface IDaoSupport<T> {

    //初始化
    public void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz);
    //插入数据
    public long insert(T t);
    //批量插入
    public void insert(List<T> datas);

    // 获取专门查询的支持类
    QuerySupport<T> querySupport();


    int delete(String whereClause, String... whereArgs);

    int update(T obj, String whereClause, String... whereArgs);
}