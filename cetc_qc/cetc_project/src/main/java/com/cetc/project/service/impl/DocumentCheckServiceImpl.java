package com.cetc.project.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.cetc.common.core.entity.DocumentLevel;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.model.project.*;
import com.cetc.project.entities.SearchAnnotation;
import com.cetc.project.entities.SearchDocumentCheck;
import com.cetc.project.feign.DicClient;
import com.cetc.project.mapper.*;
import com.cetc.project.service.DocumentCheckService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.cetc.project.utils.WordUtils.*;

@Service
public class DocumentCheckServiceImpl implements DocumentCheckService {
    public Logger log = LoggerFactory.getLogger(DocumentCheckServiceImpl.class);
    @Autowired
    private DocumentCheckDao documentCheckDao;
    @Autowired
    private AnnotationDao annotationDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private KingdomDao kingdomDao;
    @Autowired
    private CodeDao codeDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private DicClient dicClient;
    @Override
    @Transactional
    public Result add(Map<String,Object> map) {
        log.info("添加文档审查");
        String uuid = (String) map.get("uuid");
        //获取documentCheck
        Object docObject = map.get("documentCheck");
        String docStr = JSONUtils.toJSONString(docObject);
        DocumentCheck documentCheck = JSONObject.parseObject(docStr, DocumentCheck.class);
        documentCheck.setAnnoFlag(uuid);
        //获取当前用户名
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Date current = new Date();
        int size = annotationDao.findByUuid(uuid).size();
        documentCheck.setMatterCount(size);
        documentCheck.setUpdateBy(principal);
        documentCheck.setUpdateDate(current);
        //
        if(DocumentLevel.codeLevel.equals(documentCheck.getCheckLevel())){
            Code code = codeDao.findOne(documentCheck.getParentId());
            Kingdom kingdom = kingdomDao.findOne(code.getParentId());
            documentCheck.setCodeId(code.getId());
            documentCheck.setCode(code.getValue());
            documentCheck.setKingdomId(kingdom.getId());
            documentCheck.setKingdom(kingdom.getName());
        }else if(DocumentLevel.projectLevel.equals(documentCheck.getCheckLevel())){
            Project project = projectDao.findOne(documentCheck.getParentId());
            Code code = codeDao.findOne(project.getParentId());
            Kingdom kingdom = kingdomDao.findOne(code.getParentId());
            documentCheck.setKingdom(kingdom.getName());
            documentCheck.setKingdomId(kingdom.getId());
            documentCheck.setCode(code.getValue());
            documentCheck.setCodeId(code.getId());
            documentCheck.setProject(project.getName());
            documentCheck.setProjectId(project.getId());
        }
         //保存文档
        if(documentCheck.getId()==null){
            documentCheck.setCreateBy(principal);
            documentCheck.setCreateDate(current);
            documentCheckDao.addDocument(documentCheck);
        }
        //更新备注问题的parentID，同时也要指定等级
        annotationDao.setParent(uuid,documentCheck.getId(),documentCheck.getCheckLevel());
        return new Result(true, StatusCode.OK,"ok");
    }

    @Override
    public Result update(DocumentCheck documentCheck) {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Date current = new Date();
        documentCheck.setUpdateDate(current);
        documentCheck.setUpdateBy(principal);
        documentCheckDao.updataDocument(documentCheck);
        return new Result(true,StatusCode.OK,"保存成功");
    }

    @Override
    public Result pageList(SearchDocumentCheck searchDocumentCheck) {
        PageHelper.startPage(searchDocumentCheck.getPageNum(),searchDocumentCheck.getPageSize());
        List<DocumentCheck> checkList = documentCheckDao.queryList(searchDocumentCheck);
        PageInfo<DocumentCheck> pageInfo = new PageInfo<>(checkList);
        return new Result(true,StatusCode.OK,"ok",pageInfo);
    }

    @Override
    @Transactional
    public Result deleteOne(DocumentCheck documentCheck) {

        //删除文档
        documentCheckDao.deleteOne(documentCheck.getId());
        //删除文档对应的备注
        SearchAnnotation searchAnnotation = new SearchAnnotation();
        searchAnnotation.setParentId(documentCheck.getId());
        searchAnnotation.setAnnoLevel(documentCheck.getCheckLevel());
        annotationDao.deleteAnnotation(searchAnnotation);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @Override
    @Transactional
    public Result analysisFile(String path) {
        if(!"docx".equals(path.substring(path.lastIndexOf(".")+1))){
            return new Result(false,StatusCode.ERROR,"请选择.docx文档");
        }
           SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
           String redisKey = UUID.randomUUID().toString().replace("-", "")+sdf.format(new Date());
            new Thread(()->{
                try{
                    //分析开始，分析超过1小时默认失败
                    redisTemplate.opsForValue().set(redisKey,"analysising",60, TimeUnit.MINUTES);
                    //开始解析，将状态存在redis
                    Integer pdfPages = 0;
                    //获取文档的批注
                    List<String[]> annotations = getAnnotation(new File(path));
                    Date currentDate = new Date();
                    for (String[] annotation : annotations) {
                        Annotation a = new Annotation(annotation[0],annotation[1],annotation[2],redisKey,currentDate);
                        annotationDao.addAnnotation(a);
                    }
                    //查询页数方法
                    XWPFDocument docx = new XWPFDocument(POIXMLDocument.openPackage(path));
                    pdfPages = docx.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
                    //更新分析状态
                    redisTemplate.opsForValue().set(redisKey,"analysised",60, TimeUnit.MINUTES);
                    //文档页数保存在redis中
                    redisTemplate.opsForValue().set(redisKey+"result",pdfPages,60, TimeUnit.MINUTES);

                }catch (Exception e){
                    e.printStackTrace();
                    redisTemplate.opsForValue().set(redisKey,"analysisFailed",60, TimeUnit.MINUTES);
                }
            }).start();
            return new Result(true,StatusCode.OK,"",redisKey);
    }

    @Override
    public Result pageAnnoList(SearchAnnotation searchAnnotation) {
        PageHelper.startPage(searchAnnotation.getPageNum(),searchAnnotation.getPageSize());
        List<Annotation> annotationList = annotationDao.queryList(searchAnnotation);
        PageInfo<Annotation> info = new PageInfo<>(annotationList);
        return new Result(true,StatusCode.OK,"查询成功",info);
    }

    @Override
    @Transactional
    public Result deleteAnno(Annotation annotation) {
        annotationDao.deleteOne(annotation.getId());
        //判断注解是否有ParentID,如果有则更新对应父级的问题数
        if(annotation.getParentId()!=null){
            SearchAnnotation searchAnnotation = new SearchAnnotation();
            searchAnnotation.setParentId(annotation.getParentId());
            searchAnnotation.setAnnoLevel(annotation.getAnnoLevel());
            List<Annotation> annotationList = annotationDao.queryList(searchAnnotation);
            DocumentCheck documentCheck = new DocumentCheck();
            documentCheck.setId(annotation.getParentId());
            documentCheck.setMatterCount(annotationList.size());
            documentCheckDao.updataDocument(documentCheck);
        }
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @Override
    public Result getAnalysisStatus(String uuid) {
        String status = (String)redisTemplate.opsForValue().get(uuid);
        if("analysised".equals(status)){
            int resule = (int)redisTemplate.opsForValue().get(uuid+"result");
            return new Result(true,StatusCode.OK,"分析完毕",resule);
        }else if("analysising".equals(status)){
            return new Result(false,StatusCode.OK,"分析中");
        }else{
            return new Result(true,StatusCode.ERROR,"分析失败");
        }

    }

    @Override
    public Result getAnnoByUuid(Map<String,String> map) {
        PageHelper.startPage(Integer.parseInt(map.get("pageNum")),Integer.parseInt(map.get("pageSize")));
        List<Annotation> annotationList = annotationDao.findByUuid(map.get("uuid"));
        PageInfo<Annotation> info = new PageInfo<>(annotationList);
        return new Result(true,StatusCode.OK,"",info);
    }

    @Override
    public Result saveFile(MultipartFile file) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(".");
            sb.append(File.separator);
            sb.append("documentCheckFiles");
            sb.append(File.separator);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            sb.append(sdf.format(new Date()));
            //创建文件夹
            if(!Files.isWritable(Paths.get(sb.toString()))){
                try {
                    Files.createDirectories(Paths.get(sb.toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            byte[] bytes = file.getBytes();
            //保存后的文件名
            sb.append("/");
            sb.append(file.getOriginalFilename());
            String newPath = sb.toString();
            Path path = Paths.get(newPath);
            Files.write(path,bytes);
            return new Result(true, StatusCode.OK,"",newPath);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"");
        }

    }

    @Override
    public Result countOfDocAndAnnotationAndPageSize(SearchDocumentCheck searchDocumentCheck) {
        Map<String,Object> map = new HashMap<>();
        Integer[] documentArray = new Integer[12];
        Integer[] annotationArray = new Integer[12];
        Integer[] pageSizeArray = new Integer[12];
        Calendar calendar = Calendar.getInstance();
        if(searchDocumentCheck.getYear()!=null&&!"".equals(searchDocumentCheck.getYear())){
            calendar.set(Calendar.YEAR,searchDocumentCheck.getYear());
        }
        for(int i=0;i<12;i++){
            calendar.set(Calendar.MONTH,i);  //设置月份
            calendar.set(Calendar.DAY_OF_MONTH,1); //设置日期
            calendar.set(Calendar.HOUR_OF_DAY,0);//设置小时
            calendar.set(Calendar.MINUTE,0);//设置分钟
            calendar.set(Calendar.SECOND,0);//设置秒
            Date start = calendar.getTime();
            calendar.add(Calendar.MONTH,1);
            Date end = calendar.getTime();
            searchDocumentCheck.setStart(start);
            searchDocumentCheck.setEnd(end);
            List<DocumentCheck> documentCheckList = documentCheckDao.queryList(searchDocumentCheck);
                documentArray[i]=documentCheckList.size();
            int totalAnnotation = 0;
            int totalPage = 0;
            for (DocumentCheck documentCheck : documentCheckList) {
                SearchAnnotation searchAnnotation = new SearchAnnotation();
                searchAnnotation.setAnnoLevel(documentCheck.getCheckLevel());
                searchAnnotation.setParentId(documentCheck.getId());
                totalAnnotation += annotationDao.queryList(searchAnnotation).size();
                totalPage += documentCheck.getPageSize();
            }
            annotationArray[i] = totalAnnotation;
            pageSizeArray[i] = totalPage;
        }
        map.put("document",documentArray);
        map.put("annotation",annotationArray);
        map.put("page",pageSizeArray);

        return new Result(true,StatusCode.OK,"",map);
    }

    @Override
    public Result countOfDocType(SearchDocumentCheck searchDocumentCheck) {
        List<Map<String, Object>> typeList = (List)dicClient.findByName("document_type").getData();
        Map<String,Integer> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,0);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        searchDocumentCheck.setStart(calendar.getTime());
        calendar.add(Calendar.YEAR,1);
        searchDocumentCheck.setEnd(calendar.getTime());
        for (Map<String, Object> stringObjectMap : typeList) {
            searchDocumentCheck.setDocType((String) stringObjectMap.get("value"));
            List<DocumentCheck> list = documentCheckDao.queryList(searchDocumentCheck);
            map.put((String) stringObjectMap.get("value"),list.size());
        }

        return new Result(true,StatusCode.OK,"",map);
    }
    //应该是根据令号查询
    @Override
    public Result countOfAnnotationType(SearchDocumentCheck searchDocumentCheck) {
        List<Map<String, Object>> typeList = (List)dicClient.findByName("annotation_type").getData();
        Map<String,Integer> map = new HashMap<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,0);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        Date start = calendar.getTime();
        calendar.add(Calendar.YEAR,1);
        Date end = calendar.getTime();
        //设置文档的日期范围
        searchDocumentCheck.setStart(start);
        searchDocumentCheck.setEnd(end);
        List<DocumentCheck> documentCheckList = documentCheckDao.queryList(searchDocumentCheck);
        //遍历问题类型
        for (Map<String, Object> stringObjectMap : typeList) {
            int annosize = 0;
            SearchAnnotation searchAnnotation = new SearchAnnotation();
            searchAnnotation.setAnnoType((String) stringObjectMap.get("value"));
            //遍历文档
            for (DocumentCheck documentCheck : documentCheckList) {
                searchAnnotation.setAnnoLevel(searchDocumentCheck.getCheckLevel());
                searchAnnotation.setParentId(documentCheck.getId());
                List<Annotation> list = annotationDao.queryList(searchAnnotation);
                annosize += list.size();
            }

            map.put((String) stringObjectMap.get("key"),annosize);
        }

        return new Result(true,StatusCode.OK,"",map);
    }


}
