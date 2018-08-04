package com.app.assistant.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.assistant.R;
import com.app.assistant.base.BaseActivity;
import com.app.assistant.utils.CommonUtils;
import com.app.assistant.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/8/1 9:27
 * description:
 */

public class SearchNewActivity extends BaseActivity {

    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.voice_iv)
    ImageView voiceIv;
    @BindView(R.id.clear_iv)
    ImageView clearIv;
    @BindView(R.id.search_tv)
    TextView searchTv;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.scan_iv)
    ImageView scanIv;

    //搜索的关键词
    private String mSearchWords = "";
    //当前是否从WebUrlActivity中跳转而来
    private boolean mIsUrl = false;

    //0 : 取消  1：搜索  2：进入
    private int mSearchFlag = -1;

    @Override
    protected void initData() {
        super.initData();
        mSearchWords = getIntent().getStringExtra("search");
        mIsUrl = getIntent().getBooleanExtra("url_flag", false);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search_new;
    }

    @Override
    protected void initView() {
        super.initView();
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchS = s.toString();
                if (!TextUtils.isEmpty(searchS)) {
                    scanIv.setVisibility(View.GONE);
                    voiceIv.setVisibility(View.GONE);
                    clearIv.setVisibility(View.VISIBLE);
                    boolean isUrl = CommonUtils.isHttpUrl(searchS);
                    if (isUrl) {
                        mSearchFlag = 2;
                        searchTv.setText(getResources().getString(R.string.in));
                        searchIv.setImageResource(R.drawable.ic_link_black_24dp);
                    } else {
                        mSearchFlag = 1;
                        searchTv.setText(getResources().getString(R.string.search));
                        searchIv.setImageResource(R.drawable.ic_search_black_24dp);
                    }
                } else {
                    mSearchFlag = 0;
                    searchTv.setText(getResources().getString(R.string.cancel));
                    voiceIv.setVisibility(View.VISIBLE);
                    clearIv.setVisibility(View.GONE);
                    scanIv.setVisibility(View.VISIBLE);
                    searchIv.setImageResource(R.drawable.ic_search_black_24dp);
                }
            }
        });
        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    onSearch();
                }
                return false;
            }
        });
    }

    @Override
    protected void business() {
        searchEt.setText(mSearchWords);
        if (!TextUtils.isEmpty(mSearchWords)) {
            searchEt.setSelection(mSearchWords.length());
        }
        if (mIsUrl) {
            searchEt.selectAll();
            searchTv.setText(getResources().getString(R.string.cancel));
            mSearchFlag = 0;
        }
    }

    @OnClick({R.id.search_tv, R.id.voice_iv, R.id.clear_iv, R.id.scan_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_tv:
                onSearch();
                break;
            case R.id.voice_iv:
                ToastUtils.show(this, "正在开发中，客官请稍等");
                break;
            case R.id.clear_iv:
                clearText();
                break;
            case R.id.scan_iv:
                Intent scanIntent = new Intent(this, ScanActivity.class);
                startActivity(scanIntent);
                break;
        }
    }

    private void clearText() {
        searchEt.setText("");
    }


    private void onSearch() {
        String text = searchEt.getText().toString();
        switch (mSearchFlag) {
            case 0:
                finish();
                break;
            case 1:
                Intent detailIntent = new Intent(SearchNewActivity.this, WebDetailActivity.class);
                detailIntent.putExtra("search_words", text);
                startActivity(detailIntent);
                finish();
                break;
            case 2:
                Intent urlIntent = new Intent(SearchNewActivity.this, WebUrlActivity.class);
                urlIntent.putExtra("search_words", text);
                startActivity(urlIntent);
                finish();
                break;
        }
    }
}
