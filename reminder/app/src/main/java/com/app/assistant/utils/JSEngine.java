package com.app.assistant.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;


/**
 * @author zhanghe
 * @comment js桥接
 * @time: 2017-09-28
 */
public class JSEngine {

    private static final String TAG = "JSEngine";

    protected final WebView mWebView;
    protected Context mContext;


    public JSEngine(Context context, WebView webView) {
        this.mContext = context;
        this.mWebView = webView;
    }

    /**
     * @param webAction
     */
    @JavascriptInterface
    public void post(String webAction) {
        if (null != webAction) {

        }
    }

    /**
     * 调用JS方法
     *
     * @param function
     * @param objects
     */
    public void postDataToJs(final String function, final String... objects) {
        if (!TextUtils.isEmpty(function)) {
            new Handler(Looper.getMainLooper()).post(
                    new Runnable() {
                        @Override
                        public void run() {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(function + "(");
                            if (objects != null && objects.length > 0) {
                                int length = objects.length;
                                int i = 0;
                                for (String object : objects) {
                                    if (!TextUtils.isEmpty(object)) {
                                        stringBuilder.append("'");
                                        stringBuilder.append(object);
                                        stringBuilder.append("'");
                                        if (i < length - 1) {
                                            stringBuilder.append(",");
                                        }
                                    }
                                    i++;
                                }
                            }
                            stringBuilder.append(")");
                            String jsCodes = "javascript:" + stringBuilder.toString();
                            mWebView.loadUrl(jsCodes);
                        }
                    }
            );
        } else {
            LogUtils.d(TAG + "js function's name is empty");
        }
    }
}