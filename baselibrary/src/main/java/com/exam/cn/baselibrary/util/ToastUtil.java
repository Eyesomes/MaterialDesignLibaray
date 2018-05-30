package com.exam.cn.baselibrary.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 只显示一个toast
 *
 * Created by 杰 on 2017/4/13.
 */

public class ToastUtil {

    private static Toast toast;

    public static void showToast(Context context,
                                 String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

}
