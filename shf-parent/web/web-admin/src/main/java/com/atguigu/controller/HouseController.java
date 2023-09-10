package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.en.DictCodeEnum;
import com.atguigu.en.HouseImageEnum;
import com.atguigu.entity.*;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @className: HouseController
 * @Package: com.atguigu.controller

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Slf4j
@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {


    private static final String HOUSE_INDEX = "house/index";
    private static final String PAGE_CREATE = "house/create";
    private static final String PAGE_EDIT = "house/edit";
    private static final String PAGE_REDIRECT_INDEX = "redirect:/house";
    private static final String PAGE_DETAILS = "house/detail";

    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private HouseUserService houseUserService;


    /**
     * @Description 根据过滤参数分页查询房源信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param filters
     * @return String
     */
    @PreAuthorize("hasAuthority('house.show')")
    @RequestMapping
    public ModelAndView housePageIndex(@RequestParam Map<String, Object> filters) {

        ModelAndView mv = new ModelAndView();
        mv.setViewName(HOUSE_INDEX);

        //1. 分页查询房源信息
        PageInfo<House> housePageInfo = houseService.findPage(filters);
        mv.addObject("page", housePageInfo);

        //2. 搜索条件回显
        mv.addObject("filters", filters);

        //3. 查询字典信息后设置域
        this.commonQueryAttr(mv);

        return mv;
    }

    /**
     * @Description 跳转到创建页面并且设置域数据
     * @Since: 1.0.0
     * @Author xiaoqi
     * @return ModelAndView
     */
    @PreAuthorize("hasAuthority('house.create')")
    @RequestMapping("/create")
    public ModelAndView createHouseSource() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName(PAGE_CREATE);

        //查询字典数据
        this.commonQueryAttr(mv);
        return mv;
    }

    /**
     * @Description 保存新增的数据, 新增的数据默认为未发布
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param house
     * @return ModelAndView
     */
    @PreAuthorize("hasAuthority('house.create')")
    @PostMapping("/save")
    public ModelAndView saveHouseSource(House house) {

        log.debug("新增保存的数据为 house={}", house);

        //设置默认新增房源不发布
        house.setStatus(0);
        int effectRows = houseService.insert(house);

        if (effectRows > 0) {
            return successPage("新增成功! 新增数据: " + effectRows + " 条");
        }
        return successPage("新增失败!");
    }

    /**
     * @Description 跳转到修改页面
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @return String
     */
    @PreAuthorize("hasAuthority('house.edit')")
    @RequestMapping("/edit/{id}")
    public ModelAndView editHouseSource(@PathVariable("id") Long id) {

        log.debug("修改的房源id={}", id);

        if (id <= 0) {
            throw new RuntimeException("参数异常! 非法请求!!!");
        }

        ModelAndView mv = new ModelAndView();
        mv.setViewName(PAGE_EDIT);

        //查询字典信息
        this.commonQueryAttr(mv);

        //根据id查询房源信息
        House house = houseService.selectById(id);
        mv.addObject("house", house);

        return mv;
    }

    /**
     * @Description 修改房源信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param house
     * @return ModelAndView
     */
    @PreAuthorize("hasAuthority('house.edit')")
    @PostMapping("/update")
    public ModelAndView updateHouseSource(House house) {

        log.debug("更新的数据为 house={}", house);

        int effectRows = houseService.update(house);

        if (effectRows > 0) {
            return successPage("修改成功! 修改条数: " + effectRows + " 条");
        }
        return successPage("修改失败!");
    }

    /**
     * @Description 根据房源id删除房源, 逻辑删除
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @return String
     */
    @PreAuthorize("hasAuthority('house.delete')")
    @RequestMapping("/delete/{id}")
    public String mdeleteHouseSource(@PathVariable("id") Long id) {

        log.debug("需要删除的小区id={}", id);

        int rows = houseService.deleteById(id);
        log.debug("删除条数 rows={}", rows);

        return PAGE_REDIRECT_INDEX;
    }

    /**
     * @Description 根据房源的id发布或取消发布房源
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @param status
     * @return String
     */
    @PreAuthorize("hasAuthority('house.publish')")
    @RequestMapping("/publish/{id}/{status}")
    public String publishHouse(@PathVariable("id") Long id, @PathVariable("status") Short status) {

        log.debug("需要发布的房源id={} status={}", id, status);

        int rows = houseService.publishHouseById(id, status);
        log.debug("发布房源数 rows={}", rows);

        return PAGE_REDIRECT_INDEX;
    }

    @PreAuthorize("hasAuthority('house.show')")
    @RequestMapping("/detail/{id}")
    public ModelAndView detailHouse(@PathVariable("id") Long id) {
        log.debug("详情房源id={}", id);

        ModelAndView mv = new ModelAndView();
        mv.setViewName(PAGE_DETAILS);

        //1. 根据id查询房源信息
        House house = houseService.selectById(id);
        mv.addObject("house", house);


        //2. 根据房源id中的小区id查询小区信息
        Community community = communityService.selectById(house.getCommunityId());
        mv.addObject("community", community);

        //3. 查询房源图片列表
        List<HouseImage> houseSourceImageList = houseImageService.selectHouseImageByType(id, HouseImageEnum.HOUSE_SOURCE.getType());
        mv.addObject("houseImage1List", houseSourceImageList);

        //4. 查询房产图片列表
        List<HouseImage> housePropertyImageList = houseImageService.selectHouseImageByType(id, HouseImageEnum.HOUSE_PROPERTY.getType());
        mv.addObject("houseImage2List", housePropertyImageList);

        //5. 查询房源经纪人信息
        List<HouseBroker> houseBrokerList = houseBrokerService.selectHouseBrokerByHouseId(id);
        mv.addObject("houseBrokerList", houseBrokerList);

        //6. 查询房源房东信息
        List<HouseUser> houseUserList = houseUserService.selectHouseUserByHouseId(id);
        mv.addObject("houseUserList", houseUserList);

        return mv;
    }

    /**
     * @Description 字典公共查询数据后设置域
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param mv
     * @return void
     */
    private void commonQueryAttr(@NonNull ModelAndView mv) {
        //3. 所有小区
        List<Community> communityList = communityService.findAll();
        mv.addObject("communityList", communityList);

        //4. 所有户型
        List<Dict> houseTypeList = dictService.findAreaListByCode(DictCodeEnum.HOUSETYPE.getDictCode());
        mv.addObject("houseTypeList", houseTypeList);

        //5. 所有楼层
        List<Dict> floorList = dictService.findAreaListByCode(DictCodeEnum.FLOOR.getDictCode());
        mv.addObject("floorList", floorList);

        //6. 所有建筑结构
        List<Dict> buildStructureList = dictService.findAreaListByCode(DictCodeEnum.BUILDSTRUCTURE.getDictCode());
        mv.addObject("buildStructureList", buildStructureList);

        //7. 所有朝向
        List<Dict> directionList = dictService.findAreaListByCode(DictCodeEnum.DIRECTION.getDictCode());
        mv.addObject("directionList", directionList);

        //8. 所有装修情况
        List<Dict> decorationList = dictService.findAreaListByCode(DictCodeEnum.DECORATION.getDictCode());
        mv.addObject("decorationList", decorationList);

        //所有房屋用途
        List<Dict> houseUseList = dictService.findAreaListByCode(DictCodeEnum.HOUSEUSE.getDictCode());
        mv.addObject("houseUseList", houseUseList);
    }

}
