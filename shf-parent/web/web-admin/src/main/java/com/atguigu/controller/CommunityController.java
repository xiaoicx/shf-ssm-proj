package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.en.DictCodeEnum;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.atguigu.service.CommunityService;
import com.atguigu.service.DictService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @className: CommunityController
 * @Package: com.atguigu.controller

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Slf4j
@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {

    private static final String PAGE_INDEX = "community/index";
    private static final String PAGE_CREATE = "community/create";
    private static final String PAGE_EDIT = "community/edit";

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    /**
     * @Description 首页数据显示和跳转
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param filters
     * @return ModelAndView
     */
    @RequestMapping
    public ModelAndView index(@RequestParam Map<String, Object> filters) {

        log.debug("过滤的参数 filters:{}", filters);

        if (ObjectUtils.isEmpty(filters.get("areaId"))) {
            filters.put("areaId", 0);
        }

        if (ObjectUtils.isEmpty(filters.get("plateId"))) {
            filters.put("plateId", 0);
        }

        //分页查询数据
        PageInfo<Community> communityPageInfo = communityService.findPage(filters);
        //查询城市区域
        List<Dict> areaListByCode = dictService.findAreaListByCode(DictCodeEnum.BEIJING.getDictCode());

        ModelAndView mv = new ModelAndView();
        mv.setViewName(PAGE_INDEX);

        //设置分页数据
        mv.addObject("page", communityPageInfo);
        //设置过滤条件
        mv.addObject("areaList", areaListByCode);
        //设置回显过滤条件
        mv.addObject("filters", filters);
        return mv;
    }


    /**
     * @Description 跳转到新增页面
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param
     * @return ModelAndView
     */
    @RequestMapping("/create")
    public ModelAndView createPage() {
        //查询城市区域信息
        List<Dict> areaListByCode = dictService.findAreaListByCode(DictCodeEnum.BEIJING.getDictCode());

        ModelAndView mv = new ModelAndView();
        mv.setViewName(PAGE_CREATE);
        mv.addObject("areaList", areaListByCode);

        return mv;
    }

    /**
     * @Description 保存小区信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param community
     * @return ModelAndView
     */
    @RequestMapping("/save")
    public ModelAndView saveCommunity(Community community) {

        log.debug("新增的小区信息 community={}", community);

        int effectRows = communityService.insert(community);

        if (effectRows > 0) {
            return successPage("保存成功! 新增记录: " + effectRows + "条");
        }
        return successPage("保存失败!");
    }

    /**
     * @Description 跳转到编辑页面
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param
     * @return String
     */
    @RequestMapping("/edit/{id}")
    public ModelAndView editPage(@PathVariable("id") Long id) {

        log.debug("查询小区的id={}", id);

        //查询小区信息
        Community community = communityService.getCommunityById(id);
        log.debug("查询到的小区信息: community={}", community);

        //查询区域信息
        List<Dict> areaListByCode = dictService.findAreaListByCode(DictCodeEnum.BEIJING.getDictCode());

        ModelAndView mv = new ModelAndView();
        mv.setViewName(PAGE_EDIT);
        mv.addObject("community", community);
        mv.addObject("areaList", areaListByCode);

        return mv;
    }

    /**
     * @Description 更新小区信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param community
     * @return ModelAndView
     */
    @PostMapping("/update")
    public ModelAndView updateCommunity(Community community) {

        log.debug("更新的小区信息 community={}", community);

        //跟新信息
        int effectRows = communityService.update(community);
        if (effectRows > 0) {
            return successPage("跟新成功! 跟新条数: " + effectRows + "条");
        }

        return successPage("跟新失败!");
    }

    /**
     * @Description 根据小区id删除小区, 失败抛出异常
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @return ModelAndView
     */
    @RequestMapping("/delete/{id}")
    public String deleteCommunity(@PathVariable("id") Long id) {

        log.debug("删除小区  小区id={}", id);

        communityService.deleteCommunityById(id);

        return "redirect:/community";

    }

}
