package com.cetc.dic.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.dic.Dictionary;


import java.util.Map;

public interface DicService {
     Result saveDic(Dictionary dictionary);

     Result queryAllDics(Map map);

     Result queryDicById(Long id);

     Result queryByDicName(String dicName);

     Result queryByDicType(String dicType);

     Result updateDic(Dictionary dictionary);

     Result fuzzyQueryDic(Map map);

     Result deleteDic(Long id);

}
