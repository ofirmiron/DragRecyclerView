package com.wonshinhyo.dragrecyclerview.sample.item;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Shinhyo on 2016. 6. 12..
 */

public class Dummy extends RealmObject {

    @PrimaryKey
    int id;
    int num;
    int sort;

    public Dummy() {
    }

    public Dummy(int id, int num, int sort) {
        this.id = id;
        this.num = num;
        this.sort = sort;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }


}
