package com.cetc.alm.service.impl;
import com.cetc.alm.dao.AlmConfigDao;
import com.cetc.alm.dao.CodeDao;
import com.cetc.alm.dao.DocumentCheckDao;
import com.cetc.alm.service.AuthencateService;
import com.cetc.alm.service.DocCheckService;
import com.cetc.alm.service.EntityService;
import com.cetc.alm.utils.EntityUtils;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.model.hpalm.AlmConfig;
import com.cetc.model.hpalm.EntityField;
import com.cetc.model.hpalm.EntityFieldListValue;
import com.cetc.model.hpalm.EntityFieldListValueItem;
import com.cetc.model.project.Code;
import com.cetc.model.project.DocumentCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DocCheckServiceImpl implements DocCheckService {

    @Autowired
    private AuthencateService authencateService;

    @Autowired
    private AlmConfigDao almConfigDao;

    @Autowired
    private EntityService entityService;

    @Autowired
    private DocumentCheckDao documentCheckDao;

    @Autowired
    private CodeDao codeDao;

    @Override
    public Result docCheckToAlm(Map map) {
        List<String> cookieList =null;
        AlmConfig almConfig=almConfigDao.findAlmConfig();
        try {
            String almDomainName = (String)map.get("docCheckAlmDomain");
            String almProjectName = (String)map.get("docCheckAlmProject");
            int id=(int)map.get("docCheckId");
            DocumentCheck documentCheck = documentCheckDao.findById(id);
            String checker=documentCheck.getChecker();
            String designer=documentCheck.getDesigner();
            String docName=documentCheck.getFileName();
            String kingdomName=documentCheck.getKingdom();
            Long codeId = documentCheck.getCodeId();
            Code code = codeDao.findOne(codeId);
            String checkMonth="2";
            String company=documentCheck.getCompany();
            int matter=documentCheck.getMatterCount();
            String docType=documentCheck.getDocType();
            int pageSize=documentCheck.getPageSize();
            String matterType="一致性";
            String version=documentCheck.getVersion();
            String sysConfItem=documentCheck.getSysConfItem();

            cookieList = authencateService.login(almConfig);
            Result defect = entityService.findEntityFields(almDomainName, almProjectName, "defect", almConfig, cookieList);
            Result result = entityService.queryEntityListValues(almDomainName, almProjectName, "defect", almConfig, cookieList);
            List<EntityField> entityFields=( List<EntityField>)defect.getData();
            Map fileldListValue = (Map) result.getData();
            Map<String, Object> entityFieldsResult = new HashMap<>();
            for (EntityField entityField: entityFields) {
                if(entityField.getRequired()){
                    if("LookupList".equals(entityField.getType())){
                        EntityFieldListValue entityFieldListValue = (EntityFieldListValue)fileldListValue.get(entityField.getListId());
                        List<EntityFieldListValueItem> items = entityFieldListValue.getItems();
                        for (EntityFieldListValueItem entityFieldListValueItem: items) {
                            if(entityFieldListValueItem.getValue()!=null&&!"".equals(entityFieldListValueItem.getValue())){
                                entityFieldsResult.put(entityField.getName(),entityFieldListValueItem.getValue());
                                break;
                            }
                        }
                    }else if("String".equals(entityField.getType())) {
                        entityFieldsResult.put(entityField.getName(),"空缺占位");
                    }else if("DateTime".equals(entityField.getType())||"Date".equals(entityField.getType())){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String format = sdf.format(new Date());
                        entityFieldsResult.put(entityField.getName(),format);
                    }else if("Number".equals(entityField.getType())){
                        entityFieldsResult.put(entityField.getName(),100000);
                    }else {
                        entityFieldsResult.put(entityField.getName(),"字段缺失");
                    }
                }else if("产品代号".equals(entityField.getLabel())&&"String".equals(entityField.getType())){
                        entityFieldsResult.put(entityField.getName(),code.getValue());
                }
                else if("质量工作考核人".equals(entityField.getLabel())&&checkDocValueExistList(entityField,checker, fileldListValue)){
                    entityFieldsResult.put(entityField.getName(),checker);
                }else if("分配给".equals(entityField.getLabel())&&checkDocValueExistList(entityField,designer, fileldListValue)){
                    entityFieldsResult.put(entityField.getName(),designer);
                }else if ("项目领域".equals(entityField.getLabel())&&checkDocValueExistList(entityField,kingdomName, fileldListValue)){
                    entityFieldsResult.put(entityField.getName(),kingdomName);
                }else if ("被考核单位".equals(entityField.getLabel())&&checkDocValueExistList(entityField,company, fileldListValue)){
                    entityFieldsResult.put(entityField.getName(),company);
                }else if ("问题个数".equals(entityField.getLabel())){
                    entityFieldsResult.put(entityField.getName(),matter);
                }else if ("文档检查月份".equals(entityField.getLabel())&&checkDocValueExistList(entityField,checkMonth, fileldListValue)){
                    entityFieldsResult.put(entityField.getName(),checkMonth);
                }else if ("文档类型".equals(entityField.getLabel())&&checkDocValueExistList(entityField,docType, fileldListValue)){
                    entityFieldsResult.put(entityField.getName(),docType);
                }else if ("文档页数".equals(entityField.getLabel())){
                    entityFieldsResult.put(entityField.getName(),pageSize);
                }else if ("问题类型".equals(entityField.getLabel())&&checkDocValueExistList(entityField,matterType, fileldListValue)){
                    entityFieldsResult.put(entityField.getName(),matterType);
                }else if ("文档名称".equals(entityField.getLabel())){
                    entityFieldsResult.put(entityField.getName(),docName);
                }else if ("检测于版本".equals(entityField.getLabel())&&checkDocValueExistList(entityField,version, fileldListValue)){
                    entityFieldsResult.put(entityField.getName(),version);
                }else if ("系统配置项名称".equals(entityField.getLabel())){
                    entityFieldsResult.put(entityField.getName(),sysConfItem);
                }
            }
            if(!entityFieldsResult.isEmpty()){
                String docCheckXml = EntityUtils.createEntityXml("defect", entityFieldsResult);
                String url=almConfigDao.findAlmConfig().getUrl()+"/rest/domains/"+almDomainName+"/projects/"+almProjectName+"/defects";
                entityService.createEntity(docCheckXml,url,cookieList);
            }
            return new Result(true, StatusCode.OK,"导入文档审查成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(true, StatusCode.OK,"导入文档审查失败");
        }finally {
            if(cookieList!=null){
                authencateService.logout(almConfig,cookieList);
            }
        }
    }

    public Boolean checkDocValueExistList(EntityField entityField,String value,Map fileldListValue){
        if("String".equals(entityField.getType())){
            return true;
        }
        EntityFieldListValue entityFieldListValue = (EntityFieldListValue)fileldListValue.get(entityField.getListId());
        if(entityFieldListValue!=null){
            List<EntityFieldListValueItem> items = entityFieldListValue.getItems();
            for (EntityFieldListValueItem entityFieldListValueItem: items) {
                if(value.equals(entityFieldListValueItem.getValue())){
                    return true;
                }
            }
        }
      return false;
    }

}
