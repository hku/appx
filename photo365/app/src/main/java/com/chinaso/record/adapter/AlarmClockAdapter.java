package com.chinaso.record.adapter;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chinaso.record.R;
import com.chinaso.record.entity.AlarmEntity;
import com.chinaso.record.utils.AlarmDaoManager;
import com.chinaso.record.utils.AlarmManagerUtil;
import com.chinaso.record.utils.CommonUtils;

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

    public void refreshItem(AlarmEntity entity) {
        if (mData != null) {
            for (int i = 0; i < mData.size(); i++) {
                AlarmEntity alarmEntity = mData.get(i);
                if (alarmEntity.getId() == entity.getId()) {
                    mData.set(i, entity);
                    notifyItemChanged(i);
                    break;
                }
            }
        }
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
        String time = CommonUtils.formatTime(hour, minute);
        helper.setText(R.id.cycle, cycleS + " " + time);
        //是否打开
        boolean isOpen = item.getIsOpen();
        SwitchCompat switchCompat = helper.getView(R.id.switch_sv);
        if (isOpen) {
            switchCompat.setChecked(true);
        } else {
            switchCompat.setChecked(false);
        }
        helper.addOnClickListener(R.id.switch_sv);
    }
}
