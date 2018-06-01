package com.demon.com.materialdesign.widget.slidemenu;

import android.view.View;
import android.view.ViewGroup;

public abstract class SlideMenuAdapter {

    private MenuObserver mObserver;

    public void registerObserver(MenuObserver observer) {
        this.mObserver = observer;
    }

    public void unRegisterObserver() {
        this.mObserver = null;
    }

    public void closeMenu() {
        if (mObserver != null)
            mObserver.closeMenu();
    }

    public abstract int getCount();

    public abstract View getTabView(int position, ViewGroup parent);

    public abstract View getMenuView(int position, ViewGroup parent);

    public void menuOpen(int position, View tabview) {
    }

    public void menuClose(int mCurrentPosition, View childAt) {
    }
}
