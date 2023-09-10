package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Community;

/**
 * @className: CommunityService
 * @Package: com.atguigu.service

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface CommunityService extends BaseService<Community> {

    /**
     * @Description 根据id查询小区信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @return Community
     */
    Community getCommunityById(Long id);


    /**
     * @Description 根据id删除小区信息, 删除成功返回删除记录数, 失败抛出异常
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @return Integer
     */
    Integer deleteCommunityById(Long id);

}
