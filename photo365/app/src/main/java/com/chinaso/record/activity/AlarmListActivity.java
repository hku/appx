package com.chinaso.record.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.chinaso.record.R;
import com.chinaso.record.adapter.AlarmClockAdapter;
import com.chinaso.record.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/6/25 10:20
 * description:闹钟列表界面
 */

public class AlarmListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.alarm_list)
    RecyclerView recyclerView;
    @BindView(R.id.alarm_add_iv)
    ImageView alarmAddIv;

    private AlarmClockAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_alarm_list;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AlarmClockAdapter(R.layout.item_alarm);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void business() {

    }

    @OnClick({R.id.alarm_add_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.alarm_add_iv:
                Intent intent = new Intent(AlarmListActivity.this, AlarmAddActivity.class);
                startActivity(intent);
                break;
        }
    }
}
