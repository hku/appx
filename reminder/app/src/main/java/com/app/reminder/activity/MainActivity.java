package com.app.reminder.activity;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.app.reminder.R;
import com.app.reminder.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: zhanghe
 * created on: 2018/6/4 15:57
 * description:主页面
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.show_iv)
    ImageView addIv;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void business() {

    }

    @OnClick({R.id.show_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_iv:
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                startActivity(intent);
                break;
        }
    }
}
