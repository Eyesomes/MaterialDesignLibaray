package com.demon.com.materialdesign.widget.DragBubbleView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.demon.com.materialdesign.R;

/**
 * 监听触摸事件
 */
public class DragViewTouchListener implements View.OnTouchListener, BubbleDragView.OnDisappearListener {

    private Context mContext;
    private View mOriginView;
    private WindowManager mWindowManager;
    private BubbleDragView mBubbleDragView;
    private WindowManager.LayoutParams mLayoutParams;
    //爆炸动画
    private FrameLayout mBoomFrame;
    private ImageView mBoomImage;

    public DragViewTouchListener(View view, Context context) {
        this.mOriginView = view;
        this.mContext = context;
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mBubbleDragView = new BubbleDragView(mContext);
        mBubbleDragView.setOnDisapearListener(this);
        mLayoutParams = new WindowManager.LayoutParams();
        // 背景要透明
        mLayoutParams.format = PixelFormat.TRANSLUCENT;

        mBoomFrame = new FrameLayout(mContext);
        mBoomImage = new ImageView(mContext);
        mBoomFrame.addView(mBoomImage,
                BubbleUtils.dip2px(30, mContext), BubbleUtils.dip2px(30, mContext));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //隐藏原来的view
                mOriginView.setVisibility(View.INVISIBLE);
                //在windowmanager上面添加贝塞尔view
                mWindowManager.addView(mBubbleDragView, mLayoutParams);

                Bitmap originBitmap = getBitmapFromView(mOriginView);
                mBubbleDragView.setDragBitmap(originBitmap);

                //初始化点
                int[] location = new int[2];
                mOriginView.getLocationInWindow(location);
                mBubbleDragView.initPoint(location[0] + originBitmap.getWidth() / 2,
                        location[1] + originBitmap.getHeight() / 2 - BubbleUtils.getStatusBarHeight(mContext));


                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("1111", "onTouch: 1111 ");
                mBubbleDragView.updateDragPoint(event.getRawX(),
                        event.getRawY() - BubbleUtils.getStatusBarHeight(mContext));
                break;
            case MotionEvent.ACTION_UP:
                mBubbleDragView.handleActionUp(new PointF(event.getRawX(),
                        event.getRawY() - BubbleUtils.getStatusBarHeight(mContext)));
                break;
        }
        return true;
    }

    /**
     * 获取view的bitmap
     *
     * @param view
     * @return
     */
    private Bitmap getBitmapFromView(View view) {
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    @Override
    public void onDismiss(PointF pointF) {
        //移除
        mWindowManager.removeView(mBubbleDragView);
        //爆炸
        mWindowManager.addView(mBoomFrame, mLayoutParams);
        mBoomImage.setBackgroundResource(R.drawable.anim_bubble_pop);
        mBoomImage.setX(pointF.x);
        mBoomImage.setY(pointF.y);
        AnimationDrawable drawable = (AnimationDrawable) mBoomImage.getBackground();
        drawable.start();
        mBoomImage.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWindowManager.removeView(mBoomFrame);
                mOriginView.setVisibility(View.VISIBLE);
            }
        }, getDrawableAnimationTime(drawable));
    }

    private long getDrawableAnimationTime(AnimationDrawable drawable) {
        long time = 0;
        for (int i = 0; i < drawable.getNumberOfFrames(); i++) {
            time += drawable.getDuration(i);
        }
        return time;
    }

    @Override
    public void onRestore() {
        //移除
        try {
            mWindowManager.removeView(mBubbleDragView);
            mOriginView.setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }
    }
}
