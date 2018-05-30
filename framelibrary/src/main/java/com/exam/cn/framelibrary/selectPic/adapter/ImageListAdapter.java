package com.exam.cn.framelibrary.selectPic.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.exam.cn.baselibrary.recyclerView.adapter.RecyclerCommonAdapter;
import com.exam.cn.baselibrary.recyclerView.adapter.ViewHolder;
import com.exam.cn.baselibrary.util.OpenFileUtil;
import com.exam.cn.framelibrary.R;
import com.exam.cn.framelibrary.selectPic.SelectPictureActivity;
import com.exam.cn.framelibrary.selectPic.listener.SelectListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 杰 on 2017/11/24.
 */

public class ImageListAdapter extends RecyclerCommonAdapter {

    private ArrayList<String> mResultList;

    private SelectListener mSelectListener;

    private int mMaxCount;

    public ImageListAdapter(Context context, List data, ArrayList<String> list, int maxCount) {
        super(context, data, R.layout.item_select_pic_image_list);
        this.mResultList = list;
        this.mMaxCount = maxCount;
    }

    public ImageListAdapter(Context context, List data, ArrayList<String> list, int maxCount, SelectListener selectListener) {
        this(context, data, list, maxCount);
        this.mSelectListener = selectListener;
    }

    @Override
    protected void convert(ViewHolder holder, Object o, int position) {
        final String path = (String) o;

        LinearLayout takePic = holder.getView(R.id.item_select_pic_take_pic);
        final ImageView rightIcon = holder.getView(R.id.item_select_pic_right_icon);
        ImageView picture = holder.getView(R.id.item_select_pic_pic);

        if (TextUtils.isEmpty(path)) {
            takePic.setVisibility(View.VISIBLE);
            rightIcon.setVisibility(View.GONE);
            picture.setVisibility(View.GONE);

            // 拍照
            takePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toTakePhotot();
                }
            });
            return;
        }
        takePic.setVisibility(View.GONE);
        rightIcon.setVisibility(View.VISIBLE);
        picture.setVisibility(View.VISIBLE);

        Glide.with(mContext).load(path).centerCrop().into(picture);

        final View mask = holder.getView(R.id.item_select_pic_mask);

        // 初始化选中条目
        if (mResultList.contains(path)) {
            rightIcon.setSelected(true);
            mask.setVisibility(View.VISIBLE);
        } else {
            rightIcon.setSelected(false);
            mask.setVisibility(View.GONE);
        }

        // 点击右上角选中
        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mResultList.contains(path)) {
                    mResultList.remove(path);
                    rightIcon.setSelected(false);
                    mask.setVisibility(View.GONE);
                } else {
                    // 不能大于最大选中条目
                    if (mResultList.size() >= mMaxCount) {
                        Toast.makeText(mContext, "最多只能选取" + mMaxCount + "张图片", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    mResultList.add(path);
                    rightIcon.setSelected(true);
                    mask.setVisibility(View.VISIBLE);
                }

                if (mSelectListener != null) {
                    mSelectListener.select();
                }
            }
        });
        // 条目的点击事件 打开图片
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFileUtil.openFile(mContext, path);
            }
        });
    }

    private void toTakePhotot() {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String imageFileName = "JPEG" + timeStamp;
            String storagePath = Environment.getExternalStorageDirectory() + File.separator + mContext.getPackageName()
                    + File.separator + "temp" + File.separator;
            File storageDir = new File(storagePath);
            if (!storageDir.mkdirs()) {
                storageDir.createNewFile();
            }
            File imageFile = null;

            imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider", imageFile));
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(imageFile));
            }
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.name());
            ((Activity) mContext).startActivityForResult(intent, SelectPictureActivity.EXTRA_CAMERA_RESULT);
            ((SelectPictureActivity) mContext).mTempImagePath = imageFile.getAbsolutePath();
            ((SelectPictureActivity) mContext).mTempImageName = imageFile.getName();

//        Log.i("select", "toTakePhotot: " +  imageFile.getName() +imageFile.getAbsolutePath() );
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "无法创建文件目录...", Toast.LENGTH_LONG).show();
        }
    }
}
