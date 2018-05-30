package com.exam.cn.baselibrary.permission;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;

import java.util.List;

/**
 * 权限申请类
 * Created by 杰 on 2017/11/12.
 */

public class PermissionHelper {

    // activity  fragment
    private Object mContext;

    private String[] mPermissions;

    private int mRequestCode;

    private PermissionHelper(Object o){
        this.mContext = o;
    }

    public static PermissionHelper with(Activity activity){
        return new PermissionHelper(activity);
    }

    public static PermissionHelper with(Fragment fragment){
        return new PermissionHelper(fragment);
    }

    /**
     * 添加需要验证的权限
     * @param permission
     * @return
     */
    public PermissionHelper addPermission(String... permission){
        this.mPermissions = permission;
        return this;
    }

    /**
     * 设置requestCode
     * @param requestCode
     * @return
     */
    public PermissionHelper setRequestCode(int requestCode){
        this.mRequestCode = requestCode;
        return this;
    }
//---------------------------------------------------------------------------------------------------------------
    /**
     * 开始执行
     */
    public void request(){
        //判断是否是6.0以上
        if (!PermissionUtils.isOverMarshmallow()) {
            // 1.     6.0以下直接反射执行方法  用反射+注解的方式
            PermissionUtils.executeMethod(mContext , mRequestCode);
            return;
        }
        //  2.   6.0以上需要去 request 判断是否有权限
        List<String> deniedPermission = PermissionUtils.getDeniedPermission(mContext,mPermissions);

        //  2.1    如果已经授予了权限 直接反射执行方法
        if (deniedPermission.size() == 0){
            PermissionUtils.executeMethod(mContext , mRequestCode);
            return;
        }
        //  2.2   如果没授予了权限 ， 需要去申请权限
        ActivityCompat.requestPermissions(PermissionUtils.getActivity(mContext),
                deniedPermission.toArray(new String[deniedPermission.size()]),
                mRequestCode);
    }

//---------------------------------------------------------------------------------------------
    /**
     * 处理申请权限返回结果
     */
    public static void requestPermissionsResult(Object o,int requestCode, String[] permissions, int[] grantResults) {
        //  再次判断权限是否被授予
        List<String> deniedPermission = PermissionUtils.getDeniedPermission(o,permissions);

        //  如果已经授予了权限 直接反射执行方法
        if (deniedPermission.size() == 0){
            PermissionUtils.executeMethod(o , requestCode);
            return;
        }
        // 用户拒绝了权限
        PermissionUtils.executeFailMethod(o , requestCode);
    }

}
