package com.cetc.admin;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {

    public static void main(String[] args) throws IOException {
        FileInputStream fis=null;
        try {
            File file = new File("C:\\Users\\iuit\\Desktop\\111111.txt");
            fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            StringBuffer buffer = new StringBuffer();
            char[] buf = new char[64];
            int temp=0;
            while((temp=isr.read(buf))!=-1){
                System.out.println(temp);
               // buffer.append(buf,0,temp);
                //System.out.println(buffer);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            fis.close();
        }

    }
}
