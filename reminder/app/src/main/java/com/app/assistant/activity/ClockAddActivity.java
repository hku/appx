package com.app.assistant.activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.app.assistant.R;
import com.app.assistant.adapter.DateArrayAdapter;
import com.app.assistant.base.BaseActivity;
import com.app.assistant.entity.AlarmEntity;
import com.app.assistant.entity.MessageEvent;
import com.app.assistant.utils.AlarmDaoManager;
import com.app.assistant.utils.AlarmManagerUtil;
import com.app.assistant.utils.ToastUtils;
import com.app.assistant.widget.wheelview.WheelView;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/6/25 11:24
 * description:添加闹钟界面
 */

public class ClockAddActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.hour)
    WheelView hourWheelView;
    @BindView(R.id.minute)
    WheelView minuteWheelView;
    @BindView(R.id.cycle_llyt)
    LinearLayout cycleLLyt;
    @BindView(R.id.cycle_tv)
    TextView cycleTv;
    @BindView(R.id.how_warn_tv)
    TextView bellTv;
    @BindView(R.id.title_et)
    EditText titleEt;
    @BindView(R.id.remark_et)
    EditText remarkEt;

    private static final String[] hours = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    private static final String[] minutes = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};


    //当前小时
    private int mHour;
    //当前分钟
    private int mMinute;

    //响铃周期标志位 0 : 每天 ， -1：只响一次 1：周一至周五  2：周末
    private int mCycleTag = -100;
    //响铃周期
    private String mCycleWeeks = "";
    //响铃方式
    private int mBell = -100;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_clock_add;
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
        getNowDate();
        initTimeView();
    }

    /**
     * 初始化小时分钟 wheelview
     */
    private void initTimeView() {
        this.hourWheelView.setViewAdapter(new DateArrayAdapter(this, this.hours, this.mHour));
        this.minuteWheelView.setViewAdapter(new DateArrayAdapter(this, this.minutes, this.mMinute));
        this.hourWheelView.setCurrentItem(this.mHour);
        this.minuteWheelView.setCurrentItem(this.mMinute);
        this.hourWheelView.setCyclic(true);
        this.minuteWheelView.setCyclic(true);
    }

    @Override
    protected void business() {

    }

    /**
     * 获取当前的日期
     */
    public void getNowDate() {
        Calendar ca = Calendar.getInstance(Locale.CHINA);
        this.mHour = ca.get(Calendar.HOUR_OF_DAY);
        this.mMinute = ca.get(Calendar.MINUTE);
    }

    @OnClick({R.id.cycle_llyt, R.id.how_warn_llyt, R.id.alarm_add_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            //响铃周期
            case R.id.cycle_llyt:
                showCycleDialog();
                break;
            //响铃方式
            case R.id.how_warn_llyt:
                showRingBellDialog();
                break;
            //确定添加
            case R.id.alarm_add_iv:
                onSave();
                break;
        }
    }

    /**
     * 显示响铃周期选择框
     */
    private void showCycleDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.activity_alarm_clock_cycle)
                .items(R.array.cycle)
                .itemsCallbackSingleChoice(
                        3,
                        new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView,
                                                       int which, CharSequence text) {
                                switch (which) {
                                    case 0:
                                        mCycleTag = 0;//每天
                                        break;
                                    case 1:
                                        mCycleTag = 1;//周一至周五
                                        mCycleWeeks = "1,2,3,4,5";
                                        break;
                                    case 2:
                                        mCycleTag = 2;//周末
                                        mCycleWeeks = "6,7";
                                        break;
                                    case 3:
                                        mCycleTag = -1;//响铃一次
                                        break;
                                }
                                cycleTv.setText(text.toString());
                                return true;
                            }
                        })
                .positiveText(R.string.ok)
                .show();
    }


    /**
     * 显示响铃方式选择框
     */
    private void showRingBellDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.activity_alarm_clock_how_warn)
                .items(R.array.bell)
                .itemsCallbackSingleChoice(
                        0,
                        new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                switch (which) {
                                    case 0:
                                        mBell = 0;//振动
                                        break;
                                    case 1:
                                        mBell = 1;//响铃
                                        break;
                                    case 2:
                                        mBell = 2;//振动并响铃
                                        break;
                                }
                                bellTv.setText(text.toString());
                                return true;
                            }
                        })
                .positiveText(R.string.ok)
                .show();
    }

    /**
     * 保存
     */
    private void onSave() {
        //小时
        mHour = hourWheelView.getCurrentItem();
        //分钟
        mMinute = minuteWheelView.getCurrentItem();
        //闹钟标题
        String title = titleEt.getText().toString();
        if (TextUtils.isEmpty(title)) {
            title = getResources().getString(R.string.activity_alarm_clock_title_hint);
        }
        //响铃周期
        if (mCycleTag == -100) {
            ToastUtils.show(this, "请选择响铃周期");
            return;
        }
        //响铃方式
        if (mBell == -100) {
            ToastUtils.show(this, "请选择响铃方式");
            return;
        }
        //闹钟备注
        String remark = remarkEt.getText().toString();
        //插入表
        AlarmEntity alarmEntity = new AlarmEntity();
        alarmEntity.setHour(mHour);
        alarmEntity.setMinute(mMinute);
        alarmEntity.setTitle(title);
        alarmEntity.setIsOpen(true);
        alarmEntity.setBellMode(mBell);
        alarmEntity.setCycleTag(mCycleTag);
        alarmEntity.setCycleWeeks(mCycleWeeks);
        alarmEntity.setBellMode(mBell);
        alarmEntity.setRemark(remark);
        long clockId = AlarmDaoManager.getInstance().insert(alarmEntity);
        alarmEntity.setId(clockId);
        //设置闹钟
        AlarmManagerUtil.setAlarm(this, alarmEntity);
        setResult(RESULT_OK);
        MessageEvent event = new MessageEvent();
        event.setId(MessageEvent.IdPool.HOME_CLOCK_UPDATE_ID);
        EventBus.getDefault().post(event);
        finish();
    }
}
