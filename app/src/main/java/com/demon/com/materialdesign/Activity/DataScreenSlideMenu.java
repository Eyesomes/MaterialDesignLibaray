package com.demon.com.materialdesign.Activity;

import com.demon.com.materialdesign.R;
import com.demon.com.materialdesign.adapter.SlideMenuAdapter;
import com.exam.cn.baselibrary.base.BaseActivity;
import com.exam.cn.baselibrary.ioc.Onclick;
import com.exam.cn.baselibrary.ioc.ViewById;
import com.exam.cn.baselibrary.util.ToastUtil;

import java.util.concurrent.ExecutorService;

public class DataScreenSlideMenu extends BaseActivity{

    @ViewById(R.id.slidemenu)
    private com.demon.com.materialdesign.widget.slidemenu.DataScreenSlideMenu mSlideMenu;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mSlideMenu.setAdapter(new SlideMenuAdapter(this));
    }

    @Override
    protected void initTitle() {
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.layout_data_screen);
    }

    @Onclick({R.id.text})
    private void show(){
        ToastUtil.showToast(this,"1111");
    }
}
