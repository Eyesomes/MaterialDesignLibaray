package com.exam.cn.baselibrary.base.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exam.cn.baselibrary.base.mvp.proxy.FragmentMvpProxy;
import com.exam.cn.baselibrary.base.mvp.proxy.FragmentMvpProxyImpl;

public abstract class BaseMvpFragment extends Fragment implements IView {

    protected View rootView;

    protected Activity context;

    protected ViewHolder viewHolder;

    private FragmentMvpProxy mMvpProxy;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.context = getActivity();
        rootView = View.inflate(context, layoutId(), null);

//         加入注解
//        ViewUtils.inject(rootView, this);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mMvpProxy == null)
            mMvpProxy = creatPresenterProxy();
        injectPresenter();

        viewHolder = new ViewHolder(rootView);

        initView();
        initData();
    }

    protected FragmentMvpProxy creatPresenterProxy() {
        return new FragmentMvpProxyImpl(this);
    }

    private void injectPresenter() {
        mMvpProxy.bindAndCreatPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMvpProxy.unBindPresenter();
    }

    protected abstract int layoutId();

    protected abstract void initView();

    protected abstract void initData();

    /**
     * 启动Activity
     */
    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        startActivity(intent);
    }
}
