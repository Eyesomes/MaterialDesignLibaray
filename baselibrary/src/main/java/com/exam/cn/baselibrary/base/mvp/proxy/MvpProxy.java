package com.exam.cn.baselibrary.base.mvp.proxy;

/**
 * 抽离公用代码 在子类编写相应的额外功能 , 解耦
 */
public interface MvpProxy {

    void bindAndCreatPresenter();

    void unBindPresenter();
}
