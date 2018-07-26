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
import com.app.assistant.entity.MemoEntity;
import com.app.assistant.receiver.WakeReceiver;
import com.app.assistant.utils.AlarmDaoManager;
import com.app.assistant.utils.AlarmManagerUtil;
import com.app.assistant.utils.CommonUtils;
import com.app.assistant.utils.Constant;
import com.app.assistant.utils.GsonUtils;
import com.app.assistant.utils.LogUtils;
import com.app.assistant.utils.PreferenceKeyConstant;
import com.app.assistant.utils.MemoDaoManager;
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
        asynLoadMemo();
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
                //当前无闹钟时默认设置一个工作日闹钟
                if (list.size() <= 0) {
                    AlarmEntity alarmEntity = new AlarmEntity();
                    alarmEntity.setHour(16);
                    alarmEntity.setMinute(30);
                    alarmEntity.setTitle("开会");
                    alarmEntity.setIsOpen(true);
                    alarmEntity.setBellMode(2);
                    alarmEntity.setCycleTag(1);
                    alarmEntity.setCycleWeeks("1,2,3,4,5");
                    alarmEntity.setRemark("");
                    AlarmDaoManager.getInstance().insert(alarmEntity);
                }
                for (AlarmEntity alarmClock : list) {
                    if (alarmClock.getIsOpen()) {
                        AlarmManagerUtil.setAlarm(DaemonService.this, alarmClock);
                    }
                }
            }
        });
    }

    private void asynLoadMemo() {
        boolean isFirstIn = SPUtils.getInstance().getBoolean(PreferenceKeyConstant.FIRST_IN, true);
        if (!isFirstIn) {
            LogUtils.d("zhanghe " + "isFirstIn = " + isFirstIn);
            return;
        }
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String caigentanS = CommonUtils.getJson("caigentan.json", DaemonService.this);
                List<String> list = GsonUtils.fromJsonString(caigentanS, new TypeToken<List<String>>() {
                }.getType());
                for (String s : list) {
                    MemoEntity memoEntity = new MemoEntity();
                    memoEntity.setContent(s);
                    memoEntity.setIsBuiltIn(true);
                    memoEntity.setTagS(Constant.TAG_ARRAY[0]);
                    MemoDaoManager.getInstance().insert(memoEntity);
                }

                String changshiS = CommonUtils.getJson("changshi.json", DaemonService.this);
                List<String> senseList = GsonUtils.fromJsonString(changshiS, new TypeToken<List<String>>() {
                }.getType());
                for (String s : senseList) {
                    MemoEntity memoEntity = new MemoEntity();
                    memoEntity.setContent(s);
                    memoEntity.setIsBuiltIn(true);
                    memoEntity.setTagS(Constant.TAG_ARRAY[1]);
                    MemoDaoManager.getInstance().insert(memoEntity);
                }

                String englishS = CommonUtils.getJson("english.json", DaemonService.this);
                List<String> englishList = GsonUtils.fromJsonString(englishS, new TypeToken<List<String>>() {
                }.getType());
                for (String s : englishList) {
                    MemoEntity memoEntity = new MemoEntity();
                    memoEntity.setContent(s);
                    memoEntity.setIsBuiltIn(true);
                    memoEntity.setTagS(Constant.TAG_ARRAY[2]);
                    MemoDaoManager.getInstance().insert(memoEntity);
                }

                String jokeS = CommonUtils.getJson("joke.json", DaemonService.this);
                List<String> jokeList = GsonUtils.fromJsonString(jokeS, new TypeToken<List<String>>() {
                }.getType());
                for (String s : jokeList) {
                    MemoEntity memoEntity = new MemoEntity();
                    memoEntity.setContent(s);
                    memoEntity.setIsBuiltIn(true);
                    memoEntity.setTagS(Constant.TAG_ARRAY[3]);
                    MemoDaoManager.getInstance().insert(memoEntity);
                }

                String mottoS = CommonUtils.getJson("motto.json", DaemonService.this);
                List<String> mottoList = GsonUtils.fromJsonString(mottoS, new TypeToken<List<String>>() {
                }.getType());
                for (String s : mottoList) {
                    MemoEntity memoEntity = new MemoEntity();
                    memoEntity.setContent(s);
                    memoEntity.setIsBuiltIn(true);
                    memoEntity.setTagS(Constant.TAG_ARRAY[4]);
                    MemoDaoManager.getInstance().insert(memoEntity);
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
