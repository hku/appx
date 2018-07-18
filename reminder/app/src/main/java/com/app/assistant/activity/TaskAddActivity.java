package com.app.assistant.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.assistant.R;
import com.app.assistant.base.BaseActivity;
import com.app.assistant.entity.TaskEntity;
import com.app.assistant.utils.Constant;
import com.app.assistant.utils.TaskDaoManager;
import com.app.assistant.utils.TimeUtils;
import com.app.assistant.utils.ToastUtils;
import com.app.assistant.widget.TaskCalendarDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/7/14 19:43
 * description:任务添加界面
 */

public class TaskAddActivity extends BaseActivity {

    private static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.date_tv)
    TextView dateTv;
    @BindView(R.id.pre_tv)
    TextView preTv;
    @BindView(R.id.title_et)
    EditText titleEt;

    private boolean mIsPreCheck;
    private TaskEntity mTaskEntity;


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
        mTaskEntity = (TaskEntity) getIntent().getSerializableExtra(Constant.DELIVER_TAG);
        if (mTaskEntity != null) {
            String title = mTaskEntity.getTitle();
            String date = mTaskEntity.getDate();
            String preDate = mTaskEntity.getPreDate();
            if (!TextUtils.isEmpty(preDate)) {
                preTv.setVisibility(View.VISIBLE);
                mIsPreCheck = true;
            } else {
                preTv.setVisibility(View.GONE);
            }
            titleEt.setText(title);
            dateTv.setText(date);
        } else {
            String formatNowDate = TimeUtils.getNowString(DEFAULT_FORMAT);
            dateTv.setText(formatNowDate);
        }
    }

    @OnClick({R.id.date_layout, R.id.clock_add_tv, R.id.task_add_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.date_layout:
                new TaskCalendarDialog(this, new TaskCalendarDialog.CalendarDialogCallBack() {
                    @Override
                    public void onDialogClickConfirm(String selectedDateS, boolean isPreCheck) {
                        dateTv.setText(selectedDateS);
                        mIsPreCheck = isPreCheck;
                        if (isPreCheck) {
                            preTv.setVisibility(View.VISIBLE);
                        } else {
                            preTv.setVisibility(View.GONE);
                        }
                    }
                }).show();
                break;
            case R.id.clock_add_tv:
                Intent clockAddIntent = new Intent(TaskAddActivity.this, ClockAddActivity.class);
                startActivity(clockAddIntent);
                break;
            case R.id.task_add_iv:
                onSave();
                break;
        }
    }

    /**
     * save
     */
    private void onSave() {
        String titleS = titleEt.getText().toString();
        if (TextUtils.isEmpty(titleS)) {
            ToastUtils.show(this, getResources().getString(R.string.activity_task_save_title_tip));
            return;
        }
        String dateS = dateTv.getText().toString();
        if (TextUtils.isEmpty(dateS)) {
            ToastUtils.show(this, getResources().getString(R.string.activity_task_save_date_tip));
            return;
        }
        String preDate = "";
        if (mIsPreCheck) {
            //目前是默认向前提前三天
            Date selectedDate = TimeUtils.StringToDate(dateS);
            Date beforeDate = TimeUtils.getDateBefore(selectedDate, 3);
            preDate = DEFAULT_FORMAT.format(beforeDate);
        }
        if (mTaskEntity != null) {
            mTaskEntity.setTitle(titleS);
            mTaskEntity.setPreDate(preDate);
            mTaskEntity.setDate(dateS);
            mTaskEntity.setStatus(false);
            TaskDaoManager.getInstance().update(mTaskEntity);
            Intent intent = new Intent();
            intent.putExtra("back", mTaskEntity);
            setResult(RESULT_OK, intent);
        } else {
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setTitle(titleS);
            taskEntity.setDate(dateS);
            taskEntity.setStatus(false);
            TaskDaoManager.getInstance().insert(taskEntity);
            setResult(RESULT_OK);
        }
        finish();
    }
}
