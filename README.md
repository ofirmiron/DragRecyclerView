## DragRecyclerView [ ![Download](https://api.bintray.com/packages/shinhyo/maven/dragrecyclerview/images/download.svg) ](https://bintray.com/shinhyo/maven/dragrecyclerview/_latestVersion) <a href="http://www.methodscount.com/?lib=com.wonshinhyo%3Adragrecyclerview%3A1.0.2"><img src="https://img.shields.io/badge/Size-12 KB-e91e63.svg"/></a>

#####Simple Drag and Drop and Swipe RecyclerView (with Realm)
<br />

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
        android:layout_height="match_parent"
        drag:handle_id="@+id/handler"/> // set [handle_id] or do not


```
 
**java**
```java
        
        DragRecyclerView recyclerView = (DragRecyclerView) findViewById(R.id.recyclerview);

        ...

        mAdapter = new ExamAdapter(this, data);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setHandleDragEnabled(true); // default true
        mAdapter.setLongPressDragEnabled(true); // default true
        mAdapter.setSwipeEnabled(true); // default true

        mAdapter.setOnItemClickListener(new SimpleClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                super.onItemClick(v, pos);
                Toast.makeText(ExamActivity.this, "onItemClick\npos: " + pos + " text: "
                        + mAdapter.getData().get(pos), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View v, int pos) {
                super.onItemClick(v, pos);
                Toast.makeText(ExamActivity.this, "onItemLongClick\npos: " + pos + " text: "
                        + mAdapter.getData().get(pos), Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter.setOnItemDragListener(new SimpleDragListener() {

            @Override
            public void onDrop(int fromPosition, int toPosition) {  // single callback
                super.onDrop(fromPosition, toPosition);
                Log.i("drag", "onDrop " + fromPosition + " -> " + toPosition);
            }

            @Override
            public void onSwiped(int pos) {
                super.onSwiped(pos);
                Log.d("drag", "onSwiped " + pos);
                Toast.makeText(ExamActivity.this, "onSwiped\npos: " + pos, Toast.LENGTH_SHORT).show();
            }
        });

```

Download
=======

```groovy
dependencies {

    compile 'com.wonshinhyo:dragrecyclerview:0.1.1'
    
    or 
    
    compile 'com.wonshinhyo:dragrecyclerview.realm:0.1.1' // use realm
     
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