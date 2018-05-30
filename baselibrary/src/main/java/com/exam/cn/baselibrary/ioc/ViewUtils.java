package com.exam.cn.baselibrary.ioc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by admin on 2015/7/22.
 */

public class ViewUtils {

    //activity
    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    //自定义view
    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    //fragment
    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }

    /**
     * 兼容 上面3个方法  object ---> 反射需要执行的类
     *
     * @param finder
     * @param object
     */
    private static void inject(ViewFinder finder, Object object) {
        injectFiled(finder, object);
        injectEvent(finder, object);
    }

    /**
     * 注入属性
     *
     * @param finder
     * @param object
     */
    private static void injectFiled(ViewFinder finder, Object object) {

        //1.获取类里面所有的属性
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();//获取所有属性，包括私有和共有的

        //2.获取viewById的里面的所有的值
        for (Field field : fields) {
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById != null) {
                int viewId = viewById.value();//获取注解里面的Id值
                //3.findViewById 找到view
                View view = finder.findViewById(viewId);

                if (view != null) {
                    field.setAccessible(true);//能够注入所有修饰符 private project
                    //4.动态的注入找到的view
                    try {
                        field.set(object, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    /**
     * 注入事件
     *
     * @param finder
     * @param object
     */
    private static void injectEvent(ViewFinder finder, Object object) {

        //1.获取类里面所有的方法
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        //2.获取inclick里面的的value值
        for (Method method : methods) {
            Onclick onclick = method.getAnnotation(Onclick.class);
            if (onclick != null) {
                int[] values = onclick.value();
                for (int viewId : values) {
                    //3.findViewByid找到view
                    View view = finder.findViewById(viewId);

                    //扩展插入检查网络
                    boolean isCheckNet = method.getAnnotation(CheckNet.class) != null;

                    if (view != null) {
                        //4.view.setOnclickListrner
                        view.setOnClickListener(new DeclaredOnClickListener(method, object,isCheckNet));
                    }
                }
            }
        }
    }

    private static class DeclaredOnClickListener implements View.OnClickListener {

        private Object mObject;
        private Method mMethod;
        private boolean mIsCheckNet;

        public DeclaredOnClickListener(Method method, Object object, boolean isCheckNet) {
            this.mObject = object;
            this.mMethod = method;
            this.mIsCheckNet = isCheckNet;
        }

        //点击调用该方法
        @Override
        public void onClick(View v) {

            //插入检查网络
            if (mIsCheckNet){
                if (!isNetWorkAvailable(v.getContext())){
                    Toast.makeText(v.getContext(),"亲，您的网络不太给力",Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            try {
                mMethod.setAccessible(true);//能够注入所有修饰符 private project
                //5.反射执行方法
                mMethod.invoke(mObject, v);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    mMethod.invoke(mObject);//尝试找没有参数的方法
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 检查网络
     *
     * @param context
     * @return
     */
    private static boolean isNetWorkAvailable(Context context) {

        try {

            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo info = connectivityManager.getActiveNetworkInfo();

            if (info != null && info.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
