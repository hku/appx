package com.app.assistant.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.app.assistant.R;
import com.app.assistant.adapter.TaskAdapter;
import com.app.assistant.base.BaseActivity;
import com.app.assistant.entity.MessageEvent;
import com.app.assistant.entity.TaskEntity;
import com.app.assistant.utils.Constant;
import com.app.assistant.utils.TaskDaoManager;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/7/13 16:41
 * description: task list
 */

public class TaskListActivity extends BaseActivity {

    private static final int REQUEST_CODE_ADD = 10010;
    private static final int REQUEST_CODE_UPDATE = 10011;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private TaskAdapter mAdapter;

    private int mClickPosition = -1;


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

    /**
     * init recyclerView
     */
    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TaskAdapter(R.layout.item_task);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view instanceof Button) {
                    List<TaskEntity> data = mAdapter.getData();
                    if (data != null) {
                        TaskEntity entity = data.get(position);
                        TaskDaoManager.getInstance().del(entity);
                        mAdapter.remove(position);
                    }
                    refreshHomeTask();
                } else if (view instanceof RelativeLayout) {
                    mClickPosition = position;
                    TaskEntity entity = mAdapter.getItem(position);
                    boolean isFinished = entity.getStatus();
                    if (isFinished) {
                        entity.setStatus(!isFinished);
                        TaskDaoManager.getInstance().update(entity);
                        mAdapter.set(position, entity);
                        refreshHomeTask();
                    } else {
                        Intent intent = new Intent(TaskListActivity.this, TaskAddActivity.class);
                        intent.putExtra(Constant.DELIVER_TAG, entity);
                        startActivityForResult(intent, REQUEST_CODE_UPDATE);
                    }
                }
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_task_list;
    }

    @Override
    protected void business() {
        List<TaskEntity> data = TaskDaoManager.getInstance().queryAllData();
        mAdapter.addData(data);
    }

    @OnClick({R.id.task_add_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.task_add_btn:
                Intent intent = new Intent(TaskListActivity.this, TaskAddActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
                break;
        }
    }

    private void refreshHomeTask() {
        MessageEvent event = new MessageEvent();
        event.setId(MessageEvent.IdPool.HOME_TASK_UPDATE_ID);
        EventBus.getDefault().post(event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ADD:
                    mAdapter.clear();
                    business();
                    break;
                case REQUEST_CODE_UPDATE:
                    if (mClickPosition != -1) {
                        TaskEntity taskEntity = (TaskEntity) data.getSerializableExtra("cabBack");
                        mAdapter.set(mClickPosition, taskEntity);
                    }
                    refreshHomeTask();
                    break;
            }
        }
    }
}
