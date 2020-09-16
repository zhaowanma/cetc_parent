package com.cetc.project.service.impl;

import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.model.project.Code;
import com.cetc.model.project.FpgaStandard;
import com.cetc.model.project.Kingdom;
import com.cetc.model.project.Project;
import com.cetc.project.mapper.CodeDao;
import com.cetc.project.mapper.FpgaDao;
import com.cetc.project.mapper.KingdomDao;
import com.cetc.project.mapper.ProjectDao;
import com.cetc.project.service.FpgaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FpgaServiceImpl implements FpgaService {

    @Autowired
    private KingdomDao kingdomDao;
    @Autowired
    private CodeDao codeDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private FpgaDao fpgaDao;

    @Override
    @Transactional
    public Result addFpgaStandard(FpgaStandard fpgaStandard) {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Date current = new Date();
        fpgaStandard.setUpdateDate(current);
        fpgaStandard.setUpdateBy(principal);
        if (fpgaStandard.getId()==null){
            fpgaStandard.setCreateBy(principal);
            fpgaStandard.setCreateDate(current);
            List<FpgaStandard> list=fpgaDao.findFpgaStandardByPid(fpgaStandard.getParentId());
            if (list.size()!=0){
                return new Result(true, StatusCode.OK,"项目审核已存在，无法添加");
            }
            fpgaStandard.setProject(projectDao.findOne(fpgaStandard.getParentId()).getName());
            fpgaStandard.setCode(codeDao.findOne(fpgaStandard.getCodeId()).getValue());
            fpgaStandard.setKingdom(kingdomDao.findOne(fpgaStandard.getKingdomId()).getName());
            fpgaDao.addFpgaStandard(fpgaStandard);
        }
        return new Result(true,StatusCode.OK,"保存成功");
    }

    @Override
    @Transactional
    public Result updateFpgaStandard(FpgaStandard fpgaStandard) {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Date current = new Date();
        fpgaStandard.setUpdateDate(current);
        fpgaStandard.setUpdateBy(principal);
        fpgaDao.updateFpgaStandard(fpgaStandard);
        return new Result(true,StatusCode.OK,"");
    }

    @Override
    public Result pageFpgaStandard(Map<String, Integer> params) {
        PageHelper.startPage(params.get("pageNum"),params.get("pageSize"));
        Integer kingdomId=  params.get("kingdomId");
        Integer codeId=  params.get("codeId");
        Integer projectId=  params.get("projectId");
        List<FpgaStandard> fpgaStandardList=fpgaDao.findFpgaStandard(kingdomId,codeId,projectId);
        PageInfo<FpgaStandard> pageInfo = new PageInfo<>(fpgaStandardList);
        return new Result(true,StatusCode.OK,"",pageInfo);
    }

    @Override
    @Transactional
    public Result delFpgaStandard(Long id) {
        fpgaDao.delFpgaStandard(id);
        return new Result(true,StatusCode.OK,"");
    }

    @Override
    public Result countOfFpgaStandard(int year) {
        Map<String,Object> count=new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        int[] forDieTotle=new int[12];
        int[] suggTotle=new int[12];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        for(int i=0;i<12;i++){
            calendar.set(Calendar.MONTH,i);
            Date start = calendar.getTime();
            calendar.add(Calendar.MONTH,1);
            Date end = calendar.getTime();
            List<FpgaStandard> fList=fpgaDao.findByMonth(start, end);
            if (fList.size()!=0){
                for (FpgaStandard f:fList){
                    forDieTotle[i]+=f.getNumRuleViolations();
                    suggTotle[i]+=f.getNumViolations();
                }
            }
        }
        count.put("forDieTotle",forDieTotle);
        count.put("suggTotle",suggTotle);
        return new Result(true,StatusCode.OK,"",count);
    }

    @Override
    public Result countOfFpgaByKingdom() {
        Map<String,Object> m=new HashMap<>();
        List<Kingdom> kList=kingdomDao.findAll();
        if (kList.size()!=0){
            for(Kingdom k:kList){
                Map<String,Integer> map=new HashMap<>();
                int forDieTotle=0;
                int suggTotle=0;
                List<FpgaStandard> fList=fpgaDao.findByKingdom(k.getId());
                if (fList.size()!=0){
                    for (FpgaStandard f:fList){
                        forDieTotle += f.getNumRuleViolations();
                        suggTotle += f.getNumViolations();
                    }
                }
                map.put("forDieTotle",forDieTotle);
                map.put("suggTotle",suggTotle);
                m.put(k.getName(),map);
            }
            return new Result(true,StatusCode.OK,"",m);
        }
        return new Result(true,StatusCode.OK,"");
    }
}
