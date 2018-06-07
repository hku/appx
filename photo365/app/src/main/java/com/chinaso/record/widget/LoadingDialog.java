package com.chinaso.record.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.widget.TextView;

import com.chinaso.record.R;


/**
 * @说明: 自定义透明的loading dialog
 * @作者: zhanghe
 * @时间: 2018-02-05 16:52
 */
public class LoadingDialog extends Dialog {

    private String content;

    public LoadingDialog(Context context, String content) {
        super(context);
        this.content = content;
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_view_loading);
        ((TextView) findViewById(R.id.tvcontent)).setText(content);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (LoadingDialog.this.isShowing()) {
                    LoadingDialog.this.dismiss();
                }
                break;
        }
        return true;
    }
}
