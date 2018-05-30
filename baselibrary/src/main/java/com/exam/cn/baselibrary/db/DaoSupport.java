package com.exam.cn.baselibrary.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.ArrayMap;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/8/8.
 */

public class DaoSupport<T> implements IDaoSupport<T> {

    private SQLiteDatabase mSqLiteDatabase;

    private Class<T> mClazz;

    private QuerySupport<T> mQuerySupport;

    private static final Object[] mPutMethodArgs = new Object[2];
    private static final Map<String, Method> mPutMethods = new ArrayMap<>();

    // 根据 model 去建对应的表
    @Override
    public void init(SQLiteDatabase sqLiteDatabase, Class clazz) {
        this.mSqLiteDatabase = sqLiteDatabase;
        this.mClazz = clazz;

        //创建表
        StringBuffer sb = new StringBuffer();
        sb.append("create table if not exists ")
                .append(DaoUtil.getTableName(mClazz))
                .append("(id integer primary key autoincrement,");

        Field[] fields = mClazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            String type = field.getType().getSimpleName();//int String boolean
            //type需要转换
            sb.append(name).append(DaoUtil.getColumnType(type)).append(", ");
        }
        sb.replace(sb.length() - 2, sb.length(), ")");

        Log.i("DaoSupport", "init---> " + sb.toString());
        mSqLiteDatabase.execSQL(sb.toString());
    }


    /**
     * 插入
     */
    @Override
    public long insert(T t) {

        ContentValues values = contentValuesByObj(t);
        return mSqLiteDatabase.insert(DaoUtil.getTableName(mClazz), null, values);
    }

    /**
     * 批量插入 事务
     *
     * @param datas
     * @return
     */
    @Override
    public void insert(List<T> datas) {
        mSqLiteDatabase.beginTransaction();

        for (T data : datas) {
            insert(data);
        }

        mSqLiteDatabase.setTransactionSuccessful();
        mSqLiteDatabase.endTransaction();
    }

    /**
     * 获取一个查询的支持
     * @return
     */
    @Override
    public QuerySupport<T> querySupport() {
        if (mQuerySupport == null){
            mQuerySupport = new QuerySupport<>(mSqLiteDatabase,mClazz);
        }
        return mQuerySupport;
    }

    /**
     * 删除
     */
    @Override
    public int delete(String whereClause, String[] whereArgs) {
        return mSqLiteDatabase.delete(DaoUtil.getTableName(mClazz), whereClause, whereArgs);
    }

    /**
     * 更新  这些你需要对  最原始的写法比较明了 extends
     */
    @Override
    public int update(T obj, String whereClause, String... whereArgs) {
        ContentValues values = contentValuesByObj(obj);
        return mSqLiteDatabase.update(DaoUtil.getTableName(mClazz),
                values, whereClause, whereArgs);
    }


    /**
     * obj转value
     * 仿照源码 view 的创建
     * @param t
     * @return
     */
    private ContentValues contentValuesByObj(T t) {
        ContentValues values = new ContentValues();

        Field[] fields = mClazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String key = field.getName();
                Object value = field.get(t);

                mPutMethodArgs[0] = key;
                mPutMethodArgs[1] = value;

                String filedTypeName = field.getType().getName();
                Method putMethod = mPutMethods.get(filedTypeName);
                if (putMethod == null) {
                    putMethod = ContentValues.class.getDeclaredMethod("put", String.class, value.getClass());
                    mPutMethods.put(filedTypeName, putMethod);
                }

                putMethod.invoke(values, mPutMethodArgs);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mPutMethodArgs[0] = null;
                mPutMethodArgs[1] = null;
            }
        }
        return values;
    }
}
