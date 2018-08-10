package com.exam.cn.baselibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.exam.cn.baselibrary.ioc.ViewUtils;
import com.exam.cn.baselibrary.util.ActivityManageUtil;

/**
 * Created by 杰 on 2015/11/3.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManageUtil.getInstance().addActivity(this);
        // 设置布局layout
        setContentView();
        //Log.e("TAG", viewRoot + "");

        // 一些特定的算法，子类基本都会使用的
        ViewUtils.inject(this);

        savedInstanceState(savedInstanceState);

        // 初始化头部
        initTitle();

        // 初始化界面
        initView();

        // 初始化数据
        initData();
    }

    // 初始化数据
    protected void savedInstanceState(Bundle savedInstanceState) {
    }

    // 初始化数据
    protected abstract void initData();

    // 初始化界面
    protected abstract void initView();

    // 初始化头部
    protected abstract void initTitle();

    // 设置布局layout
    protected abstract void setContentView();


    /**
     * 启动Activity
     */
    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
    // 只能放一些通用的方法，基本每个Activity都需要使用的方法，readDataBase最好不要放进来 ，
    // 如果是两个或两个以上的地方要使用,最好写一个工具类。
    // 永远预留一层

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManageUtil.getInstance().removeActivity(this);
    }

    public <T extends View> T viewById(int viewId) {
        return (T) findViewById(viewId);
    }
}
