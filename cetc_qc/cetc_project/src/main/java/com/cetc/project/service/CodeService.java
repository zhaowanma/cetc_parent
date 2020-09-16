package com.cetc.project.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.project.Code;
import com.cetc.project.entities.SearchCode;

import java.util.List;
import java.util.Map;

public interface CodeService {

    Result save(Code code);   //新增令号
    Result delete(Long id);  //删除
    Result updateCode(Code code); //更新令号
    Result checkCode(Code code);  //检查令号唯一
    Result handleCodeIsZy(Code code); //修改令号的在研状态

    Result setLeader(Long id, String username, List<String> visableList);  //设置总体负责人
    Result commitCodeApply(Map params);  //派发令号
    Result findDatas(); //treezero查询负责的令号项目等数据
    /*
    * 查询
    */
    Result getNum();
    Result findPageCode(Map map); //分页查询

    Result findPageCodeWithReady(Map map); //分页查询

    Result queryList(SearchCode searchCode);
    Result countOfKingdom();
    Result findCodeById(long id);

    Result findVisableUser(Long codeId);//查询已分配的用户
    Result checkRole(long id);  //校验权限



    Result countOfMonth(Map map);
}
