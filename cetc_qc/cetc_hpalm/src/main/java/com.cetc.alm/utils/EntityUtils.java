package com.cetc.alm.utils;

import com.cetc.common.core.entity.Result;
import com.cetc.model.hpalm.EntityField;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class EntityUtils {

    //生成根测试类型
   public static String createEntityXml(String entityType,Map<String,Object> fields){
        try {
            String prefix="<Entity Type=\'"+entityType.toLowerCase()+"\'>"+"<Fields>";
            for (Map.Entry<String,Object> entry: fields.entrySet()) {
                String xmlItem="<Field Name=\'"+entry.getKey()+"\'>"+"<Value>"+entry.getValue()+"</Value></Field>";
                prefix+=xmlItem;
            }
            prefix=prefix+"</Fields></Entity>";

            return prefix;

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("****************生成alm的XML实体失败");
        }
    }

}
