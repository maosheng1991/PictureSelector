package com.example.pictureselector.manage;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CollectionManage
 * @Description 集合管理类
 * @Author ytf
 * CreateDate    2020/3/31 16:28
 */
public class CollectionManage {

    private List<String> list = new ArrayList<>();
    private static CollectionManage collectionManage;

    public CollectionManage() {

    }


    public static CollectionManage getInstance() {
        if (collectionManage == null) {
            synchronized (CollectionManage.class) {
                collectionManage = new CollectionManage();
            }
        }
        return collectionManage;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
