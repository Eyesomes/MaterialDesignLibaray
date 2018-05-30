package com.exam.cn.baselibrary.util;

import android.app.Activity;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 杰 on 2017/12/20.
 */

public class ActivityUtil {
    //运用list来保存们每一个activity是关键
    private List<Activity> mList = new LinkedList<Activity>();
    //为了实现每次使用该类时不创建新的对象而创建的静态对象
    private static ActivityUtil instance;

    //构造方法
    private ActivityUtil() {
    }

    //实例化一次
    public synchronized static ActivityUtil getInstance() {
        if (null == instance) {
            instance = new ActivityUtil();
        }
        return instance;
    }

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void removeActivity(Activity activity) {

        if (mList.contains(activity)) {
            mList.remove(activity);
        }
    }

    public List<Activity> getActivity() {
        return mList;
    }

    public void exit() {
        Iterator<Activity> iterator = mList.iterator();
        while (iterator.hasNext()){
            Activity activity = iterator.next();
            activity.finish();
            iterator.remove();
        }
    }
}