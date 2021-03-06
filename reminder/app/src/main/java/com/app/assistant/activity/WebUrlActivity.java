package com.app.assistant.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.app.assistant.R;
import com.app.assistant.base.BaseActivity;
import com.app.assistant.entity.ShareInfoEntity;
import com.app.assistant.widget.ProgressWebView;
import com.app.assistant.widget.SharePopWindow;
import com.app.assistant.widget.WebMenuPopWindow;
import com.umeng.socialize.UMShareAPI;

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

    @OnClick({R.id.refresh_iv, R.id.title_tv, R.id.web_back_iv, R.id.web_forward_iv,
            R.id.web_menu_iv, R.id.web_home_iv, R.id.web_share_iv})
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
            case R.id.web_back_iv:
                webViewBack();
                break;
            case R.id.web_forward_iv:
                webViewForward();
                break;
            case R.id.web_menu_iv:
                showMenuPopWindow();
                break;
            case R.id.web_share_iv:
                showSharePopWindow();
                break;
            case R.id.web_home_iv:
                backToMainActivity();
                break;
        }
    }

    /**
     * webView的回退
     */
    private void webViewBack() {
        if (webView.canBack()) {
            webView.back();
        } else {
            finish();
        }
    }

    /**
     * webView的forward
     */
    private void webViewForward() {
        if (webView.canForward()) {
            webView.goForward();
        }
    }

    /**
     * show menu popWindow
     */
    private void showMenuPopWindow() {
        WebMenuPopWindow popWindow = new WebMenuPopWindow(this);
        popWindow.show();
    }

    /**
     * show share popWindow
     */
    private void showSharePopWindow() {
        ShareInfoEntity entity = new ShareInfoEntity();
        entity.setTitle("1212");
        entity.setContent("22222");
        entity.setTargetUrl("https://www.baidu.com");
        SharePopWindow popWindow = new SharePopWindow(this, null);
        popWindow.show();
    }

    /**
     * 返回到首页面
     */
    private void backToMainActivity() {
        Intent intent = new Intent(WebUrlActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
