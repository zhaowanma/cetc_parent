package com.cetc.document.controller;

import com.cetc.common.core.entity.Result;
import com.cetc.document.service.DocumentTemplateService;
import com.cetc.model.document.DocumentTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("documentTemplate")
public class DocumentTemplateController {

    @Autowired
    private DocumentTemplateService documentTemplateService;


    @PostMapping("saveDocumentTemplate")
    public Result saveDocumentTemplate(@RequestBody DocumentTemplate documentTemplate) throws IOException {
        return documentTemplateService.saveDocumentTemplate(documentTemplate);

    }


    @GetMapping("delete/{id}")
    public Result delete(@PathVariable long id){
        return documentTemplateService.delete(id);

    }

    @GetMapping("findById/{id}")
    public Result findById(@PathVariable long id){
        return documentTemplateService.findById(id);

    }

    @PostMapping("update")
    public Result update(@RequestBody DocumentTemplate documentTemplate){
        return documentTemplateService.update(documentTemplate);
    }

    @PostMapping("findPageDocumentTemplates")
    public Result findPageDocumentTemplates(@RequestBody Map map){
        return documentTemplateService.fuzzyPageQueryList(map);

    }


}
