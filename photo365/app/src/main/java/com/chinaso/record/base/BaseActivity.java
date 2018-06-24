package com.chinaso.record.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author: zhanghe
 * created on: 2018/6/4 15:57
 * description:activity基类
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mBinder = ButterKnife.bind(this);
        initView();
        initData();
        business();
    }


    protected abstract int getLayoutResId();

    protected void initView() {
    }

    protected void initData() {
    }

    protected abstract void business();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinder.unbind();
        mBinder = null;
    }
}
