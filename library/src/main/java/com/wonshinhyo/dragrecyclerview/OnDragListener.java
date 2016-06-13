package com.wonshinhyo.dragrecyclerview;

/**
 * Created by Shinhyo on 2016. 6. 13..
 */

public interface OnDragListener {
    boolean onMove(int fromPosition, int toPosition);

    void onSwiped(int position);

    void onDrop(int fromPosition, int toPosition);
}
