package com.atguigu.base;

import org.springframework.web.servlet.ModelAndView;

/**
 * @className: BaseController
 * @Package: com.atguigu.base

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public class BaseController {

    private static final String PAGE_SUCCESS = "common/successPage";

    public ModelAndView successPage(String msg) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName(PAGE_SUCCESS);
        mv.addObject("messagePage", msg);
        return mv;
    }

}
