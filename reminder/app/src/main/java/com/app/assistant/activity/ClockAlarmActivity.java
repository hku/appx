package com.app.assistant.activity;

import android.app.Service;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.View;

import com.app.assistant.R;
import com.app.assistant.base.BaseActivity;
import com.app.assistant.entity.AlarmEntity;
import com.app.assistant.utils.Constant;
import com.app.assistant.widget.SimpleDialog;

/**
 * author: zhanghe
 * created on: 2018/6/27 9:42
 * description: 闹钟响起界面
 */
public class ClockAlarmActivity extends BaseActivity {

    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    private AlarmEntity mAlarmEntity;
    private int flag = 2;
    private String message = "闹钟响了";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_clock_alarm;
    }

    @Override
    protected void business() {
        mAlarmEntity = (AlarmEntity) getIntent().getSerializableExtra(Constant.ALARM_CLOCK);
        if (mAlarmEntity != null) {
            message = mAlarmEntity.getTitle() + "\n" + mAlarmEntity.getRemark();
            flag = mAlarmEntity.getBellMode();
        }
        showDialogInBroadcastReceiver(message, flag);
    }

    private void showDialogInBroadcastReceiver(String message, final int flag) {
        if (flag == 1 || flag == 2) {
            mediaPlayer = MediaPlayer.create(this, R.raw.in_call_alarm);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
        //数组参数意义：第一个参数为等待指定时间后开始震动，震动时间为第二个参数。后边的参数依次为等待震动和震动的时间
        //第二个参数为重复次数，-1为不重复，0为一直震动
        if (flag == 0 || flag == 2) {
            vibrator = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(new long[]{100, 10, 100, 600}, 0);
        }

        final SimpleDialog dialog = new SimpleDialog(this, R.style.Theme_dialog);
        dialog.show();
        dialog.setTitle("闹钟提醒");
        dialog.setMessage(message);
        dialog.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.bt_confirm == v || dialog.bt_cancel == v) {
                    if (flag == 1 || flag == 2) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                    if (flag == 0 || flag == 2) {
                        vibrator.cancel();
                    }
                    dialog.dismiss();
                    finish();
                }
            }
        });
    }
}
