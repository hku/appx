package com.chinaso.record.utils;

import android.content.Context;

/**
 * author: zhanghe
 * created on: 2018/6/4 15:57
 * description:常量类
 */


public final class Constant {

    public final static String getFileProviderName(Context context) {
        return context.getPackageName() + ".fileprovider";
    }

    public static final String ALARM_CLOCK = "alarm_clock";

    public static final String PACKAGE_NAME = "com.chinaso.record";
}