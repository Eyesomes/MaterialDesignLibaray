package com.exam.cn.baselibrary.base.mvp.proxy;

import com.exam.cn.baselibrary.base.mvp.IView;

/**
 * 额外的方法 的实现
 */
public class FragmentMvpProxyImpl extends MvpProxyImpl implements FragmentMvpProxy {
    public FragmentMvpProxyImpl(IView view) {
        super(view);
    }
}
