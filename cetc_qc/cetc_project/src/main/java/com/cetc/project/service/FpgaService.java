package com.cetc.project.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.project.FpgaStandard;

import java.util.Map;

public interface FpgaService {

    Result addFpgaStandard(FpgaStandard fpgaStandard);

    Result updateFpgaStandard(FpgaStandard fpgaStandard);

    Result pageFpgaStandard(Map<String,Integer> params);

    Result delFpgaStandard(Long id);

    Result countOfFpgaStandard(int year);

    Result countOfFpgaByKingdom();

}
