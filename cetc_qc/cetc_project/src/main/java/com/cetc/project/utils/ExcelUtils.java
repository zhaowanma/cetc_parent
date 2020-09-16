package com.cetc.project.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

   public static Boolean checkFile(MultipartFile file){
     if(file!=null&&!file.isEmpty()){
          String fileName=file.getOriginalFilename();
          if(!fileName.endsWith("xls")){
              return false;
          }
          return true;
     }else {
         return false;
     }
   }

public static Map<Integer, Object> analysicFile(MultipartFile file,int i) throws IOException {
    Workbook workbook = null;
    InputStream inputStream = file.getInputStream();
    workbook = new HSSFWorkbook(inputStream);
    Map<Integer, Object> data = new HashMap<>();
    if (workbook != null) {
        Sheet sheet = workbook.getSheetAt(i);
        if (sheet != null) {
            int firstRowNum = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();
            for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                Row row = sheet.getRow(rowNum);
                if(row!=null){
                    int firstCellNum = row.getFirstCellNum();
                    int lastCellNum = row.getLastCellNum();
                    List<Object> cellList = new ArrayList<>();
                    for(int cellNum=firstCellNum;cellNum<lastCellNum;cellNum++){
                        Cell cell = row.getCell(cellNum);
                        if(cell!=null){
                            CellType cellType = cell.getCellType();
                            if("STRING".equals(cellType.toString())){
                                cellList.add(cell.getStringCellValue());
                            }else if("NUMERIC".equals(cellType.toString())){
                                short dataFormat = cell.getCellStyle().getDataFormat();
                                if(dataFormat==14){
                                    cellList.add(cell.getDateCellValue());
                                }else {
                                    cell.setCellType(CellType.STRING);
                                    cellList.add(cell.getStringCellValue());
                                }

                            }else {
                                cellList.add("");
                            }
                        }else {
                            cellList.add("");
                        }
                    }
                    data.put(rowNum,cellList);
                }

            }
        }
    }
    return data;
}
}
