package com.app.assistant.utils;

import android.content.Context;

/**
 * author: zhanghe
 * created on: 2018/7/5 15:40
 * description: 常量
 */

public class Constant {

    public static final String[] TAG_ARRAY = {"英语", "编程", "生活", "宝宝", "孕妈", "旅游", "诗词", "memo"};

    public static final String DELIVER_TAG = "edit_data";

    public final static String getFileProviderName(Context context) {
        return context.getPackageName() + ".fileprovider";
    }

    public static final String ALARM_CLOCK = "alarm_clock";

    public static final String PACKAGE_NAME = "com.app.reminder";
}
