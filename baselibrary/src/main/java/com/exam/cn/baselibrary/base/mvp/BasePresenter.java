package com.exam.cn.baselibrary.base.mvp;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BasePresenter<V extends BaseView> {

    private WeakReference<V> mViewReference;

    private V mProxyView;

    /**
     * 绑定
     * @param view
     */
    public void attach(V view) {
        this.mViewReference = new WeakReference<>(view);

        mProxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(),
                view.getClass().getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (mViewReference.get() == null)
                            return null;
                        return method.invoke(mViewReference.get(), args);
                    }
                });
    }

    /**
     * 解绑
     */
    public void detach() {
        this.mViewReference.clear();
        this.mViewReference = null;
        mProxyView = null;
    }

    public V getView() {
        return mProxyView;
    }
}
