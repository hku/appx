package com.app.assistant.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.assistant.R;
import com.app.assistant.activity.ClockListActivity;
import com.app.assistant.activity.MemoAddActivity;
import com.app.assistant.activity.MemoListActivity;
import com.app.assistant.activity.TaskListActivity;
import com.app.assistant.adapter.HomeTaskAdapter;
import com.app.assistant.base.BaseFragment;
import com.app.assistant.entity.AlarmEntity;
import com.app.assistant.entity.MessageEvent;
import com.app.assistant.entity.ReminderEntity;
import com.app.assistant.entity.TaskEntity;
import com.app.assistant.utils.AlarmDaoManager;
import com.app.assistant.utils.Constant;
import com.app.assistant.utils.ReminderDaoManager;
import com.app.assistant.utils.TaskDaoManager;
import com.app.assistant.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * author: zhanghe
 * created on: 2018/7/13 9:39
 * description: home fragment
 */

public class HomeFragment extends BaseFragment {

    private static final int REQUEST = 10010;

    @BindView(R.id.level_iv)
    ImageView levelIv;
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.tag_tv)
    TextView tagTv;
    @BindView(R.id.refresh_iv)
    ImageView refreshIv;
    @BindView(R.id.edit_iv)
    ImageView editIv;
    @BindView(R.id.memo_list_iv)
    ImageView memoListIv;
    @BindView(R.id.task_list)
    RecyclerView taskList;
    @BindView(R.id.task_tip)
    TextView taskTipTv;
    @BindView(R.id.clock_tv)
    TextView clockTv;

    private ReminderEntity mReminderEntity;

    private long mClickTime = -1L;

    private HomeTaskAdapter mHomeTaskAdapter;

    @Override
    protected void initData() {
        super.initData();
        mReminderEntity = ReminderDaoManager.getInstance().getRandomItem();
    }

    @Override
    protected void initView() {
        super.initView();
        taskList.setLayoutManager(new LinearLayoutManager(mContext));
        mHomeTaskAdapter = new HomeTaskAdapter(R.layout.item_home_task);
        taskList.setAdapter(mHomeTaskAdapter);
        mHomeTaskAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                if (view instanceof AppCompatCheckBox) {
                    AppCompatCheckBox checkBox = (AppCompatCheckBox) view;
                    boolean isCheck = checkBox.isChecked();
                    if (isCheck) {
                        TaskEntity clickEntity = mHomeTaskAdapter.getItem(position);
                        clickEntity.setStatus(true);
                        TaskDaoManager.getInstance().update(clickEntity);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mHomeTaskAdapter.remove(position);
                            }
                        }, 500);
                    }
                }
            }
        });
    }

    @Override
    protected int getViewLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void business() {
        initMemo();
        initClock();
        initTask();
    }

    /**
     * init memo
     */
    private void initMemo() {
        String content = "";
        String tagS = "";
        if (mReminderEntity != null) {
            content = mReminderEntity.getContent();
            tagS = mReminderEntity.getTagS();
        } else {
            content = "暂无更多内容";
            tagS = "暂无标签";
        }
        contentTv.setText(content);
        tagTv.setText(tagS);
    }

    /**
     * init clock
     */
    private void initClock() {
        AlarmEntity closestEntity = AlarmDaoManager.getInstance().getClosestClock();
        if (closestEntity != null) {
            clockTv.setText(closestEntity.getTitle());
        } else {
            clockTv.setText("dasdadsdadsadad");
        }
    }

    /**
     * init task
     */
    private void initTask() {
        List<TaskEntity> todayTaskList = TaskDaoManager.getInstance().getTodayTask();
        if (todayTaskList.size() <= 0) {
            taskTipTv.setText(getResources().getString(R.string.fragment_home_task_none_tip));
        }
        mHomeTaskAdapter.addData(todayTaskList);
    }

    @OnClick({R.id.level_iv, R.id.refresh_iv, R.id.edit_iv, R.id.content_tv,
            R.id.memo_list_iv, R.id.clock_list_iv, R.id.task_list_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.level_iv:
                showMenu();
                break;
            case R.id.refresh_iv:
                refresh();
                break;
            case R.id.edit_iv:
                if (mReminderEntity != null) {
                    Intent intentAdd = new Intent(mContext, MemoAddActivity.class);
                    intentAdd.putExtra(Constant.DELIVER_TAG, mReminderEntity);
                    this.startActivityForResult(intentAdd, REQUEST);
                } else {
                    ToastUtils.show(mContext, "当前无数据，暂不可修改，请可尝试刷新");
                }
                break;
            case R.id.content_tv:
                doubleClickRefresh();
                break;
            case R.id.memo_list_iv:
                Intent memoIntent = new Intent(mContext, MemoListActivity.class);
                startActivity(memoIntent);
                break;
            case R.id.clock_list_iv:
                Intent clockIntent = new Intent(mContext, ClockListActivity.class);
                startActivity(clockIntent);
                break;
            case R.id.task_list_iv:
                Intent taskIntent = new Intent(mContext, TaskListActivity.class);
                startActivity(taskIntent);
                break;
        }
    }

    /**
     * show menu
     */
    private void showMenu() {
        PopupMenu popupMenu = new PopupMenu(mContext, levelIv);
        popupMenu.getMenuInflater().inflate(R.menu.home, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.option_memo:
                        Intent memoIntent = new Intent(mContext, MemoListActivity.class);
                        startActivity(memoIntent);
                        break;
                    case R.id.option_clock:
                        Intent clockIntent = new Intent(mContext, ClockListActivity.class);
                        startActivity(clockIntent);
                        break;
                    case R.id.option_task:
                        Intent taskIntent = new Intent(mContext, TaskListActivity.class);
                        startActivity(taskIntent);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    /**
     * double click to refresh
     */
    private void doubleClickRefresh() {
        if (System.currentTimeMillis() - mClickTime < 800) {
            refresh();
        } else {
            mClickTime = System.currentTimeMillis();
        }
    }

    /**
     * refresh
     */
    private void refresh() {
        if (mReminderEntity != null) {
            mReminderEntity = ReminderDaoManager.getInstance().getRandomItem(mReminderEntity);
            business();
        } else {
            mReminderEntity = ReminderDaoManager.getInstance().getRandomItem();
            business();
        }
    }

    @Override
    protected void messageEvent(MessageEvent event) {
        super.messageEvent(event);
        int id = event.getId();
        if (id == MessageEvent.IdPool.HOME_TASK_UPDATE_ID) {
            mHomeTaskAdapter.clear();
            List<TaskEntity> todayTaskList = TaskDaoManager.getInstance().getTodayTask();
            if (todayTaskList.size() <= 0) {
                taskTipTv.setText(getResources().getString(R.string.fragment_home_task_none_tip));
            } else {
                taskTipTv.setVisibility(View.GONE);
            }
            mHomeTaskAdapter.addData(todayTaskList);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST:
                    if (mReminderEntity != null) {
                        Long id = mReminderEntity.getId();
                        mReminderEntity = ReminderDaoManager.getInstance().queryById(id);
                        business();
                    }
                    break;
            }
        }
    }
}
