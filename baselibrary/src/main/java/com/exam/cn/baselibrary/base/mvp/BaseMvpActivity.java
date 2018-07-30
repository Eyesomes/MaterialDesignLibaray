package com.exam.cn.baselibrary.base.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseMvpActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {

    private P mPresentor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());

        mPresentor = creatPresenter();
        mPresentor.attach(this);

        initTitle();
        initView();
        initDate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresentor.detach();
    }

    protected P getPresentor() {
        return mPresentor;
    }

    protected abstract P creatPresenter();

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
