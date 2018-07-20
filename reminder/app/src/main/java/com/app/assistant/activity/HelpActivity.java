package com.app.assistant.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.app.assistant.R;
import com.app.assistant.base.BaseActivity;

import butterknife.BindView;

/**
 * author: zhanghe
 * created on: 2018/7/19 16:46
 * description:帮助页面
 */

public class HelpActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

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
    protected int getLayoutResId() {
        return R.layout.activity_help;
    }

    @Override
    protected void business() {

    }
}
