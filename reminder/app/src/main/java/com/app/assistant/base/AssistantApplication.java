package com.app.assistant.base;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.app.assistant.BuildConfig;
import com.app.assistant.utils.FileManagerUtils;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

/**
 * author: zhanghe
 * created on: 2018/6/5 10:09
 * description:application
 */

public class AssistantApplication extends Application {

    private static final String FOLDER_NAME = "assistant";
    //WeChat
    private static final String WEIXIN_ID = "wxea36521f69b47a1c";
    private static final String WEIXIN_SECRET = "99bd9b12a15524cbd90b0ae8f0a1a79d";
    //Sina
    private static final String SINA_KEY = "2214775937";
    private static final String SINA_SECRET = "8c7b701cd2201f5792979b8eb66d50a5";

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        initShareConfig();
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
        UMConfigure.setLogEnabled(true);
        initRefreshLayout();
        initDebugBridge();
        Logger.addLogAdapter(new AndroidLogAdapter());
        FileManagerUtils.getInstance().setFolderName(FOLDER_NAME);
    }


    private void initShareConfig() {
        //微信 appid appsecret
        PlatformConfig.setWeixin(WEIXIN_ID, WEIXIN_SECRET);
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo(SINA_KEY, SINA_SECRET, "http://sns.whalecloud.com");
    }

    private void initDebugBridge() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }


    private void initRefreshLayout() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsHeader(sContext);
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new BallPulseFooter(sContext);
            }
        });
    }

    public static Context getContext() {
        return sContext;
    }
}
