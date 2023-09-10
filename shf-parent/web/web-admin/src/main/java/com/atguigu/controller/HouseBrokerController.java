package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.AdminService;
import com.atguigu.service.HouseBrokerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.data.Id;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @className: HouseBrokerController
 * @Package: com.atguigu.controller

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Slf4j
@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController extends BaseController {

    private static final String PAGE_CREATE = "broker/create";
    private static final String PAGE_EDIT = "broker/edit";

    @Reference
    private AdminService adminService;

    @Reference
    private HouseBrokerService houseBrokerService;


    /**
     * @Description 跳转到添加经纪人信息的页面
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseId
     * @return String
     */
    @RequestMapping("/create/{houseId}")
    public ModelAndView createPage(@PathVariable("houseId") Long houseId) {
        log.debug("查询的houseid={}", houseId);

        ModelAndView mv = new ModelAndView();
        mv.setViewName(PAGE_CREATE);
        mv.addObject("houseId", houseId);

        //根据houseId查询其他用户需要排除已经添加的经纪人
        List<Admin> adminList = adminService.selectOtherHouseBrokerByHouseId(houseId, null);
        mv.addObject("adminList", adminList);

        return mv;
    }

    /**
     * @Description 保存经纪人信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseBroker
     * @return ModelAndView
     */
    @PostMapping("/save")
    public ModelAndView saveHouseBroker(HouseBroker houseBroker) {
        log.debug("保存的经纪人信息: houseBroker={}", houseBroker);

        //1. 根据brokerId查询admin表中的信息
        Admin admin = adminService.selectById(houseBroker.getBrokerId());

        //2. 保存经纪人信息
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        int rows = houseBrokerService.insert(houseBroker);

        if (rows > 0) {
            return successPage("保存成功! 保存数量: " + rows + "条");
        }
        return successPage("保存失败!");
    }

    /**
     * @Description 跳转到编辑页面
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseId
     * @param id
     * @return ModelAndView
     */
    @RequestMapping("/edit/{houseId}/{id}")
    public ModelAndView editPage(@PathVariable("houseId") Long houseId, @PathVariable("id") Long id) {

        log.debug("修改的id={}", id);

        //查询未添加的用户, 包括当前跟新的用户
        List<Admin> adminList = adminService.selectOtherHouseBrokerByHouseId(houseId, id);

        ModelAndView mv = new ModelAndView();
        mv.addObject("adminList", adminList);
        mv.addObject("houseId", houseId);
        mv.addObject("oldId", id);
        mv.setViewName(PAGE_EDIT);

        return mv;
    }


    /**
     * @Description 根据旧的经纪人id修改为行的经纪人信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseBroker
     * @param oldId
     * @return ModelAndView
     */
    @PostMapping("/update")
    public ModelAndView updateBroker(HouseBroker houseBroker, @RequestParam("oldId") Long oldId) {

        log.debug("修改的用户信息 houseBroker={}", houseBroker);

        //1. 如果当前经纪人id和旧的经纪人id一致则不更改
        HouseBroker broker = houseBrokerService.selectById(oldId);
        if (broker.getBrokerId().equals(houseBroker.getBrokerId())) {
            return successPage("当前经纪人与修改的经纪人一致无需修改!");
        }

        //2. 查询新经纪人信息
        Admin admin = adminService.selectById(houseBroker.getBrokerId());

        //3. 根据旧的经纪人修改为新的经纪人
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());

        //4. 根据旧的broker id修改成新的经纪人
        int rows = houseBrokerService.updateHouseBrokerByOldBrokerId(houseBroker, oldId);

        if (rows > 0) {
            return successPage("修改成功! 修改数:" + rows + " 条");
        }
        return successPage("修改失败!");
    }


    /**
     * @Description 根据houseId和id删除经纪人
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseId
     * @param brokerId
     * @return String
     */
    @RequestMapping("/delete/{houseId}/{id}")
    public String deleteHouseBroker(@PathVariable("houseId") Long houseId, @PathVariable("id") Long brokerId) {
        log.debug("删除的 brokerid={} houseid={}", brokerId, houseId);

        int rows = houseBrokerService.deleteByHouseIdAndBrikerId(houseId, brokerId);
        log.debug("删除经纪人数量 rows={}", rows);

        return "redirect:/house/detail/" + houseId;
    }

}
