package com.wonshinhyo.dragrecyclerview;

import android.view.View;

/**
 * Created by Shinhyo on 2016. 6. 13..
 */
public interface OnClickListener {
    void onItemClick(View v, int position);

    void onItemLongClick(View v, int position);
}
