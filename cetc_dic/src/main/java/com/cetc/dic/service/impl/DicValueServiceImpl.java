package com.cetc.dic.service.impl;

import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.dic.dao.DicDao;
import com.cetc.dic.dao.DicValueDao;
import com.cetc.dic.service.DicValueService;
import com.cetc.model.dic.DicValue;
import com.cetc.model.dic.Dictionary;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DicValueServiceImpl implements DicValueService {

    @Autowired
    private DicValueDao dicValueDao;

    @Autowired
    private DicDao dicDao;
    @Override
    public Result queryPageDicValues(Long dicId,Map params) {
        PageHelper.startPage((int)params.get("pageNum"),(int)params.get("pageSize"));
        List dicValues = dicValueDao.queryAll(dicId, params);
        PageInfo<Dictionary> pageInfo = new PageInfo<>(dicValues);

        return new Result(true, StatusCode.OK,"查询字典数据成功",pageInfo);
    }

    @Override
    public Result saveDicValue(DicValue dicValue) {
        dicValue.setUpdateTime(new Date());
        dicValueDao.saveDicValue(dicValue);
        return new Result(true,StatusCode.OK,"保存字典数据成功");
    }

    @Override
    public Result queryById(Long id) {
        DicValue dicValue = dicValueDao.queryById(id);
        return new Result(true,StatusCode.OK,"查询成功",dicValue);
    }

    @Override
    public Result updateById(DicValue dicValue) {
        dicValueDao.updateById(dicValue);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @Override
    public Result queryByKey(DicValue dicValue) {
        DicValue dicValue1 = dicValueDao.queryByKey(dicValue);
        return new Result(true,StatusCode.OK,"查询成功",dicValue1);
    }

    @Override
    public Result deleteById(Long id) {
        dicValueDao.deleteById(id);
        return new Result(true,StatusCode.OK,"删除字典数据成功");
    }

    @Override
    public Result queryDicValuesByDicType(String dicType) {

        Map<String, String> dicFields = new HashMap<>();
        dicFields.put("dicType",dicType);
        Dictionary dictionary = dicDao.queryByDicFields(dicFields);
        List<DicValue> dicValues = new ArrayList<>();
        if(dictionary!=null&&dictionary.getId()!=null&&dictionary.getStatus()){
            dicValues = dicValueDao.queryAllDicValuesByDicType(dictionary.getId());
        }

        return new Result(true,StatusCode.OK,"OK",dicValues);
    }


}
