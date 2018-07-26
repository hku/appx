package com.app.assistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.app.assistant.R;
import com.app.assistant.base.BaseActivity;
import com.app.assistant.widget.ProgressWebView;

import butterknife.BindView;

/**
 * author: zhanghe
 * created on: 2018/7/26 15:03
 * description:网页Activity
 */

public class WebActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webview)
    ProgressWebView webView;
    @BindView(R.id.title_tv)
    TextView titleTv;

    private String mTitle = "";
    private String mUrl = "";

    @Override
    protected void initView() {
        super.initView();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        mTitle = intent.getStringExtra("title");
        mUrl = intent.getStringExtra("url");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web;
    }

    @Override
    protected void business() {
        titleTv.setText(mTitle);
        webView.loadUrl(mUrl);
    }
}
