package com.exam.cn.baselibrary.widge.multipleIndicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.exam.cn.baselibrary.R;


/**
 * Created by admin on 2017/6/27.
 */

public class ColorTrackTextView extends TextView {

    private Paint mOriginPaint;

    private Paint mChangePaint;

    private float mCurrentProgress = (float) 0.0;

    private Direction direction = Direction.LEFT_TO_RIGHT;

    public enum Direction {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT
    }

    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context, attrs);
    }

    /**
     * 初始化画笔
     *
     * @param context
     * @param attributeSet
     */
    private void initPaint(Context context, AttributeSet attributeSet) {

        TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.ColorTrackTextView);

        int originColor = array.getColor(R.styleable.ColorTrackTextView_originColor, getTextColors().getDefaultColor());
        int changeColor = array.getColor(R.styleable.ColorTrackTextView_changeColor, getTextColors().getDefaultColor());

        mOriginPaint = getPaintByColor(originColor);
        mChangePaint = getPaintByColor(changeColor);

        array.recycle();
    }

    /**
     * 根据颜色获取画笔
     *
     * @param color
     * @return
     */
    private Paint getPaintByColor(int color) {

        Paint paint = new Paint();
        paint.setColor(color);
        //设置抗锯齿
        paint.setAntiAlias(true);
        //防抖动
        paint.setDither(true);
        //设置字体的大小  就是textview的字体大小
        paint.setTextSize(getTextSize());
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        int current = (int) (mCurrentProgress * getWidth());

        Log.i("jie", String.valueOf(current));

        if (direction == Direction.LEFT_TO_RIGHT) {
            drawText(canvas, mChangePaint, 0, current);
            drawText(canvas, mOriginPaint, current, getWidth());
        } else {
            drawText(canvas, mChangePaint, getWidth() - current, getWidth());
            drawText(canvas, mOriginPaint, 0, getWidth() - current);
        }
    }

    /**
     * 有比例的绘制text
     *
     * @param canvas
     * @param paint
     * @param start
     * @param end
     */
    private void drawText(Canvas canvas, Paint paint, int start, int end) {

        canvas.save();
        Rect rect = new Rect(start, 0, end, getHeight());
        canvas.clipRect(rect);
        String text = getText().toString();
        //获取字体的宽度
        Rect bound = new Rect();
        paint.getTextBounds(text, 0, text.length(), bound);

        int x = getWidth() / 2 - bound.width() / 2;
        //基线 baseline
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        //top 是一个负值 bottom 是一个正值
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseline = getHeight() / 2 + dy;
        canvas.drawText(text, x, baseline, paint);
        canvas.restore();
    }

    public void setDirection(Direction direction) {
        this.direction=direction;
    }

    public void setCurrentProgress(float currentProgress){
        this.mCurrentProgress=currentProgress;
        invalidate();
    }

    public void setChangeColor(int color){
        mChangePaint.setColor(color);
    }
    public void setOriginColor(int color){
        mOriginPaint.setColor(color);
    }
}
