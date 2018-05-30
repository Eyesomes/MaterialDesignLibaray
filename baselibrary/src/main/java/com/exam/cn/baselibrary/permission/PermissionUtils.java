package com.exam.cn.baselibrary.permission;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 静态工具类
 * Created by 杰 on 2017/11/12.
 */

public class PermissionUtils {

    private PermissionUtils(){}

    /**
     * 判断是否是6.0以上
     * @return
     */
    public static boolean isOverMarshmallow(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 所有权限被授予后反射执行的方法
     * @param object
     * @param requestCode
     */
    public static void executeMethod(Object object, int requestCode) {
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            PermissionSuccess permissionDone = method.getAnnotation(PermissionSuccess.class);
            if (permissionDone != null) {
                int methodCode = permissionDone.requestCode();
                if (methodCode == requestCode){
                    // 执行
                    try {
                        method.setAccessible(true);
                        method.invoke(object);
                    } catch (Exception e) {
                        Log.e("permissiondone", "反射执行方法失败" );
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    /**
     * 不是所有权限被授予执行的方法  用户拒接了
     * @param object
     * @param requestCode
     */
    public static void executeFailMethod(Object object, int requestCode) {
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            PermissionFail permissionDone = method.getAnnotation(PermissionFail.class);
            if (permissionDone != null) {
                int methodCode = permissionDone.requestCode();
                if (methodCode == requestCode){
                    // 执行
                    try {
                        method.setAccessible(true);
                        method.invoke(object);
                    } catch (Exception e) {
                        Log.e("permissiondone", "反射执行方法失败" );
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     *  检测哪些权限是没有的
     * @param object
     * @param permissions
     * @return
     */
    public static List<String> getDeniedPermission(Object object, String[] permissions) {
        List<String> deniedPermissions = new ArrayList<>();

        for (String permission : permissions) {
            int selfPermission = ContextCompat.checkSelfPermission(getActivity(object), permission);
            if (selfPermission != PackageManager.PERMISSION_GRANTED) {
                Log.e("checkPermission", "PERMISSION_DENIED" + selfPermission);
                deniedPermissions.add(permission);
            }
        }

        return deniedPermissions;
    }



    public static Activity getActivity(Object o) {
        if (o instanceof Activity){
            return (Activity) o;
        }
        if (o instanceof Fragment){
            return ((Fragment)o).getActivity();
        }

        return null;
    }

}
