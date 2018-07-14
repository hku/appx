package com.app.assistant.activity;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.app.assistant.R;
import com.app.assistant.adapter.TaskAdapter;
import com.app.assistant.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/7/13 16:41
 * description: task list
 */

public class TaskListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private TaskAdapter mAdapter;


    @Override
    protected void initView() {
        super.initView();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new TaskAdapter(R.layout.item_task);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_task_list;
    }

    @Override
    protected void business() {

    }

    @OnClick({R.id.task_add_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.task_add_btn:
                Intent intent = new Intent(TaskListActivity.this, TaskAddActivity.class);
                startActivity(intent);
                break;
        }
    }
}
