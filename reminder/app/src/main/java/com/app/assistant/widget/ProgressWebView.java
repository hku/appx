package com.app.assistant.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.assistant.R;
import com.app.assistant.utils.JSEngine;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @说明: 带进度条的webview
 * @作者: zhanghe
 * @时间: 2018-03-01 10:26
 */

public class ProgressWebView extends LinearLayout {

    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private Context mContext;
    private TextView mTitleTv;


    public ProgressWebView(Context context) {
        this(context, null);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.view_web_progress, this);
        ButterKnife.bind(this);
        initWebView();
    }

    /**
     * 带header
     *
     * @param url
     * @param header
     */
    public void loadUrl(String url, String header) {
        load(url, header);
    }

    /**
     * 不带header
     *
     * @param url
     */
    public void loadUrl(String url) {
        load(url, "");
    }

    /**
     * 自动加载标题
     *
     * @param url
     * @param title
     */
    public void loadUrl(String url, TextView title) {
        this.mTitleTv = title;
        load(url, "");
    }

    private void load(String url, String header) {
        if (!url.startsWith("http")) {
            url = "https://" + url;
        }
        if (TextUtils.isEmpty(header) && url != null) {
            mWebView.loadUrl(url);
        } else if (!TextUtils.isEmpty(header) && url != null) {
            Map<String, String> extraHeaders = new HashMap<>();
            extraHeaders.put("Authorization", header);
            mWebView.loadUrl(url, extraHeaders);
        }
    }

    public WebView getWebView() {
        return mWebView;
    }

    /**
     * 初始化webview
     */
    private void initWebView() {

        WebSettings webSettings = mWebView.getSettings();

        mWebView.addJavascriptInterface(new JSEngine(mContext, mWebView), "HostApp");

        webSettings.setJavaScriptEnabled(true);
        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 支持保存数据
        webSettings.setSaveFormData(true);
        // 设置默认缩放方式尺寸是far
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(false);
        // 清除缓存
        mWebView.clearCache(true);
        // 清除历史记录
        mWebView.clearHistory();

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mProgressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError
                    error) {
                super.onReceivedError(view, request, error);
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.GONE);
                if (mTitleTv != null) {
                    String titleS = view.getTitle();
                    mTitleTv.setText(titleS);
                }
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (mProgressBar.getVisibility() == View.VISIBLE) {
                    mProgressBar.setProgress(newProgress);
                    if (newProgress >= 100) {
                        mProgressBar.setVisibility(GONE);
                    }
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }


    /**
     * can back
     *
     * @return
     */
    public boolean canBack() {
        return mWebView.canGoBack();
    }

    /**
     * can forward
     *
     * @return
     */
    public boolean canForward() {
        return mWebView.canGoForward();
    }

    /**
     * forward
     */
    public void goForward() {
        mWebView.goForward();
    }

    /**
     * back
     */
    public void back() {
        mWebView.goBack();
    }
}
