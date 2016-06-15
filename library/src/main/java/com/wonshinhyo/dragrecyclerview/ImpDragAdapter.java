package com.wonshinhyo.dragrecyclerview;

import java.util.List;

/**
 * Created by Shinhyo on 2016. 6. 13..
 */

interface ImpDragAdapter {

    List getData();

    void setOnItemDragListener(OnDragListener dragListener);

    void setOnItemClickListener(OnClickListener clickListener);

    void setHandleDragEnabled(boolean dragEnabled);

}
