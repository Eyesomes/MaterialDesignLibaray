package com.exam.cn.baselibrary.widge.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exam.cn.baselibrary.R;


/**
 * Created by 杰 on 2017/11/13.
 */

public class BannerView extends RelativeLayout{

    private final Context mContext;
    //轮播的viewpager
    private BannerViewPager mBannerViewPager;

    private RelativeLayout mBottonLayout;

    //文字描述
    private TextView mTextView;

    // 指示的点的容器
    private LinearLayout mIndicatorContainer;

    private BannerAdapter mAdapter;

    private int mCurrentPosition = 0;

    // 自定义属性

    private Drawable mFocusIndicatorDrawable;
    private Drawable mNormalIndicatorDrawable;

    // 指示器的位置
    private int mIndicatorGravity = 1;

    private int mBottomBgColor = 0;
    private int mTextColor = 0;

    private float mHeightRate = 0;
    private float mWidthRate = 0;


    public BannerView(Context context) {
        this(context,null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;
        //加载布局
        inflate(context, R.layout.widge_banner_layout,this);
        initAttrs(attrs);
        // 初始化
        initView();
    }

    /**
     * 初始化自定义属性
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.BannerView);

        mIndicatorGravity = typedArray.getInt(R.styleable.BannerView_indicatorGravity,mIndicatorGravity);

        mNormalIndicatorDrawable = typedArray.getDrawable(R.styleable.BannerView_indicatorNormal);
        if (mNormalIndicatorDrawable == null){
            mNormalIndicatorDrawable = new ColorDrawable(Color.WHITE);
        }
        mFocusIndicatorDrawable = typedArray.getDrawable(R.styleable.BannerView_indicatorFocus);
        if (mFocusIndicatorDrawable == null){
            mFocusIndicatorDrawable = new ColorDrawable(Color.RED);
        }

        mBottomBgColor = typedArray.getColor(R.styleable.BannerView_bottemColer,0);
        mTextColor = typedArray.getColor(R.styleable.BannerView_textColer,0);

        typedArray.recycle();
    }

    /**
     * 初始化 findviewbyid
     */
    private void initView() {
        mBannerViewPager = (BannerViewPager) findViewById(R.id.banner_viewpager);
        mTextView = (TextView) findViewById(R.id.banner_text);
        mIndicatorContainer = (LinearLayout) findViewById(R.id.banner_indicator_container);
        mBottonLayout = (RelativeLayout) findViewById(R.id.banner_botton_layout);

        if (mTextColor!=0){
            mTextView.setTextColor(mTextColor);
        }

        if (mBottomBgColor!=0){
            mBottonLayout.setBackgroundColor(mBottomBgColor);
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 设置banneradapter
     * @param adapter
     */
    public void setAdapter(BannerAdapter adapter) {
        this.mAdapter = adapter;
        mBannerViewPager.setAdapter(adapter);

        initIndicator();

        mTextView.setText(mAdapter.getDescription(mCurrentPosition));

        // 监听当前选中的位置
        mBannerViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                onPageSelectedChange(position);
            }
        });

    }



    /**
     * 轮播
     */
    public void startScroll(){
        mBannerViewPager.startScroll();
    }

    /**
     * 设置宽高比
     * @param width
     * @param height
     */
    public void setWidthHeightRate(float width,float height){
        this.mWidthRate = width;
        this.mHeightRate = height;

        // 置于最后进行
        this.post(new Runnable() {
            @Override
            public void run() {
                // 设置宽高比
                if (mHeightRate == 0||mWidthRate == 0){
                    return;
                }
                int width1 = getMeasuredWidth();
                int height1 = (int) (width1*mHeightRate/mWidthRate);
                getLayoutParams().height = height1;
            }
        });
    }

//----------------------------------------------------------------------------------------------------------------------------------------------

    private void initIndicator() {

        mIndicatorContainer.removeAllViews();

        mIndicatorContainer.setGravity(getIndicatorGravity());

        int count = mAdapter.getCount();
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(mContext);
            if (i==0){
                imageView.setImageDrawable(mFocusIndicatorDrawable);
            }else {
                imageView.setImageDrawable(mNormalIndicatorDrawable);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dipToPx(5),dipToPx(5));
            params.setMargins(dipToPx(3),0,dipToPx(3),0);
            imageView.setLayoutParams(params);
            mIndicatorContainer.addView(imageView);
        }
    }

    public int getIndicatorGravity() {
        switch (mIndicatorGravity){
            case 1:
                return Gravity.RIGHT;
            case 0:
                return Gravity.CENTER;
            case -1:
                return Gravity.LEFT;
        }
        return Gravity.RIGHT;
    }

    /**
     * 监听当前选中的位置
     * @param position
     */
    private void onPageSelectedChange(int position) {
         // indicator 切换
        ImageView old = (ImageView) mIndicatorContainer.getChildAt(mCurrentPosition);
        old.setImageDrawable(mNormalIndicatorDrawable);

        mCurrentPosition = position%mAdapter.getCount();
        ImageView current = (ImageView) mIndicatorContainer.getChildAt(mCurrentPosition);
        current.setImageDrawable(mFocusIndicatorDrawable);

        // 文字切换
        mTextView.setText(mAdapter.getDescription(mCurrentPosition));
    }

    /**
     * dip 转px
     * @param dip
     * @return
     */
    private int dipToPx(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,getResources().getDisplayMetrics());
    }

    /**
     * Drawable → Bitmap
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {

        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}
