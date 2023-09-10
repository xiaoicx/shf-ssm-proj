package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.service.HouseService;
import com.atguigu.service.UserFollowService;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @className: UserFollowController
 * @Package: com.atguigu.controller

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Slf4j
@RestController
@RequestMapping("/userFollow")
public class UserFollowController {

    @Reference
    private UserFollowService userFollowService;

    @Reference
    private HouseService houseService;

    /**
     * @Description 关注房源
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @param session
     * @return Result
     */
    @GetMapping("/auth/follow/{id}")
    public Result houseFollow(@PathVariable("id") Long id, HttpSession session) {

        //1. 在session中获取登录的用户
        UserInfo userInfo = (UserInfo) session.getAttribute("USER_INFO");

        log.debug("关注房源的id={} session中的用户userInfo={}", id, userInfo);

        //1. 关注房源
        UserFollow userFollow = new UserFollow();
        userFollow.setUserId(userInfo.getId());
        userFollow.setHouseId(id);

        int rows = userFollowService.insert(userFollow);

        return Result.ok(rows);
    }


    /**
     * @Description 分页查询查询关注列表
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param pageNum
     * @param pageSize
     * @return String
     */
    @GetMapping("/auth/list/{pageNum}/{pageSize}")
    public Result pageList(@PathVariable("pageNum") int pageNum,
                           @PathVariable("pageSize") int pageSize, HttpSession session) {
        log.debug("分页查询关注列表 pageNum={}  pageSize={}", pageNum, pageSize);

        //查询登录的用户
        UserInfo userInfo = (UserInfo) session.getAttribute("USER_INFO");
        PageInfo<HouseVo> houseVoPageInfo = null;
        if (!ObjectUtils.isEmpty(userInfo)) {

            //查询用户的关注列表
            List<UserFollow> userFollowList = userFollowService.selectListByUserId(userInfo.getId());

            //根据关注列表手机需要查询的房源id
            List<Long> houseListId = null;
            if (userFollowList.size() > 0) {
                houseListId = userFollowList.stream()
                        .map(UserFollow::getHouseId)
                        .collect(Collectors.toList());
            }

            //根据房源id分页查询具体信息
            houseVoPageInfo = houseService.selectHouseVoListByUserFollowList(pageNum, pageSize, houseListId);
        }

        return Result.ok(houseVoPageInfo);
    }


    /**
     * @Description 根据关注的列表id查询具体的房源信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param houseId
     * @param session
     * @return Result
     */
    @GetMapping("/auth/cancelFollow/{houseId}")
    public Result houseUnFollow(@PathVariable("houseId") Long houseId, HttpSession session) {

        UserInfo userInfo = (UserInfo) session.getAttribute("USER_INFO");
        log.debug("需要取消关注的houseId={} userInfo={}", houseId, userInfo);

        int rows = userFollowService.deleteByUserIdAndHouseId(userInfo.getId(), houseId);

        return Result.ok(rows);
    }

}
