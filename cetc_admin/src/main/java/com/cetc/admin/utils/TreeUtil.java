package com.cetc.admin.utils;

import com.cetc.admin.utils.infreface.DataTree;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeUtil {

    /*
    * 获取树形工具类
    * */
public static <T extends DataTree<T>> List<T> getTreeList(int topId,List<T> entityList){
    List<T> resultList = new ArrayList<>();
    Map<Object, Object> treeMap = new HashMap<>();
    T itemTree;
    for (int i=0;i<entityList.size()&&!entityList.isEmpty();i++) {
      itemTree = entityList.get(i);
      treeMap.put(itemTree.getId(),itemTree);
      if(topId==itemTree.getParentId()){
          resultList.add(itemTree);
      }
    }

  for (int i=0;i<entityList.size()&&!entityList.isEmpty();i++) {
        itemTree = entityList.get(i);
        T data = (T) treeMap.get(itemTree.getParentId());
        if (data != null) {
            if (data.getChildList() == null) {
                data.setChildList(new ArrayList<>());
            }
            data.getChildList().add(itemTree);
            treeMap.put(itemTree.getParentId(), data);
        }
    }
   return resultList;
}



}
