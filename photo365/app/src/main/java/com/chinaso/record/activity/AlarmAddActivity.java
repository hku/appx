package com.chinaso.record.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chinaso.record.R;
import com.chinaso.record.adapter.DateArrayAdapter;
import com.chinaso.record.base.BaseActivity;
import com.chinaso.record.widget.wheelview.WheelView;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/6/25 11:24
 * description:添加闹钟界面
 */

public class AlarmAddActivity extends BaseActivity {

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

    private String[] hours = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    private String[] mimutes = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};


    private int nowDay;
    private int nowHour;
    private int nowMinute;
    private int nowMonth;
    private int nowYear;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_alarm_add;
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
        getNowdate();
        initTimeView();
    }

    /**
     * 初始化小时分钟 wheelview
     */
    private void initTimeView() {
        this.hourWheelView.setViewAdapter(new DateArrayAdapter(this, this.hours, this.nowHour));
        this.minuteWheelView.setViewAdapter(new DateArrayAdapter(this, this.mimutes, this.nowMinute));
        this.hourWheelView.setCurrentItem(this.nowHour);
        this.minuteWheelView.setCurrentItem(this.nowMinute);
        this.hourWheelView.setCyclic(true);
        this.minuteWheelView.setCyclic(true);
    }

    @Override
    protected void business() {

    }

    /**
     * 获取当前的日期
     */
    public void getNowdate() {
        Calendar ca = Calendar.getInstance(Locale.CHINA);
        this.nowYear = ca.get(Calendar.YEAR);
        this.nowMonth = ca.get(Calendar.MONTH) + 1;
        this.nowDay = ca.get(Calendar.DAY_OF_MONTH);
        this.nowHour = ca.get(Calendar.HOUR_OF_DAY);
        this.nowMinute = ca.get(Calendar.MINUTE);
    }

    @OnClick({R.id.cycle_llyt, R.id.how_warn_llyt})
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
                        0,
                        new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
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
                                bellTv.setText(text.toString());
                                return true;
                            }
                        })
                .positiveText(R.string.ok)
                .show();
    }
}
