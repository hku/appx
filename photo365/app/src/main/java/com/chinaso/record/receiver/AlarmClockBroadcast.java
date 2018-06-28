/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com | 3772304@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.chinaso.record.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;

import com.chinaso.record.activity.ClockAlarmActivity;
import com.chinaso.record.entity.AlarmEntity;
import com.chinaso.record.utils.AlarmDaoManager;
import com.chinaso.record.utils.AlarmManagerUtil;
import com.chinaso.record.utils.AlarmManagerUtil2;
import com.chinaso.record.utils.Constant;
import com.orhanobut.logger.Logger;

import java.util.Calendar;

/**
 * 闹钟响起广播
 *
 * @author 咖枯
 * @version 1.0 2015/06
 */
public class AlarmClockBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        int value = bundle.getInt("1");
        Logger.e("value = " + value);
        AlarmEntity alarmClock = (AlarmEntity) bundle.getSerializable(Constant.ALARM_CLOCK);
        if (alarmClock == null) {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            alarmClock = AlarmDaoManager.getInstance().queryByTime(hour, minute);
        }
        if (alarmClock != null) {
            int cycleTag = alarmClock.getCycleTag();
            // 单次响铃
            if (cycleTag == -1) {
                int id = alarmClock.getId().intValue();
                AlarmEntity alarmEntity = AlarmDaoManager.getInstance().query(id);
                alarmEntity.setIsOpen(false);
                AlarmDaoManager.getInstance().update(alarmEntity);
            } else {
                // 重复周期闹钟
                AlarmManagerUtil2.setAlarm(context, alarmClock);
            }
        }
        Intent clockIntent = new Intent(context, ClockAlarmActivity.class);
        clockIntent.putExtra(Constant.ALARM_CLOCK, (Parcelable) alarmClock);
        // 清除栈顶的Activity
//        clockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(clockIntent);
    }
}
