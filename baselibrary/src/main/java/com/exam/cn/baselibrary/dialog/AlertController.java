package com.exam.cn.baselibrary.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by zj on 2017/8/4.
 */

class AlertController {

    private AlertDialog mDialog;
    private Window mWindow;

    private DialogViewHelper mViewHelper;

    public AlertController(AlertDialog dialog, Window window) {
        this.mDialog = dialog;
        this.mWindow = window;
    }

    public AlertDialog getDialog() {
        return mDialog;
    }

    public Window getWindow() {
        return mWindow;
    }

    public void setViewHelper(DialogViewHelper viewHelper) {
        this.mViewHelper = viewHelper;
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {
        mViewHelper.setText(viewId,text);
    }

    /**
     * 设置 点击监听
     *
     * @param viewId
     * @param listener
     */
    public void setOnclickListener(int viewId, View.OnClickListener listener) {
        mViewHelper.setOnclickListener(viewId,listener);
    }

    /**
     * 获取view 避免重复 findviewbyid
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View>T getView(int viewId) {

        return (T) mViewHelper.getView(viewId);
    }

    public static class AlertParams {

        public Context mContext;
        public int mThemeResId;
        public boolean mCancelable = true;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;//按键监听
        public View mView = null;//布局view
        public int mViewLayoutResId = 0;//布局viewid

        public SparseArray<CharSequence> mTextArray = new SparseArray<>();
        public SparseArray<View.OnClickListener> mClickArray = new SparseArray<>();
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;//宽度
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

        public int mGravity = Gravity.CENTER;
        public int mAnimations = 0;//动画


        public AlertParams(Context context, int themeResId) {
            this.mContext = context;
            this.mThemeResId = themeResId;
        }

        /**
         * 绑定和设置参数
         *
         * @param mAlert
         */
        public void apply(AlertController mAlert) {

            // 1. 设置布局 viewhelper
            DialogViewHelper viewHelper = null;
            if (mViewLayoutResId != 0) {
                viewHelper = new DialogViewHelper(mContext, mViewLayoutResId);
            }
            if (mView != null) {
                viewHelper = new DialogViewHelper(mView);
            }

            if (viewHelper == null) {
                throw new IllegalArgumentException("请设置布局setContentView");
            }

            mAlert.getDialog().setContentView(viewHelper.getContentView());
            // 2. 设置文本
            int textArraySize = mTextArray.size();
            for (int i = 0; i < textArraySize; i++) {
                viewHelper.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }
            // 3. 设置点击事件
            int clickArraySize = mClickArray.size();
            for (int i = 0; i < clickArraySize; i++) {
                viewHelper.setOnclickListener(mClickArray.keyAt(i), mClickArray.valueAt(i));
            }

            mAlert.setViewHelper(viewHelper);

            // 4. 配置自定义效果
            Window window = mAlert.getWindow();
            window.setGravity(mGravity);

            if (mAnimations != 0) {
                window.setWindowAnimations(mAnimations);
            }

            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mWidth;
            params.height = mHeight;
            window.setAttributes(params);
        }
    }
}
