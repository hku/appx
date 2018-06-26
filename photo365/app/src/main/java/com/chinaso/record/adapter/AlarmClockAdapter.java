package com.chinaso.record.adapter;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chinaso.record.R;
import com.chinaso.record.entity.AlarmEntity;
import com.chinaso.record.utils.AlarmDaoManager;
import com.chinaso.record.utils.AlarmManagerUtil;

import java.util.ArrayList;

/**
 * author: zhanghe
 * created on: 2018/6/25 10:39
 * description: 闹钟列表数据源
 */

public class AlarmClockAdapter extends BaseQuickAdapter<AlarmEntity, BaseViewHolder> {

    private Context mContext;

    public AlarmClockAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.mContext = context;
    }

    public void clear() {
        setNewData(new ArrayList<AlarmEntity>());
    }

    @Override
    protected void convert(BaseViewHolder helper, final AlarmEntity item) {
        //标题
        helper.setText(R.id.title, item.getTitle());
        //响铃周期 + 时间
        int cycleTag = item.getCycleTag();
        String cycleS = "";
        switch (cycleTag) {
            case 0://每天一次
                cycleS = "每天一次";
                break;
            case -1://只响一次
                cycleS = "只响一次";
                break;
            case 1://周一至周五
                cycleS = "周一至周五";
                break;
            case 2://周末
                cycleS = "周末";
                break;
        }
        int hour = item.getHour();
        int minute = item.getMinute();
        helper.setText(R.id.cycle, cycleS + " " + hour + ":" + minute);
        //是否打开
        boolean isOpen = item.getIsOpen();
        SwitchCompat switchCompat = helper.getView(R.id.switch_sv);
        if (isOpen) {
            switchCompat.setChecked(true);
        } else {
            switchCompat.setChecked(false);
        }
        helper.addOnClickListener(R.id.switch_sv);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setIsOpen(isChecked);
                AlarmDaoManager.getInstance().update(item);
                if (isChecked) {//打开闹钟
                    AlarmManagerUtil.setAlarm(mContext, item);
                } else {//关闭闹钟
                    AlarmManagerUtil.cancelAlarm(mContext, AlarmManagerUtil.ALARM_ACTION, item.getId().intValue());
                }
            }
        });
    }
}
