package com.cetc.admin.utils.infreface;

import java.util.List;

/*
* 可根据自己本身情况更改接口所要实现的发放，
* 我这接口类只是实现了某些属性的get、set方法，写这个接口类主要是为了下面的工具类，定义泛型T的类型
*
* */
public interface DataTree<T> {
     int getId();

     int getParentId();

     void setChildList(List<T> childList);

     List<T> getChildList();
}
