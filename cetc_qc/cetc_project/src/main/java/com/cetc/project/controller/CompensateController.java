package com.cetc.project.controller;
import com.cetc.common.core.entity.Result;
import com.cetc.project.service.CompensateService;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("compensate")
public class CompensateController {
    private final static Logger logger= LoggerFactory.getLogger(CompensateController.class);

    @Autowired
    private CompensateService compensateService;

    @GetMapping("download")
    public void download( HttpServletResponse response) throws UnsupportedEncodingException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("产品级数据");
        HSSFSheet sheet1 = wb.createSheet("项目级数据");
        HSSFRow row = sheet.createRow( 0);
        HSSFRow projectRow = sheet1.createRow( 0);
        for(int i=0;i<12;i++){
            HSSFCell cell = row.createCell(i);
            switch (i){
                case 0:
                    cell.setCellValue("产品令号（树形令号节点显示值）");
                    break;
                case 1:
                    cell.setCellValue("所属领域");
                    break;
                case 2:
                    cell.setCellValue("软件名称");
                    break;
                case 3:
                    cell.setCellValue("组长");
                    break;
                case 4:
                    cell.setCellValue("总体负责人");
                    break;
                case 5:
                    cell.setCellValue("任命时间");
                    break;
                case 6:
                    cell.setCellValue("软件总体（或开发人员）");
                    break;
                case 7:
                    cell.setCellValue("电讯总体");
                    break;
                case 8:
                    cell.setCellValue("项目主管");
                    break;
                case 9:
                    cell.setCellValue("质量师");
                    break;
                case 10:
                    cell.setCellValue("是否要号");
                    break;
                case 11:
                    cell.setCellValue("是否在研");
                    break;
            }
        }

        for(int i=0;i<16;i++){
            HSSFCell cell = projectRow.createCell(i);
            switch (i){
                case 0:
                    cell.setCellValue("软件名称（树形项目节点显示值）");
                    break;
                case 1:
                    cell.setCellValue("所属领域");
                    break;
                case 2:
                    cell.setCellValue("所属令号");
                    break;
                case 3:
                    cell.setCellValue("测试负责人");
                    break;
                case 4:
                    cell.setCellValue("任命时间");
                    break;
                case 5:
                    cell.setCellValue("软件总体(或开发人员)");
                    break;
                case 6:
                    cell.setCellValue("电讯总体");
                    break;
                case 7:
                    cell.setCellValue("项目主管");
                    break;
                case 8:
                    cell.setCellValue("质量师");
                    break;
                case 9:
                    cell.setCellValue("ALM域名");
                    break;
                case 10:
                    cell.setCellValue("ALM项目名");
                    break;
                case 11:
                    cell.setCellValue("项目序号");
                    break;
                case 12:
                    cell.setCellValue("是否要号");
                    break;
                case 13:
                    cell.setCellValue("是否在研");
                    break;
            }
        }
        response.setContentType("application/force-download");// 设置强制下载不打开
        String fileName = URLEncoder.encode("历史数据.xls", "UTF-8");
        response.addHeader("Content-Disposition","attachment;fileName="+fileName);
        OutputStream os=null;
        try {
            os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        }catch (Exception e){
            e.printStackTrace();
            logger.error("下载失败");
        }
    }

    @PostMapping("importTemplate")
    public Result importTemplate(@RequestBody MultipartFile file){
       return compensateService.importTemplate(file);
    }

}
