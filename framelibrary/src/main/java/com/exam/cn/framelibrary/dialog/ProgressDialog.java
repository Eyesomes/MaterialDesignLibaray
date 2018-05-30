package com.exam.cn.framelibrary.dialog;

import android.content.Context;

import com.exam.cn.baselibrary.dialog.AlertDialog;
import com.exam.cn.baselibrary.dialog.AlertDialog.Builder;
import com.exam.cn.framelibrary.R;

/**
 * Created by 杰 on 2017/12/18.
 */

public class ProgressDialog {

    public static AlertDialog create(Context context, String hint) {
        return new Builder(context).
                setContentView(R.layout.dialog_custom_progress).
                setText(R.id.dialog_progress_text, hint).
                setCancelable(false).
                create();
    }
}
