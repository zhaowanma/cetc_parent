package com.cetc.project.utils;


import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFComment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordUtils {
    /**
     * @see返回文件批注
     * @param file
     * @return 返回参数 [类型，作者，内容]
     * @throws IOException
     */
    public static List<String[]> getAnnotation(File file) throws IOException {
        List<String[]> list = new ArrayList<String[]>();
        XWPFDocument docx = null;
        try {
            String filename = file.getName();
            if("docx".equals(filename.substring(filename.lastIndexOf(".")+1))) {
                docx = new XWPFDocument(POIXMLDocument.openPackage(
                        file.getCanonicalPath()));
                XWPFComment[] comments = docx.getComments();
                for(XWPFComment comment :comments) {
                    String[] data = new String[3];
                    data[0] = comment.getText().substring(0, 1);
                    data[1] = comment.getAuthor();
                    data[2] = comment.getText().substring(1);
                    list.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(docx!=null) {
                docx.close();
            }
        }
        return list;
    }

    /**
     * @see删除文件
     * @param urls
     */
    public static void deleteFile(String...urls) {
        for(int i=0;i<urls.length;i++) {

            File file = new File(urls[i]);
            System.out.println("文档删除"+urls[i]);
            if(file.exists()) {
                boolean flag = file.delete();
                System.out.println("文档删除结果"+flag);
            }
        }
    }
}
