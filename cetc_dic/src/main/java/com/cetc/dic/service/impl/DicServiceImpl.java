package com.cetc.dic.service.impl;

import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.dic.dao.DicDao;
import com.cetc.dic.dao.DicValueDao;
import com.cetc.dic.service.DicService;
import com.cetc.model.dic.Dictionary;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicServiceImpl implements DicService {

    @Autowired
    private DicDao dicDao;

    @Autowired
    private DicValueDao dicValueDao;
    @Override
    public Result saveDic(Dictionary dictionary) {
        dictionary.setCreateTime(new Date());
        dictionary.setUpdateTime(new Date());
        dicDao.insert(dictionary);
        return new Result(true, StatusCode.OK,"保存字典数据成功");
    }

    @Override
    public Result queryAllDics(Map params) {
        PageHelper.startPage((int)params.get("pageNum"),(int)params.get("pageSize"));
        List<Dictionary> dictionaries =dicDao.queryAll();
        PageInfo<Dictionary> pageInfo = new PageInfo<>(dictionaries);
        return new Result(true,StatusCode.OK,"查询所有的字典数据成功",pageInfo);
    }

    @Override
    public Result queryDicById(Long id) {
        Dictionary dictionary = dicDao.queryById(id);
        return new Result(true,StatusCode.OK,"查询字典成功",dictionary);
    }

    @Override
    public Result queryByDicName(String dicName) {
        Map<String, String> dicFields = new HashMap<>();
        dicFields.put("dicName",dicName);
        Dictionary dictionary = dicDao.queryByDicFields(dicFields);
        return new Result(true,StatusCode.OK,"OK",dictionary);
    }

    @Override
    public Result queryByDicType(String dicType) {
        Map<String, String> dicFields = new HashMap<>();
        dicFields.put("dicType",dicType);
        Dictionary dictionary = dicDao.queryByDicFields(dicFields);
        return new Result(true,StatusCode.OK,"OK",dictionary);
    }

    @Override
    public Result updateDic(Dictionary dictionary) {
        dicDao.updateDic(dictionary);
        return new Result(true,StatusCode.OK,"修改字典成功");
    }

    @Override
    public Result fuzzyQueryDic(Map params) {
        PageHelper.startPage((int)params.get("pageNum"),(int)params.get("pageSize"));
        List<Dictionary> dictionaries = dicDao.fuzzyQuery(params);
        PageInfo<Dictionary> pageInfo = new PageInfo<>(dictionaries);
        return new Result(true,StatusCode.OK,"搜索字典成功",pageInfo);
    }

    @Override
    @Transactional
    public Result deleteDic(Long id) {
        dicDao.delete(id);
        dicValueDao.deleteByDicId(id);
        return new Result(true,StatusCode.OK,"删除字典成功");
    }


}
