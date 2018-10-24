package com.exam.cn.baselibrary.base.mvp.proxy;

import com.exam.cn.baselibrary.base.mvp.BasePresenter;
import com.exam.cn.baselibrary.base.mvp.IView;
import com.exam.cn.baselibrary.base.mvp.annotation.InjectPresenter;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MvpProxyImpl<V extends IView> implements MvpProxy {

    private V mView;

    private List<BasePresenter> mPresenters;

    public MvpProxyImpl(V view) {
        this.mView = view;
    }

    @Override
    public void bindAndCreatPresenter() {
        mPresenters = new ArrayList<>();

        Field[] fields = mView.getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectPresenter annotation = field.getAnnotation(InjectPresenter.class);
            if (annotation != null) {
                Class<? extends BasePresenter> presenterClazz = null;
                presenterClazz = (Class) field.getType();
                if (!BasePresenter.class.isAssignableFrom(presenterClazz)) {
                    throw new RuntimeException("InjectPresenter no support" + presenterClazz.getName());
                }
                BasePresenter presenter = null;
                try {
                    presenter = presenterClazz.newInstance();
                    presenter.attach(mView);

                    field.setAccessible(true);
                    field.set(mView, presenter);
                    mPresenters.add(presenter);
                } catch (Exception e) {
                    e.printStackTrace();
                    // 可能是 view层的接口和presenter的泛型view全不一致
                    checkView(presenter);
                }
            }
        }
    }

    private void checkView(BasePresenter presenter) {
        Type[] presenterParams = ((ParameterizedType) presenter.getClass().getGenericSuperclass()).getActualTypeArguments();
        Class presentorView = (Class) presenterParams[0];

        Class<?>[] viewInterfaces = mView.getClass().getInterfaces();
        // 判断是否有一致的
        boolean contains = false;
        for (Class<?> anInterface : viewInterfaces) {
            if (presentorView.isAssignableFrom(anInterface)) {
                contains = true;
            }
        }

        if (!contains)
            throw new RuntimeException(presenter.getClass().getName() +
                    " need : " + mView.getClass().getName() + " to implements " +
                    presentorView.getName());
    }

    @Override
    public void unBindPresenter() {
        for (BasePresenter presenter : mPresenters) {
            presenter.detach();
        }
    }
}
