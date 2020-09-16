package com.cetc.dic.service.impl;

import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.dic.dao.ParamDao;
import com.cetc.dic.service.ParamService;
import com.cetc.model.dic.Param;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ParamServiceImpl implements ParamService {

    @Autowired
    private ParamDao paramDao;
    @Override
    public Result findPageParams(Map params) {
        PageHelper.startPage((int)params.get("pageNum"),(int)params.get("pageSize"));
        List<Param> paramList = paramDao.findAll(params);
        PageInfo<Param> pageInfo = new PageInfo<>(paramList);
        return new Result(true, StatusCode.OK,"查询系统参数成功",pageInfo);
    }

    @Override
    public Result saveParam(Param param) {
        paramDao.insert(param);
        return new Result(true,StatusCode.OK,"保存参数设置成功");
    }

    @Override
    public Result queryById(Long id) {
        Param param = paramDao.queryById(id);
        return new Result(true,StatusCode.OK,"根据id查询参数成功",param);
    }

    @Override
    public Result updateById(Param param) {
        paramDao.updateById(param);
        return new Result(true,StatusCode.OK,"修改参数成功");
    }

    @Override
    public Result deleteById(Long id) {
        paramDao.delete(id);
        return new Result(true,StatusCode.OK,"删除参数成功");
    }

    @Override
    public Result queryByKey(String key) {
        Param param = paramDao.queryByKey(key);
        return new Result(true,StatusCode.OK,"查询成功",param);
    }
}
