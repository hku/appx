package com.app.assistant.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.app.assistant.R;
import com.app.assistant.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/7/14 19:43
 * description:任务
 */

public class TaskAddActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.date_et)
    EditText dateEt;


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
        return R.layout.activity_task_add;
    }

    @Override
    protected void business() {

    }

    @OnClick({R.id.date_et})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.date_et:
                break;
        }
    }
}
