package com.exam.cn.baselibrary.base.mvp;

import com.exam.cn.baselibrary.base.mvp.annotation.InjectModel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class BasePresenter<V extends IView> {

    private V mView;

    private V mProxyView;

    private List<IModel> mModels;

    /**
     * 绑定
     *
     * @param view
     */
    public void attach(V view) {
        this.mView = view;

        mProxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(),
                view.getClass().getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (mView == null)
                            return null;
                        return method.invoke(mView, args);
                    }
                });

        createModels();
    }

    private void createModels() {
        mModels = new ArrayList<>();

        Field[] fields = mView.getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectModel annotation = field.getAnnotation(InjectModel.class);
            if (annotation != null) {
                Class<? extends IModel> modelClazz = null;
                modelClazz = (Class) field.getType();
                if (!IModel.class.isAssignableFrom(modelClazz)) {
                    throw new RuntimeException("InjectModel no support" + modelClazz.getName());
                }
                try {
                    IModel model = modelClazz.newInstance();

                    field.setAccessible(true);
                    field.set(mView, model);
                    mModels.add(model);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 解绑
     */
    public void detach() {
        mView = null;
//        mProxyView = null;
    }

    public V getView() {
        return mProxyView;
    }
}
