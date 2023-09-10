package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.HouseUser;
import com.atguigu.result.Result;
import com.atguigu.service.HouseUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @className: HouseUserController
 * @Package: com.atguigu.controller

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Slf4j
@Controller
@RequestMapping("/houseUser")
public class HouseUserController extends BaseController {

    private static final String PAGE_CREATE = "lduser/create";
    private static final String PAGE_EDIT = "lduser/edit";

    @Reference
    private HouseUserService houseUserService;

    /**
     * @Description 跳转到新增页面
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseId
     * @return ModelAndView
     */
    @RequestMapping("/create/{houseId}")
    public ModelAndView createPage(@PathVariable("houseId") Long houseId) {

        log.debug("房源id={}", houseId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("houseId", houseId);
        mv.setViewName(PAGE_CREATE);
        return mv;
    }

    /**
     * @Description 保存新增的房东信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseUser
     * @return ModelAndView
     */
    @PostMapping("/save")
    public ModelAndView saveHouseUser(HouseUser houseUser) {

        log.debug("保存房东的信息 houseUser={}", houseUser);

        int rows = houseUserService.insert(houseUser);
        if (rows > 0) {
            return successPage("房东添加成功! 添加数量:" + rows + " 条");
        }
        return successPage("房东添加失败!");
    }

    /**
     * @Description 根据id删除房东信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @return Result
     */
    @RequestMapping("/delete/{houseId}/{id}")
    public String deleteHouseUser(@PathVariable("houseId") Long houseId, @PathVariable("id") Long id) {

        log.debug("删除的房东id={}", id);

        int rows = houseUserService.deleteById(id);

        return "redirect:/house/detail/" + houseId;
    }

    /**
     * @Description 跳转到修改页面
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @return String
     */
    @RequestMapping("/edit/{id}")
    public ModelAndView updateHouseUser(@PathVariable("id") Long id) {
        log.debug("修改的房东id={}", id);

        HouseUser houseUser = houseUserService.selectById(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("houseUser", houseUser);
        mv.setViewName(PAGE_EDIT);
        return mv;
    }

    /**
     * @Description 修改房东信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseUser
     * @return ModelAndView
     */
    @PostMapping("/update")
    public ModelAndView updateHouseUser(HouseUser houseUser) {
        log.debug("修改的房东信息 houseUser={}", houseUser);

        int rows = houseUserService.update(houseUser);
        if (rows > 0) {
            return successPage("修改成功! 修改数量: " + rows + "条");
        }
        return successPage("修改失败!");
    }
}
