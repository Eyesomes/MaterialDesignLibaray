package com.exam.cn.framelibrary.selectPic;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.exam.cn.baselibrary.base.BaseActivity;
import com.exam.cn.baselibrary.recyclerView.wrap.WrapRecyclerView;
import com.exam.cn.baselibrary.util.StatusBarUtil;
import com.exam.cn.framelibrary.R;
import com.exam.cn.baselibrary.navigationbar.DefaultNavigationBar;
import com.exam.cn.framelibrary.selectPic.adapter.ImageListAdapter;
import com.exam.cn.framelibrary.selectPic.listener.SelectListener;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by 杰 on 2017/11/23.
 */

public class SelectPictureActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor>, SelectListener, View.OnClickListener {


    //参数 key

    public static final String EXTRA_SHOW_CAMERA = "EXTRA_SHOW_CAMERA";
    public static final String EXTRA_SELECT_COUNT = "EXTRA_SELECT_COUNT";
    public static final String EXTRA_SELECT_MODE = "EXTRA_SELECT_MODE";
    public static final String EXTRA_DEFAULT_SELECT_LIST = "EXTRA_DEFAULT_SELECT_LIST";
    //还回图片列表的结果key
    public static final String EXTRA_RESULT = "EXTRA_RESULT";

//------------------------------------------------------------------------------------------------------------------------------------------------------------


    //参数
    public static final int MODE_MULTI = 0x0011;
    public static final int MODE_SINGLE = 0x0012;
    public static final int EXTRA_CAMERA_RESULT = 0x0013;
    //单选还是多选
    private int mMode = MODE_MULTI;
    //最大选择图片的张数
    private int mMaxCount = 10;
    //是否显示拍照按钮
    private boolean mIsShowCamera = true;
    //选择过的图片
    private ArrayList<String> mResultList;

//------------------------------------------------------------------------------------------------------------------------------------------------------------


    private final String[] IMAGE_PROJECTION = {
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media._ID
    };

//------------------------------------------------------------------------------------------------------------------------------------------------------------

    private WrapRecyclerView mImageList;

    private TextView mPicSelectNum;

    public String mTempImagePath;
    public String mTempImageName;

    @Override
    protected void initData() {
        // 获取上一个页面传过来的参数
        Intent intent = getIntent();
        mMode = intent.getIntExtra(EXTRA_SELECT_MODE, mMode);
        mMaxCount = intent.getIntExtra(EXTRA_SELECT_COUNT, mMaxCount);
        mIsShowCamera = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, mIsShowCamera);
        mMode = intent.getIntExtra(EXTRA_SELECT_MODE, mMode);
        mResultList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECT_LIST);
        if (mResultList == null) {
            mResultList = new ArrayList<>();
        }

        initImageList();

        mPicSelectNum.setText("0/" + mMaxCount);
    }


    @Override
    protected void initView() {
        mImageList = viewById(R.id.select_pic_list_recyclerview);
        mImageList.setLayoutManager(new GridLayoutManager(this, 4));

        mPicSelectNum = viewById(R.id.select_pic_num);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this).setBgColor(Color.BLACK).
                setRightText("确定").
                setRightClickListener(this).
                setTitle("所有图片").
                builder().applyView();

        StatusBarUtil.statusBarTintColor(this, Color.BLACK);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_select_picture);
    }

//------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 初始化本地图片数据
     */
    private void initImageList() {
        // 第一个参数用作区分
        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // contentprovider查询
        CursorLoader cursorLoader = new CursorLoader(SelectPictureActivity.this,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                IMAGE_PROJECTION[4] + ">0 AND " + IMAGE_PROJECTION[3] + "=? OR " + IMAGE_PROJECTION[3] + "=?",
                new String[]{"image/jpeg", "image/png"}, IMAGE_PROJECTION[2] + " DESC");
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //解析
        if (data != null && data.getCount() > 0) {
            ArrayList<String> images = new ArrayList<>();

            //需要显示拍照 用""标识
            if (mIsShowCamera) {
                images.add("");
            }

            while (data.moveToNext()) {
                String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                images.add(path);
            }

            showImageList(images);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 显示图片列表
     *
     * @param images
     */
    private void showImageList(ArrayList<String> images) {
        ImageListAdapter adapter = new ImageListAdapter(SelectPictureActivity.this, images, mResultList, mMaxCount, this);
        mImageList.setAdapter(adapter);
    }

    // 选择监听
    @Override
    public void select() {
        mPicSelectNum.setText(mResultList.size() + "/" + mMaxCount);
    }

    // 确定
    @Override
    public void onClick(View v) {
        setResultBack();
    }

    public void setResultBack() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra(EXTRA_RESULT, mResultList);
        setResult(RESULT_OK, intent);

        finish();
    }

    //拍照
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //1.加入到集合

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == EXTRA_CAMERA_RESULT) {
                mResultList.add(mTempImagePath);
//                Log.i("select", "onActivityResult: " + mTempImagePath);

                //3.通知系统本地图片变动

                // 最后通知图库更新
                saveImageToGallery();

                //2.调用确定
                setResultBack();
            }
        }
    }

    public void saveImageToGallery() {
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    mTempImagePath, mTempImageName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + mTempImagePath)));
    }
}
