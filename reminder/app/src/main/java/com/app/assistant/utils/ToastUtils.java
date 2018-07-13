package com.app.assistant.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * author: zhanghe
 * created on: 2018/6/24 16:04
 * description:
 */

public final class ToastUtils {

    public static void show(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
