package com.app.assistant.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.assistant.R;
import com.app.assistant.adapter.AlarmClockAdapter;
import com.app.assistant.base.BaseActivity;
import com.app.assistant.entity.AlarmEntity;
import com.app.assistant.entity.MessageEvent;
import com.app.assistant.utils.AlarmDaoManager;
import com.app.assistant.utils.AlarmManagerUtil;
import com.app.assistant.utils.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/6/25 10:20
 * description:闹钟列表界面
 */

public class ClockListActivity extends BaseActivity {

    private static final int REQUEST_CODE = 10010;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.alarm_list)
    RecyclerView recyclerView;
    @BindView(R.id.clock_add_btn)
    FloatingActionButton clockAddBtn;

    private AlarmClockAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_clock_list;
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
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new AlarmClockAdapter(R.layout.item_alarm, this);
        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final BaseQuickAdapter adapter, View view, final int position) {
                new MaterialDialog.Builder(ClockListActivity.this)
                        .items(R.array.alarmLongClicks)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int dialogPosition, CharSequence text) {
                                switch (dialogPosition) {
                                    case 0:
                                        //删除操作
                                        //删除数据库数据
                                        AlarmEntity item = (AlarmEntity) adapter.getItem(position);
                                        AlarmDaoManager.getInstance().remove(item);
                                        //修改数据源数据
                                        adapter.remove(position);
                                        AlarmManagerUtil.cancelAlarmClock(ClockListActivity.this, item.getId().intValue());
                                        break;
                                }
                            }
                        })
                        .show();
                return true;
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view instanceof SwitchCompat) {
                    List<AlarmEntity> data = mAdapter.getData();
                    AlarmEntity alarmEntity = data.get(position);
                    SwitchCompat switchCompat = (SwitchCompat) view;
                    boolean isChecked = switchCompat.isChecked();
                    alarmEntity.setIsOpen(isChecked);
                    AlarmDaoManager.getInstance().update(alarmEntity);
                    if (isChecked) {//打开闹钟
                        AlarmManagerUtil.setAlarm(ClockListActivity.this, alarmEntity);
                    } else {//关闭闹钟
                        AlarmManagerUtil.cancelAlarmClock(ClockListActivity.this, alarmEntity.getId().intValue());
                    }
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void business() {
        List<AlarmEntity> list = AlarmDaoManager.getInstance().queryDesc();
        mAdapter.addData(list);
    }

    @OnClick({R.id.clock_add_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clock_add_btn:
                Intent intent = new Intent(ClockListActivity.this, ClockAddActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    /**
     * 闹钟不响相关情况说明
     */
    private void showAlarmExplain() {
        boolean isShow = SPUtils.getInstance().getBoolean("explain_key", true);
        if (isShow) {
            new MaterialDialog.Builder(this)
                    .title(R.string.warm_tips_title)
                    .content(R.string.warm_tips_detail)
                    .positiveText("确定")
                    .negativeText("不再提示")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            SPUtils.getInstance().put("explain_key", false);
                        }
                    })
                    .show();
        }
    }

    @Override
    protected void onEvent(MessageEvent event) {
        super.onEvent(event);
        int id = event.getId();
        if (id == MessageEvent.IdPool.ALARM_ID) {
            AlarmEntity entity = (AlarmEntity) event.getObject();
            mAdapter.refreshItem(entity);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE://闹钟添加成功
                    List<AlarmEntity> list = AlarmDaoManager.getInstance().queryDesc();
                    mAdapter.clear();
                    mAdapter.addData(list);
                    showAlarmExplain();
                    break;
            }
        }
    }
}
