package com.cetc.admin.exception;


import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(value= IllegalArgumentException.class)
    @ResponseBody
    public Result error(IllegalArgumentException e){
         e.printStackTrace();
         return new Result(true, StatusCode.ERROR,e.getMessage());
    }
}
