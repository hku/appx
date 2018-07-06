package com.app.reminder.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.app.reminder.R;
import com.app.reminder.adapter.ShowAdapter;
import com.app.reminder.base.BaseActivity;
import com.app.reminder.entity.ReminderEntity;
import com.app.reminder.utils.Constant;
import com.app.reminder.utils.ReminderDaoManager;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/7/5 14:00
 * description:显示全部界面
 */

public class ShowActivity extends BaseActivity {

    private static final int REQUEST_CODE_ADD = 10010;
    private static final int REQUEST_CODE_UPDATE = 10011;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.add_btn)
    FloatingActionButton addBtn;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private ShowAdapter mAdapter;

    private int mClickPosition = -1;

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
        initRecycleView();
    }

    /**
     * init recyclerView
     */
    private void initRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ShowAdapter(R.layout.item_show);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view instanceof Button) {
                    List<ReminderEntity> data = mAdapter.getData();
                    if (data != null) {
                        ReminderEntity entity = data.get(position);
                        ReminderDaoManager.getInstance().del(entity);
                        mAdapter.remove(position);
                    }
                } else if (view instanceof RelativeLayout) {
                    mClickPosition = position;
                    ReminderEntity entity = mAdapter.getItem(position);
                    Intent intent = new Intent(ShowActivity.this, AddActivity.class);
                    intent.putExtra(Constant.DELIVER_TAG, entity);
                    startActivityForResult(intent, REQUEST_CODE_UPDATE);
                }
            }
        });
    }

    @Override
    protected void business() {
        List<ReminderEntity> data = ReminderDaoManager.getInstance().queryAllData();
        mAdapter.addData(data);
    }

    @OnClick(R.id.add_btn)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_btn:
                Intent intent = new Intent(ShowActivity.this, AddActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
                break;
        }
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
                        ReminderEntity reminderEntity = (ReminderEntity) data.getSerializableExtra("back");
                        mAdapter.set(mClickPosition, reminderEntity);
                    }
                    break;
            }
        }
    }
}
