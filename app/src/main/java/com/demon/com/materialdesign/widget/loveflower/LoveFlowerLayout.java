package com.demon.com.materialdesign.widget.loveflower;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.demon.com.materialdesign.R;

import java.util.Random;

/**
 * 点赞的心花
 */
public class LoveFlowerLayout extends RelativeLayout {

    private LayoutParams mFlowerParams;

    private Random mRandom;

    private int mWidth, mHeight;

    private Interpolator[] mInterpolators = new Interpolator[]{
            new AccelerateDecelerateInterpolator(),
            new AccelerateInterpolator(),
            new DecelerateInterpolator(),
            new LinearInterpolator()
    };

    private int[] mLoveFlowerRess = new int[]{R.drawable.pl_blue, R.drawable.pl_red, R.drawable.pl_yellow};

    public LoveFlowerLayout(Context context) {
        this(context, null);
    }

    public LoveFlowerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoveFlowerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRandom = new Random();
        mFlowerParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mFlowerParams.addRule(ALIGN_PARENT_BOTTOM);
        mFlowerParams.addRule(CENTER_HORIZONTAL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    public void addLoveFlower() {
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(mFlowerParams);
        imageView.setImageResource(mLoveFlowerRess[mRandom.nextInt(mLoveFlowerRess.length)]);
        addView(imageView);
        animFlower(imageView);
    }

    private void animFlower(final ImageView imageView) {
        AnimatorSet allAnimatorSet = new AnimatorSet();
        allAnimatorSet.playSequentially(getInnerAnim(imageView), getBezierAnim(imageView));
        allAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(imageView);
            }
        });
        allAnimatorSet.start();
    }

    private AnimatorSet getInnerAnim(ImageView imageView) {
        AnimatorSet innerAnimatorSet = new AnimatorSet();
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(imageView, "alpha", 0.3f, 1.0f);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(imageView, "scaleX", 0.3f, 1.0f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(imageView, "scaleY", 0.3f, 1.0f);
        innerAnimatorSet.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator);
        innerAnimatorSet.setDuration(350);
        return innerAnimatorSet;
    }

    private Animator getBezierAnim(final ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        int viewWidth = drawable.getIntrinsicWidth();
        int viewHeight = drawable.getIntrinsicHeight();
        PointF point0 = new PointF(mWidth / 2 - viewWidth / 2, mHeight - viewHeight);
        PointF point1 = new PointF(mRandom.nextInt(mWidth) - viewWidth / 2, mHeight / 2 + mRandom.nextInt(mHeight / 2));
        PointF point2 = new PointF(mRandom.nextInt(mWidth), mRandom.nextInt(mHeight / 2));
        PointF point3 = new PointF(mRandom.nextInt(mWidth) - viewWidth / 2, 0);


        LoveFlowerEvaluator evaluator = new LoveFlowerEvaluator(point1, point2);
        ValueAnimator bezierAnimator = ObjectAnimator.ofObject(evaluator, point0, point3);
        //随机插值器
        bezierAnimator.setInterpolator(mInterpolators[mRandom.nextInt(mInterpolators.length)]);
        bezierAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);

                float t = animation.getAnimatedFraction();
                imageView.setAlpha(1 - t);
            }
        });
        bezierAnimator.setDuration(3000);
        return bezierAnimator;
    }
}
