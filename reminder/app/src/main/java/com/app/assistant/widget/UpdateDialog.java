package com.app.assistant.widget;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.FileProvider;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.app.assistant.R;
import com.app.assistant.base.BaseDialog;
import com.app.assistant.utils.Constant;
import com.app.assistant.utils.FileManagerUtils;
import com.app.assistant.utils.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * author: zhanghe
 * created on: 2018/7/16 10:26
 * description:版本更新对话框
 */
public class UpdateDialog extends BaseDialog {

    private String title, message;
    private Context mContext;
    protected Notification notify;
    private NotificationManager manager;
    private String apkName, downloadUrl;

    private File fileInstall;

    private boolean isForced = false;

    public UpdateDialog(Context context, String title, String message, String apkName, String url, boolean isForced) {
        super(context, R.style.update_dialog);
        this.mContext = context;
        this.title = title;
        this.message = message;
        this.apkName = apkName;
        this.downloadUrl = url;
        this.isForced = isForced;
    }

    @Override
    public int setLayout() {
        return R.layout.dlg_update;
    }

    @Override
    public void init() {
        TextView title = (TextView) findViewById(R.id.title);
        TextView content = (TextView) findViewById(R.id.message);
        Button update = (Button) findViewById(R.id.positiveButton);
        Button cancel = (Button) findViewById(R.id.negativeButton);

        title.setText(this.title);
        content.setText(Html.fromHtml(message));
        content.setMovementMethod(LinkMovementMethod.getInstance());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(mContext, "通知栏查看下载进度");
                downLoadNewApk();
                dismiss();
            }
        });

        if (isForced) {
            this.setCancelable(false);
            cancel.setText("退出应用");
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.exit(0);
                    dismiss();
                }
            });
        } else {
            this.setCancelable(true);
            cancel.setText("残忍拒绝");
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    /**
     * download apk
     */
    private void downLoadNewApk() {
        manager = (NotificationManager) mContext
                .getSystemService((mContext.NOTIFICATION_SERVICE));
        notify = new Notification();
        notify.icon = R.mipmap.ic_launcher;
        notify.flags = Notification.FLAG_ONGOING_EVENT;
        notify.contentView = new RemoteViews(mContext.getPackageName(), R.layout.layout_download_notification);
        manager.notify(100, notify);
        fileInstall = new File(FileManagerUtils.getInstance().getApkPath(), apkName + ".apk");
        downLoadSchedule(completeHandler, mContext, fileInstall);
    }


    private Handler completeHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what < 100) {
                notify.contentView.setTextViewText(R.id.notify_update_values_tv, msg.what + "%");
                notify.contentView.setProgressBar(R.id.notify_update_progress, 100, msg.what, false);
                manager.notify(100, notify);
            } else {
                notify.contentView.setTextViewText(R.id.notify_update_values_tv, "下载完成");
                notify.contentView.setProgressBar(R.id.notify_update_progress, 100, msg.what, false);
                manager.cancel(100);
                installApk(fileInstall);
            }
        }
    };


    /**
     * schedule download
     *
     * @param handler
     * @param context
     * @param file
     */
    public void downLoadSchedule(
            final Handler handler, Context context, final File file) {
        final int perLength = 4096;
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(downloadUrl);
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream in = conn.getInputStream();
                    long length = conn.getContentLength();
                    byte[] buffer = new byte[perLength];
                    int len = -1;
                    FileOutputStream out = new FileOutputStream(file);
                    int temp = 0;
                    while ((len = in.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                        int schedule = (int) ((file.length() * 100) / length);
                        if (temp != schedule
                                && (schedule % 10 == 0 || schedule % 4 == 0 || schedule % 7 == 0)) {
                            temp = schedule;
                            handler.sendEmptyMessage(schedule);
                        }
                    }
                    out.flush();
                    out.close();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /**
     * install apk
     *
     * @param file
     */
    private void installApk(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri imageUri = null;
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(file);
        } else {
            imageUri = FileProvider.getUriForFile(mContext, Constant.getFileProviderName(mContext), file);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授
        intent.setDataAndType(imageUri,
                "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

}
