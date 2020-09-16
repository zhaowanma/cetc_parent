package com.cetc.dic.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.dic.DicValue;
import java.util.Map;

public interface DicValueService {

     Result queryPageDicValues(Long dicId,Map params);

     Result saveDicValue(DicValue dicValue);

     Result queryById(Long id);

     Result updateById(DicValue dicValue);

     Result queryByKey(DicValue dicValue);

     Result deleteById(Long id);

     Result queryDicValuesByDicType(String dicType);
}
