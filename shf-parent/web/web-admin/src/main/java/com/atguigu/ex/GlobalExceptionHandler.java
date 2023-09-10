package com.atguigu.ex;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @className: GlobalExceptionHandler
 * @Package: com.atguigu.ex

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    public ModelAndView ExceptionPage(Exception exception) {

        ModelAndView mv = new ModelAndView();
        mv.addObject("ex", exception.getMessage());
        mv.setViewName("common/error");
        return mv;
    }
}
