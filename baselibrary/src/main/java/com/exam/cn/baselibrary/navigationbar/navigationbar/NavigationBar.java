package com.exam.cn.baselibrary.navigationbar.navigationbar;

import android.content.Context;
import android.view.ViewGroup;

/**
 * 直接使用的基础构建类
 */
public class NavigationBar extends AbsNavigationBar {
    protected NavigationBar(Builder builder) {
        super(builder);
    }

    public static class Builder extends AbsNavigationBar.Builder<NavigationBar.Builder> {

        public Builder(Context context, int layotId, ViewGroup parent) {
            super(context, layotId, parent);
        }

        @Override
        public NavigationBar create() {
            return new NavigationBar(this);
        }
    }
}
