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
import android.content.Entity;
import android.content.Intent;
import android.os.Bundle;

import com.chinaso.record.activity.ClockAlarmActivity;
import com.chinaso.record.entity.AlarmEntity;
import com.chinaso.record.entity.AlarmEntityCopy;
import com.chinaso.record.entity.MessageEvent;
import com.chinaso.record.utils.AlarmDaoManager;
import com.chinaso.record.utils.AlarmManagerUtil;
import com.chinaso.record.utils.Constant;
import com.chinaso.record.utils.GsonUtils;
import com.chinaso.record.utils.LogUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

/**
 * author: zhanghe
 * created on: 2018/6/27 9:42
 * description: 闹钟响起广播
 */
public class AlarmClockBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String alarmClockS = bundle.getString(Constant.ALARM_CLOCK);
        //序列化AlarmEntity 出错，暂不知道原因，暂时用AlarmEntityCopy代替
        AlarmEntityCopy alarmEntityCopy = GsonUtils.fromJsonString(alarmClockS, AlarmEntityCopy.class);
        LogUtils.d("zhanghe   " + "alarm will ring" + alarmEntityCopy.getId());
        AlarmEntity alarmClock = copy(alarmEntityCopy);
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

                MessageEvent event = new MessageEvent();
                event.setId(MessageEvent.IdPool.ALARM_ID);
                event.setObject(alarmEntity);
                EventBus.getDefault().post(event);
            } else {
                // 重复周期闹钟
                AlarmManagerUtil.setAlarm(context, alarmClock);
            }
        }
        Intent clockIntent = new Intent(context, ClockAlarmActivity.class);
        clockIntent.putExtra(Constant.ALARM_CLOCK, alarmClock);
        clockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(clockIntent);
    }

    private AlarmEntity copy(AlarmEntityCopy alarmEntity2) {
        AlarmEntity alarmEntity = new AlarmEntity();
        alarmEntity.setId(alarmEntity2.getId());
        alarmEntity.setIsOpen(alarmEntity2.getIsOpen());
        alarmEntity.setTitle(alarmEntity2.getTitle());
        alarmEntity.setRemark(alarmEntity2.getRemark());
        alarmEntity.setBellMode(alarmEntity2.getBellMode());
        alarmEntity.setCycleWeeks(alarmEntity2.getCycleWeeks());
        alarmEntity.setCycleTag(alarmEntity2.getCycleTag());
        alarmEntity.setMinute(alarmEntity2.getMinute());
        alarmEntity.setHour(alarmEntity2.getHour());
        return alarmEntity;
    }
}
