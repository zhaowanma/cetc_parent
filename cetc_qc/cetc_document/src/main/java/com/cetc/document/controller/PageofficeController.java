package com.cetc.document.controller;

import com.cetc.common.core.entity.Result;
import com.cetc.document.service.DocumentTemplateService;
import com.cetc.model.document.DocumentTemplate;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

@RestController
public class PageofficeController {

    @Autowired
    private DocumentTemplateService documentTemplateService;

    @RequestMapping(value="/index", method= RequestMethod.GET)
    public ModelAndView showIndex(){
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }

    /**
     * office online打开
     *
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value="/word", method=RequestMethod.GET)
    public ModelAndView showWord(HttpServletRequest request, Map<String,Object> map) throws Exception {

        long templateId =Long.valueOf(request.getParameter("templateId")) ;
        Result template = documentTemplateService.findById(templateId);
        DocumentTemplate documentTemplate = (DocumentTemplate)template.getData();
        String docLocation = documentTemplate.getDocLocation();
        File file = new File(docLocation);
        String absolutePath = file.getAbsolutePath();

        //--- PageOffice的调用代码 开始 -----
        PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
        poCtrl.setServerPage("/poserver.zz");//设置授权程序servlet
        poCtrl.addCustomToolButton("保存","Save()",1); //添加自定义按钮
        poCtrl.addCustomToolButton("打印", "PrintFile()", 6);
        poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
        poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
        poCtrl.setSaveFilePage("/save");//设置保存的action
        poCtrl.webOpen(absolutePath, OpenModeType.docAdmin,"张三");
        poCtrl.setCaption("软件质量管控平台");
        map.put("template",poCtrl.getHtmlCode("PageOfficeCtrl1"));
        //--- PageOffice的调用代码 结束 -----
        ModelAndView mv = new ModelAndView("word");
        return mv;
    }

    /**
     * 保存office
     *
     * @param request
     * @param response
     */
    @RequestMapping("/save")
    public void saveFile(HttpServletRequest request, HttpServletResponse response){
        FileSaver fs = new FileSaver(request, response);
        //保存文件
        fs.saveToFile("D:\\test.docx");
        fs.close();
    }


}
