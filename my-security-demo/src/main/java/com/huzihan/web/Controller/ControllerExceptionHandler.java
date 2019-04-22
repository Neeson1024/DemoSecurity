package com.huzihan.web.Controller;

import com.huzihan.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice   //处理其他Controller抛出来的异常
public class ControllerExceptionHandler {

    @ExceptionHandler(UserNotExistException.class)//处理UserNotExistException异常
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> handlerUserNotExistException(UserNotExistException ex){
        Map<String,Object> map = new HashMap<>();
        map.put("id" ,ex.getId());
        map.put("message",ex.getMessage());
        return map;
    }
}
