package com.cetc.project.service.impl;

import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;

import com.cetc.model.dic.DicValue;
import com.cetc.model.project.Code;
import com.cetc.model.project.Kingdom;
import com.cetc.model.project.SQA;
import com.cetc.project.mapper.CodeDao;
import com.cetc.project.mapper.KingdomDao;
import com.cetc.project.mapper.SqaDao;
import com.cetc.project.service.SqaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.Synchronized;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SqaServiceImpl implements SqaService {

    private Logger log = LoggerFactory.getLogger(SqaServiceImpl.class);

    @Autowired
    private SqaDao sqaDao;

    @Autowired
    private KingdomDao kingdomDao;

    @Autowired
    private CodeDao codeDao;

    @Override
    public Result pageByParent(Map<String, Object> params) {
            PageHelper.startPage((int)params.get("pageNum"),(int)params.get("pageSize"));
            List<SQA> sqaList = sqaDao.findByParent(params);
            PageInfo<SQA> pageInfo = new PageInfo<>(sqaList);
            return new Result(true, StatusCode.OK,"",pageInfo);
    }

    @Override
    public Result getMaxIndex() {
        synchronized (this){
            Integer totalNum = sqaDao.getMaxIndex();
            if(totalNum == null){
                totalNum = 1;
            }else{
                totalNum = totalNum + 1;
            }
            log.info(String.valueOf(totalNum));
            return new Result(true, StatusCode.OK,"",totalNum);
        }
    }

    @Override
    public Result add(SQA sqa) {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Date date = new Date();
        sqa.setUpdateBy(principal);
        sqa.setUpdateDate(date);
        if(sqa.getId() == null){// id : null 新增
            synchronized (this){
                Integer totalNum = sqaDao.getMaxIndex();
                if(totalNum == null){
                    totalNum = 1;
                }else{
                    totalNum = totalNum + 1;
                }
                sqa.setTotalNum(totalNum);
                sqa.setCreateBy(principal);
                sqa.setCreateDate(date);
                Code code = codeDao.findOne(sqa.getCodeId());
                sqa.setKingdomId(code.getParentId());
                int count = sqaDao.insertSelective(sqa);
                if(count == 1){
                    return new Result(true, StatusCode.OK,"添加成功！");
                }else{
                    return new Result(false, StatusCode.ERROR,"添加失败!");
                }
            }

        }else{//修改
            int count = sqaDao.updateByPrimaryKeySelective(sqa);
            if(count == 1){
                return new Result(true, StatusCode.OK,"修改成功!");
            }else{
                return new Result(false, StatusCode.OK,"修改失败!");
            }
        }
    }

    @Override
    public Result delete(Long id) {
        if(id != null){
            int count = sqaDao.deleteByPrimaryKey(id);
            if(count == 1){
                return new Result(true, StatusCode.OK,"删除成功!");
            }else{
                return new Result(false, StatusCode.ERROR,"删除失败!");
            }
        }
        return new Result(false, StatusCode.ERROR,"删除失败!");
    }

    @Override
    public Result pageShow(Map<String, Object> params) throws ParseException {
        ArrayList<String> createDate = (ArrayList<String>) params.get("createDate");
        SQA sqa = new SQA();
        Date startTime = null;
        Date endTime = null;
        Long kingdomId = null;
        Long codeId = null;
        if(params.get("kingdomId") == null){
            kingdomId = null;
        }else{
            kingdomId = Long.valueOf((Integer) params.get("kingdomId"));
        }
        if(params.get("codeId") == null){
            codeId = null;
        }else{
            codeId = Long.valueOf((Integer) params.get("codeId"));
        }
        sqa.setKingdomId(kingdomId);
        sqa.setCodeId(codeId);
        String workSource = (String) params.get("workSource");
        String classification = (String) params.get("classification");
        sqa.setWorkSource(workSource);
        sqa.setClassification(classification);
        if(createDate != null && !createDate.isEmpty()){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            String time1 = createDate.get(0);
            String time2 = createDate.get(1);
            time1 = time1.replace("Z", " UTC");
            time2 = time2.replace("Z"," UTC");
            startTime = df.parse(time1);
            endTime = df.parse(time2);
            sqa.setCreateDate(startTime);
            sqa.setUpdateDate(endTime);
        }
        PageHelper.startPage((int)params.get("pageNum"),(int)params.get("pageSize"));
        List<SQA> sqaList =  sqaDao.pageShow(sqa);
        PageInfo<SQA> pageInfo = new PageInfo<>(sqaList);
        return new Result(true, StatusCode.OK,"",pageInfo);
    }

    @Override
    public Result getByYear(List<Date> yesrs) throws ParseException {
        Date startYear = yesrs.get(0);
        Date endYear = yesrs.get(1);
        Calendar c = Calendar.getInstance();
        c.setTime(startYear);
        int start = c.get(Calendar.YEAR);
        c.setTime(endYear);
        int end = c.get(Calendar.YEAR);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList<Integer> list = new ArrayList<>();
        int m = 0;
        for(int i = start; i <= end; i++){
            SQA sqa1 = new SQA();
            Date startTime = sdf.parse(i + "-01-01 00:00:00");
            Date endTime = sdf.parse(i + "-12-31 23:59:59");
            SQA sqa = new SQA();
            sqa.setCreateDate(startTime);
            sqa.setUpdateDate(endTime);
            List<SQA> sqaList = sqaDao.pageShow(sqa);
            list.add(m,sqaList.size());
            m = m+1;
        }
        return new Result(true, StatusCode.OK,"",list);
    }

    @Override
    public Result getSqaCountByKm() {
        List<Kingdom> kingdomList = kingdomDao.findAll();
        ArrayList<Object> objects = new ArrayList<>();
        if(!kingdomList.isEmpty() && kingdomList != null){
            ArrayList<String> keys = new ArrayList<>();
            ArrayList<Integer> values = new ArrayList<>();
            int m = 0;
            for (Kingdom kingdom : kingdomList) {
                List<SQA> sqaList = sqaDao.findByKingDomName(kingdom.getId());
                keys.add(m,kingdom.getName());
                if(sqaList != null && ! sqaList.isEmpty()){
                    values.add(m,sqaList.size());
                }else{
                    values.add(m,0);
                }
            }
            objects.add(keys);
            objects.add(values);
            return new Result(true, StatusCode.OK,"",objects);
        }
        return new Result(true, StatusCode.OK,"",objects);
    }

    @Override
    public Result getByKindDom(String domain) {
        Kingdom kingdom = kingdomDao.findByName(domain);
        List<Code> codes = codeDao.findByKingdom(kingdom.getId());
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<Integer> values = new ArrayList<>();
        ArrayList<Object> objects = new ArrayList<>();
        int m = 0;
        if(!codes.isEmpty() && codes != null){
            for (Code code : codes) {
                List<SQA> sqaList = sqaDao.findByCode(code.getId());
                keys.add(m,code.getValue());
                if(sqaList != null && !sqaList.isEmpty()){
                    values.add(m,sqaList.size());
                }else{
                    values.add(m,0);
                }

            }
        }
        objects.add(keys);
        objects.add(values);
        return new Result(true, StatusCode.OK,"",objects);
    }

    @Override
    public Result AllSQASByKingDom() {
        List<Kingdom> kingdomList = kingdomDao.findAll();
        ArrayList<HashMap<String, Object>> objects = new ArrayList<>();

        if(!kingdomList.isEmpty()){
            for (Kingdom kingdom : kingdomList) {
                HashMap<String, Object> map = new HashMap<>();
                List<SQA> sqaList = sqaDao.findByKingDomName(kingdom.getId());
                map.put("value",sqaList.size());
                map.put("name",kingdom.getName());
                objects.add(map);
            }
        }

        return new Result(true, StatusCode.OK,"",objects);
    }

    @Override
    public Result getSQACompletionRate(List<DicValue> deptList, Date year) {
        try {
            List<Object[]> list = new ArrayList<Object[]>();
            SQA sqa = new SQA();
            Calendar c = Calendar.getInstance();
            c.setTime(year);
            int end = c.get(Calendar.YEAR);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = sdf.parse(end + "-01-01 00:00:00");
            Date endTime = sdf.parse(end + "-12-31 23:59:59");
            sqa.setCreateDate(startTime);
            sqa.setUpdateDate(endTime);
            for (DicValue dept : deptList) {
                sqa.setCheckGroup(dept.getValue());
                //该部门下所有SQA
                List<SQA> sqas = sqaDao.pageShow(sqa);
                sqa.setCompleteStatus("1");
                //该部门下已完成的SQA
                List<SQA> sqas1 = sqaDao.pageShow(sqa);
                Object[] obj = new Object[4];
                obj[0] = dept.getValue();
                if(!sqas.isEmpty() && sqas != null){
                    obj[1] = sqas.size();
                    if(!sqas1.isEmpty() && sqas1 != null){
                        obj[2] = sqas1.size();
                        double rate = (double)sqas1.size()/(double) sqas.size()*100;
                        BigDecimal b = new BigDecimal(rate);
                        rate = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        obj[3] = rate;
                    }else{
                        obj[2] = 0;
                        obj[3] = 0;
                    }
                }else{
                    obj[1] = 0;
                    obj[2] = 0;
                    obj[3] = 0;
                }
                list.add(obj);
            }
            return new Result(true,StatusCode.OK,"",list);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Result(false,StatusCode.OK,"");
        }
    }
}
