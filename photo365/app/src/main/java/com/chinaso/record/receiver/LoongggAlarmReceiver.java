package com.chinaso.record.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;

import com.chinaso.record.activity.ClockAlarmActivity;
import com.chinaso.record.entity.AlarmEntity;
import com.chinaso.record.utils.AlarmDaoManager;
import com.chinaso.record.utils.AlarmManagerUtil;

/**
 * Created by loongggdroid on 2016/3/21.
 */
public class LoongggAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("msg");
        int flag = intent.getIntExtra("soundOrVibrator", 0);
        long intervalMillis = intent.getLongExtra("intervalMillis", 0);
        int id = intent.getIntExtra("id", -1);
        if (intervalMillis != 0) {//重复设置
            AlarmManagerUtil.setAlarmTime(context, System.currentTimeMillis() + intervalMillis,
                    intent);
        } else {//只响一次
            AlarmEntity alarmEntity = AlarmDaoManager.getInstance().query(id);
            alarmEntity.setIsOpen(false);
            AlarmDaoManager.getInstance().update(alarmEntity);
        }
        Intent clockIntent = new Intent(context, ClockAlarmActivity.class);
        clockIntent.putExtra("msg", msg);
        clockIntent.putExtra("flag", flag);
        clockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(clockIntent);
    }


}
