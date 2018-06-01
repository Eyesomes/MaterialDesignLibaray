package com.demon.com.materialdesign.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SlideMenuAdapter extends com.demon.com.materialdesign.widget.slidemenu.SlideMenuAdapter {

    private String[] tab = {"tab1", "tab2", "tab3"};

    private Context mContext;

    public SlideMenuAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return tab.length;
    }

    @Override
    public View getTabView(int position, ViewGroup parent) {
        TextView textView = new TextView(mContext);
        textView.setText(tab[position]);
        textView.setPadding(20, 20, 20, 20);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        return textView;
    }

    @Override
    public View getMenuView(int position, ViewGroup parent) {
        TextView textView = new TextView(mContext);
        textView.setText(tab[position]);
        textView.setPadding(20, 20, 20, 20);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.WHITE);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return textView;
    }
}
