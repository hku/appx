package com.app.assistant.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.app.assistant.R;
import com.app.assistant.base.BaseActivity;
import com.app.assistant.widget.ProgressWebView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/8/2 17:16
 * description:
 */

public class WebUrlActivity extends BaseActivity {

    @BindView(R.id.webview)
    ProgressWebView webView;
    @BindView(R.id.title_tv)
    TextView titleTv;

    private String mSearchUrl = "";

    @Override
    protected void initData() {
        super.initData();
        mSearchUrl = getIntent().getStringExtra("search_words");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web_url;
    }

    @Override
    protected void business() {
        webView.loadUrl(mSearchUrl, titleTv);
    }

    @OnClick({R.id.refresh_iv, R.id.title_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.refresh_iv:
                webView.loadUrl(mSearchUrl, titleTv);
                break;
            case R.id.title_tv:
                Intent intent = new Intent(WebUrlActivity.this, SearchNewActivity.class);
                intent.putExtra("search", mSearchUrl);
                intent.putExtra("url_flag", true);
                startActivity(intent);
                break;
        }
    }
}
