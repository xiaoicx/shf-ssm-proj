package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.en.HouseImageEnum;
import com.atguigu.entity.*;
import com.atguigu.result.Result;
import com.atguigu.service.*;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * @className: HouseController
 * @Package: com.atguigu.controller

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Slf4j
@RestController
@RequestMapping("/house")
public class HouseController {

    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseUserService houseUserService;

    @Reference
    private UserFollowService userFollowService;

    /**
     * @Description 查询分页数据
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param pageNum
     * @param pageSize
     * @return Result
     */
    @PostMapping("/list/{pageNum}/{pageSize}")
    public Result pagenationList(@PathVariable("pageNum") Integer pageNum,
                                 @PathVariable("pageSize") Integer pageSize,
                                 @RequestBody HouseQueryVo houseQueryVo) {
        log.debug("查询分页的参数 pageNum={} pageSize={} houseQueryVo={}", pageNum, pageSize, houseQueryVo);
        PageInfo<HouseVo> houseVoPageInfo = houseService.selectHousePageInfoByHouseQueryVo(pageNum, pageSize, houseQueryVo);
        return Result.ok(houseVoPageInfo);
    }


    /**
     * @Description 根据房源id查询具体信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @return String
     */
    @GetMapping("/info/{id}")
    public Result showHouseInfo(@PathVariable("id") Long id, HttpSession session) {
        log.debug("查询的id={}", id);

        //1. 先查询房源id
        House house = houseService.selectById(id);

        //2. 根据房源中的小区id查询小区信息
        Community community = communityService.selectById(house.getCommunityId());

        //3. 根据房源查询id查询经纪人列表
        List<HouseBroker> houseBrokerList = houseBrokerService.selectHouseBrokerByHouseId(house.getId());

        //4. 根据房源id查询查询房源的普通图片
        List<HouseImage> houseImageList = houseImageService.selectHouseImageByType(house.getId(), HouseImageEnum.HOUSE_SOURCE.getType());

        //5. 根据房源id查询房东信息
        List<HouseUser> houseUserList = houseUserService.selectHouseUserByHouseId(house.getId());


        //6. 根据房源id查询当前用户是否关注房源
        //用户是否登录
        boolean isFollow = false;
        UserInfo userInfo = (UserInfo) session.getAttribute("USER_INFO");
        if (!ObjectUtils.isEmpty(userInfo)) {
            //根据用户id和房源id查询是否关注该房源
            UserFollow userFollow = userFollowService.selectByUserIdAndHouseId(userInfo.getId(), house.getId());
            isFollow = !ObjectUtils.isEmpty(userFollow);
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("house", house);
        map.put("community", community);
        map.put("houseBrokerList", houseBrokerList);
        map.put("houseImage1List", houseImageList);
        map.put("houseUserList", houseUserList);
        map.put("isFollow", isFollow);

        return Result.ok(map);
    }

}
