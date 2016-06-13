package com.wonshinhyo.dragrecyclerview;

/**
 * Created by Shinhyo on 2016. 6. 13..
 */

public interface ImpDragAdapter {

    void setOnItemStartDragListener(OnStartDragListener onStartDragListener);

    void setOnItemDragListener(OnDragListener dragListener);

    void setOnItemClickListener(OnClickListener clickListener);

    void setHandleDragEnabled(boolean dragEnabled);

    void setHandleId(int handleId);
}
