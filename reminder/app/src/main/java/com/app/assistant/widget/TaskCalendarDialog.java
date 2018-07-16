package com.app.assistant.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.app.assistant.R;
import com.app.assistant.utils.TimeUtils;
import com.app.assistant.utils.ToastUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author: zhanghe
 * created on: 2018/7/16 10:26
 * description:
 */

public class TaskCalendarDialog extends AppCompatDialog implements OnDateSelectedListener {

    private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private CalendarDialogCallBack mCalendarDialogCallBack;
    private Context mContext;
    private String mSelectedDate = "";
    private boolean isPreCheck = false;

    public TaskCalendarDialog(Context context, TaskCalendarDialog.CalendarDialogCallBack calendarDialogCallBack) {
        super(context);
        this.mContext = context;
        this.mCalendarDialogCallBack = calendarDialogCallBack;
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_calendar_task);
        setCancelable(true);
        MaterialCalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setSelectedDate(TimeUtils.getNowDate());
        CalendarDay selectedDay = calendarView.getSelectedDate();
        mSelectedDate = FORMAT.format(selectedDay.getDate());
        calendarView.setOnDateChangedListener(this);
        final AppCompatCheckBox preDayCheckBox = findViewById(R.id.pre_cb);
        TextView okTv = findViewById(R.id.ok_tv);
        okTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = preDayCheckBox.isChecked();
                if (TextUtils.isEmpty(mSelectedDate)) {
                    ToastUtils.show(mContext, mContext.getResources().getString(R.string.dialog_task_date_select_tip));
                    return;
                }
                mCalendarDialogCallBack.onDialogClickConfirm(mSelectedDate, isChecked);
                dismiss();
            }
        });
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
        mSelectedDate = FORMAT.format(calendarDay.getDate());
    }

    public interface CalendarDialogCallBack {
        void onDialogClickConfirm(String selectedDateS, boolean isPreCheck);
    }
}
