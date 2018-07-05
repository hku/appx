package com.app.reminder.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.app.reminder.R;
import com.app.reminder.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/7/5 14:00
 * description:显示全部界面
 */

public class ShowActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.add_btn)
    FloatingActionButton addBtn;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_show;
    }

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
    protected void business() {

    }

    @OnClick(R.id.add_btn)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_btn:
                Intent intent = new Intent(ShowActivity.this, AddActivity.class);
                startActivity(intent);
                break;
        }
    }
}
