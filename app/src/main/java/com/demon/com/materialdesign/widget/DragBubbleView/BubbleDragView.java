package com.demon.com.materialdesign.widget.DragBubbleView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

public class BubbleDragView extends View {

    private PointF mFixationPoint, mDragPoint;

    private Paint mPaint;

    private int mRadius = BubbleUtils.dip2px(12, getContext());

    private int mFixationRadius;
    private Bitmap mDragBitmap;

    private OnDisappearListener mDisappearListener;

    public BubbleDragView(Context context) {
        this(context, null);
    }

    public BubbleDragView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleDragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mFixationPoint == null || mDragPoint == null)
            return;

        canvas.drawCircle(mDragPoint.x, mDragPoint.y, mRadius, mPaint);

        double distance = calculateDistance(mDragPoint, mFixationPoint);
        mFixationRadius = (int) (mRadius - distance / 14);

        if (isShowBezier()) {
            Path bezierPath = getBezierPath();
            canvas.drawPath(bezierPath, mPaint);
            canvas.drawCircle(mFixationPoint.x, mFixationPoint.y, mFixationRadius, mPaint);
        }

        if (mDragBitmap != null) {
            canvas.drawBitmap(mDragBitmap,
                    mDragPoint.x - mDragBitmap.getWidth() / 2,
                    mDragPoint.y - mDragBitmap.getHeight() / 2, null);
        }
    }

    private boolean isShowBezier() {
        return mFixationRadius > mRadius / 5;
    }

    /**
     * 贝塞尔曲线
     *
     * @return
     */
    private Path getBezierPath() {
        Path path = new Path();

        //求tan(a)
        //斜率
        float tanA = (mDragPoint.y - mFixationPoint.y) / (mDragPoint.x - mFixationPoint.x);
        //求角度
        double a = Math.atan(tanA);
        //求点
        //p0
        float p0x = (float) (mFixationPoint.x + mFixationRadius * Math.sin(a));
        float p0y = (float) (mFixationPoint.y - mFixationRadius * Math.cos(a));
        //p1
        float p1x = (float) (mDragPoint.x + mRadius * Math.sin(a));
        float p1y = (float) (mDragPoint.y - mRadius * Math.cos(a));
        //p2
        float p2x = (float) (mDragPoint.x - mRadius * Math.sin(a));
        float p2y = (float) (mDragPoint.y + mRadius * Math.cos(a));
        //p3
        float p3x = (float) (mFixationPoint.x - mFixationRadius * Math.sin(a));
        float p3y = (float) (mFixationPoint.y + mFixationRadius * Math.cos(a));
        //知4点求2条贝塞尔曲线
        //取中点为控制点
        PointF controlPoint = getControlPoint();
        //第一条
        path.moveTo(p0x, p0y);
        path.quadTo(controlPoint.x, controlPoint.y, p1x, p1y);
        //第二条
        path.lineTo(p2x, p2y);
        path.quadTo(controlPoint.x, controlPoint.y, p3x, p3y);
        path.close();
        return path;
    }

    private double calculateDistance(PointF dragPoint, PointF fixationPoint) {
        return Math.sqrt((dragPoint.x - fixationPoint.x) * (dragPoint.x - fixationPoint.x) +
                (dragPoint.y - fixationPoint.y) * (dragPoint.y - fixationPoint.y));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                initPoint(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                float xMove = event.getX();
                float yMove = event.getY() - BubbleUtils.getStatusBarHeight(getContext());
                updateDragPoint(xMove, yMove);
                Log.e("11112", "onTouch: 11112 ");
                break;
            case MotionEvent.ACTION_UP:
                handleActionUp(new PointF(event.getX(),event.getY()));
                break;
        }
        return true;
    }

    public void initPoint(float x, float y) {
        mFixationPoint = new PointF(x, y);
        mDragPoint = new PointF(x, y);
    }

    public void updateDragPoint(float x, float y) {
        mDragPoint.x = x;
        mDragPoint.y = y;
        invalidate();
    }

    public PointF getControlPoint() {
        return new PointF((mDragPoint.x + mFixationPoint.x) / 2,
                (mDragPoint.y + mFixationPoint.y) / 2);
    }

    public void setDragBitmap(Bitmap dragBitmap) {
        this.mDragBitmap = dragBitmap;
    }

    public void handleActionUp(PointF pointF) {
        if (isShowBezier()) {
            restore(pointF);
        } else {
            if (mDisappearListener != null)
                mDisappearListener.onDismiss(pointF);
        }
    }

    private void restore(PointF pointF) {
        final PointF start = new PointF(mDragPoint.x, mDragPoint.y);
        final PointF end = new PointF(mFixationPoint.x, mFixationPoint.y);

        ValueAnimator animator = ObjectAnimator.ofFloat(1);
        animator.setDuration(350);
        animator.setInterpolator(new OvershootInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float percent = (float) animation.getAnimatedValue();
                PointF current = BubbleUtils.getPointByPercent(start, end, percent);
                updateDragPoint(current.x, current.y);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mDisappearListener != null)
                    mDisappearListener.onRestore();
            }
        });
        animator.start();
    }

    public void setOnDisapearListener(OnDisappearListener listener) {
        this.mDisappearListener = listener;
    }

    /**
     * 绑定需要拖拽消失的view
     *
     * @param view
     * @param listener
     */
    public static void attach(View view, OnDisappearListener listener) {
        view.setOnTouchListener(new DragViewTouchListener(view, view.getContext(),listener));
    }

    public interface OnDisappearListener {
        void onDismiss(PointF pointF);

        void onRestore();
    }
}
