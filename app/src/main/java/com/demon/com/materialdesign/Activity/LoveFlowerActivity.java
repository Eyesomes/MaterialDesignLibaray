package com.demon.com.materialdesign.Activity;

import com.demon.com.materialdesign.R;
import com.demon.com.materialdesign.widget.loveflower.LoveFlowerLayout;
import com.exam.cn.baselibrary.base.BaseActivity;
import com.exam.cn.baselibrary.bindview.Onclick;
import com.exam.cn.baselibrary.bindview.ViewById;

public class LoveFlowerActivity extends BaseActivity{

    @ViewById(R.id.lovelayout)
    LoveFlowerLayout mFlowerLayout;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_love_flower);
    }

    @Onclick({R.id.add})
    private void add(){
        mFlowerLayout.addLoveFlower();
    }
}
