package com.cetc.alm.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.dic.DicValue;

import java.util.List;

public interface SQAService {

        Result findSQA(long codeId,int year);

        Result findSQATableField(List<DicValue> dicValues,int year);

        Result getSqaINFO(List<DicValue> dicValues,long codeId);
}
