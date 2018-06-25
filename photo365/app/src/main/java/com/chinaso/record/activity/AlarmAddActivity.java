package com.chinaso.record.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chinaso.record.R;
import com.chinaso.record.base.BaseActivity;

import butterknife.BindView;

/**
 * author: zhanghe
 * created on: 2018/6/25 11:24
 * description:添加闹钟界面
 */

public class AlarmAddActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_alarm_add;
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
}
