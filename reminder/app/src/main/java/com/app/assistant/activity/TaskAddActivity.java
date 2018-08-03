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
import com.app.assistant.entity.MessageEvent;
import com.app.assistant.entity.TaskEntity;
import com.app.assistant.utils.CommonUtils;
import com.app.assistant.utils.Constant;
import com.app.assistant.utils.TaskDaoManager;
import com.app.assistant.utils.TimeUtils;
import com.app.assistant.utils.ToastUtils;
import com.app.assistant.widget.TaskCalendarDialog;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.ParseException;
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
            titleEt.setSelection(titleEt.getText().length());
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
        String preDateS = "";
        if (mIsPreCheck) {
            //目前是默认向前提前三天
            Date selectedDate = TimeUtils.StringToDate(dateS);
            Date beforeDate = TimeUtils.getDateBefore(selectedDate, 3);
            preDateS = DEFAULT_FORMAT.format(beforeDate);
        }
        boolean isEffective = false;
        if (mTaskEntity != null) {
            mTaskEntity.setTitle(titleS);
            mTaskEntity.setPreDate(preDateS);
            mTaskEntity.setDate(dateS);
            mTaskEntity.setStatus(false);
            TaskDaoManager.getInstance().update(mTaskEntity);
            Intent intent = new Intent();
            intent.putExtra("cabBack", mTaskEntity);
            setResult(RESULT_OK, intent);
            isEffective = isEffectiveDate(preDateS, dateS);
        } else {
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setTitle(titleS);
            taskEntity.setDate(dateS);
            taskEntity.setStatus(false);
            taskEntity.setPreDate("");
            TaskDaoManager.getInstance().insert(taskEntity);
            setResult(RESULT_OK);
            isEffective = isEffectiveDate(preDateS, dateS);
        }
        if (isEffective) {
            MessageEvent event = new MessageEvent();
            event.setId(MessageEvent.IdPool.HOME_TASK_UPDATE_ID);
            EventBus.getDefault().post(event);
        }
        finish();
    }

    private boolean isEffectiveDate(String preDateS, String dateS) {
        Date preDate = null;
        Date date = null;
        Date today = null;
        boolean isEffective = false;
        try {
            date = DEFAULT_FORMAT.parse(dateS);
            String todayS = DEFAULT_FORMAT.format(new Date());
            today = DEFAULT_FORMAT.parse(todayS);
        } catch (ParseException px) {
            px.printStackTrace();
        }
        //该条数据是提前3天提醒
        if (!TextUtils.isEmpty(preDateS)) {
            try {
                preDate = DEFAULT_FORMAT.parse(preDateS);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            isEffective = CommonUtils.isEffectiveDate(today, preDate, date);
        } else {
            isEffective = CommonUtils.isEffectiveDate(today, date);
        }
        return isEffective;
    }
}
