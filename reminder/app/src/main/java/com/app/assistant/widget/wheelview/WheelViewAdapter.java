package com.app.assistant.widget.wheelview;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

public interface WheelViewAdapter {
    View getEmptyItem(View view, ViewGroup viewGroup);

    View getItem(int i, View view, ViewGroup viewGroup);

    int getItemsCount();

    void registerDataSetObserver(DataSetObserver dataSetObserver);

    void unregisterDataSetObserver(DataSetObserver dataSetObserver);
}
