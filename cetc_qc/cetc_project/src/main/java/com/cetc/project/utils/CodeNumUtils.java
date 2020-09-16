package com.cetc.project.utils;

import com.cetc.model.project.Code;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CodeNumUtils {

    public static Integer getNum(List<Code> codes, Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int y=cal.get(Calendar.YEAR);
        cal.set(y, 0, 1, 0, 0, 0);
        Date startDate=cal.getTime();
        cal.set(y+1, 0, 1, 0, 0, 0);
        Date endDate=cal.getTime();
        int num=y*100;
        if(codes!=null&&codes.size()>0){
            List<Code> list = codes.stream().filter(c -> (c.getCreateDate().after(startDate)||c.getCreateDate().equals(startDate)) && c.getCreateDate().before(endDate)).collect(Collectors.toList());
            for (Code code: list) {
                if(code.getNum()>num){
                    num=code.getNum();
                }
            }
            int asc=num%100;
            if(asc<99){
                num+=1;
            }else {
                num=(num/100)*1000+asc+1;
            }

        }else {
            num+=1;
        }
         return num;
    }


    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();

    }
}
