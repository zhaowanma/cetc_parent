package com.cetc.document.service.impl;

import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.common.core.utils.LoginUserUtil;
import com.cetc.document.dao.DocumentTemplateDao;
import com.cetc.document.service.DocumentTemplateService;
import com.cetc.model.document.DocumentTemplate;
import com.cetc.model.project.Code;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DocumentTemplateServiceImpl implements DocumentTemplateService {

    @Value("${file.documentTemplate.path}")
    private String documentTemplatePath;

    @Autowired
    private DocumentTemplateDao documentTemplateDao;

    @Override
    @Transactional
    public Result saveDocumentTemplate(DocumentTemplate documentTemplate) throws IOException {
        String documentName= UUID.randomUUID().toString();
        String dictoryName=documentTemplatePath+File.separator+documentName;
        Path dictoryPath = Paths.get(dictoryName);
        if(!Files.exists(dictoryPath)){
            try {
                Files.createDirectories(dictoryPath);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        String docName=documentTemplate.getDocName();
        String location=documentTemplatePath+File.separator+documentName+File.separator+docName+".docx";
        FileOutputStream outputStream=null;
        try{
            XWPFDocument doc = new XWPFDocument();
            XWPFParagraph paragraph = doc.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText("如今的现在早已不是曾今说好的以后。。。");
            outputStream = new FileOutputStream(location);
            doc.write(outputStream);
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(outputStream!=null){
                outputStream.close();
            }
        }
        documentTemplate.setCreateBy(LoginUserUtil.getLoginSysUser().getUsername());
        documentTemplate.setCreateDate(new Date());
        documentTemplate.setUpdateBy(LoginUserUtil.getLoginSysUser().getUsername());
        documentTemplate.setUpdateDate(new Date());
        documentTemplate.setUploader(LoginUserUtil.getLoginSysUser().getUsername());
        documentTemplate.setDocLocation(location);
        documentTemplateDao.save(documentTemplate);
        return new Result(true, StatusCode.OK,"文档模板保存成功");
    }

    @Override
    public Result delete(long id) {
        documentTemplateDao.delete(id);
        return new Result(true,StatusCode.OK,"文档模板删除成功");
    }

    @Override
    public Result update(DocumentTemplate documentTemplate) {
         documentTemplateDao.update(documentTemplate);
        return new Result(true,StatusCode.OK,"文档模板修改成功");
    }

    @Override
    public Result fuzzyPageQueryList(Map map) {
        PageHelper.startPage((int)map.get("pageNum"),(int)map.get("pageSize"));
        List<DocumentTemplate> documentTemplates = documentTemplateDao.fuzzyQueryList(map);
        PageInfo<DocumentTemplate> pageInfo = new PageInfo<>(documentTemplates);
        return new Result(true,StatusCode.OK,"文档模板模糊查询成功",pageInfo);
    }

    @Override
    public Result findById(long id) {
        DocumentTemplate documentTemplate = documentTemplateDao.findById(id);
        return new Result(true,StatusCode.OK,"文档模板查询成功",documentTemplate);
    }
}
