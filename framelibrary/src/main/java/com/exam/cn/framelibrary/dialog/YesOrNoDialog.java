package com.exam.cn.framelibrary.dialog;

import android.content.Context;
import android.view.View;

import com.exam.cn.baselibrary.dialog.AlertDialog;
import com.exam.cn.framelibrary.R;

/**
 * Created by 杰 on 2018/2/3.
 */

public class YesOrNoDialog {

    private static AlertDialog alertDialog;

    public static AlertDialog create(Context context, String hint, final onSelectListener onSelectListener) {

        alertDialog = new AlertDialog.Builder(context).
                setContentView(R.layout.dialog_yes_or_no).
                setText(R.id.dialog_yes_or_no_content, hint).
                setOnclickListener(R.id.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        onSelectListener.onClick(false);
                    }
                }).
                setOnclickListener(R.id.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        onSelectListener.onClick(true);
                    }
                }).
                setCancelable(false).
                create();

        return alertDialog;
    }

    public interface onSelectListener {
        public void onClick(boolean isOk);
    }
}
