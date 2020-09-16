package com.cetc.project.controller;

import com.cetc.common.core.entity.Result;
import com.cetc.model.project.FpgaStandard;
import com.cetc.project.service.FpgaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("fpga")
public class FpgaController {

    @Autowired
    FpgaService fpgaService;

    @PostMapping("addFpgaStandard")
    public Result addFpgaStandard(@RequestBody FpgaStandard fpgaStandard){
        System.out.println(fpgaStandard+"8888888888888888888");
        return fpgaService.addFpgaStandard(fpgaStandard);
    }

    @PostMapping("updateFpgaStandard")
    public Result updateFpgaStandard(@RequestBody FpgaStandard fpgaStandard){
        return fpgaService.updateFpgaStandard(fpgaStandard);
    }

    @PostMapping("pageFpgaStandard")
    public Result pageFpgaStandard(@RequestBody Map<String,Integer> params){
        return fpgaService.pageFpgaStandard(params);
    }

    @DeleteMapping("delFpgaStandard/{id}")
    public Result delFpgaStandard(@PathVariable Long id){
        return fpgaService.delFpgaStandard(id);
    }

    @PostMapping("countOfFpgaStandardByYear")
    public Result countOfFpgaStandard(@RequestBody Map<String,String> params){
        return fpgaService.countOfFpgaStandard(Integer.parseInt(params.get("year")));
    }

    @GetMapping("countOfFpgaByKingdom")
    public Result countOfFpgaByKingdom(){
        return fpgaService.countOfFpgaByKingdom();
    }

}
