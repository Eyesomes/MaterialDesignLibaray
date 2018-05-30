package com.exam.cn.framelibrary.skin.activitry;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;

import com.exam.cn.baselibrary.base.BaseActivity;
import com.exam.cn.framelibrary.skin.SkinAttrSupport;
import com.exam.cn.framelibrary.skin.attr.SkinAttr;
import com.exam.cn.framelibrary.skin.attr.SkinView;
import com.exam.cn.framelibrary.skin.support.SkinViewInflater;

import java.util.List;

/**
 * Created by 杰 on 2017/11/3.
 */

public abstract class BaseSkinActivity extends BaseActivity implements LayoutInflaterFactory {

    private SkinViewInflater mSkinViewInflater;
    private static final boolean IS_PRE_LOLLIPOP = Build.VERSION.SDK_INT < 21;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        // 使用自己的 factory
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory(layoutInflater, this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        // 1 . 创建 View
        //        View view = getDelegate().createView(parent,name,context,attrs); 这种不能拦截layout
        View view = createView(parent, name, context, attrs);

        Log.i("BaseSkinActivity", view + "");
        if (view == null) {
            return view;
        }
        // 2. 解析属性
        // 2.1 一个activity对应多个SkinView
        List<SkinAttr> skinAttrs = SkinAttrSupport.getSkinAttrs(context, attrs);
        SkinView skinView = new SkinView(view, skinAttrs);
        // 3 . 交给manager去管理
        managerSkinView(skinView);

        return view;
    }

    /**
     * 统一管理 SkinView
     * @param skinView
     */
    private void managerSkinView(SkinView skinView) {
    }


    /**
     * 下面都是 cope 的 5.0 源码 为了兼容
     */
    public View createView(View parent, final String name, @NonNull Context context,
                           @NonNull AttributeSet attrs) {
        final boolean isPre21 = Build.VERSION.SDK_INT < 21;

        if (mSkinViewInflater == null) {
            mSkinViewInflater = new SkinViewInflater();
        }

        // We only want the View to inherit it's context if we're running pre-v21
        final boolean inheritContext = isPre21 && true
                && shouldInheritContext((ViewParent) parent);

        return mSkinViewInflater.createView(parent, name, context, attrs, inheritContext,
                isPre21, /* Only read android:theme pre-L (L+ handles this anyway) */
                true /* Read read app:theme as a fallback at all times for legacy reasons */
        );
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true;
            } else if (parent == getWindow().getDecorView() || !(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false;
            }
            parent = parent.getParent();
        }
    }
}
