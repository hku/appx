package com.app.assistant.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Dialog
 *
 * @author xufuhong
 */
public abstract class BaseDialog extends Dialog {

    public BaseDialog(Context context, int theme) {
        super(context, theme);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        init();
    }


    public abstract int setLayout();

    public abstract void init();
}
