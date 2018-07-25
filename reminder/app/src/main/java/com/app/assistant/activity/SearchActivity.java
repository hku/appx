package com.app.assistant.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.app.assistant.R;
import com.app.assistant.base.BaseActivity;
import com.app.assistant.utils.Constant;
import com.app.assistant.utils.KeyboardUtils;
import com.app.assistant.utils.LogUtils;
import com.app.assistant.utils.ToastUtils;
import com.app.assistant.widget.ProgressWebView;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/7/24 17:15
 * description: 搜索页面
 */

public class SearchActivity extends BaseActivity {

    @BindView(R.id.search_webView)
    ProgressWebView searchWebView;
    @BindView(R.id.spinner)
    NiceSpinner spinner;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.search_tv)
    TextView searchTv;

    private List<String> mSearchWayList;
    private String mSearchUrl;

    private String mDeliverWord = "";

    @Override
    protected void initData() {
        super.initData();
        mSearchWayList = new ArrayList<>();
        mSearchWayList.add(Constant.CHINAOSO_URL);
        mSearchWayList.add(Constant.BAIDU_URL);
        mSearchWayList.add(Constant.SOUGOU_URL);
        mSearchWayList.add(Constant.BING_URL);
        mSearchWayList.add(Constant.QIHU_URL);
        mDeliverWord = getIntent().getStringExtra("search_word");
    }

    @Override
    protected void initView() {
        super.initView();
        List<String> dataSet = Arrays.asList(Constant.SEARCH_WAYS_ARRAY);
        spinner.attachDataSource(dataSet);
        int index = spinner.getSelectedIndex();
        mSearchUrl = mSearchWayList.get(index);
        LogUtils.d("zhanghe " + "mSearchUrl" + mSearchUrl);
        spinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSearchUrl = mSearchWayList.get(position);
                LogUtils.d("zhanghe " + "mSearchUrl" + mSearchUrl);
            }
        });

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void business() {
        if (!TextUtils.isEmpty(mDeliverWord)) {
            searchEt.setText(mDeliverWord);
            onSearch();
        }
    }

    @OnClick({R.id.search_tv, R.id.back_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_tv:
                onSearch();
                break;
            case R.id.back_iv:
                finish();
                break;
        }
    }

    private void onSearch() {
        String searchS = searchEt.getText().toString();
        if (TextUtils.isEmpty(searchS)) {
            ToastUtils.show(this, "请输入关键词");
            return;
        }
        String url = mSearchUrl + searchS;
        searchWebView.loadUrl(url);
        KeyboardUtils.hideSoftInput(this);
    }
}
