package com.app.assistant.activity;

import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.app.assistant.R;
import com.app.assistant.base.BaseActivity;
import com.app.assistant.utils.CommonUtils;
import com.app.assistant.utils.LogUtils;
import com.app.assistant.utils.ToastUtils;


import butterknife.BindView;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * author: zhanghe
 * created on: 2018/7/13 16:41
 * description: 扫码界面
 */
public class ScanActivity extends BaseActivity implements QRCodeView.Delegate {

    @BindView(R.id.zxingview)
    ZXingView mZXingView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void initView() {
        super.initView();
        mZXingView.setDelegate(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_scan;
    }

    @Override
    protected void business() {

    }

    @Override
    public void onStart() {
        super.onStart();
        mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
//        mZXingView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别
        mZXingView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
    }


    @Override
    public void onStop() {
        mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZXingView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        LogUtils.d("zhanghe " + result);
        vibrate();
        if (!TextUtils.isEmpty(result)) {
            boolean isUrl = CommonUtils.isHttpUrl(result);
            Intent intent = new Intent();
            if (isUrl) {
                intent.setClass(ScanActivity.this, WebActivity.class);
                intent.putExtra("title", result);
                intent.putExtra("url", result);
            } else {
                intent.setClass(ScanActivity.this, SearchActivity.class);
                intent.putExtra("search_word", result);
            }
            startActivity(intent);
            finish();
        } else {
            ToastUtils.show(this, "无效码");
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        ToastUtils.show(this, "扫描出错，请稍后重试");
    }
}