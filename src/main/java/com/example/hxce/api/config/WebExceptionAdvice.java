package com.example.hxce.api.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class WebExceptionAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HashMap validateException(MethodArgumentNotValidException e) {
        HashMap map = new HashMap();
        //提取异常信息
        String message=e.getBindingResult().getFieldError().getDefaultMessage();
        map.put("error",message);
        return map;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public HashMap runException(RuntimeException e) {
        Throwable rootCause = getRootCause(e);
        HashMap map = new HashMap();
        if(rootCause instanceof RuntimeException==false){
            e.printStackTrace();

            map.put("error","执行异常");
        }
        else{
            String message=e.getMessage();
            map.put("error",message);
        }
        return map;
    }

    private Throwable getRootCause(Throwable throwable){
        Throwable rootCause = throwable.getCause();
        return rootCause == null ? throwable : getRootCause(rootCause);
    }



}
