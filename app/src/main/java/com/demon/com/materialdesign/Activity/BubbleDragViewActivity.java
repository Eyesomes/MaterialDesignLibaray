package com.demon.com.materialdesign.Activity;

import android.graphics.PointF;
import android.widget.TextView;

import com.demon.com.materialdesign.R;
import com.demon.com.materialdesign.widget.DragBubbleView.BubbleDragView;
import com.exam.cn.baselibrary.base.BaseActivity;
import com.exam.cn.baselibrary.ioc.ViewById;

public class BubbleDragViewActivity extends BaseActivity {

    @ViewById(R.id.text)
    private TextView textView;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        BubbleDragView.attach(textView, new BubbleDragView.OnDisappearListener() {
            @Override
            public void onDismiss(PointF pointF) {

            }

            @Override
            public void onRestore() {

            }
        });
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.demon_bubble_drag);
    }
}
