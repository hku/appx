package com.app.assistant.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.assistant.R;
import com.app.assistant.activity.ClockAddActivity;
import com.app.assistant.activity.ClockListActivity;
import com.app.assistant.activity.HelpActivity;
import com.app.assistant.activity.MemoAddActivity;
import com.app.assistant.activity.TaskAddActivity;
import com.app.assistant.activity.TaskListActivity;
import com.app.assistant.adapter.HomeTaskAdapter;
import com.app.assistant.base.BaseFragment;
import com.app.assistant.entity.AlarmEntity;
import com.app.assistant.entity.MessageEvent;
import com.app.assistant.entity.TaskEntity;
import com.app.assistant.utils.AlarmDaoManager;
import com.app.assistant.utils.PreferenceKeyConstant;
import com.app.assistant.utils.SPUtils;
import com.app.assistant.utils.TaskDaoManager;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * author: zhanghe
 * created on: 2018/7/13 9:39
 * description: home fragment
 */

public class HomeFragment extends BaseFragment {


    @BindView(R.id.level_iv)
    ImageView levelIv;
    @BindView(R.id.task_list)
    RecyclerView taskList;
    @BindView(R.id.task_tip)
    TextView taskTipTv;
    @BindView(R.id.clock_tv)
    TextView clockTv;
    @BindView(R.id.clock_layout)
    RelativeLayout clockRLayout;
    @BindView(R.id.task_layout)
    LinearLayout taskLLayout;
    @BindView(R.id.memo_layout)
    LinearLayout memoLLayout;


    private HomeTaskAdapter mHomeTaskAdapter;
    private FragmentManager mFragmentManager;

    @Override
    protected void initData() {
        super.initData();
        mFragmentManager = getChildFragmentManager();
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
                                if (mHomeTaskAdapter.getData().size() <= 0) {
                                    taskTipTv.setText(getResources().getString(R.string.fragment_home_task_none_tip));
                                }
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
        HomeMemoFragment memoFragment = HomeMemoFragment.newInstance("memo");
        mFragmentManager.beginTransaction().replace(R.id.memo_layout, memoFragment).commit();
        initHomeShow();
        initClock();
        initTask();
    }

    /**
     * init 首页要显示的模块
     */
    private void initHomeShow() {
        boolean memoChecked = SPUtils.getInstance().getBoolean(
                PreferenceKeyConstant.HOME_MEMO_SHOW_KEY, true);
        if (memoChecked) {
            memoLLayout.setVisibility(View.VISIBLE);
        } else {
            memoLLayout.setVisibility(View.GONE);
        }
        boolean clockChecked = SPUtils.getInstance().getBoolean(
                PreferenceKeyConstant.HOME_CLOCK_SHOW_KEY, true);
        if (clockChecked) {
            clockRLayout.setVisibility(View.VISIBLE);
        } else {
            clockRLayout.setVisibility(View.GONE);
        }
        boolean taskChecked = SPUtils.getInstance().getBoolean(
                PreferenceKeyConstant.HOME_TASK_SHOW_KEY, true);
        if (taskChecked) {
            taskLLayout.setVisibility(View.VISIBLE);
        } else {
            taskLLayout.setVisibility(View.GONE);
        }
    }

    /**
     * init clock
     */
    private void initClock() {
        AlarmEntity closestEntity = AlarmDaoManager.getInstance().getClosestClock();
        if (closestEntity != null) {
            int hour = closestEntity.getHour();
            int minute = closestEntity.getMinute();
            String tip = "您" + hour + "时" + minute + "分" + "的闹钟即将响铃";
            clockTv.setText(tip);
        } else {
            clockTv.setText(getResources().getString(R.string.fragment_home_clock_none_tip));
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

    @OnClick({R.id.level_iv, R.id.clock_list_iv, R.id.task_list_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.level_iv:
                showMenu();
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
                        Intent memoIntent = new Intent(mContext, MemoAddActivity.class);
                        startActivity(memoIntent);
                        break;
                    case R.id.option_clock:
                        Intent clockIntent = new Intent(mContext, ClockAddActivity.class);
                        startActivity(clockIntent);
                        break;
                    case R.id.option_task:
                        Intent taskIntent = new Intent(mContext, TaskAddActivity.class);
                        startActivity(taskIntent);
                        break;
                    case R.id.option_help:
                        Intent helpIntent = new Intent(mContext, HelpActivity.class);
                        startActivity(helpIntent);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
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
        } else if (id == MessageEvent.IdPool.HOME_CLOCK_UPDATE_ID) {
            AlarmEntity closestEntity = AlarmDaoManager.getInstance().getClosestClock();
            if (closestEntity != null) {
                int hour = closestEntity.getHour();
                int minute = closestEntity.getMinute();
                String tip = "您" + hour + "时" + minute + "分" + "的闹钟即将响铃";
                clockTv.setText(tip);
            } else {
                clockTv.setText(getResources().getString(R.string.fragment_home_clock_none_tip));
            }
        } else if (id == MessageEvent.IdPool.HOME_CLOCK_SHOW) {
            boolean isClockChecked = (boolean) event.getObject();
            if (isClockChecked) {
                clockRLayout.setVisibility(View.VISIBLE);
            } else {
                clockRLayout.setVisibility(View.GONE);
            }
        } else if (id == MessageEvent.IdPool.HOME_TASK_SHOW) {
            boolean isTaskChecked = (boolean) event.getObject();
            if (isTaskChecked) {
                taskLLayout.setVisibility(View.VISIBLE);
            } else {
                taskLLayout.setVisibility(View.GONE);
            }
        } else if (id == MessageEvent.IdPool.HOME_MEMO_SHOW) {
            boolean isMemoChecked = (boolean) event.getObject();
            if (isMemoChecked) {
                memoLLayout.setVisibility(View.VISIBLE);
            } else {
                memoLLayout.setVisibility(View.GONE);
            }
        } else if (id == MessageEvent.IdPool.HOME_MEMO_FLIP_LEFT) {
            mFragmentManager.beginTransaction().replace(R.id.memo_layout, MemoTagFragment.newInstance()).commit();
        } else if (id == MessageEvent.IdPool.HOME_MEMO_FLIP_RIGHT) {
            String tags = (String) event.getObject();
            mFragmentManager.beginTransaction().replace(R.id.memo_layout, HomeMemoFragment.newInstance(tags)).commit();
        }
    }
}
