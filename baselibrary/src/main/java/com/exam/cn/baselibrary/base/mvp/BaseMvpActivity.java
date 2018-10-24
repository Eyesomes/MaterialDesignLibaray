package com.exam.cn.baselibrary.base.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.exam.cn.baselibrary.base.mvp.proxy.ActivityMvpProxy;
import com.exam.cn.baselibrary.base.mvp.proxy.ActivityMvpProxyImpl;

public abstract class BaseMvpActivity extends AppCompatActivity implements IView {

    private ActivityMvpProxy mMvpProxy;

    protected Activity context;

    protected ViewHolder viewHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        context = this;
        viewHolder = new ViewHolder(this);
        if (mMvpProxy == null)
            mMvpProxy = creatPresenterProxy();
        injectPresenter();

        initTitle();
        initView();
        initDate();
    }

    protected ActivityMvpProxy creatPresenterProxy() {
        return new ActivityMvpProxyImpl(this);
    }

    private void injectPresenter() {
        mMvpProxy.bindAndCreatPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMvpProxy.unBindPresenter();
    }

    protected abstract int layoutId();

    protected abstract void initTitle();

    protected abstract void initView();

    protected abstract void initDate();

    /**
     * 启动Activity
     */
    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
