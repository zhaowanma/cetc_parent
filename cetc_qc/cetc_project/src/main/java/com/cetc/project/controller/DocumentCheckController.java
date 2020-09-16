package com.cetc.project.controller;

import com.cetc.common.core.entity.Result;
import com.cetc.model.log.LogAnnotation;
import com.cetc.model.project.Annotation;
import com.cetc.model.project.DocumentCheck;
import com.cetc.project.entities.SearchAnnotation;
import com.cetc.project.entities.SearchDocumentCheck;
import com.cetc.project.entities.SearchProject;
import com.cetc.project.service.DocumentCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
@RestController
@RequestMapping("/documentcheck")
public class DocumentCheckController {
    @Autowired
    private DocumentCheckService documentCheckService;

    @PostMapping("add" )
    @LogAnnotation(module = "添加文档审查")
    public Result add(@RequestBody Map<String,Object> map){
        return documentCheckService.add(map);
    }

    @PostMapping("update")
    @LogAnnotation(module = "更新文档审查")
    public Result update(@RequestBody DocumentCheck documentCheck){
        return documentCheckService.update(documentCheck);
    }

    @DeleteMapping("del")
    @LogAnnotation(module = "删除文档审查")
    public Result delete(@RequestBody DocumentCheck documentCheck){
        return documentCheckService.deleteOne(documentCheck);
    }

    @PostMapping("pageList")
    public Result pageList(@RequestBody SearchDocumentCheck searchDocumentCheck){
        return documentCheckService.pageList(searchDocumentCheck);
    }

    @PostMapping("analysisFile")
    public Result analysisFile(@RequestBody Map<String ,String> map){

        return documentCheckService.analysisFile(map.get("filePath"));
    }

    @PostMapping("findAnnoList")
    public Result findAnnoList(@RequestBody SearchAnnotation searchAnnotation){
        return documentCheckService.pageAnnoList(searchAnnotation);
    }

    @PostMapping("delAnnotaton")
    @LogAnnotation(module = "删除文档批注")
    public Result delAnnotaton(@RequestBody Annotation annotation){
        return documentCheckService.deleteAnno(annotation);
    }

    @GetMapping("getAnalysisStatus/{uuid}")
    public Result getAnalysisStatus(@PathVariable String uuid){
        return documentCheckService.getAnalysisStatus(uuid);
    }

    @PostMapping("getAnnoByUuid")
    public Result getAnnoByUuid(@RequestBody Map<String,String> map){
        return documentCheckService.getAnnoByUuid(map);
    }

    @PostMapping("uploadFile")
    @LogAnnotation(module = "上传待审查文件")
    public Result uploadFile(@RequestBody MultipartFile file){
        return documentCheckService.saveFile(file);
    }

    /**
     * 每个月文档审查，问题数，页数统计
     * @param searchDocumentCheck
     * @return
     */
    @PostMapping("countOfDocAndAnnotationAndPageSize")
    public Result countOfDocAndAnnotationAndPageSize(@RequestBody SearchDocumentCheck searchDocumentCheck){
      return documentCheckService.countOfDocAndAnnotationAndPageSize(searchDocumentCheck);
    }

    /**
     * 统计每个测试类型的文档数
     * @param searchDocumentCheck
     * @return
     */
    @PostMapping("countOfDocType")
    public Result countOfDocType(@RequestBody SearchDocumentCheck searchDocumentCheck){
        return documentCheckService.countOfDocType(searchDocumentCheck);
    }

    @PostMapping("countOfAnnotationType")
    public Result countOfAnnotationType(@RequestBody SearchDocumentCheck searchDocumentCheck){
        return documentCheckService.countOfAnnotationType(searchDocumentCheck);
    }


}
