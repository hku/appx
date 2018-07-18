package com.app.assistant.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.app.assistant.entity.AlarmEntity;
import com.app.assistant.entity.PoetEntity;
import com.app.assistant.entity.ReminderEntity;
import com.app.assistant.receiver.WakeReceiver;
import com.app.assistant.utils.AlarmDaoManager;
import com.app.assistant.utils.AlarmManagerUtil;
import com.app.assistant.utils.CommonUtils;
import com.app.assistant.utils.GsonUtils;
import com.app.assistant.utils.LogUtils;
import com.app.assistant.utils.PreferenceKeyConstant;
import com.app.assistant.utils.ReminderDaoManager;
import com.app.assistant.utils.SPUtils;
import com.google.gson.reflect.TypeToken;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author: zhanghe
 * created on: 2018/7/10 16:27
 * description: 保活的service
 */

public class DaemonService extends Service {

    // 定时唤醒的时间间隔，5分钟
    private final static int ALARM_INTERVAL = 5 * 60 * 1000;

    private final static int GRAY_SERVICE_ID = -1001;

    private final static int WAKE_REQUEST_CODE = 6666;

    private ExecutorService executorService;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d("zhanghe " + "DaemonService onCreate");
        grayGuard();
        test();
        executorService = Executors.newFixedThreadPool(2);
        asynSetClock();
        startTimeTask2();
    }

    private void test() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                LogUtils.d("zhanghe " + "the daemon service is coming");
            }
        }, new Date(), 5000);
    }

    /**
     * 异步设置闹钟
     */
    private void asynSetClock() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<AlarmEntity> list = AlarmDaoManager.getInstance().queryDesc();
                for (AlarmEntity alarmClock : list) {
                    if (alarmClock.getIsOpen()) {
                        AlarmManagerUtil.setAlarm(DaemonService.this, alarmClock);
                    }
                }
            }
        });
    }

    private void startTimeTask2() {
        boolean isFirstIn = SPUtils.getInstance().getBoolean(PreferenceKeyConstant.FIRST_IN, true);
        if (!isFirstIn) {
            LogUtils.d("zhanghe " + "isFirstIn = " + isFirstIn);
            return;
        }
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String poetS = CommonUtils.getJson("poet.song.1000.json", DaemonService.this);
                List<PoetEntity> list = GsonUtils.fromJsonString(poetS, new TypeToken<List<PoetEntity>>() {
                }.getType());
                for (PoetEntity entity : list) {
                    List<String> paragraphsList = entity.getParagraphs();
                    StringBuilder sb = new StringBuilder();
                    for (String paragraph : paragraphsList) {
                        sb.append(paragraph);
                    }
                    String content = sb.toString();
                    ReminderEntity reminderEntity = new ReminderEntity();
                    reminderEntity.setContent(content);
                    reminderEntity.setTagS("诗词");
                    ReminderDaoManager.getInstance().insert(reminderEntity);
                }
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d("zhanghe " + "DaemonService onStartCommand");
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    private void grayGuard() {
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(GRAY_SERVICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
        } else {
            Intent innerIntent = new Intent(this, DaemonInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }
        //发送唤醒广播来促使挂掉的UI进程重新启动起来
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent();
        alarmIntent.setAction(WakeReceiver.GRAY_WAKE_ACTION);
        PendingIntent operation = PendingIntent.getBroadcast(this, WAKE_REQUEST_CODE, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), ALARM_INTERVAL, operation);
    }


    /**
     * 给 API >= 18 的平台上用的灰色保活手段
     */
    public static class DaemonInnerService extends Service {

        @Override
        public void onCreate() {
            super.onCreate();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(GRAY_SERVICE_ID, new Notification());
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public IBinder onBind(Intent intent) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d("zhanghe " + "onDestroy");
        startService(new Intent(this, DaemonService.class));
    }
}
