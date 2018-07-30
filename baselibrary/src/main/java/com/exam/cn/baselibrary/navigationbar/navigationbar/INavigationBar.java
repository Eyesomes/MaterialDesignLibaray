package com.exam.cn.baselibrary.navigationbar.navigationbar;

import android.view.View;
import android.view.ViewGroup;

/**
 * 导航栏的规范
 */
public interface INavigationBar {

    void createNavigationBar();

    void attachParent(View view, ViewGroup parent);

    void attachNavigationParams();
}
