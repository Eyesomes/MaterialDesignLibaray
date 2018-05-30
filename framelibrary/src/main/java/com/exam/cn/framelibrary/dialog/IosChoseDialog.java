package com.exam.cn.framelibrary.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exam.cn.baselibrary.dialog.AlertDialog;
import com.exam.cn.framelibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 杰 on 2018/3/7.
 */

public class IosChoseDialog {
    private List<MenuItem> mMenuItems;
    private Context mContext;

    private AlertDialog alertDialog;

    private IosChoseDialog(Context context) {
        mMenuItems = new ArrayList<>();
        this.mContext = context;
    }

    public static IosChoseDialog build(Context context) {
        return new IosChoseDialog(context);
    }

    public IosChoseDialog addItem(String name, View.OnClickListener listener) {
        MenuItem menuItem = new MenuItem();
        menuItem.name = name;
        menuItem.listener = listener;
        mMenuItems.add(menuItem);
        return this;
    }

    public AlertDialog applyView() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.dialog_ios_chose, null);

        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.dialog_ios_chose_container);
        for (int i = 0; i < mMenuItems.size(); i++) {
            final MenuItem menuItem = mMenuItems.get(i);

            if (i != 0) {
                View view = new View(mContext);
                view.setBackgroundColor(Color.BLACK);
                view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                linearLayout.addView(view);
            }

            TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_dialog_ios_chose, null);
            textView.setText(menuItem.name);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    menuItem.listener.onClick(v);
                }
            });
            linearLayout.addView(textView);
        }
        alertDialog = new AlertDialog.Builder(mContext).
                setContentView(inflate).
                setOnclickListener(R.id.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                }).
                fullWidth().
                setGravity(Gravity.BOTTOM).
                setCancelable(true).
                create();
        return alertDialog;
    }

    //转换dip为px
    private int dpToPx(int dip) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }
}
