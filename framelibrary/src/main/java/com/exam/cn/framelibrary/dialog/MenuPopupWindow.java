package com.exam.cn.framelibrary.dialog;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.exam.cn.framelibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 杰 on 2018/1/22.
 */

public class MenuPopupWindow {
    private List<MenuItem> mMenuItems;
    private Context mContext;


    private MenuPopupWindow(Context context) {
        mMenuItems = new ArrayList<>();
        this.mContext = context;
    }

    public static MenuPopupWindow build(Context context) {
        return new MenuPopupWindow(context);
    }

    public MenuPopupWindow addItem(String name, View.OnClickListener listener) {
        MenuItem menuItem = new MenuItem();
        menuItem.name = name;
        menuItem.listener = listener;
        mMenuItems.add(menuItem);
        return this;
    }

    public PopupWindow applyView(final View view) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundResource(R.drawable.menu_drawable);
        linearLayout.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
        for (MenuItem menuItem : mMenuItems) {
            TextView textView = new TextView(mContext);
            textView.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(100), ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setText(menuItem.name);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpToPx(14));
            textView.setTextColor(Color.BLACK);
            textView.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
            textView.setOnClickListener(menuItem.listener);
            linearLayout.addView(textView);
        }


        final PopupWindow mPopWindow = new PopupWindow(linearLayout,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.showAsDropDown(view);
            }
        });

        return mPopWindow;
    }

    //转换dip为px
    private int dpToPx(int dip) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }
}
