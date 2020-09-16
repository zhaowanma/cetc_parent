package com.cetc.project.service.impl;

import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.model.project.CodeTest;
import com.cetc.project.entities.SearchCodeTest;
import com.cetc.project.mapper.CodeTestDao;
import com.cetc.project.service.CodeTestService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CodeTestServiceImpl implements CodeTestService {
    @Autowired
    private CodeTestDao codeTestDao;

    @Override
    public Result addCodeTest(CodeTest codeTest) {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Date current = new Date();
        codeTest.setCreater(principal);
        codeTest.setUpdater(principal);
        codeTest.setCreateTime(current);
        codeTest.setUpdateTime(current);
        codeTestDao.addCodeTest(codeTest);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    @Override
    public Result delCodeTest(Long id) {
        codeTestDao.deleteOne(id);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    @Override
    public Result pageList(SearchCodeTest searchCodeTest) {
        PageHelper.startPage(searchCodeTest.getPageNum(),searchCodeTest.getPageSize());
        List<CodeTest> list = codeTestDao.searchCodeTest(searchCodeTest);
        PageInfo<CodeTest> info = new PageInfo<>(list);
        return new Result(true,StatusCode.OK,"",info);
    }

    @Override
    public Result updateCodeTest(CodeTest codeTest) {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Date current = new Date();
        codeTest.setUpdater(principal);
        codeTest.setUpdateTime(current);
        codeTestDao.updateCodeTest(codeTest);
        return new Result(true,StatusCode.OK,"更行成功");
    }
}
