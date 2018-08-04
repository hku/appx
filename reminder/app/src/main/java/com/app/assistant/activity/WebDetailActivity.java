package com.app.assistant.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.assistant.R;
import com.app.assistant.adapter.SearchWayAdapter;
import com.app.assistant.base.BaseActivity;
import com.app.assistant.utils.CommonUtils;
import com.app.assistant.utils.Constant;
import com.app.assistant.utils.LogUtils;
import com.app.assistant.utils.PreferenceKeyConstant;
import com.app.assistant.utils.SPUtils;
import com.app.assistant.utils.ToastUtils;
import com.app.assistant.widget.ProgressWebView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/8/1 14:21
 * description:网页详情页面
 */

public class WebDetailActivity extends BaseActivity {

    @BindView(R.id.voice_iv)
    ImageView voiceIv;
    @BindView(R.id.search_words_tv)
    TextView wordsTv;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.web_back_iv)
    ImageView backIv;
    @BindView(R.id.spinner)
    NiceSpinner spinner;
    @BindView(R.id.webview)
    ProgressWebView webView;

    private Map<String, String> mWaysMap = new HashMap<>();
    private List<String> mDataSet = new ArrayList<>();
    private SearchWayAdapter mAdapter;
    private String mSearchWords = "";
    private String mSearchUrl = "";

    @Override
    protected void initData() {
        super.initData();
        mWaysMap.put(Constant.SEARCH_WAYS_ARRAY[0], Constant.CHINAOSO_URL);
        mWaysMap.put(Constant.SEARCH_WAYS_ARRAY[1], Constant.BAIDU_URL);
        mWaysMap.put(Constant.SEARCH_WAYS_ARRAY[2], Constant.SOUGOU_URL);
        mWaysMap.put(Constant.SEARCH_WAYS_ARRAY[3], Constant.BING_URL);
        mWaysMap.put(Constant.SEARCH_WAYS_ARRAY[4], Constant.QIHU_URL);
        mDataSet.addAll(Arrays.asList(Constant.SEARCH_WAYS_ARRAY));
        mSearchWords = getIntent().getStringExtra("search_words");
    }

    @Override
    protected void initView() {
        super.initView();
        initRecyclerView();
        initSpinner();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web_detail;
    }

    @Override
    protected void business() {
        mAdapter.addData(Arrays.asList(Constant.SEARCH_WAYS_ARRAY));
        wordsTv.setText(mSearchWords);
        int spinnerKey = SPUtils.getInstance().getInt(PreferenceKeyConstant.WEB_DETAIL_SPINNER_KET, -1);
        if (spinnerKey != -1) {
            spinner.setSelectedIndex(spinnerKey);
        } else {
            spinner.setSelectedIndex(0);
        }
        loadWeb(spinner.getSelectedIndex());
    }

    /**
     * init recyclerView
     */
    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        mAdapter = new SearchWayAdapter(R.layout.item_search_way);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String searchWayName = mAdapter.getItem(position);
                String ways = mWaysMap.get(searchWayName);
                mSearchUrl = ways + mSearchWords;
                webView.loadUrl(mSearchUrl);
            }
        });
    }

    /**
     * init spinner
     */
    private void initSpinner() {
        spinner.attachDataSource(mDataSet);
        spinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SPUtils.getInstance().put(PreferenceKeyConstant.WEB_DETAIL_SPINNER_KET, position);
                loadWeb(position);
            }
        });
    }

    @OnClick({R.id.voice_iv, R.id.web_back_iv, R.id.search_words_tv, R.id.scan_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.voice_iv:
                ToastUtils.show(this, "正在开发中，客官请稍等");
                break;
            case R.id.web_back_iv:
                webViewBack();
                break;
            case R.id.search_words_tv:
                updateSearch();
                break;
            case R.id.scan_iv:
                Intent scanIntent = new Intent(this, ScanActivity.class);
                startActivity(scanIntent);
                break;
        }
    }

    /**
     * 加载网页
     */
    private void loadWeb(int spinnerPosition) {
        String key = mDataSet.get(spinnerPosition);
        String ways = mWaysMap.get(key);
        //国搜的搜索要特殊处理
        if (spinnerPosition == 0) {
            String encodeWords = CommonUtils.encodeURL(mSearchWords);
            mSearchUrl = ways.replaceAll("\\{key\\}", encodeWords);
        } else {
            mSearchUrl = ways + mSearchWords;
        }
        webView.loadUrl(mSearchUrl);
        LogUtils.d("zhanghe " + "mSearchUrl" + mSearchUrl);
    }

    private void updateSearch() {
        String searchText = wordsTv.getText().toString();
        Intent intent = new Intent(WebDetailActivity.this, SearchNewActivity.class);
        intent.putExtra("search", searchText);
        startActivity(intent);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
