# DragRecyclerView [ ![Download](https://api.bintray.com/packages/shinhyo/maven/dragrecyclerview/images/download.svg) ](https://bintray.com/shinhyo/maven/dragrecyclerview/_latestVersion) <a href="http://www.methodscount.com/?lib=com.wonshinhyo%3Adragrecyclerview%3A1.0.2"><img src="https://img.shields.io/badge/Size-12 KB-e91e63.svg"/></a>

List Sample | Grid Sample
---- | ----
![](web/list.gif) |![](web/grid.gif) 

Code
====

**xml**
```xml
<com.wonshinhyo.dragrecyclerview.DragRecyclerView
    android:id="@+id/recyclerview"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    drag:handle_id="@+id/handler"/>      // set [handle_id] or do not
```
 

**java**
```java
DragRecyclerView recyclerView = (DragRecyclerView) findViewById(R.id.recyclerview);

...

mAdapter = new Adapter(this, data);
recyclerView.setAdapter(mAdapter);

recyclerView.setHandleDragEnabled(true); // default true
recyclerView.setLongPressDragEnabled(true); // default true
recyclerView.setSwipeEnabled(true); // default true

// click listener
mAdapter.setOnItemClickListener(new DragRecyclerViewAdapter.SimpleClickListener() {
    @Override
    public void onItemClick(int pos, View v) {}

    @Override
    public void onItemLongClick(int pos, View v) {}
});

// drag & swipe listener
mAdapter.setOnItemDragListener(new DragTouchCallback.SimpleDragListener() {
    @Override
    public boolean onMove(int fromPosition, int toPosition) {
        return super.onMove(fromPosition, toPosition);
    }

    @Override
    public void onSwiped(int pos) {
        super.onSwiped(pos);
    }
});

```

Download
=======

```groovy

dependencies {
    compile 'com.wonshinhyo:dragrecyclerview:1.0.2'
}
```



--
Inspired by [ItemTouchHelper](https://github.com/iPaulPro/Android-ItemTouchHelper-Demo).


License
=======

    Copyright 2016 Shinhyo Won

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.