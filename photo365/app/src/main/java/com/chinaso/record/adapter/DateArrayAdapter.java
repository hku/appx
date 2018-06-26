package com.chinaso.record.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chinaso.record.widget.wheelview.ArrayWheelAdapter;

/**
 * author: zhanghe
 * created on: 2018/6/25 15:32
 * description:闹钟添加小时分钟数据源
 */

public class DateArrayAdapter extends ArrayWheelAdapter<String> {
    int currentItem;
    int currentValue;

    public DateArrayAdapter(Context context, String[] items, int current) {
        super(context, items);
        this.currentValue = current;
        setTextSize(16);
    }

    protected void configureTextView(TextView view) {
        super.configureTextView(view);
        view.setTypeface(Typeface.SANS_SERIF);
    }

    public View getItem(int index, View cachedView, ViewGroup parent) {
        this.currentItem = index;
        return super.getItem(index, cachedView, parent);
    }
}
