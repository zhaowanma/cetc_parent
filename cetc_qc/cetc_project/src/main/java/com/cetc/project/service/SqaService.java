package com.cetc.project.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.dic.DicValue;
import com.cetc.model.project.SQA;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SqaService {
    Result pageByParent(Map<String, Object> params);

    Result getMaxIndex();

    Result add(SQA sqa);

    Result delete(Long id);

    Result pageShow(Map<String, Object> params) throws ParseException;

    Result getByYear(List<Date> yesrs) throws ParseException;

    Result getSqaCountByKm();

    Result getByKindDom(String domain);

    Result AllSQASByKingDom();

    Result getSQACompletionRate(List<DicValue> depeList, Date year);
}
