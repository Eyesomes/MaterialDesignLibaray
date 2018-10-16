package com.demon.com.materialdesign.Activity;

import com.exam.cn.baselibrary.base.BaseActivity;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class RetrofitActivity extends BaseActivity {
    @Override
    protected void initData() {
        Retrofit build = new Retrofit.Builder().build();
        Object o = build.create().;
        Observable.just().compose().map()
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void setContentView() {

    }
}
