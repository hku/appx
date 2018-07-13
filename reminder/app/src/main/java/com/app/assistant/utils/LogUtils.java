package com.app.assistant.utils;

import com.app.assistant.BuildConfig;
import com.orhanobut.logger.Logger;

/**
 * author: zhanghe
 * created on: 2018/7/10 16:33
 * description:
 */

public final class LogUtils {


    public static void d(String msg) {
        if (BuildConfig.DEBUG) {
            Logger.d(msg);
        }
    }
}
